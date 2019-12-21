package com.boyu.erp.platform.usercenter.TPOS.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * 取消单据对象
 * @author HHe
 * @date 2019/11/4 20:14
 */
@Data
@XmlRootElement(name = "request")
public class CancelOrder implements Serializable {
    /**
     * 仓库编码 必填
     */
    private String warehouseCode;
    /**
     * 货主编码
     */
    private String ownerCode;
    /**
     * 单据编码 必填
     */
    private String orderCode;
    /**
     * 仓储系统单据编码 条件必填
     */
    private String orderId;
    /**
     * 单据类型  JYCK= 一般交易出库单，HHCK= 换货出库 ，BFCK= 补发出库 PTCK=普通出库单，DBCK=调拨出库 ，B2BRK=B2B入库，B2BCK=B2B出库，QTCK=其他出库， SCRK=生产入库，LYRK=领用入库，CCRK=残次品入库，CGRK=采购入库 ，DBRK= 调拨入库 ，QTRK= 其他入库 ，XTRK= 销退入库 HHRK= 换货入库 CNJG= 仓内加工单
     */
    private String orderType;
    /**
     * 取消原因
     */
    private String cancelReason;
}
