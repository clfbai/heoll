package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * prod_cat 商品分类
 */
@Data
public class ProdCat implements Serializable {
    /**
     * 商品分类ID
     */
    @NotNull(message = "商品分类Id不能为空")
    private String prodCatId;

    /**
     * 新的商品分类ID
     */
    private String newProdCatId;

    /**
     * 分类名称
     */
    @NotEmpty(message = "商品分类名称不能为空")
    private String prodCatName;

    /**
     * 分类级别
     */
    private Short prodCatLvl;

    /**
     * 上级分类ID,前端要求，默认为0
     */
    private String parnCatId = "0";

    /**
     * 描述
     */
    private String description;

    /**
     * 前端控件插件需要
     */
    private boolean last;


    private List<ProdCat> prodCatList = new ArrayList<>();
}