package com.boyu.erp.platform.usercenter.entity.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * grn    (入库单表)
 *
 * @author
 */
@Data
@ToString
public class Grn implements Serializable {
    public Grn(long unitId, String grnNum) {
        this.unitId = unitId;
        this.grnNum = grnNum;
    }
    public Grn() {
    }

    /**
     * 组织ID
     */
    @NotNull(message = "组织id不能为空")
    private long unitId;

    /**
     * 入库单编号
     */
    @NotBlank(message = "入库单编号不能为空")
    private String grnNum;

    /**
     * 入库方式
     */
    private String rcvMode;

    /**
     * 会计入库方式
     */
    private String fsclRcvMode;

    /**
     * 发货方ID
     */
    private Long delivUnitId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 发货会计组织ID
     */
    private Long delivFsclUnitId;

    /**
     * 始发方ID
     */
    private Long stUnitId;

    /**
     * 始发仓库ID
     */
    private Long stWarehId;

    /**
     * 启用验收
     */
    private String acptReqd;

    /**
     * 启用分储
     */
    private String paReqd;

    /**
     * 收货人ID
     */
    private Long tkovrId;

    /**
     * 收货开始时间
     */
    private Date tkovStTime;

    /**
     * 收货结束时间
     */
    private Date tkovFinTime;

    /**
     * 接收人ID
     */
    private Long rcvrId;

    /**
     * 收货时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date rcvTime;

    /**
     * 存放人ID
     */
    private Long storerId;

    /**
     * 存放开始时间
     */
    private Date strStTime;

    /**
     * 存放结束时间
     */
    private Date strFinTime;

    /**
     * 进度  C确认、PG以保存、RD 已入库 SD 以存放、SI 存放中、TD已收货、TI收货中
     */
    private String progress;
    /**
     * 是否显示
     */
    private String showDoc;

    /**
     * 基准日期 1 代表 业务 2 代表 会计
     */
    private Integer baseInst;


}