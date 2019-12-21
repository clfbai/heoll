package com.boyu.erp.platform.usercenter.entity.brand;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * unit_brand  组织品牌表
 *
 * @author clf
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UnitBrand extends BaseData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 品牌Id
     */
    private Long brandId;
}