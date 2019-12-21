package com.boyu.erp.platform.usercenter.TPOS.entity.godown;

import com.boyu.erp.platform.usercenter.TPOS.entity.common.ExtendProps;
import com.boyu.erp.platform.usercenter.TPOS.entity.common.GoDownBase;
import com.boyu.erp.platform.usercenter.TPOS.entity.common.SenderAndReceiverInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述:
 *
 * @Description: 入库单创建类
 * @auther: CLF
 * @date: 2019/11/6 11:45
 */
@Data
@NoArgsConstructor
public class EntryOrderCreate extends GoDownBase {

    /**
     * 采购单号，当单据类型 orderType=CGRK时使用
     */
    private String purchaseOrderCode;

    /**
     * 单据创建时间 YYYY-MM-DD HH:MM:SS
     */
    private String orderCreateTime;

    /**
     * 单据类型 (SCRK=生产入库，LYRK=领用入库，CCRK=残次品入库，CGRK=采购入库，DBRK=调拨入库, QTRK=其他入库，B2BRK=B2B入库(必传，只传英文编码)
     */
    private String orderType;
    /**
     * 预期到货时间 YYYY-MM-DD HH:MM:SS
     */
    private String expectStartTime;
    /**
     * 最迟预期到货时间, YYYY-MM-DD HH:MM:SS
     */
    private String expectEndTime;

    /**
     * 物流公司编码,SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通  、ZTO=中通 (ZTO) 、HTKY=百世汇通、UC=优速、STO=申通、
     * TTKDEX=天天快递  、QFKD=全峰、FAST=快捷、POSTB=邮政小包  、GTO=国通、YUNDA=韵达、JD=京东配送、DD=当当宅配、OTHER=其他(只传英文编码)
     */
    private String logisticsCode;
    /**
     * 物流公司名称
     */
    private String logisticsName;
    /**
     * 运单号,
     */
    private String expressCode;
    /**
     * 供应商编码 string (50)
     */
    private String supplierCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 操作员编码
     */
    private String operatorCode;
    /**
     * 操作员名称
     */
    private String operatorName;

    /**
     * 发件人
     */
    private SenderAndReceiverInfo senderInfo;

    /**
     * 收件人
     */
    private SenderAndReceiverInfo receiverInfo;

    /**
     * 扩展属性
     */
    private ExtendProps extendProps;
}
