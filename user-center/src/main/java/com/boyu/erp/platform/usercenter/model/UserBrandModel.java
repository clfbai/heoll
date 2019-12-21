package com.boyu.erp.platform.usercenter.model;

import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: boyu-platform
 * @description: 用户品牌数据模型
 * @author: clf
 * @create: 2019-05-22 15:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBrandModel extends PageModel {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 用户领域(组织)
     */
    private Long unitId;
    /**
    * 领域管理员Id
    * */
    private Long saId;

    private String add;

    private String delete;

    private Set<Brand> addBrand = new HashSet<>();

    private Set<Brand> deleteBrand = new HashSet<>();

}
