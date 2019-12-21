package com.boyu.erp.platform.usercenter.entity.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 类描述:  鉴别权限类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/10/8 11:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivIdExamine implements Serializable {
    /**
     * 权限Id
     */
    private String privId;
    /**
     * 是否拥有指定权限
     */
    private Boolean privBoolean;

    private String meages;

}
