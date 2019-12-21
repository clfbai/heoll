package com.boyu.erp.platform.usercenter.service.priceservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnDtlVo;

import java.util.List;

/**
 * @program: SpnDtlService
 * @description: 销售价格单明细接口
 * @author: wz
 * @create: 2019-8-26 9:43
 */
public interface SpnDtlService {

    /**
     * 查询销售价格单明细
     * @param vo
     * @return
     */
    List<SpnDtlVo> findBySpn(SpnDtlVo vo);

    /**
     * 新增销售价格单明细
     * @param vo
     * @param user
     * @return
     */
    int insertSpnDtl(SpnDtlVo vo, SysUser user);

    /**
     * 修改采购价格单明细
     * @param vo
     * @param user
     * @return
     */
    int updateSpnDtl(SpnDtlVo vo, SysUser user);

    /**
     * 删除销售价格单明细
     * @param vo
     * @param user
     * @return
     */
    int deleteSpnDtl(SpnDtlVo vo, SysUser user);
}
