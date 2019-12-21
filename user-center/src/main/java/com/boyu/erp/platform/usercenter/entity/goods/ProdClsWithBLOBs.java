package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * prod_cls
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdClsWithBLOBs extends ProdCls implements Serializable {
    /**
     * 商品小图
     */
    private byte[] prodThumb;

    /**
     * 商品图片
     */
    private byte[] prodPict;

    /**
     * 详情
     */
    private String detail;
    /**
     * 组织Id
     */
    private Long unitId;

    private static final long serialVersionUID = 1L;



}