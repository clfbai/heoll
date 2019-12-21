package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.boyu.erp.platform.usercenter.vo.sales.SloVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 销售单接口
 * @author: wz
 * @create: 2019-8-12 15:27
 */
public interface SloService{

    PageInfo<PsoVo> selectAll(PsoVo vo, Integer page, Integer size, SysUser sysUser);
    /**
     * 采购单中创建销售单
     * @param vo
     * @param user
     * @return
     */
    int insertByPuo(PsoVo vo, SysUser user);

    /**
     * 采购单中删除销售单
     * @param vo
     * @return
     */
    int deleteByPuo(PsoVo vo);

    /**
     * 新增销售单
     * @param vo
     * @param user
     * @return
     */
    int insertSlo(PsoVo vo, SysUser user);

    /**
     * 修改销售单
     * @param vo
     * @param user
     * @return
     */
    int updateSlo(PsoVo vo, SysUser user);

    /**
     * 删除销售单
     * @return
     */
    int deleteSlo(PsoVo vo);

    /**
     * 确认单据
     * @param vo
     * @param sysUser
     * @return
     */
    int confirm(PsoVo vo, SysUser sysUser);

    /**
     * 重做单据
     * @param vo
     * @return
     */
    int redo(PsoVo vo);

    /**
     * 审核单据
     * @param vo
     * @param sysUser
     * @return
     */
    int examine(PsoVo vo, SysUser sysUser) throws Exception;

    /**
     * 反审单据
     * @param vo
     * @return
     */
    int reiterate(PsoVo vo, SysUser sysUser) throws Exception;

    /**
     * 挂起单据
     * @param vo
     * @param sysUser
     * @return
     */
    int hangUp(PsoVo vo, SysUser sysUser);

    /**
     * 恢复单据
     * @param vo
     * @param sysUser
     * @return
     */
    int recovery(PsoVo vo, SysUser sysUser);

    /**
     * 作废单据
     * @param vo
     * @param sysUser
     * @return
     */
    int toVoid(PsoVo vo, SysUser sysUser) throws Exception;

    //导入销售合同明细
    Map<String,Object> importBill(PsoVo vo);

    //采购单中通知销售单审核事件
    int noticeExamine(PsoVo vo, SysUser sysUser) throws Exception;

    //采购单通知销售单反审事件
    int noticeReiterate(PsoVo vo, SysUser sysUser) throws Exception;

    //采购单通知销售单采购商作废事件
    void vendeeAbolished(PsoVo vo, SysUser sysUser) throws Exception;

    /**
     * 查询销售单可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(PsoVo vo);

    //查询单条记录
    PsoVo selectByOnly(PsoVo vo);

    /**
     * 查询出入库详细
     * @param vo
     * @return
     */
    PsoVo selectObject(PsoVo vo);

    /**
     * 销售单开始发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int startDeliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user);

    /**
     * 销售单终止发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int stopDeliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 销售单发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int deliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 销售单取消发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int reverseDeliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

}
