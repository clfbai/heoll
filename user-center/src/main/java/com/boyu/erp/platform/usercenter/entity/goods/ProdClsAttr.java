package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * prod_cls_attr
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class ProdClsAttr implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 属性值
     */
    private String attrVal;
    /**
     * 商品品种ID
     */
    private Long prodClsId;

    /**
     * 属性类别
     */
    private String attrType;
    /**
     * 修改前属性值(仅仅作为修改是否上传前台传入)
     */
    private String updateAttrVal;
    /**
     * 商品品种管理组织Id
     */
    private Long ctrlUnitId;

}