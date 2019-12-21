package com.boyu.erp.platform.usercenter.entity.brand;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
*@description: user_brand 用户品牌权限表
 *@author: CLF
*@create:
*/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserBrand extends BaseData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 属主Id
     */
    private Long ownerId;

    /**
     * 品牌Id
     */
    private Long brandId;
}