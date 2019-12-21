package com.boyu.erp.platform.usercenter.service.priceservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;

import java.util.List;

/**
 * @program: PpnDtlService
 * @description: 采购价格单明细接口
 * @author: wz
 * @create: 2019-8-26 9:43
 */
public interface PpnDtlService {

    /**
     * 查询采购价格单明细
     * @param vo
     * @return
     */
    List<PpnDtlVo> findByPpn(PpnDtlVo vo);

    /**
     * 新增采购价格单明细
     * @param vo
     * @param user
     * @return
     */
    int insertPpnDtl(PpnDtlVo vo, SysUser user);

    /**
     * 修改采购价格单明细
     * @param vo
     * @param user
     * @return
     */
    int updatePpnDtl(PpnDtlVo vo, SysUser user);

    /**
     * 删除采购价格单明细
     * @param vo
     * @param user
     * @return
     */
    int deletePpnDtl(PpnDtlVo vo, SysUser user);
}
