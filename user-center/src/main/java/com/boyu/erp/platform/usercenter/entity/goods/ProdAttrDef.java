package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;

import java.io.Serializable;

/**
 * prod_attr_def
 * @author
 * 商品属性定义
 */
@Data
public class ProdAttrDef implements Serializable {
    /**
     * 属性类别
     */
    private String attrType;

    /**
     * 行号
     */
    private Long lineNum;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 数据长度
     */
    private Integer dataLen;

    /**
     * 小数位数
     */
    private Short dataDec;

    /**
     * 宽度
     */
    private Short width;

    /**
     * 大小写
     */
    private String charCase;

    /**
     * 是否必需
     */
    private String valReqd;

    /**
     * 属性缺省来源
     */
    private String attrDfltSrc;

    /**
     * 缺省公式
     */
    private String dfltFml;

    /**
     * 编辑定义
     */
    private String edtFml;
    /**
     * 是否下拉
     */
    private String isOpetion;

}