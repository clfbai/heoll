package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Classname RtqQtaService
 * @Description TODO
 * @Date 2019/9/16 10:58
 * @Created by wz
 */
public interface RtqQtaService {

    //冻结可退货数量
    int freeze(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType, Map<String, Object> map,boolean flag) throws Exception;

    //解冻可退货数量
    int defreeze(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType, Map<String, Object> map);

    //消耗可退数量
    int consume(Psa psa, List<RtcDtlVo> dtlList, PrcVo vo, String docType,boolean flag) throws Exception;

    //累计可退数量
    int accumulate(Psa psa,List<ProdClsDtlVo> clsDtlList,boolean flag);
}
