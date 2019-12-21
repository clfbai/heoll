package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.purchase.PsaTr;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.VenderMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsaTrMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaTrService;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaRateVo;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PsaTrServiceimpl
 * @Description TODO
 * @Date 2019/7/2 12:07
 * @Created wz
 */
@Service
@Transactional
public class PsaTrServiceimpl implements PsaTrService {

    @Autowired
    private PsaTrMapper psaTrMapper;
    @Autowired
    private VenderMapper venderMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;

    @Override
    public List<PsaRateVo> selectAll(PsaRateVo vo) throws ServiceException {
        List<PsaRateVo> voList = null;
        voList = psaTrMapper.selectByPsa(vo);
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
        return psaTrMapper.insertByPsa(vo);
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
        return psaTrMapper.updateByPsa(vo);
    }

    @Override
    public int deleteByPsa(PsaRateVo vo) throws ServiceException{
        return psaTrMapper.deleteByPsaTr(vo);
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
    public String selectByTr(String pscType, String prodCatId, Psa psa) throws ServiceException{
        String tr = "";

        PsaTr pd = new PsaTr();
        pd.setVenderId(psa.getVenderId());
        pd.setVendeeId(psa.getVendeeId());
        pd.setPsaCtlr(psa.getPsaCtlr());
        pd.setPscType(pscType);
        //合同类别查
        PsaTr pt1 = psaTrMapper.selectByPrimaryKey(pd);
        if(pt1!=null){

            String prod = pt1.getProdCatId();
            if("*".equals(prod)){
                tr = pt1.getTaxRate().toString();
            }else{
                prodCatId = prodCatId.substring(0,prod.length());
                if(prod.equals(prodCatId)){
                    tr = pt1.getTaxRate().toString();
                }else{
                    tr = sysParameterMapper.findById("DEFAULT_TAX_RATE").getParmVal();
                }
            }
        }else{
            tr = sysParameterMapper.findById("DEFAULT_TAX_RATE").getParmVal();
        }
        return tr;
    }

    @Override
    public void insertByPsa(PsaVo vo) {
        for (PsaRateVo pv:vo.getTrList()) {
            this.judge(pv);
            if(StringUtils.NullEmpty(pv.getPscType())){
                pv.setPscType("*");
            }
            if(StringUtils.NullEmpty(pv.getProdCatId())){
                pv.setProdCatId("*");
            }
            psaTrMapper.insertByPsa(pv);
        }
    }

    /**
     * 判断是否存在
     * @param vo
     */
    private void judge(PsaRateVo vo){
        List<PsaRateVo> list = psaTrMapper.selectByPsaTr(vo);
        if(list!=null && list.size()>0){
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "购销合同类别已经被占用，请修改");
        }
    }

}
