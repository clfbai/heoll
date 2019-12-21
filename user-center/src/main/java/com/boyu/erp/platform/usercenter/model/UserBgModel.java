package com.boyu.erp.platform.usercenter.model;

import com.boyu.erp.platform.usercenter.entity.brand.UserBg;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: boyu-platform
 * @description: 用户品牌分组数据模型
 * @author: clf
 * @create: 2019-05-24 12:04
 */
@Data
@ToString
@NoArgsConstructor
public class UserBgModel {

    private Long userId;

    private Long ownerId;

    private String bgId;

    private Long unitId;

    private String add;

    private String delete;

    private Set<UserBg> addBg = new HashSet<>();

    private Set<UserBg> deleteBg = new HashSet<>();
}
