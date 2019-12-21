package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * stt_prod_cat
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SttProdCat implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 盘点表编号
     */
    private String sttNum;

    /**
     * 商品分类ID
     */
    private String prodCatId;

    private static final long serialVersionUID = 1L;
}