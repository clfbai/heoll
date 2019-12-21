package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * ven
 * @author 
 */
@Data
public class Vender  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 供应商Id
     */
    private Long venderId;

    /**
     * 属主Id
     */
    private Long ownerId;
    /**
     * 协议控制方
     */
    private String psaCtlr;

    /**
     * 主营商品
     */
    private String majorProd;

    /**
     * 对应采购仓库ID
     */
    private Long cpdPuWarehId;

    /**
     * 对应退购仓库ID
     */
    private Long cpdRpWarehId;

    /**
     * 受托代销仓库ID
     */
    private Long cmsdWarehId;

    /**
     * 供应商状态
     */
    private String vdrStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 判断权限用
     */
    @Transient
    private Long unitId;

    public Vender() {
    }

    public Vender(Long venderId, Long ownerId) {
        this.venderId = venderId;
        this.ownerId = ownerId;
    }

    public Vender(Long venderId, Long ownerId, String psaCtlr, String vdrStatus, Long oprId) {
        this.venderId = venderId;
        this.ownerId = ownerId;
        this.psaCtlr = psaCtlr;
        this.vdrStatus = vdrStatus;
        this.oprId = oprId;
    }
}