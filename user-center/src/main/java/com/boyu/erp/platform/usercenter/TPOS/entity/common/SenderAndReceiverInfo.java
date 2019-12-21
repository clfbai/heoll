package com.boyu.erp.platform.usercenter.TPOS.entity.common;

import lombok.Data;

/**
 * 类描述: 入库单创建 收、发件人信息 公共类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/11/6 11:28
 */
@Data
public class SenderAndReceiverInfo {

    /**
     * 公司名称
     */
    private String company;
    /**
     * 姓名(退货必填)
     */
    private String name;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 固定电话
     */
    private String tel;
    /**
     * 移动电话(退货必填)
     */
    private String mobile;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 国家二字码
     */
    private String countryCode;
    /**
     *省份(退货必填)
     */
    private String province;
    /**
     * 城市(退货必填)
     */
    private String city;
    /**
     *区域
     */
    private String area;
    /**
     * 村镇
     */
    private String town;
    /**
     * 详细地址(退货必填)
     */
    private String detailAddress;

}
