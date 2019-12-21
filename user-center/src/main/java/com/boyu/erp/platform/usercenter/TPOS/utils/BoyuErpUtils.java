package com.boyu.erp.platform.usercenter.TPOS.utils;

import com.boyu.erp.platform.usercenter.TPOS.common.confirm.WarehSingConfirm;
import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTask;
import com.boyu.erp.platform.usercenter.TPOS.model.DeliveryOrderConfirmModel;
import com.boyu.erp.platform.usercenter.TPOS.service.DhOrdTaskService;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.warehouse.GdnService;
import com.boyu.erp.platform.usercenter.service.warehouse.GrnService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DeliveryDefineUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BoyuErpUtils {
    @Autowired
    private RequestTPOService requestTPOService;
    @Autowired
    private GrnService grnService;
    @Autowired
    private DhOrdTaskService dhOrdTaskService;
    @Autowired
    private GdnService gdnService;
    /**
     * WMS确认出库单接口
     */
    private static final String CONFIRM_GDN = "stockout.confirm";
    /**
     * 功能描述:
     *
     * @param xml    外部(xml格式)请求体
     * @param method 请求内部方法
     * @return:
     * @auther: CLF
     * @date: 2019/11/21 15:47
     */
    public void boyuErpService(String xml, String method) throws Exception {
        if (StringUtils.isBlank(xml)) {
            throw new ServiceException("403", "请求体为空");
        }
        if (StringUtils.isBlank(method)) {
            throw new ServiceException("403", "请求Method不能为空");
        }
        /**
         * 入库单确认
         */
        if (method.equals("taobao.qimen.entryorder.confirm")) {
            WarehSingConfirm warehSingConfirm = requestTPOService.createStringXml(xml, WarehSingConfirm.class);
            System.out.println(warehSingConfirm);
            grnService.warehSingConfirm(warehSingConfirm);
        }
        /**
         * 出库单确认
         * @author HHe
         * @date 2019/12/4 15:41
         */
        if(method.equals(CONFIRM_GDN)){
            //请求体转对象
            JaxbUtil jaxbUtil = new JaxbUtil(DeliveryOrderConfirmModel.class);
            DeliveryOrderConfirmModel deliveryOrderConfirmModel = jaxbUtil.fromXml(xml);
            DhOrdTask dhOrdTask = new DhOrdTask();
            dhOrdTask.setHandleType(CONFIRM_GDN);
            dhOrdTask.setId(deliveryOrderConfirmModel.getDeliveryOrder().getDeliveryOrderCode());
            dhOrdTask.setParams("PG");
            dhOrdTask.setBizType(1);
            dhOrdTask.setWaiting("T");
            dhOrdTask.setImplTimes(0);
            dhOrdTask.setExecuteStatus(DeliveryDefineUtil.executeStatus.requesting.toString());
            Date nowDate = new Date();
            dhOrdTask.setRegTime(nowDate);
            dhOrdTask.setOpTime(nowDate);
            dhOrdTask.setContent(xml);
            //登记下发任务
            dhOrdTaskService.registerDhOrdTask(dhOrdTask);
            //异步处理出库单
            gdnService.wmsConfirmGdn(dhOrdTask,xml);
        }
    }

}
