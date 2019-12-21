package com.boyu.erp.platform.usercenter.TPOS.entity.common;

import lombok.Data;

/**
 * 类描述:
 *
 * @Description: 入库单 和入库确认公共属性
 * @auther: CLF
 * @date: 2019/11/6 11:37
 */
@Data
public class GoDownBase {
    /**
     * 单据总行数，int，当单据需要分多个请求发送时，发送方需要将totalOrderLines填入，
     * 接收方收到后，根据实际接收到的条数和totalOrderLines进行比对，
     * 如果小于，则继续等待接收请求。如果等于，则表示该单据的所有请求发送完成。
     */
    private Integer totalOrderLines;
    /**
     * 入库单编码必填(ERP 生成的入库单编号)
     */
    private String entryOrderCode;
    /**
     * 货主编码  必填
     */
    private String ownerCode;
    /**
     * 仓库编码,必填
     */
    private String warehouseCode;
    /**
     * 操作时间,YYYY-MM-DD HH:MM:SS，(当status=FULFILLED,operateTime为入库时间)
     */
    private String operateTime;
    /**
     * 备注
     */
    private String remark;

}
