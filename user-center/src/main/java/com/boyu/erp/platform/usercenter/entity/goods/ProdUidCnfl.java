package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * prod_uid_cnfl
 * @author 
 */
@Data
@NoArgsConstructor
public class ProdUidCnfl implements Serializable {
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 商品唯一码
     */
    private String prodUid;

    /**
     * 仓库ID
     */
    private Long warehId;

    private static final long serialVersionUID = 1L;

}