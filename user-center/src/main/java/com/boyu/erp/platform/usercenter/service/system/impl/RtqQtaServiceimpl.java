package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.basic.RtqQta;
import com.boyu.erp.platform.usercenter.entity.basic.RtqQtaPg;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.RtqQtaMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.RtqQtaPgMapper;
import com.boyu.erp.platform.usercenter.service.system.RtqQtaService;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname RtqQtaServiceimpl
 * @Description TODO
 * @Date 2019/9/16 12:15
 * @Created by wz
 * 可退数量额度
 */
@Service
@Transactional
public class RtqQtaServiceimpl implements RtqQtaService {

    @Autowired
    private RtqQtaMapper rtqQtaMapper;
    @Autowired
    private RtqQtaPgMapper rtqQtaPgMapper;

    /**
     * 冻结可退数量
     *
     * @param psa
     * @param dtlList
     * @param vo
     */
    @Override
    public int freeze(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType, Map<String, Object> map, boolean flag) throws Exception {
        if (dtlList != null && dtlList.size() > 0) {
            //修改集合
            List<RtqQta> updateList = new ArrayList<>();
            //循环判断
            for (RtcDtlVo dtlVo : dtlList) {
                RtqQta rtqQta = new RtqQta(psa.getVenderId(), psa.getVendeeId(), psa.getPsaCtlr(), dtlVo.getProdClsId());
                RtqQta rtq = rtqQtaMapper.selectByPrimaryKey(rtqQta);
                if (rtq != null) {
                    //修改
                    if (new SimpleDateFormat("yyyy-MM-dd").parse(vo.getExpdDate()).compareTo(new Date()) == 1 ||
                            (rtq.getRtqQta().add(rtq.getAdjQta()).subtract(rtq.getUsedQty())
                                    .subtract(rtq.getFrzQty().add(dtlVo.getQty()))).compareTo(new BigDecimal("0")) == -1) {
                        //抛异常
                        throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种【" + dtlVo.getProdClsId() + "】可退货数量额度不足！");
                    }
                    if(flag){
                        rtq.setFrzQty(rtq.getFrzQty().add(dtlVo.getQty()));
                    }else{
                        rtq.setFrzQty(rtq.getFrzQty().subtract(dtlVo.getQty()));
                    }

                    updateList.add(rtq);
                } else {
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种【" + dtlVo.getProdClsId() + "】可退货数量额度不足！");
                }
            }
            rtqQtaMapper.updateByBill(updateList);
            map.put("psa", psa);
            map.put("dtlList", dtlList);
            map.put("docType", docType);
            return rtqQtaPgMapper.insertByBill(map);
        }
        return 0;
    }

    /**
     * 解冻可退数量
     *
     * @param psa
     * @param dtlList
     * @param vo
     * @return
     */
    @Override
    public int defreeze(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType, Map<String, Object> map) {
        if (dtlList != null && dtlList.size() > 0) {
            map.put("psa", psa);
            map.put("dtlList", dtlList);
            map.put("docType", docType);
            //查询可退未决数量集合
            List<RtqQtaPg> pgList = rtqQtaPgMapper.selectByUpdateRtqQta(map);
            if (pgList != null && pgList.size() > 0) {
                rtqQtaMapper.updateByRtqQtaPg(pgList);
                return rtqQtaPgMapper.deleteByList(pgList);
            }
        }
        return 0;
    }

    /**
     * 消耗可退数量
     * @param psa
     * @param dtlList
     * @param vo
     * @param docType
     * @return
     */
    @Override
    public int consume(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType,boolean flag) throws Exception {
        if (dtlList != null && dtlList.size() > 0) {
            //修改集合
            List<RtqQta> updateList = new ArrayList<>();
            //循环判断
            for (RtcDtlVo dtlVo : dtlList) {
                RtqQta rtqQta = new RtqQta(psa.getVenderId(), psa.getVendeeId(), psa.getPsaCtlr(), dtlVo.getProdClsId());
                RtqQta rtq = rtqQtaMapper.selectByPrimaryKey(rtqQta);
                if (rtq != null) {
                    //修改
                    if (new SimpleDateFormat("yyyy-MM-dd").parse(vo.getExpdDate()).compareTo(new Date()) == 1 ||
                            (rtq.getRtqQta().add(rtq.getAdjQta()).subtract(rtq.getUsedQty())
                                    .subtract(rtq.getFrzQty().add(dtlVo.getQty()))).compareTo(new BigDecimal("0")) == -1) {
                        //抛异常
                        throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种【" + dtlVo.getProdClsId() + "】可退货数量额度不足！");
                    }
                    if(flag){
                        rtq.setUsedQty(rtq.getUsedQty().add(dtlVo.getQty()));
                    }else{
                        rtq.setUsedQty(rtq.getUsedQty().subtract(dtlVo.getQty()));
                    }
                    updateList.add(rtq);
                } else {
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种【" + dtlVo.getProdClsId() + "】可退货数量额度不足！");
                }
            }
            return rtqQtaMapper.updateByBill(updateList);
        }
        return 0;
    }

    /**
     * 累计可退数量
     *
     * @param psa
     * @param clsDtlList
     * @return
     */
    @Override
    public int accumulate(Psa psa, List<ProdClsDtlVo> clsDtlList, boolean flag) {
        Map<String, Object> map = null;
        int type = 0;
        for (ProdClsDtlVo dtl : clsDtlList) {
            map = new HashMap<>();
            map.put("psa", psa);
            map.put("dtl", dtl);
            map.put("flag", flag);
            type += rtqQtaMapper.insertOrUpdate(map);
        }
        return type;
    }
}
