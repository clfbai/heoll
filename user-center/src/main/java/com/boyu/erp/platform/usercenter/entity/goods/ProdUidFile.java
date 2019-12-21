package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * prod_uid_file
 * @author 
 */
@Data
@NoArgsConstructor
public class ProdUidFile implements Serializable {
    /**
     * 商品唯一码
     */
    private String prodUid;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 冲突中
     */
    private String conflicted;

    private static final long serialVersionUID = 1L;

}