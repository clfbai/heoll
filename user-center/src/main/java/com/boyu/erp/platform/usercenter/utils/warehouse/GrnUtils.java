package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.GrnStbDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.system.ColorService;
import com.boyu.erp.platform.usercenter.service.system.SpecService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述:
 *
 * @Description 入库单工具类
 * @auther: CLF
 * @date: 2019/12/6 14:05
 */
@Slf4j
@Component
public class GrnUtils {
    @Autowired
    private ProdClsService prodClsService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SpecService specService;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private SysParameterService parameterService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StbService stbService;









    //有原始单信息的库存单
    public void isStbDtlTows(GrnStbDtlModel stbDtl) throws ServiceException {
        Product product = stbDtl.getProdId() == null ? productService.queryProVoByProCode(stbDtl.getProdCode()) : productService.queryProductById(stbDtl.getProdId());
        if (product == null) {
            throw new ServiceException(JsonResultCode.FAILURE, "商品不存在");
        }
        if (StringUtils.NullEmpty(stbDtl.getRcvMode()) || StringUtils.NullEmpty(stbDtl.getType())) {
            throw new ServiceException(JsonResultCode.FAILURE, "入库方式为空、或操作类型为空");
        }
        //原有明细
        ProdCls prodCls2Id = new ProdCls();
        prodCls2Id.setProdClsId(product.getProdClsId());
        ProdCls prodCls = prodClsService.queryProdClsById(prodCls2Id);
        Stb stb = stbService.getStbByPk(new Stb(stbDtl.getUnitId(), stbDtl.getGrnNum()));
        stbDtl.setProdId(product.getProdId());
        stbDtl.setStbNum(stbDtl.getGrnNum());
        String stbNum = stbDtl.getGrnNum() == null ? "" : stbDtl.getGrnNum();
        List<StbDtl> stbDtls = stbDtlService.queryStbDtlsByNumAndUnit(stbNum, stbDtl.getUnitId());
        if (CollectionUtils.isNotEmpty(stbDtls) && "ADD".equalsIgnoreCase(stbDtl.getType())) {
            for (StbDtl d : stbDtls) {
                if (d.getProdId().equals(product.getProdId())) {
                    throw new ServiceException(JsonResultCode.FAILURE, "原始单据中商品已存在");
                }
            }
        }
        //从采购来模块来源的入库单
        if (stbDtl.getRcvMode().equalsIgnoreCase("RTRT") || stbDtl.getRcvMode().equalsIgnoreCase("SLRT")
                || stbDtl.getRcvMode().equalsIgnoreCase("PURC") || stbDtl.getRcvMode().equalsIgnoreCase("DIPU")
                || stbDtl.getRcvMode().equalsIgnoreCase("TRA")) {
            if (stb == null) {
                throw new ServiceException("403", "入库方式对应原始单据不存在");
            }
            //明细
            CommonDtl commonDtl = getVidef(stb.getSrcDocType(), stb.getCntrNum(), product.getProdId());
            setMode(stbDtl, commonDtl);
        } else {
            //这里是不在原单信息直接增加的
            stbDtl.setUnitPrice(prodCls.getLstPrice());
            stbDtl.setMkUnitPrice(prodCls.getLstPrice());
            stbDtl.setDiscRate(new BigDecimal("1"));
            stbDtl.setFnlPrice(stbDtl.getUnitPrice().multiply(stbDtl.getDiscRate()));
            SysParameter parameter = parameterService.findByParameter("DEFAULT_TAX_RATE");
            String taxRate = parameter == null ? "0" : parameter.getParmVal() == null ? "0" : parameter.getParmVal();
            stbDtl.setTaxRate(new BigDecimal(taxRate));
            stbDtl.setExpdQty(new BigDecimal(99));
        }
        stbDtl.setProdId(product.getProdId());
        stbDtl.setProdClsId(prodCls.getProdClsId());
        stbDtl.setProdCode(product.getProdCode());
        stbDtl.setProdName(prodCls.getProdName());
        stbDtl.setInputCode(prodCls.getInputCode());
        //根据code和type查询sysCodeDtl对象
        SysCodeDtl sysCodeDtluom = sysCodeDtlService.queryCodeDtlByCodeType(prodCls.getUom(), DeliveryDefineUtil.CODE_UOM);
        stbDtl.setUomName(sysCodeDtluom.getDescription());
        Color color2Id = new Color();
        color2Id.setColorId(product.getColorId());
        Color color = colorService.getColorById(color2Id);
        if (color != null) {
            stbDtl.setColorName(color.getColorName());
        }
        //版型名
        stbDtl.setEditionCp(prodCls.getEdition());
        SysCodeDtl sysCodeDtledition = sysCodeDtlService.queryCodeDtlByCodeType(prodCls.getEdition(), DeliveryDefineUtil.CODE_EDITION);
        if (sysCodeDtledition != null) {
            stbDtl.setEditionCp(sysCodeDtledition.getDescription());
        }
        Spec spec2Id = new Spec();
        spec2Id.setSpecId(prodCls.getSpecId().longValue());
        Spec spec = specService.getSpecById(spec2Id);
        if (spec != null) {
            stbDtl.setSpecName(spec.getSpecName());
        }
    }

    /**
     * 功能描述: 后期手动赋值
     * 假设原始单据单价能改加上单价
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/6 21:18
     */
    private void setMode(GrnStbDtlModel stbDtl, CommonDtl commonDtl) {
        //预期数量
        stbDtl.setExpdQty(commonDtl.getQty());
        BeanUtils.copyProperties(commonDtl, stbDtl);
    }


    /**
     * 功能描述: 新增时验证原单信息是否存在商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/6 20:47
     */
    public CommonDtl getVidef(String docType, String cntrNum, Long prodId) throws ServiceException {

        List<CommonDtl> list = purchaseService.selectBillInfo(docType, cntrNum);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("403", "原始单据中不存在任何商品信息");
        }
        List<CommonDtl> c = list.stream().filter(s -> s.getProdId().equals(prodId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(c)) {
            throw new ServiceException("403", "原始单据中不存在当前选择商品信息");
        }
        return c.get(0);
    }


}
