package com.boyu.erp.platform.usercenter.service.purchaseservice;


import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.purchase.Pso;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname PuoService
 * @Description TODO
 * @Date 2019/7/17 18:08
 * @Created wz
 */

public interface PuoService{

    /**
     * 分页查询
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<PsoVo> selectAll(PsoVo vo, Integer page, Integer size, SysUser sysUser);

    /**
     * 添加
     * @param vo
     * @param sysUser
     * @return
     */
    int insertPuo(PsoVo vo, SysUser sysUser);

    /**
     * 更新
     * @param vo
     * @param sysUser
     * @return
     */
    int updatePuo(PsoVo vo, SysUser sysUser);

    /**
     * 删除
     * @param vo
     * @return
     */
    int deletePuo(PsoVo vo);

    /**
     * 通过单号清空明细
     * @param psoNum
     * @return
     */
    int deleteByPsoDtl(String psoNum);

    //确认单据
    int confirm(PsoVo vo, SysUser sysUser);

    //重做单据
    int redo(PsoVo vo);

    //审核单据
    int examine(PsoVo vo, SysUser sysUser) throws Exception;

    //反审单据
    int reiterate(PsoVo vo, SysUser sysUser) throws Exception;

    //挂起单据
    int hangUp(PsoVo vo, SysUser sysUser);

    //恢复单据
    int recovery(PsoVo vo, SysUser sysUser);

    //作废单据
    int toVoid(PsoVo vo, SysUser sysUse) throws Exception;

    //结束入库
    int closeGoods(PsoVo vo, SysUser sysUse) throws Exception;

    //重启入库
    int restartGoods(PsoVo vo, SysUser sysUse) throws Exception;

    //导入采购合同明细
    Map<String,Object> importBill(PsoVo vo);

    //导入更新
    int importUpdate(PsoVo vo);

    //销售单通知采购单审核事件
    int noticeExamine(PsoVo vo, SysUser user) throws Exception;

    //销售单通知采购单重审事件
    int noticeReiterate(PsoVo vo, SysUser user) throws Exception;

    /**
     * 销售单中删除采购单
     * @param vo
     * @return
     */
    int deleteByPuo(PsoVo vo);

    /**
     * 销售单中创建采购单
     * @param vo
     * @param user
     * @return
     */
    int insertByPuo(PsoVo vo, SysUser user);

    /**
     * 关闭单据
     * @param vo
     * @param user
     * @return
     */
    int close(PsoVo vo,SysUser user);

    /**
     * 重用单据
     * @param vo
     * @param user
     * @return
     */
    int reusing(PsoVo vo,SysUser user);

    /**
     * 销售单通知采购单供应商作废事件
     * @param vo
     * @param user
     */
    void venderAbolished(PsoVo vo,SysUser user) throws Exception;

    /**
     * 查询采购单可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(PsoVo vo);

    /**
     * 查询单条记录
     * @param vo
     * @return
     */
    PsoVo selectByOnly(PsoVo vo);

    /**
     * 查询出入库详细
     * @param vo
     * @return
     */
    PsoVo selectObject(PsoVo vo);

    /**
     * 供货方发货通知。
     * @param psc
     * @param vo
     * @param docUnitId
     * @param docNum
     * @return
     */
    void venderDelivered(PscVo psc, PsoVo vo, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 供货方取消发货通知
     * @param psc
     * @param vo
     * @param docUnitId
     * @param docNum
     * @param user
     */
    void venderReverseDelivered(PscVo psc,PsoVo vo, Long docUnitId, String docNum,SysUser user) throws Exception;

    /**
     * 采购单开始收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int startReceive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 采购单终止收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int stopReceive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 采购单收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int receive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 采购单取消收货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int reverseReceive(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

}
