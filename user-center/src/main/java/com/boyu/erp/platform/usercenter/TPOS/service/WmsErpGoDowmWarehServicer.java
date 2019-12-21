package com.boyu.erp.platform.usercenter.TPOS.service;

import com.boyu.erp.platform.usercenter.TPOS.entity.CancelOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.sales.SalesOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.EntryOrderModel;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;

/**
 * 类描述:
 *
 * @Description: C-WMS 入库单 接口
 * @auther: CLF
 * @date: 2019/11/5 19:39
 */

public interface WmsErpGoDowmWarehServicer {

    /**
     * 功能描述: 创建入库单对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/5 19:41
     */
    EntryOrderModel createrEntryOrder(GrnDtlModel grnDtlModel, SysUser user) throws ServiceException, Exception;

    /**
     * 功能描述: 创建退货入库单对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/5 19:41
     */
    SalesOrder createSalesOrder(GrnDtlModel grnDtlModel, SysUser user) throws ServiceException, Exception;

    /**
     * 生成入库单创建URL 并发送消息入库单创建消息
     */
    ResponseOrder createEntryOrderPostURL(EntryOrderModel entryOrderModel, SysUser user) throws ServiceException, Exception;


    //order.cancel

    /**
     * 功能描述: 发送取消单据
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 10:24
     */
    ResponseOrder createOrderCancelURL(CancelOrder cancelOrder, String name, SysUser user) throws Exception;

    /**
     * 功能描述: 创建入库单取消对象
     *
     * @param orderId      erp生成单据Id
     * @param warehRcvTask 入库任务
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 10:42
     */
    CancelOrder createGrnCancelOrder(String orderId, WarehRcvTask warehRcvTask);

}
