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
public class RtcDtlVo extends CommonDtl implements Serializable {

    /**
     * 退货合同号
     */
    private String rtcNum;

    /**
     * 市值
     */
    private BigDecimal mkv;

    /**
     * 发货数量
     */
    private BigDecimal delivQty;

    /**
     * 发货金额
     */
    private BigDecimal delivVal;

    /**
     * 发货税款
     */
    private BigDecimal delivTax;

    /**
     * 发货市值
     */
    private BigDecimal delivMkv;

    /**
     * 到货数量
     */
    private BigDecimal rcvQty;
    /**
     * 到货金额
     */
    private BigDecimal rcvVal;
    /**
     * 到货税款
     */
    private BigDecimal rcvTax;

    /**
     * 到货市值
     */
    private BigDecimal rcvMkv;


    /**
     * 商品分类id
     */
    private String prodCatId;

    /**
     * 商品代码
     */
    private String prodCode;

    /**
     * 商品品种id
     */
    private Long prodClsId;
    /**
     * 商品品种代码
     */
    private String prodClsCode;
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


    private List<RtcDtlVo> list;


    public RtcDtlVo() {
    }
    /**
     * 查询明细时使用
     * @author HHe
     */
    public RtcDtlVo(String rtcNum) {
        this.rtcNum = rtcNum;
    }
}
