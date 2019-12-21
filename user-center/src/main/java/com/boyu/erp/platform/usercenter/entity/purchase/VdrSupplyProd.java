package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * vdr_supply_prod
 * @author 
 */
@Data
@NoArgsConstructor
public class VdrSupplyProd implements Serializable {
    /**
     * 供应商id
     */
    private Long venderId;

    /**
     * 采购商id
     */
    private Long vendeeId;

    /**
     * 商品id
     */
    private Long prodClsId;
    /**
     * 是否删除，1为可用，-1为不可用
     */
    private Byte isDel;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}