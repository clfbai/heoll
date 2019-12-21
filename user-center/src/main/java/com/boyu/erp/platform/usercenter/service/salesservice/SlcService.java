package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 销售合同接口
 * @author: wz
 * @create: 2019-8-9 11:07
 */
public interface SlcService{

    /**
     * 查询销售合同
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    public PageInfo<PscVo> selectAll(PscVo vo, Integer page, Integer size, SysUser sysUser) throws Exception;

    /**
     * 采购合同中新增销售合同
     * @param vo
     * @return
     */
    int insertByPuc(PscVo vo, SysUser user);

    /**
     * 采购合同中删除销售合同
     * @param vo
     * @return
     */
    int deleteByPuc(PscVo vo);

    /**
     * 新增销售合同
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    String insertSlc(PscVo vo, SysUser user) throws Exception;

    /**
     * 修改销售合同
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    int updateSlc(PscVo vo, SysUser user) throws Exception;

    /**
     * 删除销售合同
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    int deleteSlc(PscVo vo, SysUser user) throws Exception;

    /**
     * 取消关联
     * @param pscNum
     * @param user
     * @return
     */
    void relation(String pscNum, SysUser user) throws Exception;

    /**
     *销售确认
     */
    int confirm(PscVo vo, SysUser sysUser);

    /**
     * 销售重做
     * @param vo
     * @return
     */
    int redo(PscVo vo);

    /**
     * 销售审核
     * @param vo
     * @param sysUser
     * @return
     */
    int examine(PscVo vo, SysUser sysUser, int orderType) throws Exception;

    /**
     * 销售反审
     * @param vo
     * @return
     */
    int reiterate(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 销售关闭
     * @param vo
     * @param sysUser
     * @return
     */
    int close(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 销售合同重用单据
     * @param vo
     * @param sysUser
     * @return
     */
    int reusing(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 销售合同挂起单据
     * @param vo
     * @param sysUser
     * @return
     */
    int hangUp(PscVo vo, SysUser sysUser);

    /**
     * 销售合同恢复单据
     * @param vo
     * @param sysUser
     * @return
     */
    int recovery(PscVo vo, SysUser sysUser);

    /**
     * 销售合同作废单据
     * @param vo
     * @param sysUser
     * @return
     */
    int toVoid(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 销售合同中导入
     * @param vo
     * @return
     */
    Map<String,Object> importBill(PscVo vo);

    /**
     * 采购合同中反审时通知销售合同反审
     * @param vo
     * @return
     */
    int noticeReiterate(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 采购合同中审核时通知销售合同审核
     * @return
     */
    int noticeExamine(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 采购合同中关闭通知销售合同采购商关闭事件
     * @param vo
     * @param sysUser
     */
    void vendeeClosed(PscVo vo, SysUser sysUser) throws Exception;

    /**
     * 采购合同通知销售合同重用事件
     * @param vo
     * @param sysUse
     */
    void vendeeReused(PscVo vo, SysUser sysUse) throws Exception;

    /**
     * 采购合同通知销售合同采购商作废事件
     * @param vo
     * @param sysUse
     */
    void vendeeAbolished(PscVo vo, SysUser sysUse) throws Exception;

    /**
     * 查询销售合同可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(PscVo vo);

    /**
     * 销售合同分配接口
     * @param vo
     * @return
     */
    void admeasure(PscVo vo, List<CommonDtl> comList);

    /**
     * 销售合同取消分配接口
     * @param vo
     * @param comList
     * @return
     */
    void reverseAdmeasure(PscVo vo,List<CommonDtl> comList);

    /**
     * 调配单审核时生成销售合同  并确认以及审核
     * @param vo
     * @param user
     */
    String creatSlcByRgo(RgoVo vo, SysUser user, RgoType rgoType) throws Exception;

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
     * 判断是否居间
     * @param vo
     * @return
     */
    boolean contract(PscVo vo);

    /**
     * 销售合同开始发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int startDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user);

    /**
     * 销售合同终止发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @return
     */
    int stopDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 销售合同发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int deliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 销售合同取消发货
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum 原始单据编号
     * @param docUnitId 出库单组织id
     * @param docNum 出库单据编号
     * @param user  用户
     * @return
     */
    int reverseDeliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception;

    /**
     * 购货方收货通知
     * @param vo
     * @param stb
     * @return
     */
    void vendeeReceived(PscVo vo, Stb stb);

    /**
     * 购货方取消收货通知
     * @param vo
     * @param stb
     * @return
     */
    void vendeeReverseReceived(PscVo vo, Stb stb);

    /**
     * 退货收货
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    int returnReceive(String pscNum, Long docUnitId, String docNum);

    /**
     * 取消退货收货
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    int reverseReturnReceive(String pscNum, Long docUnitId, String docNum);

    /**
     * 后天配货调整
     * @param vo
     * @param comList
     */
    void postAdmeasure(PscVo vo, List<CommonDtl> comList, boolean flag);

}
