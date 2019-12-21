package com.boyu.erp.platform.usercenter.model.goods;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProductModel extends BaseData implements Serializable {
    /**
     * 商品代码
     * */
    private String prodCode;
    /**
     * 商品分类Id
     * */
    private String prodCatId;
    /**
     * 商品名称
     * */
    private String prodName;
    /**
     * 适销季节
     * */
    private String season;
    /**
     * 品牌Id
     * */
    private String brandId;
    /**
     * 组织Id
     * */
    private Long unitId;
}
