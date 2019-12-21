package com.boyu.erp.platform.usercenter.vo.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopEmp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ShopEmpVo extends ShopEmp implements Serializable {
    /**
     * 全名
     */
    private String fullName;

    /**
     * 员工代码
     */
    private String prsnlCode;

    /**
     * 员工编号
     */
    private String prsnlNum;
    /**
     * 办公电话号码
     */
    private String officeNum;

    /**
     * 移动电话号码
     */
    private String mobileNum;
}
