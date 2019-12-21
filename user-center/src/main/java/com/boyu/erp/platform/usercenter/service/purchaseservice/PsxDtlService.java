package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.vo.purchase.PsxDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;

import java.util.List;

/**
 * @Classname PscDtlService
 * @Description TODO
 * @Date 2019/7/8 12:17
 * @Created wz
 */
public interface PsxDtlService {

    public List<PsxDtlVo> findByPsxNum(PsxDtlVo vo);

    int insertPsxDtl(PsxDtlVo vo) throws Exception;

    int updatePsxDtl(PsxDtlVo vo);

    int deletePsxDtl(PsxDtlVo vo);

    PsxVo insertByPua(PsxVo vo) throws Exception;
}
