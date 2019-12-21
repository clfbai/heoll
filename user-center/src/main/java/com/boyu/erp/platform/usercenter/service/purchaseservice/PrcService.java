package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname PrcService
 * @Description TODO
 * @Date 2019/7/25 11:21
 * @Created wz
 */
public interface PrcService{

    /**
     * 查询退购合同
     * @param page
     * @param size
     * @return
    * @throws ServiceException
     */
     PageInfo<PrcVo> selectAll(PrcVo vo, Integer page, Integer size, SysUser sysUser) throws Exception;

    /**
     * 添加退购合同
     * @param vo
     * @return
     */
    int insertPrc(PrcVo vo, SysUser sysUser);

    /**
     * 修改退购合同
     * @param vo
     * @return
     */
    int updatePrc(PrcVo vo, SysUser sysUser);

    /**
     * 删除退购合同
     * @param vo
     * @return
     */
    int deletePrc(PrcVo vo, SysUser sysUser) throws Exception;

    /**
     * 清除明细 更新退销合同
     * @param rtcNum
     * @param sysUser
     * @return
     */
    int deletePrcDtl(String rtcNum,SysUser sysUser);

    /**
     * 确认单据
     * @return
     */
    int confirm(PrcVo vo, SysUser sysUser);

    /**
     * 重做单据
     * @param vo
     * @return
     */
    int redo(PrcVo vo);

    /**
     * 审核单据
     * @param vo
     * @param sysUser
     * @return
     */
    int examine(PrcVo vo, SysUser sysUser) throws Exception;

    /**
     * 退销合同通知退购合同审核事件
     * @param vo
     * @return
     */
    int noticeExamine(PrcVo vo,SysUser user) throws Exception;

    /**
     * 反审单据
     * @return
     */
    int reiterate(PrcVo vo,SysUser user) throws Exception;

    /**
     * 退销合同中调退购合同重审事件
     * @param vo
     * @return
     */
    int noticeReiterate(PrcVo vo, SysUser user) throws Exception;


    /**
     * 挂起单据
     * @param vo
     * @param user
     * @return
     */
    int hangUp(PrcVo vo, SysUser user);

    /**
     * 恢复单据
     * @param vo
     * @param user
     * @return
     */
    int recovery(PrcVo vo, SysUser user);

    /**
     * 关闭单据
     * @param vo
     * @param user
     * @return
     */
    int close(PrcVo vo, SysUser user) throws Exception;

    /**
     * 重用单据
     * @param vo
     * @param user
     * @return
     */
    int reusing(PrcVo vo, SysUser user) throws Exception;

    /**
     * 作废单据
     * @param vo
     * @param user
     * @return
     */
    int toVoid(PrcVo vo, SysUser user) throws Exception;

    /**
     * 退销合同中删除退购合同
     */
    int deleteByPrc(PrcVo vo);

    /**
     * 取消关联
     * @param rtcNum
     * @param user
     */
    void relation(String rtcNum,SysUser user) throws Exception;

    /**
     * 退销合同中确认生成退购合同
     * @param vo
     * @param user
     * @return
     */
    int insertByPrc(PrcVo vo, SysUser user);

    /**
     * 退购合同可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(PrcVo vo);

    /**
     * 判断是否居间合同
     * @param vo
     * @return
     */
    boolean contract(PrcVo vo);

    //退销合同通知退购合同供应商关闭事件
    void venderClosed(PrcVo vo, SysUser user) throws Exception;

    //退销合同通知退购合同供应商重用事件
    void venderReused(PrcVo vo, SysUser user) throws Exception;

    //退销合同通知退购合同供应商作废事件
    void venderAbolished(PrcVo vo, SysUser user) throws Exception;

    /**
     * 查询单条记录
     * @param vo
     * @return
     */
    PrcVo selectByOnly(PrcVo vo);

    /**
     * 查询出入库详细
     * @param vo
     * @return
     */
    PrcVo selectObject(PrcVo vo);

    //是否固定单价
    Map<String,Object> getFixedPrice(String rtcType);

    /**
     * 退购合同开始发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int startDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user);

    /**
     * 退购合同终止发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int stopDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 退购合同发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int deliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 退购合同取消发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int reverseDeliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;
}
