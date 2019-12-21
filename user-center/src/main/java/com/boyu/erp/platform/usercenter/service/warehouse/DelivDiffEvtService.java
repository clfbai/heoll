package com.boyu.erp.platform.usercenter.service.warehouse;
/**
 * 出库差异事件接口
 * @author HHe
 * @date 2019/10/29 9:53
 */
public interface DelivDiffEvtService {
    /**
     * 登记出库差异事件
     * @param unitId 组织Id
     * @param gdnNum 出库单编号
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/29 11:00
     */
    int register(Long unitId, String gdnNum);
    /**
     * 撤销出库差异事件
     * @param unitId 组织Id
     * @param gdnNum 出库单编号
     * @return
     * @author HHe
     * @date 2019/12/2 15:59
     */
    int revocation(Long unitId, String gdnNum);
}
