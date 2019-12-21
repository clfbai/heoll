package com.boyu.erp.platform.usercenter.service.priceservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.github.pagehelper.PageInfo;

/**
 * @program: PpnService
 * @description: 采购价格单接口
 * @author: wz
 * @create: 2019-8-26 9:43
 */
public interface PpnService {

    /**
     * 查询采购价格单
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<PpnVo> selectAll(PpnVo vo, Integer page, Integer size, SysUser sysUser);

    /**
     * 新增采购价格单
     * @param vo
     * @param user
     * @return
     */
    int insertPpn(PpnVo vo,SysUser user);

    /**
     * 修改采购价格单
     * @param vo
     * @param user
     * @return
     */
    int updatePpn(PpnVo vo,SysUser user);

    /**
     * 删除采购价格单
     * @param vo
     * @param user
     * @return
     */
    int deletePpn(PpnVo vo,SysUser user);

    /**
     * 切换定价范围的时候删除当前范围
     * @param vo
     * @return
     */
    int deletePpnScp(PpnVo vo);

    /**
     * 确认单据
     * @param vo
     * @return
     */
    int confirm(PpnVo vo);

    /**
     * 重做单据
     * @param vo
     * @return
     */
    int redo(PpnVo vo);

    /**
     * 审核单据
     * @param vo
     * @return
     */
    int examine(PpnVo vo,SysUser user);

    /**
     * 重审单据
     * @param vo
     * @return
     */
    int reiterate(PpnVo vo);

    /**
     * 挂起单据
     * @return
     */
    int hangUp(PpnVo vo);

    /**
     * 恢复单据
     * @param vo
     * @return
     */
    int recovery(PpnVo vo);

    /**
     * 作废单据
     * @param vo
     * @return
     */
    int toVoid(PpnVo vo);

    /**
     * 执行单据
     * @param vo
     * @param user
     * @return
     */
    int implement(PpnVo vo,SysUser user);
}
