package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Classname PuaService
 * @Description TODO
 * @Date 2019/7/15 19:33
 * @Created wz
 */
public interface PuaService {

    /**
     * 分页查询采购合同
     * @param psa
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws ServiceException
     */
    PageInfo<PsxVo> selectAll(PsxVo psa, Integer page, Integer size, SysUser sysUser) throws ServiceException;

    /**
     * 添加采购合同
     * @param psa
     * @param sysUser
     * @return
     * @throws Exception
     */
    int insertPua(PsxVo psa, SysUser sysUser) throws Exception;

    /**
     * 更新采购合同
     * @param psa
     * @param sysUser
     * @return
     * @throws Exception
     */
    int updatePua(PsxVo psa, SysUser sysUser) throws Exception;

    /**
     * 删除采购合同
     * @param psa
     * @return
     */
    int deletePua(PsxVo psa);

    /**
     * 删除明细
     * @param psxNum
     * @return
     */
    int deletePsxDtl(String psxNum);

    /**
     * 采购申请中确认单据
     * @param vo
     * @param sysUser
     * @return
     */
    int confirm(PsxVo vo, SysUser sysUser);

    /**
     * 销售申请中生成采购申请
     * @param vo
     * @param sysUser
     * @return
     */
    int insertBySla(PsxVo vo, SysUser sysUser);

    /**
     * 采购申请中重做单据
     * @param vo
     * @return
     */
    int redo(PsxVo vo, SysUser sysUser);

    /**
     * 采购申请中审核单据
     * @param vo
     * @param sysUser
     * @return
     */
    int examine(PsxVo vo, SysUser sysUser);

    /**
     * 销售申请通知采购申请审核
     * @param vo
     * @param sysUser
     * @return
     */
    int noticeExamine(PsxVo vo, SysUser sysUser);

    /**
     * 采购申请中重审单据
     * @param vo
     * @param sysUser
     * @return
     */
    int reiterate(PsxVo vo, SysUser sysUser);

    /**
     * 销售申请中通知采购申请重审
     * @param vo
     * @param user
     * @return
     */
    int noticeReiterate(PsxVo vo,SysUser user);

    /**
     * 采购申请中挂起单据
     * @param vo
     * @param user
     * @return
     */
    int hangUp(PsxVo vo,SysUser user);

    /**
     * 采购申请中恢复单据
     * @param vo
     * @param user
     * @return
     */
    int recovery(PsxVo vo,SysUser user);

    /**
     * 采购申请中作废单据
     * @param vo
     * @param user
     * @return
     */
    int toVoid(PsxVo vo,SysUser user);

    /**
     * 采购申请中关闭单据
     * @param vo
     * @param user
     * @return
     */
    int close(PsxVo vo,SysUser user);

    /**
     * 采购申请中重用单据
     * @param vo
     * @param user
     * @return
     */
    int reusing(PsxVo vo,SysUser user);

    /**
     * 查询采购申请可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(PsxVo vo);

    //查询单条
    PsxVo selectByOnly(PsxVo vo);
}
