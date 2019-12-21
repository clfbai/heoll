package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * vdr_attr
 * @author 
 */
@Data
public class VdrAttr implements Serializable{


    private static final long serialVersionUID = 1L;

    /**
     * 属性值
     */
    private String attrVal;
    /**
     * 供应商Id
     */
    private Long venderId;

    /**
     * 属主Id
     */
    private Long ownerId;

    /**
     * 属性类别
     */
    private String attrType;

    public VdrAttr(Long venderId, Long ownerId) {
        this.venderId = venderId;
        this.ownerId = ownerId;
    }
}