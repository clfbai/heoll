package com.boyu.erp.platform.usercenter.entity.brand;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * unit_bg (组织品牌分组)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class UnitBg extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 品牌分组Id
     */
    private String bgId;


}