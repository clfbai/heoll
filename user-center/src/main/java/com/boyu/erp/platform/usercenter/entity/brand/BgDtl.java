package com.boyu.erp.platform.usercenter.entity.brand;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * bg_dtl
 *
 * @author 品牌明细
 */

@Data
public class BgDtl implements Serializable {
    /**
     * 品牌分组ID
     */
    private String bgId;

    /**
     * 品牌分组名称
     */
    private String bgName;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    private List<BgDtl> list = new ArrayList<>();
}