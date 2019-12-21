package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PsoDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname PucService
 * @Description TODO
 * @Date 2019/7/4 20:05
 * @Created wz
 */
public interface PucService {

    /**
     * 查询采购合同
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    public PageInfo<PscVo> selectAll(PscVo vo,Integer page, Integer size, SysUser sysUser) throws Exception;

    /**
     * 添加采购合同
     * @param vo
     * @return
     */
    int insertPuc(PscVo vo,SysUser sysUser) throws Exception;

    /**
     * 修改采购合同
     * @param vo
     * @return
     */
    int updatePuc(PscVo vo,SysUser sysUser)throws Exception;

    /**
     * 删除采购合同
     * @param vo
     * @return
     */
    int deletePuc(PscVo vo,SysUser sysUser) throws Exception;

    /**
     * 采购合同中确认单据
     * @param vo
     * @param sysUser
     * @return
     */
    int confirm(PscVo vo, SysUser sysUser);

    /**
     * 采购合同中重做单据
     * @param vo
     * @return
     */
    int redo(PscVo vo);

    /**
     * 采购合同中审核单据
     * @param vo
     * @param sysUser
     * @return
     */
    int examine(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 采购合同中反审单据
     * @param vo
     * @return
     */
    int reiterate(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 取消关联
     * @return
     */
    void relation(String pscNum, SysUser sysUser) throws Exception;

    /**
     * 销售合同中删除采购合同
     * @param vo
     * @return
     */
    int deleteByPuc(PscVo vo);

    /**
     * 销售合同新增采购合同
     */
    int insertByPuc(PscVo vo, SysUser user);

    /**
     * 销售合同中通知采购合同审核
     * @param vo
     * @param user
     * @return
     */
    int noticeExamine(PscVo vo, SysUser user) throws Exception;

    /**
     * 销售合同中通知采购合同重审
     * @param vo
     * @return
     */
    int noticeReiterate(PscVo vo, SysUser user) throws Exception;

    /**
     * 采购合同中关闭
     * @param vo
     * @param user
     * @return
     */
    int close(PscVo vo, SysUser user) throws Exception;

    /**
     * 采购合同中重用
     * @param vo
     * @param user
     * @return
     */
    int reusing(PscVo vo, SysUser user) throws Exception;

    /**
     * 采购合同中挂起
     * @param vo
     * @param user
     * @return
     */
    int hangUp(PscVo vo, SysUser user);

    /**
     * 采购合同中恢复
     * @param vo
     * @param user
     * @return
     */
    int recovery(PscVo vo, SysUser user);

    /**
     * 采购合同中作废
     * @param vo
     * @param user
     * @return
     */
    int toVoid(PscVo vo, SysUser user) throws Exception;

    /**
     * 采购合同中导入
     * @param vo
     * @return
     */
    Map<String,Object> importBill(PscVo vo);

    /**
     * 采购合同/销售合同中导入更新
     * @param vo
     * @return
     */
    int importUpdate(PscVo vo, SysUser user) throws Exception;

    /**
     * 采购合同分配接口
     * @param vo
     * @return
     */
    void admeasure(PscVo vo);

    /**
     * 采购合同取消分配接口
     * @param vo
     * @return
     */
    void reverseAdmeasure(PscVo vo);

    /**
     * 采购合同后天配货调整
     * @param vo
     * @param dtlList
     * @param reversed
     */
    void postAdmeasure(PscVo vo, List<CommonDtl> dtlList, boolean reversed);

    //判断是否居间合同
    boolean contract(PscVo vo);

    //采购单/销售单所需数据
    Map<String,Object> getExbl(String pscNum);

    //采购单/销售单所需数据
    Map<String,Object> getFixedPrice(String pscType,String pscNum);

    /**
     * 查询采购合同可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(PscVo vo);

    /**
     * 销售合同关闭通知采购合同供应商关闭事件
     */
    void venderClosed(PscVo vo, SysUser user) throws Exception;

    /**
     * 销售合同重用通知采购合同供应商重用事件
     * @param vo
     * @param user
     */
    void venderReused(PscVo vo, SysUser user) throws Exception;

    /**
     * 销售合同通知采购合同供应商作废事件
     * @param vo
     * @param user
     */
    void venderAbolished(PscVo vo, SysUser user) throws Exception;

    /**
     * 查询单条
     * @param vo
     * @return
     */
    PscVo selectByOnly(PscVo vo);

    /**
     * 查询出入库详细
     * @param vo
     * @return
     */
    PscVo selectObject(PscVo vo);

    /**
     * 供货方发货通知
     * @param vo
     * @param docUnitId 库存单组织id
     * @param docNum 库存单编号
     * @param sysUser
     * @return
     */
    void venderDelivered(PscVo vo, Long docUnitId, String docNum, SysUser sysUser) throws Exception;

    /**
     * 供货方取消发货通知
     * @param vo
     * @param docUnitId 库存单组织id
     * @param docNum 库存单编号
     * @param sysUser
     * @return
     */
    void venderReverseDelivered(PscVo vo, Long docUnitId, String docNum, SysUser sysUser) throws Exception;

    /**
     * 采购合同开始发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int startReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 采购合同终止收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int stopReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 采购合同收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 入库单组织id
     * @param docNum 入库单据编号
     * @param user  用户
     * @return
     */
    int receive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 采购合同取消收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int reverseReceive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 退货发货
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    int returnDeliver(String pscNum, Long docUnitId, String docNum);

    /**
     * 退货取消发货
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    int reverseReturnDeliver(String pscNum, Long docUnitId, String docNum);

    /**
     * 开始分配
     * @param vo
     */
    void startAdmeasure(PscVo vo);

    /**
     * 终止分配
     * @param vo
     */
    void stopAdmeasure(PscVo vo);
}
