package com.boyu.erp.platform.usercenter.vo.purchase;

import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname PscDtlVo
 * @Description TODO
 * @Date 2019/7/8 12:23
 * @Created wz
 */
@Data
public class PscDtlVo extends CommonDtl implements Serializable {

    /**
     * 购销合同号
     */
    private String pscNum;
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
     * 退销数量
     */
    private BigDecimal rsQty;
    /**
     * 退销金额
     */
    private BigDecimal rsVal;
    /**
     * 退销税款
     */
    private BigDecimal rsTax;
    /**
     * 退销市值
     */
    private BigDecimal rsMkv;
    /**
     * 退购数量
     */
    private BigDecimal rpQty;
    /**
     * 退购金额
     */
    private BigDecimal rpVal;
    /**
     * 退购税款
     */
    private BigDecimal rpTax;
    /**
     * 退购市值
     */
    private BigDecimal rpMkv;
    /**
     * 商品分类id
     */
    private String prodCatId;
    /**
     * 可退率
     */
    private BigDecimal rtnaRate;
    /**
     * 货期
     */
    private Date reqdDate;
    /**
     * 明细集合
     */
    private List<PscDtlVo> list;

    public PscDtlVo() {
    }

    public PscDtlVo(String pscNum) {
        this.pscNum = pscNum;
    }

    public PscDtlVo(String pscNum, List<PscDtlVo> list) {
        this.pscNum = pscNum;
        this.list = list;
    }
}
