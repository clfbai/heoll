package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.CatStkLmt;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CatStkLmtVo extends CatStkLmt implements Serializable {
    /**
     * 分类名称
     */
    private String typeCp;

    /**
     * 分类Id取值code
     */
    private String catCode;

    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 组织编号
     */
    private String unitNum;
    /**
     * 商品代码
     */
    private String prodCode;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 品牌Id
     */
    private String brandId;
    /**
     * 颜色名称
     */
    private String colorName;
    /**
     * 规格Id
     */
    private String specId;
    /**
     * 版型名
     */
    private String editionCp;

}
