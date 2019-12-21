package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.Rtc;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.UnitBatchSerivce;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVoByBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @Classname RtcDtlServiceimpl
 * @Description TODO
 * @Date 2019/7/26 9:55
 * @Created wz
 */
@Service
@Transactional
public class RtcDtlServiceimpl implements RtcDtlService {

    @Autowired
    private RtcDtlMapper rtcDtlMapper;
    @Autowired
    private RtcMapper rtcMapper;
    @Autowired
    private UnitBatchSerivce unitBatchSerivce;

    @Override
    public List<RtcDtlVo> findByRtcNum(RtcDtlVo vo) {
        return rtcDtlMapper.findByRtcNum(vo);
    }

    /**
     * 新增明细数据
     *
     * @param p
     * @return
     */
    @Override
    public int insertRtcDtl(RtcDtlVo p) {
        String rtcNum = p.getRtcNum();
        //先查出Rtc推销合同数据
        Rtc rtc = rtcMapper.selectByPrimaryKey(rtcNum);
        //设置序号 默认为0  插到数据更改
        Long num = 0L;
        //查询它得最后一条记录
        RtcDtl rd = rtcDtlMapper.selectByDesc(rtcNum);
        if (rd != null) {
            if (rd.getLineNum() != null) {
                num = rd.getLineNum();
            }
        }
        //计算退销协议需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlVal = new BigDecimal("0");
        BigDecimal ttlTax = new BigDecimal("0");
        BigDecimal ttlMkv = new BigDecimal("0");

        if (p.getList() != null && p.getList().size() > 0) {
            for (int i = 0; i < p.getList().size(); i++) {
                RtcDtlVo rDtl = p.getList().get(i);
                rDtl.setRtcNum(rtcNum);
                //设置行号 排号
                rDtl.setLineNum(num + i + 1);
                rDtl.setRowNum(num + i + 1);

                rDtl.setTax(rDtl.getVal().divide(rDtl.getTaxRate().add(new BigDecimal(1))).multiply(rDtl.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                ttlQty = ttlQty.add(rDtl.getQty());
                ttlVal = ttlVal.add(rDtl.getVal());
                ttlTax = ttlTax.add(rDtl.getTax());
                ttlMkv = ttlMkv.add(rDtl.getMkv());
            }
            try {
                rtcDtlMapper.insertList(p.getList());
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }
        } else {
            //设置行号 排号
            p.setLineNum( num + 1);
            p.setRowNum( num + 1);
            p.setTax(p.getVal().divide(p.getTaxRate().add(new BigDecimal(1))).multiply(p.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlTax = ttlTax.add(p.getTax());
            ttlMkv = ttlMkv.add(p.getMkv());
            try {
                rtcDtlMapper.insertByRtcNum(p);
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }

        }

        return this.updateRtc(rtc, ttlQty, ttlVal, ttlTax, ttlMkv);
    }

    /**
     * 修改明细数据
     *
     * @param p
     * @return
     */
    @Override
    public int updateRtcDtl(RtcDtlVo p) {
        String rtcNum = this.rtcNum(p);
        //先查出Rtc退销合同数据
        Rtc rtc = rtcMapper.selectByPrimaryKey(rtcNum);
        BigDecimal qty = new BigDecimal("0");//数量
        BigDecimal tax = new BigDecimal("0");//税款
        BigDecimal val = new BigDecimal("0");//金额
        BigDecimal mkv = new BigDecimal("0");//市值

        if (p.getList() != null && p.getList().size() > 0) {
            for (RtcDtlVo pDtl : p.getList()) {
                RtcDtl pd = new RtcDtl();
                pd.setRtcNum(pDtl.getRtcNum());
                pd.setProdId(pDtl.getProdId());
                pd = rtcDtlMapper.selectByPrimaryKey(pd);

                pDtl.setTax(pDtl.getVal().divide(pDtl.getTaxRate().add(new BigDecimal(1))).multiply(pDtl.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                //计算当前
                rtc.setTtlQty(rtc.getTtlQty().subtract(pd.getQty()).add(pDtl.getQty()));
                rtc.setTtlTax(rtc.getTtlTax().subtract(pd.getTax()).add(pDtl.getTax()));
                rtc.setTtlVal(rtc.getTtlVal().subtract(pd.getVal()).add(pDtl.getVal()));
                rtc.setTtlMkv(rtc.getTtlMkv().subtract(pd.getMkv()).add(pDtl.getMkv()));
            }
            rtcDtlMapper.updateList(p.getList());
        } else {
            //查询之前的记录
            RtcDtl pd = new RtcDtl();
            pd.setRtcNum(p.getRtcNum());
            pd.setProdId(p.getProdId());
            pd = rtcDtlMapper.selectByPrimaryKey(pd);

            p.setTax(p.getVal().divide(p.getTaxRate().add(new BigDecimal(1))).multiply(p.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

            //计算当前
            rtc.setTtlQty(rtc.getTtlQty().subtract(pd.getQty()).add(p.getQty()));
            rtc.setTtlTax(rtc.getTtlTax().subtract(pd.getTax()).add(p.getTax()));
            rtc.setTtlVal(rtc.getTtlVal().subtract(pd.getVal()).add(p.getVal()));
            rtc.setTtlMkv(rtc.getTtlMkv().subtract(pd.getMkv()).add(p.getMkv()));

            rtcDtlMapper.updateByRtcNum(p);
        }

        return rtcMapper.updateByPrimaryKeySelective(rtc);
    }


    /**
     * 删除明细数据
     *
     * @param p
     * @return
     */
    @Override
    public int deleteRtcDtl(RtcDtlVo p) {
        String rtcNum = this.rtcNum(p);
        //删除退销合同明细的数据PSO_DTL
        //最后修改退销合同RTC
        //先查出RTC退销合同数据
        Rtc rtc = rtcMapper.selectByPrimaryKey(rtcNum);

        if (p.getList() != null && p.getList().size() > 0) {
            for (RtcDtlVo pDtl : p.getList()) {
                rtc.setTtlQty(rtc.getTtlQty().subtract(pDtl.getQty()));
                rtc.setTtlTax(rtc.getTtlTax().subtract(pDtl.getTax()));
                rtc.setTtlVal(rtc.getTtlVal().subtract(pDtl.getVal()));
                rtc.setTtlMkv(rtc.getTtlMkv().subtract(pDtl.getMkv()));
            }
            rtcDtlMapper.deleteList(p.getList());
        } else {
            rtc.setTtlQty(rtc.getTtlQty().subtract(p.getQty()));
            rtc.setTtlTax(rtc.getTtlTax().subtract(p.getTax()));
            rtc.setTtlVal(rtc.getTtlVal().subtract(p.getVal()));
            rtc.setTtlMkv(rtc.getTtlMkv().subtract(p.getMkv()));


            RtcDtl pd = new RtcDtl();
            pd.setRtcNum(p.getRtcNum());
            pd.setProdId(p.getProdId());
            rtcDtlMapper.deleteByPrimaryKey(pd);
        }

        return rtcMapper.updateByPrimaryKeySelective(rtc);
    }

    @Override
    public PrcVo insertByPrc(PrcVo v) {
        //计算退销协议需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlVal = new BigDecimal("0");
        BigDecimal ttlTax = new BigDecimal("0");
        BigDecimal ttlMkv = new BigDecimal("0");
        for (int i = 0; i < v.getRtcDtlList().size(); i++) {
            RtcDtlVo p = v.getRtcDtlList().get(i);
            p.setRtcNum(v.getRtcNum());

            Long num = 0L;

            //设置行号 排号
            p.setLineNum(num + i + 1);
            p.setRowNum(num + i + 1);

            p.setTax(p.getVal().divide(p.getTaxRate().add(new BigDecimal(1))).multiply(p.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlTax = ttlTax.add(p.getTax());
            ttlMkv = ttlMkv.add(p.getMkv());

        }

        rtcDtlMapper.insertList(v.getRtcDtlList());

        v.setTtlQty(ttlQty);
        v.setTtlVal(ttlVal);
        v.setTtlTax(ttlTax);
        v.setTtlMkv(ttlMkv);
        return v;
    }

    /**
     * 输入数量后，调用批次接口，重算数据
     * @param batchVo
     * @return
     */
    @Override
    public RtcDtlVo updatePrcDtl(RtcDtlVoByBatch batchVo) {
        RtcDtlVo dtlVo = batchVo.getVo().get(0);
        //计算可退的总金额
        BigDecimal sum = this.Calculation(batchVo.getVo().get(0).getProdId(),batchVo.getVendeeId(),batchVo.getVenderId(),batchVo.getQty());
        dtlVo.setQty(new BigDecimal(batchVo.getQty()));
        dtlVo.setVal(sum);
        dtlVo.setUnitPrice(sum.divide(new BigDecimal(batchVo.getQty()),4, RoundingMode.HALF_UP));
        dtlVo.setFnlPrice(dtlVo.getUnitPrice().multiply(dtlVo.getDiscRate()).setScale(4, BigDecimal.ROUND_HALF_UP));
        dtlVo.setTax(sum.divide(dtlVo.getTaxRate().add(new BigDecimal(1))).multiply(dtlVo.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        dtlVo.setMkv(dtlVo.getMkUnitPrice().multiply(new BigDecimal(batchVo.getQty())));
        return dtlVo;
    }

    //查出所有批次，采用先进先出原理计算总金额
    @Override
    public BigDecimal Calculation(Long prodId, Long vendeeId, Long venderId, int qty) {
        //查询出所有的批次
        List<UnitBatchNum> batchList = unitBatchSerivce.getUnitBatchNum(new UnitBatchNum(prodId,vendeeId,venderId));

        BigDecimal sum = new BigDecimal(0);
        for (UnitBatchNum batch:batchList) {
            if(qty>0){
                if((qty-batch.getReturnableUnitQuantity())>0){
                    sum = sum.add(new BigDecimal(batch.getPrice().toString()).multiply(new BigDecimal(batch.getReturnableUnitQuantity())));
                    qty = qty-batch.getReturnableUnitQuantity();
                }else{
                    sum = sum.add(new BigDecimal(batch.getPrice().toString()).multiply(new BigDecimal(qty)));
                    qty = 0;
                }
            }
        }
        return sum;
    }


    private int updateRtc(Rtc rtc, BigDecimal ttlQty, BigDecimal ttlVal, BigDecimal ttlTax, BigDecimal ttlMkv) {
        //修改购销协议的数据PSC
        if (rtc.getTtlQty() != null) {
            rtc.setTtlQty(rtc.getTtlQty().add(ttlQty));
        } else {
            rtc.setTtlQty(ttlQty);
        }
        if (rtc.getTtlVal() != null) {
            rtc.setTtlVal(rtc.getTtlVal().add(ttlVal));
        } else {
            rtc.setTtlVal(ttlVal);
        }
        if (rtc.getTtlTax() != null) {
            rtc.setTtlTax(rtc.getTtlTax().add(ttlTax));
        } else {
            rtc.setTtlTax(ttlTax);
        }
        if (rtc.getTtlMkv() != null) {
            rtc.setTtlMkv(rtc.getTtlMkv().add(ttlMkv));
        } else {
            rtc.setTtlMkv(ttlMkv);
        }
        return rtcMapper.updateByPrimaryKeySelective(rtc);
    }

    public String rtcNum(RtcDtlVo p) {
        String rtcNum = "";
        if (p.getList() != null && p.getList().size() > 0) {
            rtcNum = p.getList().get(0).getRtcNum();
        } else {
            rtcNum = p.getRtcNum();
        }
        return rtcNum;
    }

}
