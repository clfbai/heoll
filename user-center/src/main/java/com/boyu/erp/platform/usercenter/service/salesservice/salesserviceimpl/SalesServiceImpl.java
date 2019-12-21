package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.mq.order.OrderAdd;
import com.boyu.erp.platform.usercenter.entity.mq.order.SrcOrderAdd;
import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitOwner;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PscDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.shop.ShopMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitOwnerMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaService;
import com.boyu.erp.platform.usercenter.service.salesservice.SalesService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcService;
import com.boyu.erp.platform.usercenter.vo.purchase.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname SalesServiceImpl
 * @Description TODO
 * @Date 2019/11/29 10:07
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SlcTypeMapper slcTypeMapper;
    @Autowired
    private PsaService psaService;
    @Autowired
    private SysUnitMapper sysUnitMapper;
    @Autowired
    private PscDtlMapper pscDtlMapper;
    @Autowired
    private SlcService slcService;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private SrcTypeMapper srcTypeMapper;
    @Autowired
    private SysUnitOwnerMapper sysUnitOwnerMapper;
    @Autowired
    private RtcDtlMapper rtcDtlMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private SrcService srcService;
    @Autowired
    private SrcMapper srcMapper;

    /**
     * B2B普通订单  接收处理  生成-确认-审核
     *
     * @param add
     * @return
     */
    @Override
    public int creatSlc(OrderAdd add, SysUser user) throws Exception {
        this.init(add);
        //销售合同类别默认用补货接收（参数控制）
        SysParameter sysPa = sysParameterMapper.findById("B2B_SLC_TYPE");
        if (sysPa == null) {
            //报异常

        }
        if(StringUtils.isEmpty(sysPa.getParmVal())){
            //报异常

        }
        //通过门店获取采购商id
        Shop shop = shopMapper.selectByPrimaryKey(Long.valueOf(add.getShopId()));
        if(shop==null){
            //报异常

        }
        //通过参数的值去获取填充的值
        PscAutoVo pscAuto = slcTypeMapper.selectByPscAuto(sysPa.getParmVal());
        //初始化对象
        PscVo vo = new PscVo();
        //将类别同步到购销合同中
        BeanUtils.copyProperties(pscAuto, vo);
        vo.setUnitId(Long.valueOf(add.getSupplierId()));
        vo.setVenderId(add.getSupplierId());
        vo.setVendeeId(shop.getOwnerId().toString());
        vo.setVdeWarehId(add.getShopId());
        //查询协议 获取协议控制方与采购商是否介入
        OptionByPsaVo psa = psaService.selectByPsaByVdr(new OptionByPsaVo(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
        if(psa!=null){
            vo.setVdeInvd(psa.getInte());
        }else{
            //没有协议时报错


        }
        //根绝订单类型不同，去获取发货仓库id（可能查出多条或者没有）
        List<SysUnit> warehList = sysUnitMapper.findWarehByOrder(add.getOrderType(),add.getSupplierId());
        if(!warehList.isEmpty()){
            vo.setVdrWarehId(warehList.get(0).getUnitId()+"");
        }
        //计算商品明细
        if(!add.getItems().isEmpty()){
            //查询系统默认的税率/可退率/市场单价
            List<PscDtlVo> pscDtlList = pscDtlMapper.findByOrderItem(add.getItems());
            vo.setPscDtlList(pscDtlList);
        }
        //创建合同
        String pscNum = slcService.insertSlc(vo,user);
        //确认合同
        slcService.confirm(slcMapper.selectByOnly(new PscVo(pscNum)),user);
        //审核合同 订单类型为2   代表2B推送
        slcService.examine(slcMapper.selectByOnly(new PscVo(pscNum)),user, 2);

        return 1;
    }

    /**
     * B2B退货订单  接收处理  生成-确认-审核
     * @param add
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int creatSrc(SrcOrderAdd add, SysUser user) throws Exception {
        //初始化
        this.init(add);
        //查询退销合同类别
        SysParameter sysPa = sysParameterMapper.findById("B2B_SRC_TYPE");
        if (sysPa == null) {
            //报异常

        }
        if(StringUtils.isEmpty(sysPa.getParmVal())){
            //报异常

        }
        //通过门店获取采购商id
        Shop shop = shopMapper.selectByPrimaryKey(Long.valueOf(add.getShopId()));
        if(shop==null){
            //报异常

        }
//        //通过shopId与供货商编号查询出供应商id
//        SysUnitOwner owner = sysUnitOwnerMapper.queryObjByNumAndOwner(add.getSupplierCode(),Long.valueOf(shop.getOwnerId()));
//        if(owner==null){
//
//        }
        SysUnit unit = sysUnitMapper.selectByUnitCode(add.getSupplierCode());
        //获取退销合同类别
        String val[] = sysPa.getParmVal().split(";");
        String srcType = "";
        for (String ss:val) {
            if(Integer.valueOf(ss.substring(ss.length()-1)).equals(add.getType())){
                srcType = ss.substring(0,ss.indexOf(":"));
            }
        }

        if(StringUtils.isEmpty(srcType)){
            //报异常
        }
        //通过参数获取自动填充的值
        RtcAutoVo rtcAuto = srcTypeMapper.selectByRtcAuto(srcType);
        //初始化对象
        PrcVo vo = new PrcVo();
        //将类别同步到购销合同中
        BeanUtils.copyProperties(rtcAuto,vo);
        vo.setTtlVal(add.getAmount());
        vo.setUnitId(unit.getUnitId());
        vo.setVenderId(unit.getUnitId());
        vo.setVendeeId(shop.getOwnerId());
        vo.setVdeWarehId(Long.valueOf(add.getShopId()));
        //查询协议 获取协议控制方与采购商是否介入
        OptionByPsaVo psa = psaService.selectByPsaByVdr(new OptionByPsaVo(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
        if(psa!=null){
            vo.setVdeInvd(psa.getInte());
        }else{
            //没有协议时报错


        }
        //计算商品明细
        if(!add.getItems().isEmpty()){
            //查询商品的存储仓库
            Long vdrWarehId = sysUnitMapper.fidnByProdLine(add.getItems().get(0).getProductSKUId());
            if(!StringUtils.isEmpty(vdrWarehId)){
                vo.setVdrWarehId(vdrWarehId);
            }
            //查询系统默认的税率/可退率/市场单价
            List<RtcDtlVo> rtcDtlList = rtcDtlMapper.findByOrderItem(add.getItems());
            vo.setRtcDtlList(rtcDtlList);
        }

        //创建退销合同
        String rtcNum = srcService.insertSrc(vo,user);

        //确认退销合同
        srcService.confirm(srcMapper.selectByOnly(new PrcVo(rtcNum)), user);

        //审核退销合同
        srcService.examine(srcMapper.selectByOnly(new PrcVo(rtcNum)), user , 2);

        return 0;
    }

    /**
     * 将接收的对象里面字段初始化（去掉HM_）
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public Object init(Object obj) throws Exception {
        Field[] field = obj.getClass().getDeclaredFields();
        for (Field f : field) {
            f.setAccessible(true);
            if (f.get(obj) != null) {
                if (f.getType().getName().contains("String")) {
                    f.set(obj, this.split(f.get(obj).toString()));
                }
                if (f.getType().getName().contains("List")) {
                    List<Object> list = (List<Object>) f.get(obj);
                    for (Object object : list) {
                        init(object);
                    }
                }
            } else {
                if (f.getType().getName().contains("String")) {
                    f.set(obj, "");
                }
                if (f.getType().getName().contains("BigDecimal")) {
                    f.set(obj, new BigDecimal(0));
                }
            }
        }
        return obj;
    }

    /**
     * 截取正确格式的数据
     *
     * @param sp
     * @return
     */
    private Object split(String sp) {
        //判断是否存在下划线
        int num = sp.indexOf("_");
        if (num != -1) {
            //截取返回
            sp = sp.substring(num + 1);
        }
        return sp;
    }

}
