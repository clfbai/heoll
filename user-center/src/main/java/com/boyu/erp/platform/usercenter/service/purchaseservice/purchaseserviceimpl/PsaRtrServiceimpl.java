package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.purchase.PsaRtr;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.VenderMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsaRtrMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaRtrService;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaRateVo;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PsaRtrServiceimpl
 * @Description TODO
 * @Date 2019/7/2 15:04
 * @Created by js
 */
@Service
@Transactional
public class PsaRtrServiceimpl implements PsaRtrService {

    @Autowired
    private PsaRtrMapper psaRtrMapper;
    @Autowired
    private VenderMapper venderMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;

    @Override
    public List<PsaRateVo> selectAll(PsaRateVo vo) throws ServiceException {
        List<PsaRateVo> voList = null;
        voList = psaRtrMapper.selectByPsa(vo);
        return voList;
    }

    @Override
    public int insertByPsa(PsaRateVo vo) throws ServiceException {
        this.judge(vo);
        if(StringUtils.NullEmpty(vo.getPscType())){
            vo.setPscType("*");
        }
        if(StringUtils.NullEmpty(vo.getProdCatId())){
            vo.setProdCatId("*");
        }
        return psaRtrMapper.insertByPsa(vo);
    }

    @Override
    public int updateByPsa(PsaRateVo vo) throws ServiceException{
        if(StringUtils.isNotEmpty(vo.getUpPscType())){
            //判断修改了购销合同是否已有记录
            PsaRateVo pr = new PsaRateVo();
            pr.setVenderId(vo.getVenderId());
            pr.setVendeeId(vo.getVendeeId());
            pr.setPsaCtlr(vo.getPsaCtlr());
            pr.setPscType(vo.getUpPscType());
            this.judge(pr);
        }
        return psaRtrMapper.updateByPsa(vo);
    }

    @Override
    public int deleteByPsa(PsaRateVo vo) throws ServiceException{
        return psaRtrMapper.deleteByPsaRtr(vo);
    }

    /**
     * 首先查出协议控制方
     * 先通过合同类别查，查不到在用商品和合同类别查，否则就是默认参数中的值
     * @param pscType
     * @param prodCatId
     * @return
     * @throws ServiceException
     */
    @Override
    public String selectByRtr(String pscType, String prodCatId, Psa psa) throws ServiceException{
        String rtr = "";

        PsaRtr pd = new PsaRtr();
        pd.setVenderId(psa.getVenderId());
        pd.setVendeeId(psa.getVendeeId());
        pd.setPsaCtlr(psa.getPsaCtlr());
        pd.setPscType(pscType);
        //合同类别查
        PsaRtr pr1 = psaRtrMapper.selectByPrimaryKey(pd);
        if(pr1!=null){

            String prod = pr1.getProdCatId();
            if("*".equals(prod)){
                rtr = pr1.getRtnaRate().toString();
            }else{
                prodCatId = prodCatId.substring(0,prod.length());
                if(prod.equals(prodCatId)){
                    rtr = pr1.getRtnaRate().toString();
                }else{
                    rtr = sysParameterMapper.findById("DEFAULT_PURCHASE_RETURN_RATE").getParmVal();
                }
            }
        }else{
            rtr = sysParameterMapper.findById("DEFAULT_PURCHASE_RETURN_RATE").getParmVal();
        }
        return rtr;
    }

    @Override
    public void insertByPsa(PsaVo vo) throws ServiceException {
        for (PsaRateVo pv:vo.getRtrList()) {
            this.judge(pv);
            if(StringUtils.NullEmpty(pv.getPscType())){
                pv.setPscType("*");
            }
            if(StringUtils.NullEmpty(pv.getProdCatId())){
                pv.setProdCatId("*");
            }
            psaRtrMapper.insertByPsa(pv);
        }
    }

    /**
     * 判断是否存在
     * @param vo
     */
    private void judge(PsaRateVo vo){
        List<PsaRateVo> list = psaRtrMapper.selectByPsaRtr(vo);
        if(list!=null && list.size()>0){
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "购销合同类别已经被占用，请修改");
        }
    }
}
