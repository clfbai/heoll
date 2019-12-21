package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.wareh.GdnModel;

/**
 * 出库单外部调用接口
 * @author HHe
 * @date 2019/12/3 15:21
 */
public interface GdnExternalCiteService {
    /**
     * 添加出库单和明细
     *
     * @param
     * @return
     * @author HHe
     * @date 2019/11/6 10:10
     */
    String addGdnAndDtl(GdnModel gdnModel, SysUser sysUser);
    /**
     * 根据原始单据组织id、类别、编号和收货方id查询仓库id
     * @param srcDocType 原始单据类别
     * @param srcDocNum 原始单据编号
     * @param srcDocUnitId 原始单据组织id
     * @param rcvUnitId 收货方Id
     * @return warehId 仓库Id
     * @author HHe
     * @date 2019/9/16 10:01
     */
    Long queryDelivWarehIdBySrcDocMess(String srcDocType,String srcDocNum,Long srcDocUnitId,Long rcvUnitId);

    /**
     * 根据原始单据组织id、类别、编号和收货方id查询仓库id
     * @param srcDocType 原始单据类别
     * @param srcDocNum 原始单据编号
     * @param srcDocUnitId 原始单据组织id
     * @param delivUnitId 发货方Id
     * @return warehId 仓库Id
     * @author wz
     * @date 2019/9/16 10:01
     */
    Long queryRcvWarehIdBySrcDocMess(String srcDocType,String srcDocNum,Long srcDocUnitId,Long delivUnitId);
}
