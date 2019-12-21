package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;

import java.util.List;
import java.util.Map;

/**
 * @Classname RtvQtaService
 * @Description TODO
 * @Date 2019/9/16 14:23
 * @Created by wz
 */
public interface RtvQtaService {

    //冻结可退金额
    int freeze(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType, Map<String, Object> map, boolean flag);

    //解冻可退金额
    int defreeze(Psa psa, PrcVo vo, String docType, Map<String, Object> map);

    //消耗可退金额
    int consume(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType,boolean flag);

    //累计可退金额
    int accumulate(Psa psa,List<ProdClsDtlVo> clsDtlList, boolean flag);
}
