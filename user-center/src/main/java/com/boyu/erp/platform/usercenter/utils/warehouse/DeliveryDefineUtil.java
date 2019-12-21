package com.boyu.erp.platform.usercenter.utils.warehouse;
/**
 * 出库字段定义
 * @author HHe
 * @date 2019/12/1 11:13
 */
public class DeliveryDefineUtil {
    /**
     * 成功代码
     */
    public static final String SUCC_CODE = "200";
    /**
     * 失败编号
     */
    public static final String FAIN_CODE = "201";
    /**
     * 出库任务组合展示：收货方
     */
    public static final String GROUP_RCVUNIT = "1";
    /**
     * 出库任务组合展示：收货方仓库
     */
    public static final String GROUP_RCVWAREH= "2";
    /**
     * 原单开始发货
     */
    public static final String START_DELIVER = "startDeliver";
    /**
     * 字符串true
     */
    public static final String BOOLEAN_STR_T = "T";
    /**
     * 字符串false
     */
    public static final String BOOLEAN_STR_F = "F";
    /**
     * 计量单位
     */
    public static final String CODE_UOM="UOM";
    /**
     * 版型
     */
    public static final String CODE_EDITION = "EDITION";
    /**
     * 内订
     */
    public static final String INTERNAL_ORDER = "ITOD";
    /**
     * 内退
     */
    public static final String INTERNAL_RETURN = "ITRT";
    /**
     * 调拨
     */
    public static final String TRANSFER = "TRAN";
    /**
     * 销售
     */
    public static final String SELL = "SELL";
    public static final String PURCHASE_RETURN = "PURT";
    public static final String DIRECT_SELL = "DISL";
    public static final String DIRECT_PURCHASE_RETURN = "DIPR";
    public static final String RETAIL = "RETL";
    public static final String COMMISSION_SELL = "CMSL";
    public static final String COMMISSIONED_PURCHASE_RETURN = "CDPR";
    /**
     * 零售单据说
     */
    public static final String RETAIL_ORDER = "RLB";
    /**
     * 调拨库存挂账方参数Id
     */
    public static final String TRANSFER_STOCK_HOLDER = "TRANSFER_STOCK_HOLDER";
    /**
     * 购销对应往来账户类别
     */
    public static final String PS_CA_TYPE = "PS_CA_TYPE";
    /**
     * 原单发货
     */
    public static final String DELIVER = "deliver";
    /**
     * 原单取消发货
     */
    public static final String REVERSE_DELIVER = "reverseDeliver";
    /**
     * 终止发货
     */
    public static final String STOP_DELIVER = "stopDeliver";
    /**
     * 仓库类型
     */
    public static final String WAREH_TYPE = "WH";
    /**
     * 对应关系类型：供应商
     */
    public static final String VENDOR_TYPE = "VE";
    /**
     * 总部组织
     */
    public static final String HQ = "1";

    /**
     * 出库任务已生成出库单
     */
    public static final String YES_GENERATE = "YG";
    /**
     * 字段
     */
    public enum fie{
        /**
         * 基准日期：业务
         */
        doc,
        /**
         * 基准日期：会计
         */
        fscl
    }

    /**
     * 执行状态
     */
    public enum executeStatus{
        /**
         * 等待中
         */
        requesting,
        /**
         * 成功
         */
        success,
        /**
         * 失败
         */
        failure
    }

    /**
     * 出库方式
     */
    public enum DELIV_MODE{
        /**
         * 其他
         */
        OTHR,
        DISL
    }
}
