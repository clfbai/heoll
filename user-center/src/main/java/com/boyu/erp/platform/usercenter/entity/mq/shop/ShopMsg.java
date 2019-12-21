package com.boyu.erp.platform.usercenter.entity.mq.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述:  门店消息对象
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/10/11 10:59
 */
@Data
@NoArgsConstructor
public class ShopMsg  implements Serializable {


    /**
     * 是	门店Id
     */
    private String id;
    /**
     * 是 门店名称
     */
    private String name;

    /**
     * 是 所属加盟商Id
     */
    private String franchiseeId;
    /**
     * 是 门店类型（1-自营店，2-专卖店，3-其它）
     */
    private Integer type;

    /**
     * 否 分公司()
     */
    private String areaName;
    /**
     * 否 联系人
     */
    private String contacts;

    /**
     * 联系人手机号码(默认登录名)
     */
    private String phone;
    /**
     * 否	地区（精确到区），如 “广东省 深圳市 宝安区”
     */
    private String region ;
    /**
     * 否	详细地址
     */
    private String address;

    /**
     * String	是	创建时间
     */
    private String createTime;
}
