package com.boyu.erp.platform.usercenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: boyu-platform
 * @description: 品牌分组模型
 * @author: clf
 * @create: 2019-05-29 12:49
 */
@Data
@ToString
@NoArgsConstructor
public class BgDtlModel implements Serializable {

    /**
     * 品牌分组ID
     */
    private String bgId;


    /**
     * 增加品牌ID
     */
    private Long brandId;

    /**
     * 删除品牌ID
     */
    private Long updateBrandId;
}
