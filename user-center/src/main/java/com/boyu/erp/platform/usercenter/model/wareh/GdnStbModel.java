package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 出库库存单
 * @author HHe
 * @date 2019/12/5 14:31
 */
@Data
public class GdnStbModel extends Stb implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 出库单编号
     */
    private String gdnNum;

    /**
     * 出库方式
     */
    private String delivMode;

    /**
     * 会计出库方式
     */
    private String fsclDelivMode;

    /**
     * 收货方ID
     */
    private Long rcvUnitId;

    /**
     * 收货仓库ID
     */
    private Long rcvWarehId;

    /**
     * 收货会计组织ID
     */
    private Long rcvFsclUnitId;

    /**
     * 转送方ID
     */
    private Long endUnitId;

    /**
     * 转送仓库ID
     */
    private Long endWarehId;

    /**
     * 送货方式
     */
    private String delivMthd;

    /**
     * 邮政编码
     */
    private String delivPstd;

    /**
     * 送货地址
     */
    private String delivAddr;

    /**
     * 启用分拣
     */
    private String pickReqd;

    /**
     * 启用复核
     */
    private String rckReqd;

    /**
     * 启用派车
     */
    private String vehReqd;

    /**
     * 派车单编号
     */
    private String vwcNum;

    /**
     * 启用托运
     */
    private String cnsnReqd;

    /**
     * 托运单号
     */
    private String csbNum;

    /**
     * 拣货人ID
     */
    private Long ftchrId;

    /**
     * 拣货开始时间
     */
    private Date ftchStTime;

    /**
     * 拣货结束时间
     */
    private Date ftchFinTime;

    /**
     * 发货人ID
     */
    private Long dlvrId;

    /**
     * 发货时间
     */
    private Date delivTime;

    /**
     * 托运人ID
     */
    private Long cngnrId;

    /**
     * 托运时间
     */
    private Date cnsnTime;

    /**
     * 进度
     */
    private String progress;
    /**
     * 是否显示
     */
    private String showDoc;
}
