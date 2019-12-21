package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * bg_dtl
 *
 * @author 品牌明细
 */

@Data
public class VdrAttrDef implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性类别  主键
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
     * 数据类型 ("nu","dt","tm","ts","ch","vc","bi","vb","cl","bl","ob")
     */
    private String dataType;
    /**
     * 数据长度
     */
    private Long dataLen;
    /**
     * 小数位数
     */
    private Long dataDec;
    /**
     * 宽度
     */
    private Long width;
    /**
     * 大小写 ("u","l","n")
     */
    private String charCase;
    /**
     * 是否必需("t","f")
     */
    private String valReqd;
    /**
     * 属性缺省来源("c","v")
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