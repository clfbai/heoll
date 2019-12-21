package com.boyu.erp.platform.usercenter.model.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品弹窗筛选类
 * @author HHe
 * @date 2019/12/5 17:56
 */
@Data
public class GoodsPopupFilterModel implements Serializable {
    private Long sUnitId;
    /**
     * 商品代码%（product）
     */
    private String prodCode;
    /**
     * %商品名称%（prod_cls）
     */
    private String prodName;
    /**
     * 分类Id%（prod_cls）
     */
    private String prodCatId;
    /**
     * 助记码%（prod_cls）
     */
    private String inputCode;
    /**
     * 品牌Id（prod_cls）
     */
    private Long brandId;
    /**
     * 商品状态（prod_cls）
     */
    private String prodStatus;
    /**
     * 筛选匹配结果集
     */
    private List<Long> prodIds;

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }
}
