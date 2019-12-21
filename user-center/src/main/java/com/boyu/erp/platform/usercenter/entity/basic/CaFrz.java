package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ca_frz
 * @author 
 */
@Data
public class CaFrz implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 往来账户ID
     */
    private Long caId;

    /**
     * 单据类别
     */
    private String docType;

    /**
     * 单据组织ID
     */
    private Long docUnitId;

    /**
     * 单据编号
     */
    private String docNum;
    /**
     * 借方冻结金额
     */
    private BigDecimal dbFrzVal;

    /**
     * 贷方冻结金额
     */
    private BigDecimal crFrzVal;

    /**
     * 绑定单据类别
     */
    private String bndDocType;

    /**
     * 绑定单据组织ID
     */
    private Long bndDocUnitId;

    /**
     * 绑定单据编号
     */
    private String bndDocNum;

    @Transient
    private String docTypeCp;

    private static final long serialVersionUID = 1L;

    public CaFrz() {
    }

    public CaFrz(Long unitId, Long caId, String docType, Long docUnitId, String docNum) {
        this.unitId = unitId;
        this.caId = caId;
        this.docType = docType;
        this.docUnitId = docUnitId;
        this.docNum = docNum;
    }
}