package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * vde_attr
 * @author 
 */
@Data
public class VdeAttr implements Serializable {
    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 属性类别
     */
    private String attrType;
    /**
     * 属性值
     */
    private String attrVal;

    private static final long serialVersionUID = 1L;

    public VdeAttr(Long vendeeId, Long ownerId) {
        this.vendeeId = vendeeId;
        this.ownerId = ownerId;
    }
}