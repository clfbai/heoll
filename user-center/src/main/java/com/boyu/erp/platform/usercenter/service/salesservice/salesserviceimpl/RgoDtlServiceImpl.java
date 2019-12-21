package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.UnitProdCls;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.purchase.PscDtl;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcDtl;
import com.boyu.erp.platform.usercenter.entity.sales.Rgo;
import com.boyu.erp.platform.usercenter.entity.sales.RgoDtl;
import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.UnitProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsaMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.*;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoService;
import com.boyu.erp.platform.usercenter.service.salesservice.SpnScpService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.purchase.PnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoProdDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: RgoDtlServiceImpl
 * @description: 调配单明细接口实现
 * @author: wz
 * @create: 2019-10-7 10:36
 */
@Slf4j
@Service
@Transactional
public class RgoDtlServiceImpl implements RgoDtlService {

    @Autowired
    private RgoMapper rgoMapper;
    @Autowired
    private RgoDtlMapper rgoDtlMapper;
    @Autowired
    private RtcDtlService rtcDtlService;
    @Autowired
    private SpnScpService spnScpService;
    @Autowired
    private RgoTypeMapper rgoTypeMapper;
    @Autowired
    private SlcTypeMapper slcTypeMapper;
    @Autowired
    private UnitProdClsMapper unitProdClsMapper;

    //查询调配单数据
    @Override
    public List<RgoDtl> selectByRgo(RgoVo vo) {
        return rgoDtlMapper.selectByRgo(vo);
    }

    /**
     * 清除明细并更新主表数据
     * @param list
     * @return
     */
    @Override
    public int deleteRgoDtl(List<RgoDtl> list) {
        rgoDtlMapper.deleteByRgo(new RgoDtl(list.get(0).getUnitId(),list.get(0).getRgoNum()));
        return rgoMapper.updateByDeleteDtl(new RgoVo(list.get(0).getUnitId(),list.get(0).getRgoNum()));
    }

    /**
     * 通过调配单号查询明细
     * @param vo
     * @return
     */
    @Override
    public List<RgoDtlVo> selectByRgoDtl(RgoDtlVo vo) {
        return rgoDtlMapper.selectByRgoDtl(vo);
    }

    /**
     * 添加调配单明细
     * @param vo
     * @return
     */
    @Override
    public int insertRgoDtl(RgoDtlVo vo) {
        Rgo rgo = this.rgo(vo);

        //设置序号 默认为0
        Long num = 0L;
        RgoDtl dtl = rgoDtlMapper.findByRgoDtl(new RgoDtl(rgo.getUnitId(),rgo.getRgoNum()));
        if (dtl != null) {
            if (dtl.getLineNum() != null) {
                num = dtl.getLineNum();
            }
        }
        //计算调配单需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlSrcVal = new BigDecimal("0");
        BigDecimal ttlSrcTax = new BigDecimal("0");
        BigDecimal ttlSrcMkv = new BigDecimal("0");
        BigDecimal ttlDestVal = new BigDecimal("0");
        BigDecimal ttlDestTax = new BigDecimal("0");
        BigDecimal ttlDestMkv = new BigDecimal("0");
        if (vo.getList() != null && vo.getList().size() > 0) {
            for (int i = 0; i < vo.getList().size(); i++) {
                RgoDtlVo rDtl = vo.getList().get(i);
                //设置行号 排号
                rDtl.setLineNum(num + i + 1);
                rDtl.setRowNum(num + i + 1);

                ttlQty = ttlQty.add(rDtl.getQty());
                ttlSrcVal = ttlSrcVal.add(rDtl.getPuVal());
                ttlSrcTax = ttlSrcTax.add(rDtl.getPuTax());
                ttlSrcMkv = ttlSrcMkv.add(rDtl.getPuMkv());
                ttlDestVal = ttlDestVal.add(rDtl.getSlVal());
                ttlDestTax = ttlDestTax.add(rDtl.getSlTax());
                ttlDestMkv = ttlDestMkv.add(rDtl.getSlMkv());
            }
            try {
                rgoDtlMapper.insertList(vo.getList());
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }
        }else{
            vo.setLineNum(num + 1);
            vo.setRowNum(num + 1);
            ttlQty = ttlQty.add(vo.getQty());
            ttlSrcVal = ttlSrcVal.add(vo.getPuVal());
            ttlSrcTax = ttlSrcTax.add(vo.getPuTax());
            ttlSrcMkv = ttlSrcMkv.add(vo.getPuMkv());
            ttlDestVal = ttlDestVal.add(vo.getSlVal());
            ttlDestTax = ttlDestTax.add(vo.getSlTax());
            ttlDestMkv = ttlDestMkv.add(vo.getSlMkv());
            try {
                rgoDtlMapper.insertSelective(vo);
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }
        }
        return this.updateRgo(rgo, ttlQty, ttlSrcVal, ttlSrcTax, ttlSrcMkv, ttlDestVal, ttlDestTax, ttlDestMkv);
    }

    /**
     * 修改调配单明细
     * @param vo
     * @return
     */
    @Override
    public int updateRgoDtl(RgoDtlVo vo) {
        Rgo rgo = this.rgo(vo);
        //计算调配单需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlSrcVal = new BigDecimal("0");
        BigDecimal ttlSrcTax = new BigDecimal("0");
        BigDecimal ttlSrcMkv = new BigDecimal("0");
        BigDecimal ttlDestVal = new BigDecimal("0");
        BigDecimal ttlDestTax = new BigDecimal("0");
        BigDecimal ttlDestMkv = new BigDecimal("0");
        if (vo.getList() != null && vo.getList().size() > 0) {
            for (RgoDtlVo rDtl : vo.getList()) {
                RgoDtl rd = rgoDtlMapper.selectByPrimaryKey(new RgoDtl(rDtl.getUnitId(),rDtl.getRgoNum(),rDtl.getProdId()));

                //计算当前
                rgo.setTtlQty(rgo.getTtlQty().subtract(rd.getQty()).add(rDtl.getQty()));
                rgo.setTtlSrcVal(rgo.getTtlSrcVal().subtract(rd.getPuVal()).add(rDtl.getPuVal()));
                rgo.setTtlSrcTax(rgo.getTtlSrcTax().subtract(rd.getPuTax()).add(rDtl.getPuTax()));
                rgo.setTtlSrcMkv(rgo.getTtlSrcMkv().subtract(rd.getPuMkv()).add(rDtl.getPuMkv()));
                rgo.setTtlDestVal(rgo.getTtlDestVal().subtract(rd.getSlVal()).add(rDtl.getSlVal()));
                rgo.setTtlDestTax(rgo.getTtlDestTax().subtract(rd.getSlTax()).add(rDtl.getSlTax()));
                rgo.setTtlDestMkv(rgo.getTtlDestMkv().subtract(rd.getSlMkv()).add(rDtl.getSlMkv()));
            }
            rgoDtlMapper.updateList(vo.getList());
        } else {
            //查询之前的记录
            RgoDtl rd = rgoDtlMapper.selectByPrimaryKey(new RgoDtl(vo.getUnitId(),vo.getRgoNum(),vo.getProdId()));

            //计算当前
            rgo.setTtlQty(rgo.getTtlQty().subtract(rd.getQty()).add(vo.getQty()));
            rgo.setTtlSrcVal(rgo.getTtlSrcVal().subtract(rd.getPuVal()).add(vo.getPuVal()));
            rgo.setTtlSrcTax(rgo.getTtlSrcTax().subtract(rd.getPuTax()).add(vo.getPuTax()));
            rgo.setTtlSrcMkv(rgo.getTtlSrcMkv().subtract(rd.getPuMkv()).add(vo.getPuMkv()));
            rgo.setTtlDestVal(rgo.getTtlDestVal().subtract(rd.getSlVal()).add(vo.getSlVal()));
            rgo.setTtlDestTax(rgo.getTtlDestTax().subtract(rd.getSlTax()).add(vo.getSlTax()));
            rgo.setTtlDestMkv(rgo.getTtlDestMkv().subtract(rd.getSlMkv()).add(vo.getSlMkv()));

            rgoDtlMapper.updateByPrimaryKeySelective(vo);
        }
        return rgoMapper.updateByPrimaryKeySelective(rgo);
    }

    /**
     * 删除调配单明细
     * @param vo
     * @return
     */
    @Override
    public int deleteRgoDtl(RgoDtlVo vo) {
        Rgo rgo = this.rgo(vo);
        if (vo.getList() != null && vo.getList().size() > 0) {
            for (RgoDtlVo rd : vo.getList()) {
                rgo.setTtlQty(rgo.getTtlQty().subtract(rd.getQty()));
                rgo.setTtlSrcVal(rgo.getTtlSrcVal().subtract(rd.getPuVal()));
                rgo.setTtlSrcTax(rgo.getTtlSrcTax().subtract(rd.getPuTax()));
                rgo.setTtlSrcMkv(rgo.getTtlSrcMkv().subtract(rd.getPuMkv()));
                rgo.setTtlDestVal(rgo.getTtlDestVal().subtract(rd.getSlVal()));
                rgo.setTtlDestTax(rgo.getTtlDestTax().subtract(rd.getSlTax()));
                rgo.setTtlDestMkv(rgo.getTtlDestMkv().subtract(rd.getSlMkv()));
            }
            rgoDtlMapper.deleteList(vo.getList());
        } else {
            rgo.setTtlQty(rgo.getTtlQty().subtract(vo.getQty()));
            rgo.setTtlSrcVal(rgo.getTtlSrcVal().subtract(vo.getPuVal()));
            rgo.setTtlSrcTax(rgo.getTtlSrcTax().subtract(vo.getPuTax()));
            rgo.setTtlSrcMkv(rgo.getTtlSrcMkv().subtract(vo.getPuMkv()));
            rgo.setTtlDestVal(rgo.getTtlDestVal().subtract(vo.getSlVal()));
            rgo.setTtlDestTax(rgo.getTtlDestTax().subtract(vo.getSlTax()));
            rgo.setTtlDestMkv(rgo.getTtlDestMkv().subtract(vo.getSlMkv()));

            rgoDtlMapper.deleteByPrimaryKey(new RgoDtl(vo.getUnitId(),vo.getRgoNum(),vo.getProdId()));
        }
        return rgoMapper.updateByPrimaryKeySelective(rgo);
    }

    /**
     * 新增主表时同步添加明细
     * @param vo
     * @return
     */
    @Override
    public RgoVo insertByRgo(RgoVo vo) {
        //计算调配单需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlSrcVal = new BigDecimal("0");
        BigDecimal ttlSrcTax = new BigDecimal("0");
        BigDecimal ttlSrcMkv = new BigDecimal("0");
        BigDecimal ttlDestVal = new BigDecimal("0");
        BigDecimal ttlDestTax = new BigDecimal("0");
        BigDecimal ttlDestMkv = new BigDecimal("0");

        for (int i = 0; i < vo.getList().size(); i++) {
            RgoDtlVo rDtl = vo.getList().get(i);
            rDtl.setUnitId(vo.getUnitId());
            rDtl.setRgoNum(vo.getRgoNum());
            //设置行号 排号
            Long num = 0L;
            rDtl.setLineNum(num + i + 1);
            rDtl.setRowNum(num + i + 1);

            ttlQty = ttlQty.add(rDtl.getQty());
            ttlSrcVal = ttlSrcVal.add(rDtl.getPuVal());
            ttlSrcTax = ttlSrcTax.add(rDtl.getPuTax());
            ttlSrcMkv = ttlSrcMkv.add(rDtl.getPuMkv());
            ttlDestVal = ttlDestVal.add(rDtl.getSlVal());
            ttlDestTax = ttlDestTax.add(rDtl.getSlTax());
            ttlDestMkv = ttlDestMkv.add(rDtl.getSlMkv());
        }
        rgoDtlMapper.insertList(vo.getList());

        vo.setTtlQty(ttlQty);
        vo.setTtlSrcVal(ttlSrcVal);
        vo.setTtlSrcTax(ttlSrcTax);
        vo.setTtlSrcMkv(ttlSrcMkv);
        vo.setTtlDestVal(ttlDestVal);
        vo.setTtlDestTax(ttlDestTax);
        vo.setTtlDestMkv(ttlDestMkv);

        return vo;
    }

    /**
     * 选择商品后查询相关详情
     * @param vo
     * @return
     */
    @Override
    public RgoDtlVo updateDtl(RgoProdDtlVo vo) {
        //取出数据
        RgoDtlVo dtlVo = vo.getVo().get(0);
        //用调配单类别得出购销合同类别
        RgoType type = rgoTypeMapper.selectByRgoType(vo.getRgoType());
        String pscType = "";
        if(type!=null){
            if(StringUtils.isNotEmpty(type.getSlcType())){
                pscType = slcTypeMapper.selectByPscAuto(type.getSlcType()).getPscType();
            }
        }
        //调出计算
        if(StringUtils.NullEmpty(vo.getQty()+"")){
            vo.setQty(1);
        }
        //设置可退数量1件时 退款的单价
        BigDecimal sum = rtcDtlService.Calculation(dtlVo.getProdId(),vo.getSrcUnitId(),vo.getUnitId(),vo.getQty());
        //判断折率/税率是否为空   不为空 不计算 避免更改数量后重复计算
        dtlVo.setQty(BigDecimal.valueOf(vo.getQty()));
        dtlVo.setPuVal(sum);
        dtlVo.setPuUnitPrice(sum.divide(dtlVo.getQty(),4, RoundingMode.HALF_UP));
        if(StringUtils.NullEmpty(dtlVo.getPuDiscRate()+"")){
            //折率为空 计算折率/税率
            PnScpVo pn = spnScpService.updateRgoDtlVo(dtlVo,vo.getSrcUnitId(),vo.getUnitId(),pscType);
            dtlVo.setPuDiscRate(pn.getDiscRate());
            dtlVo.setPuTaxRate(pn.getTaxRate());
        }
        dtlVo.setPuFnlPrice(dtlVo.getPuUnitPrice().multiply(dtlVo.getPuDiscRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        dtlVo.setPuTax(sum.multiply(dtlVo.getPuTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        if(StringUtils.isNotEmpty(dtlVo.getPuMkUnitPrice()+"")){
            dtlVo.setPuMkv(dtlVo.getQty().multiply(dtlVo.getPuMkUnitPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        //调入计算（判断是否有值  有值就不计算 避免重复计算）
        if(StringUtils.NullEmpty(dtlVo.getSlUnitPrice()+"")){
            //计算折率/税率
            PnScpVo pn = spnScpService.updateRgoDtlVo(dtlVo,vo.getDestUnitId(),vo.getUnitId(),pscType);
            dtlVo.setSlDiscRate(pn.getDiscRate());
            dtlVo.setSlTaxRate(pn.getTaxRate());
            dtlVo.setSlRtnaRate(pn.getRtnaRate());
            if(StringUtils.isNotEmpty(pn.getUnitPrice()+"")){
                dtlVo.setSlUnitPrice(pn.getUnitPrice());
            }else{
                //计算单价 取unit_prod_cls中unitId为当前登录组织的id
                UnitProdCls unitProd = unitProdClsMapper.selectUnitProdCls(new UnitProdCls(vo.getUnitId(),dtlVo.getProdClsId()));
                if(unitProd!=null){
                    dtlVo.setSlUnitPrice(new BigDecimal(unitProd.getPuUnitPrice().toString()));
                }else{
                    dtlVo.setSlUnitPrice(dtlVo.getPuUnitPrice());
                }
            }
            dtlVo.setSlVal(dtlVo.getSlUnitPrice().multiply(dtlVo.getQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
            dtlVo.setSlFnlPrice(dtlVo.getSlUnitPrice().multiply(dtlVo.getSlDiscRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            dtlVo.setSlTax(dtlVo.getSlVal().multiply(dtlVo.getSlTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            if(StringUtils.isNotEmpty(dtlVo.getSlMkUnitPrice()+"")){
                dtlVo.setSlMkv(dtlVo.getQty().multiply(dtlVo.getSlMkUnitPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return dtlVo;
    }


    /**
     * 查询出主表数据
     * @param vo
     * @return
     */
    private Rgo rgo(RgoDtlVo vo) {
        //查询出主表数据
        Rgo rgo = null;
        if(vo.getList()==null || vo.getList().size()==0){
            rgo = rgoMapper.selectByPrimaryKey(new RgoVo(vo.getUnitId(),vo.getRgoNum()));
        }else{
            rgo = rgoMapper.selectByPrimaryKey(new RgoVo(vo.getList().get(0).getUnitId(),vo.getList().get(0).getRgoNum()));
        }
        return rgo;
    }

    /**
     * 更新主表数据
     * @param rgo
     * @param ttlQty
     * @param ttlSrcVal
     * @param ttlSrcTax
     * @param ttlSrcMkv
     * @param ttlDestVal
     * @param ttlDestTax
     * @param ttlDestMkv
     * @return
     */
    private int updateRgo(Rgo rgo, BigDecimal ttlQty, BigDecimal ttlSrcVal, BigDecimal ttlSrcTax, BigDecimal ttlSrcMkv, BigDecimal ttlDestVal, BigDecimal ttlDestTax, BigDecimal ttlDestMkv) {
        if(rgo.getTtlQty()!=null){
            rgo.setTtlQty(rgo.getTtlQty().add(ttlQty));
        }else{
            rgo.setTtlQty(ttlQty);
        }
        if(rgo.getTtlSrcVal()!=null){
            rgo.setTtlSrcVal(rgo.getTtlSrcVal().add(ttlSrcVal));
        }else{
            rgo.setTtlSrcVal(ttlSrcVal);
        }
        if(rgo.getTtlSrcTax()!=null){
            rgo.setTtlSrcTax(rgo.getTtlSrcTax().add(ttlSrcTax));
        }else{
            rgo.setTtlSrcTax(ttlSrcTax);
        }
        if(rgo.getTtlSrcMkv()!=null){
            rgo.setTtlSrcMkv(rgo.getTtlSrcMkv().add(ttlSrcMkv));
        }else{
            rgo.setTtlSrcMkv(ttlSrcMkv);
        }
        if(rgo.getTtlDestVal()!=null){
            rgo.setTtlDestVal(rgo.getTtlQty().add(ttlDestVal));
        }else{
            rgo.setTtlDestVal(ttlDestVal);
        }
        if(rgo.getTtlDestTax()!=null){
            rgo.setTtlDestTax(rgo.getTtlDestTax().add(ttlDestTax));
        }else{
            rgo.setTtlDestTax(ttlDestTax);
        }
        if(rgo.getTtlDestMkv()!=null){
            rgo.setTtlDestMkv(rgo.getTtlDestMkv().add(ttlDestMkv));
        }else{
            rgo.setTtlDestMkv(ttlDestMkv);
        }

        return rgoMapper.updateByPrimaryKeySelective(rgo);
    }
}
