package com.boyu.erp.platform.usercenter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @program: boyu-platform
 * @description: 用户数据模型
 * @author: clf
 * @create: 2019-06-04 10:11
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "request")
public class UserModel {

    private Long userId;

    private Long unitId;


    private String test;


}
