package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * company
 * @author 
 */
@Data
@NoArgsConstructor
public class Company implements Serializable {
    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 国税登记号码
     */
    private String natTaxNum;

    /**
     * 地税登记号码
     */
    private String locTaxNum;

    /**
     * 注册资本
     */
    private BigDecimal regFund;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 银行账号
     */
    private String bankAcNum;

    /**
     * 供需策略id
     */
    private String sdPlcyId;

    /**
     * 缺省采购仓库id
     */
    private Long dfltPuWarehId;

    /**
     * 缺省退购仓库id
     */
    private Long dfltRpWarehId;

    /**
     * 缺省销售仓库id
     */
    private Long dfltSlWarehId;

    /**
     * 缺省退销仓库id
     */
    private Long dfltRsWarehId;

    /**
     * 缺省次品仓库id
     */
    private Long dfltInfWarehId;

    /**
     * 缺省中转仓库id
     */
    private Long dfltTranWarehId;

    /**
     * 缺省维修仓库id
     */
    private Long dfltRepWarehId;

    /**
     * 缺省寄修仓库id
     */
    private Long dfltRhdWarehId;

    /**
     * 会计组织id
     */
    private Long fsclUnitId;

    /**
     * 操作员id
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    private static final long serialVersionUID = 1L;


    }

