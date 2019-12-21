package com.boyu.erp.platform.usercenter.vo.price;

import com.boyu.erp.platform.usercenter.entity.Price.Xpl;
import com.boyu.erp.platform.usercenter.entity.Price.XplH;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 价格单历史实体类
 * @Classname XplHVo
 * @Description TODO
 * @Date 2019/8/30 9:25
 * @Created by wz
 */
@Data
@NoArgsConstructor
public class XplHVo implements Serializable {

    /**
     *组织id
     */
    private String unitId;

    /**
     * 品种代码
     */
    private String prodClsCode;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    private String unitName;

    /**
     * 价格单编号
     */
    private String xpnNum;
    /**
     * 定价操作
     */
    private String prcOpr;

    /**
     * 执行时间
     */
    private String execTime;

    /**
     * 定价策略
     */
    private String prcPlcy;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 折率
     */
    private BigDecimal discRate;

    /**
     * 折后价
     */
    private BigDecimal fnlPrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 特价
     */
    private String specOfr;

    /**
     * 生效日期
     */
    private String effDate;

    /**
     * 失效日期
     */
    private String expdDate;

    /**
     * 定价范围
     */
    private String prcScp;

    /**
     * 价格类别
     */
    private String xpType;

    /**
     * 判断是采购价格单还是销售价格单
     */
    private String type;

}
