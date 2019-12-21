package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * stt_brand
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SttBrand implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 盘点表编号
     */
    private String sttNum;

    /**
     * 品牌ID
     */
    private Long brandId;

    private static final long serialVersionUID = 1L;
}