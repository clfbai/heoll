package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.basic.RtvQta;
import com.boyu.erp.platform.usercenter.entity.basic.RtvQtaPg;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.RtvQtaMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.RtvQtaPgMapper;
import com.boyu.erp.platform.usercenter.service.system.RtvQtaService;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname RtvQtaServiceimpl
 * @Description TODO
 * @Date 2019/9/16 14:23
 * @Created by wz
 * 可退金额额度
 */
@Service
@Transactional
public class RtvQtaServiceimpl implements RtvQtaService {

    @Autowired
    private RtvQtaMapper rtvQtaMapper;
    @Autowired
    private RtvQtaPgMapper rtvQtaPgMapper;

    /**
     * 冻结可退金额
     *
     * @param psa
     * @param dtlList
     * @param vo
     * @return
     */
    @Override
    public int freeze(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType,  Map<String, Object> map, boolean flag) {
        if (dtlList != null && dtlList.size() > 0) {
            RtvQta rtvQta = new RtvQta(psa.getVenderId(), psa.getVendeeId(), psa.getPsaCtlr());
            RtvQta rtv = rtvQtaMapper.selectByPrimaryKey(rtvQta);
            if (rtv != null) {
                BigDecimal val = new BigDecimal("0");
                for (RtcDtlVo dtlVo : dtlList) {
                    val = val.add(dtlVo.getVal());
                }
                if(flag){
                    rtv.setFrzVal(rtv.getFrzVal().add(val));
                }else{
                    rtv.setFrzVal(rtv.getFrzVal().subtract(val));
                }

                //修改
                if ((rtv.getRtvQta().add(rtv.getAdjQta()).subtract(rtv.getUsedVal())
                        .subtract(rtv.getFrzVal())).compareTo(new BigDecimal("0")) == -1) {
                    //抛异常
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "可退金额额度不足！");
                }
                rtvQtaMapper.updateByPrimaryKeySelective(rtv);

                return rtvQtaPgMapper.insertSelective(new RtvQtaPg(psa.getVenderId(),psa.getVendeeId(),psa.getPsaCtlr(),docType,(Long) map.get("unitId"),(String) map.get("docNum"),val));
            } else {
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "可退金额额度不足！");
            }
        }
        return 0;
    }

    /**
     * 解冻可退金额
     * @param psa
     * @param vo
     * @return
     */
    @Override
    public int defreeze(Psa psa,  PrcVo vo, String docType, Map<String, Object> map) {
        RtvQtaPg rtvQtaPg= new RtvQtaPg(psa.getVenderId(),psa.getVendeeId(),psa.getPsaCtlr(),docType,(Long) map.get("unitId"),(String) map.get("docNum"),null);
        RtvQtaPg pg = rtvQtaPgMapper.selectByPrimaryKey(rtvQtaPg);
        if(pg!=null){
            rtvQtaMapper.updateByRtvQtaPg(pg);
            return rtvQtaPgMapper.deleteByPrimaryKey(rtvQtaPg);
        }
        return 0;
    }

    /**
     * 消耗可退金额
     * @param psa
     * @param vo
     * @param docType
     * @return
     */
    @Override
    public int consume(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType,boolean flag) {
        if (dtlList != null && dtlList.size() > 0) {
            RtvQta rtvQta = new RtvQta(psa.getVenderId(), psa.getVendeeId(), psa.getPsaCtlr());
            RtvQta rtv = rtvQtaMapper.selectByPrimaryKey(rtvQta);
            if (rtv != null) {
                BigDecimal val = new BigDecimal("0");
                for (RtcDtlVo dtlVo : dtlList) {
                    val = val.add(dtlVo.getVal());
                }
                if(flag){
                    rtv.setUsedVal(rtv.getUsedVal().add(val));
                }else{
                    rtv.setUsedVal(rtv.getUsedVal().subtract(val));
                }

                //修改
                if ((rtv.getRtvQta().add(rtv.getAdjQta()).subtract(rtv.getUsedVal())
                        .subtract(rtv.getFrzVal())).compareTo(new BigDecimal("0")) == -1) {
                    //抛异常
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "可退金额额度不足！");
                }
                return rtvQtaMapper.updateByPrimaryKeySelective(rtv);

            } else {
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "可退金额额度不足！");
            }
        }
        return 0;
    }

    /**
     * 累计可退金额
     * @param psa
     * @param clsDtlList
     * @return
     */
    @Override
    public int accumulate(Psa psa, List<ProdClsDtlVo> clsDtlList, boolean flag) {
        Map<String,Object> map = null;
        int type = 0;
        for (ProdClsDtlVo dtl:clsDtlList) {
            map = new HashMap<>();
            map.put("psa",psa);
            map.put("dtl",dtl);
            map.put("flag",flag);
            type+=rtvQtaMapper.insertOrUpdate(map);
        }
        return type;
    }
}
