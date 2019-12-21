package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 销售申请接口
 * @author: wz
 * @create: 2019-9-4 19:41
 */
public interface SlaService {

    /**
     * 分页查询
     * @param sla
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws ServiceException
     */
    PageInfo<PsxVo> selectAll(PsxVo sla, Integer page, Integer size, SysUser sysUser) throws ServiceException;

    /**
     * 添加销售申请
     * @param sla
     * @param sysUser
     * @return
     * @throws Exception
     */
    int insertSla(PsxVo sla, SysUser sysUser) throws Exception;

    /**
     * 修改销售申请
     * @param sla
     * @param sysUser
     * @return
     * @throws Exception
     */
    int updateSla(PsxVo sla, SysUser sysUser) throws Exception;

    /**
     * 销售申请删除
     * @param psa
     * @return
     */
    int deleteSla(PsxVo psa);

    /**
     * 采购申请生成销售申请
     * @param vo
     * @return
     */
    int insertByPua(PsxVo vo, SysUser sysUser);

    /**
     * 销售申请中确认单据
     * @param vo
     * @param sysUser
     * @return
     */
    int confirm(PsxVo vo, SysUser sysUser);

    /**
     * 销售申请中重做单据
     * @param vo
     * @return
     */
    int redo(PsxVo vo, SysUser sysUser);

    /**
     * 销售申请中审核单据
     * @param vo
     * @param sysUser
     * @return
     */
    int examine(PsxVo vo, SysUser sysUser);

    /**
     * 采购申请通知销售申请审核
     * @param vo
     * @param sysUser
     * @return
     */
    int noticeExamine(PsxVo vo, SysUser sysUser);

    /**
     * 销售申请中重审单据
     * @param vo
     * @param sysUser
     * @return
     */
    int reiterate(PsxVo vo, SysUser sysUser);

    /**
     * 采购申请中通知销售申请重审
     * @param vo
     * @param user
     * @return
     */
    int noticeReiterate(PsxVo vo,SysUser user);

    /**
     * 销售申请中挂起单据
     * @param vo
     * @param user
     * @return
     */
    int hangUp(PsxVo vo,SysUser user);

    /**
     * 销售申请中恢复单据
     * @param vo
     * @param user
     * @return
     */
    int recovery(PsxVo vo,SysUser user);

    /**
     * 销售申请中作废单据
     * @param vo
     * @param user
     * @return
     */
    int toVoid(PsxVo vo,SysUser user);

    /**
     * 销售申请中关闭单据
     * @param vo
     * @param user
     * @return
     */
    int close(PsxVo vo,SysUser user);

    /**
     * 销售申请中重用单据
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
