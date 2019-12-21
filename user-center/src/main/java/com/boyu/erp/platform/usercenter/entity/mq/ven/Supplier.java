package com.boyu.erp.platform.usercenter.entity.mq.ven;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname Supplier
 * 供应商/加盟商
 * @Description TODO
 * @Date 2019/10/8 9:27
 * @Created by wz
 */
@Data
@NoArgsConstructor
public class Supplier  implements Serializable {

    /**
     * 加盟商id
     */
    private String id;

    /**
     * 加盟商code
     */
    private String code;

    /**
     * 加盟商名称
     */
    private String name;

    /**
     * 所属组织id
     */
    private String unitId;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 地区（精确到区）
     */
    private String region;

    /**
     * 街道地址
     */
    private String address;

    /**
     * 创建日期
     */
    private String createDate;
}
