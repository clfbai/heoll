package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * empl_attr_def  员工属性定义
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplAttrDef implements Serializable {
    /**
     * 属性类别
     */
    private String attrType;

    /**
     * 行号
     */
    private Integer lineNum;

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
    private Integer dataDec;

    /**
     * 宽度
     */
    private Integer width;

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

    private static final long serialVersionUID = 1L;

}