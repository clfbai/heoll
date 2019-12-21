package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * product
 *
 * @author 商品表实体
 */
@Data
@ToString
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 规格号
     */
    private String specNum;
    /**
     * 规格名称
     */
    private String specName;
    /**
     * 商品代码
     */
    private String prodCode;

    /**
     * 商品品种ID
     */
    private Long prodClsId;

    /**
     * 颜色ID
     */
    private Long colorId;


    /**
     * 颜色标注
     */
    private String colorCmt;

    /**
     * 规格ID
     */
    private Long specId;

    /**
     * 规格标注
     */
    private String specCmt;


    /**
     * 版型
     */
    private String edition;

    /**
     * 版型标注
     */
    private String editionCmt;


    /**
     * 配比
     */
    private Long proportion;

    /**
     * 店内码
     */
    private String innerBc;

    /**
     * 国际码
     */
    private String intlBc;

    /**
     * 最大序号
     */
    private Long maxSn;

    /**
     * 明细属性1
     */
    private String pdDtlProp1;

    /**
     * 明细属性2
     */
    private String pdDtlProp2;

    /**
     * 明细描述
     */
    private String pdDtlDesc;

    /**
     * 已删除
     */
    private String deleted;

    /**
     * 是否可用
     */
    private Byte isDel;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 商品明细状态
     */
    private String skuStatus;
}