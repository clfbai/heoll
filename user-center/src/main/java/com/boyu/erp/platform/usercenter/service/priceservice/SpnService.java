package com.boyu.erp.platform.usercenter.service.priceservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnVo;
import com.github.pagehelper.PageInfo;

/**
 * @Classname SpnService
 * @Description TODO 销售价格单接口
 * @Date 2019/8/27 11:46
 * @Created by wz
 */
public interface SpnService {

    /**
     * 查询销售价格单
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<SpnVo> selectAll(SpnVo vo, Integer page, Integer size, SysUser sysUser);

    /**
     * 新增销售价格单
     * @param vo
     * @param user
     * @return
     */
    int insertSpn(SpnVo vo,SysUser user);

    /**
     * 修改销售价格单
     * @param vo
     * @param user
     * @return
     */
    int updateSpn(SpnVo vo,SysUser user);

    /**
     * 删除销售价格单
     * @param vo
     * @param user
     * @return
     */
    int deleteSpn(SpnVo vo,SysUser user);

    /**
     * 切换定价范围时删除范围
     * @param vo
     * @return
     */
    int deleteSpnScp(SpnVo vo);

    /**
     * 销售价格单确认单据
     * @param vo
     * @return
     */
    int confirm(SpnVo vo);

    /**
     * 销售价格单中重做单据
     * @param vo
     * @return
     */
    int redo(SpnVo vo);

    /**
     * 审核单据
     * @param vo
     * @return
     */
    int examine(SpnVo vo,SysUser user);

    /**
     * 重审单据
     * @param vo
     * @return
     */
    int reiterate(SpnVo vo);

    /**
     * 挂起单据
     * @return
     */
    int hangUp(SpnVo vo);

    /**
     * 恢复单据
     * @param vo
     * @return
     */
    int recovery(SpnVo vo);

    /**
     * 作废单据
     * @param vo
     * @return
     */
    int toVoid(SpnVo vo);

    /**
     * 执行单据
     * @param vo
     * @param user
     * @return
     */
    int implement(SpnVo vo,SysUser user);

}
