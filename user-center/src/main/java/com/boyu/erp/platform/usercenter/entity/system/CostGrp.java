package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * cost_grp
 * @author 
 */
@Data
@NoArgsConstructor
public class CostGrp  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *  成本组名称
     */
    private String costGrpName;
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 成本组ID
     */
    private Long costGrpId;



}