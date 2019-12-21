package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 退销合同接口
 * @author: wz
 * @create: 2019-8-13 16:07
 */
public interface SrcService{

    PageInfo<PrcVo> selectAll(PrcVo vo, Integer page, Integer size, SysUser sysUser);
    /**
     * 退购合同中确认生成退销合同
     * @param vo
     * @param user
     * @return
     */
    int insertByPrc(PrcVo vo, SysUser user);

    /**
     * 退购合同中删除退销合同
     * @param vo
     * @return
     */
    int deleteByPrc(PrcVo vo);

    /**
     * 新增退销合同
     * @param vo
     * @param user
     * @return
     */
    String insertSrc(PrcVo vo, SysUser user);

    /**
     * 修改退销合同
     * @param vo
     * @param user
     * @return
     */
    int updateSrc(PrcVo vo, SysUser user);

    /**
     * 删除退销合同
     * @param vo
     * @param user
     * @return
     */
    int deleteSrc(PrcVo vo, SysUser user)throws Exception;

    /**
     * 退购合同中取消关联
     * @param rtcNum
     * @param user
     * @return
     */
    void relation(String rtcNum, SysUser user) throws Exception;

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
    int examine(PrcVo vo, SysUser sysUser, int orderType) throws Exception;

    /**
     * 重审单据
     * @param vo
     * @return
     */
    int reiterate(PrcVo vo, SysUser user) throws Exception;

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
     * 退购合同可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(PrcVo vo);

    //退购合同通知退销合同审核事件
    int noticeExamine(PrcVo vo, SysUser user) throws Exception;

    /**
     * 退购合同通知退销合同重审事件
     * @param vo
     * @return
     */
    int noticeReiterate(PrcVo vo, SysUser user) throws Exception;

    //退购合同通知退销合同采购商关闭事件
    void vendeeClosed(PrcVo vo, SysUser user) throws Exception;

    //退购合同通知退销合同采购商重用事件
    void vendeeReused(PrcVo vo, SysUser user) throws Exception;

    //退购合同通知退销合同采购商作废事件
    void vendeeAbolished(PrcVo vo, SysUser user) throws Exception;

    //调配单审核时生成退销合同  并确认以及审核
    String creatSrcByRgo(RgoVo vo, SysUser user, RgoType rgoType) throws Exception;

    //查询单条记录
    PrcVo selectByOnly(PrcVo vo);

    /**
     * 查询出入库详细
     * @param vo
     * @return
     */
    PrcVo selectObject(PrcVo vo);

    /**
     * 导入退购合同明细
     * @param vo
     * @return
     */
    Map<String, Object> importBill(PrcVo vo);

    /**
     * 导入更新
     * @param vo
     * @return
     */
    int importUpdate(PrcVo vo);

    /**
     * 退货方发货通知
     * @param vo
     * @param stb
     * @param sysUser
     * @return
     */
    void vendeeDelivered(PrcVo vo, Stb stb, SysUser sysUser) throws Exception;

    /**
     * 退货方取消发货通知
     * @param vo
     * @param stb
     * @param sysUser
     * @return
     */
    int vendeeReverseDelivered(PrcVo vo, Stb stb, SysUser sysUser) throws Exception;


    /**
     * 退销合同开始收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int startReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 退销合同终止收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int stopReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 退销合同收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int receive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 退销合同取消收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int reverseReceive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

}
