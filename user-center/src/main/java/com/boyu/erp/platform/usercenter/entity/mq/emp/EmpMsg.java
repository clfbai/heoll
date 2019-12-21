package com.boyu.erp.platform.usercenter.entity.mq.emp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EmpMsg   implements Serializable {

    private String province;

    private String city;

    private String district;

    private Long empId;

    private Long shop;


    /**
     * 是	导购Id
     */
    private String id;
    /**
     * String	20	是	导购姓名
     */
    private String name;
    /**
     * String	20	是	工号
     */
    private String number;
    /**
     * String	20	是	人员代码(保证唯一)
     */
    private String accountNo;
    /**
     * Identity		是	门店Id
     */
    private String shopId;

    /**
     * DateTime(String)	是	创建时间
     */
    private String createTime;
    /**
     * String	30	否	地区（精确到区），如 “广东省 深圳市 宝安区”
     */
    private String region;
    /**
     * String	100	否	详细地址
     */
    private String address;
    /**
     * Date		否	入职日期
     */
    private String entryDate;
    /**
     * String	20	否	手机号码(默认登录名)
     */
    private String phone;

}
