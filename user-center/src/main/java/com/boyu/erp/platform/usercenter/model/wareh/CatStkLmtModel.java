package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.CatStkLmt;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CatStkLmtModel implements Serializable {
    /**
     * 组织范围
     */
    @NotNull(message = "范围不能为空")
    private String scopeUnit;
    /**
     * sql 语句
     */
    private String sql;

    /**
     * 分类Id取值code
     */
    private String catCode;

    /**
     * 组织编号
     */
    private String unitNum;

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 分类ID
     */
    private String catId;

    /**
     * 商品代码
     */
    private String prodCode;

    /**
     * 品牌Id
     */
    private String brandId;


    private CatStkLmt catStkLmt;

    private List<CatStkLmt> catStkLmtList = new ArrayList<>();
}
