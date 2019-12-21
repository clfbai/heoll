package com.boyu.erp.platform.usercenter.vo.purchase;

import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname PsoDtlVo
 * @Description TODO
 * @Date 2019/7/18 12:23
 * @Created wz
 */
@Data
public class PsxDtlVo extends CommonDtl implements Serializable {

    /**
     * 购销申请单号
     */
    private String psxNum;

    /**
     * 货期
     */
    private Date reqdDate;


    /**
     * 市值
     */
    private BigDecimal mkv;


    /**
     * 商品分类id
     */
    private String prodCatId;

    /**
     * 商品代码
     */
    private String prodCode;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 序号
     */
    private Integer seqNum;
    /**
     * 计量单位
     */
    private String uom;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 颜色
     */
    private String color;
    /**
     * 规格
     */
    private String spec;
    /**
     * 版型
     */
    private String edition;

    private List<PsxDtlVo> list;

}
