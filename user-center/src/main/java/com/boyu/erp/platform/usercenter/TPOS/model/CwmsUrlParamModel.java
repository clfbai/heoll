package com.boyu.erp.platform.usercenter.TPOS.model;

import lombok.Data;

import java.io.Serializable;

/**
 * C-WMS URL 参数
 *
 * @author HHe
 * @date 2019/9/9 20:29
 */
@Data
public class CwmsUrlParamModel implements Serializable {
    /**
     * C-WMS相关的业务接口名称;
     * 如商品信息同步的请求，method= item.synchronize
     */
    private String method;

    private String timestamp;
    /**
     * content格式
     */
    private String format = "xml";

    /**
     * 应用接入时申请的appkey
     */
    private String appKey;

    /**
     * 协议版本号，1.0或者2.0
     */
    private String v = "1.0";

    /**
     * 根据url和密钥计算的结果
     */
    private String sign;

    /**
     * 参数加密方法
     */
    private String signMethod = "md5";

    /**
     * C-WMS颁发给用户的ID
     */
    private String customerid;

    /**
     * 根据对象生成的 String 类型的XMl
     */
    private String objXml;
    /**
     * 签名头
     */
    private String secret;

    /**
     * 请求方法   /app/xx
     */
    private String requestMapping;


}
