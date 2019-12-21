package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.basic.Partner;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitOwner;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.model.system.CommonPopupModel;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.partner.VenVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.Purchase;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Classname PurchaseService
 * @Description TODO
 * @Date 2019/8/22 17:36
 * @Created wz
 */
public interface PurchaseService {

    /**
     * 出入库中获取原始单据信息
     *
     * @param type   单据类别
     * @param unitId 单据原始组织id
     * @param num    单据编号
     * @return
     */
    Purchase selectBill(String type, String unitId, String num);

    /**
     * 出入库中获取原始单据信息2
     *
     * @param type   单据类别
     * @param unitId 单据原始组织id
     * @param num    单据编号
     *               10-29
     * @return
     */
//    WarehSrcInfoVo selectWarehSrc(String type, String unitId, String num);

    /**
     * 出入库中获取原始单据信息2
     *
     * @param type   单据类别
     * @param unitId 单据原始组织id
     * @param num    单据编号
     *               10-29
     * @return
     */
    WarehSrcInfoVo selectWarehSrc(String type, String unitId, String num);

    /**
     * 出入库中获取原始单据信息
     *
     * @param type 单据类别
     * @param num  单据明细编号
     * @return
     */
    List<CommonDtl> selectBillInfo(String type, String num);

    /**
     * 出入库中获取原始单据信息  选择明细判断是否存在
     *
     * @param type 单据类别
     * @param num  单据明细编号
     * @param prodId  商品id
     * @return
     */
    List<CommonDtl> selectBillInfo2(String type, String num, Long prodId);

    /**
     * 新增采购商，供应商时，获取unit_id
     */
    Long addUnitId(String refNumId, SysUser user);


    //供应商/采购商 新增或修改伙伴信息
    void updatePartner(Partner partner);

    //供应商/采购商  新增或修改属主信息
    void updateOwner(SysUnitOwner sysUnitOwner);

    //创建默认往来账户 (供应商/采购商相对)
    void account(Long unitId, Long ownerId, Long oprId, String templetCode, String balDir, SysUser user);

    //创建默认往来账户(供应商/采购商相对)
    void accountBy(Long unitId, Long ownerId, Long oprId, String templetCode, String balDir, SysUser user);

    //根据购销单号判断是否监管往来
    boolean judgePsc(String pscNum);

    //根据退货单号判断是否监管往来
    boolean judgeRtc(String rtcNum);

    //监管往来简单公共方法（采购）
    void supervise(PscVo vo, SysUser user) throws Exception;

    //监管往来简单公共方法（销售）
    void superviseBySales(PscVo vo, SysUser user) throws Exception;

    /**
     * 采购合同中收发货累计数量/金额
     *
     * @param psa
     * @param docUnitId
     * @param docNum
     */
    void pscAccumulate(Psa psa, Long docUnitId, String docNum, boolean flag);

    /**
     * 采购中登记收发差异事件
     */
    void register(PscVo vo);

    /**
     * 判断输入得原始合同是否正确
     *
     * @param src
     * @return
     */
    int findByNum(Psc src);

    /**
     * 判断输入得购销合同是否正确
     *
     * @param src
     * @return
     */
    Psc findByPscNum(Psc src);

    /**
     * 通过输入当前组织判断是否是总部
     * @param unitId
     * @return
     */
    int getHeadByUnitId(Long unitId);

    /**
     * 公共方法
     * @param methonName 调用方法名
     * @param srcDocType
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @param user
     * @throws Exception
     */
    void publicMethon(String methonName,String srcDocType,Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception;

    /**
     * 输入查询公共接口
     * @param model
     * @return
     */
    Object getObject(CommonPopupModel model, SysUser user);

    /**
     * 采购商/供应商不可修改字段
     * @param type
     * @param methoyType
     * @return
     */
    List<TableFile> updateNotFile(String type , String methoyType);

}
