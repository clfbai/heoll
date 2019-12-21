package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * stt_prod_cls
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SttProdCls implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 盘点表编号
     */
    private String sttNum;

    /**
     * 商品品种ID
     */
    private Long prodClsId;

    private static final long serialVersionUID = 1L;
}