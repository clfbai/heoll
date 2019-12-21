package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * tran_diff_evt
 * @author 
 */
@Data
public class TranDiffEvt implements Serializable {
    /**
     * 发货方ID
     */
    private Long delivUnitId;

    /**
     * 发货方单据类别
     */
    private String delivDocType;

    /**
     * 发货方单据编号
     */
    private String delivDocNum;
    /**
     * 收货方ID
     */
    private Long rcvUnitId;

    /**
     * 收货方单据类别
     */
    private String rcvDocType;

    /**
     * 收货方单据编号
     */
    private String rcvDocNum;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 收货仓库ID
     */
    private Long rcvWarehId;

    /**
     * 差异裁定方
     */
    private String drDiffJgd;

    /**
     * 责任方
     */
    private String rspUnit;

    /**
     * 处理方式
     */
    private String tdfStlMthd;

    /**
     * 入队时间
     */
    private Date joinTime;

    /**
     * 进度
     */
    private String progress;

    /**
     * 发货方审核人ID
     */
    private Long delivChkrId;

    /**
     * 发货方审核时间
     */
    private Date delivChkTime;

    /**
     * 收货方审核人ID
     */
    private Long rcvChkrId;

    /**
     * 收货方审核时间
     */
    private Date rcvChkTime;

    /**
     * 处理人ID
     */
    private Long hdlrId;

    /**
     * 处理时间
     */
    private Date hdlTime;

    /**
     * 关闭人ID
     */
    private Long clsrId;

    /**
     * 关闭时间
     */
    private Date clsTime;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

    public TranDiffEvt() {
    }

    public TranDiffEvt(Long delivUnitId, String delivDocType, String delivDocNum, Long rcvUnitId, String rcvDocType, String rcvDocNum) {
        this.delivUnitId = delivUnitId;
        this.delivDocType = delivDocType;
        this.delivDocNum = delivDocNum;
        this.rcvUnitId = rcvUnitId;
        this.rcvDocType = rcvDocType;
        this.rcvDocNum = rcvDocNum;
    }

    public TranDiffEvt(Long delivUnitId, String delivDocType, String delivDocNum, Long rcvUnitId, String rcvDocType, String rcvDocNum, Long delivWarehId, Long rcvWarehId, String drDiffJgd) {
        this.delivUnitId = delivUnitId;
        this.delivDocType = delivDocType;
        this.delivDocNum = delivDocNum;
        this.rcvUnitId = rcvUnitId;
        this.rcvDocType = rcvDocType;
        this.rcvDocNum = rcvDocNum;
        this.delivWarehId = delivWarehId;
        this.rcvWarehId = rcvWarehId;
        this.drDiffJgd = drDiffJgd;
    }
}