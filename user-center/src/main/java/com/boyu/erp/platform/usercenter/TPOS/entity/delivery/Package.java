package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;

@Data
public class Package {
    /**
     * 物流公司名称
     */
    private String logisticsName;
    /**
     * 运单号
     */
    private String expressCode;
    /**
     * 包裹编号
     */
    private String packageCode;
    /**
     * 包裹长度
     */
    private double length;
    /**
     * 包裹宽度
     */
    private double width;
    /**
     * 包裹高度
     */
    private double height;
    /**
     * 包裹重量
     */
    private double weight;
    /**
     * 包裹体积
     */
    private double volume;
    private PackageMaterialList packageMaterialList;
    private Items items;
}
