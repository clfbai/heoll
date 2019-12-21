package com.boyu.erp.platform.usercenter.model.goods;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: boyu-platform_text
 * @description: 添加自定义属性模型
 * @author: clf
 * @create: 2019-06-26 18:29
 */
@Data
@ToString
@NoArgsConstructor
public class ProdAttrDefModel extends BaseData implements Serializable {
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


    /**
     * 修改是否下拉(仅供前台传入)
     */
    private String updateisOpetion;
}
