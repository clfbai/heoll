package com.boyu.erp.platform.usercenter.service.purchaseservice;


import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;

import java.util.List;

/**
 * @Classname PsoDtlService
 * @Description TODO
 * @Date 2019/7/18 11:33
 * @Created wz
 */

public interface PsoDtlService {

    List<PsoDtlVo> findByPuoNum(PsoDtlVo vo);

    int insertPsoDtl(PsoDtlVo vo);

    int updatePsoDtl(PsoDtlVo vo);

    int deletePsoDtl(PsoDtlVo vo);

    PsoVo insertByPso(PsoVo vo);
}
