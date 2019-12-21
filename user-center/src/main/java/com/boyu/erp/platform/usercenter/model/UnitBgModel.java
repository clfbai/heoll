package com.boyu.erp.platform.usercenter.model;

import com.boyu.erp.platform.usercenter.entity.brand.UnitBg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: boyu-platform
 * @description: 组织品牌分组添加删除模型
 * @author: clf
 * @create: 2019-05-23 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitBgModel {

    private Long unitId;

    private Long saId;

    private String add;

    private String delete;

    private Set<UnitBg> addBg = new HashSet<>();

    private Set<UnitBg> deleteBg = new HashSet<>();
}
