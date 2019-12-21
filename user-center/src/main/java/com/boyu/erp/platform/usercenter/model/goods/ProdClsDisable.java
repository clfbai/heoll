package com.boyu.erp.platform.usercenter.model.goods;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProdClsDisable implements Serializable {
    /**
     * 商品品种ID
     */
    private Long prodClsId;
    /**
     * 商品品种代码
     */
    private String prodClsCode;
    /**
     * 管理组织ID
     */
    private Long ctrlUnitId;
    /**
     * 审核状态 se 待审核 am 已审核
     */
    private String auditType;

    /**
     * 是否审核过 0 否 1是
     */
    private Integer isAudit;



    private List<ProdClsDisable> prodClsModels = new ArrayList<>();
}
