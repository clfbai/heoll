package com.boyu.erp.platform.usercenter.TPOS.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * WMS创建出库单回执
 * @author HHe
 * @date 2019/12/4 11:35
 */
@Data
@XmlRootElement(name = "response")
public class CreateDeliveryXmlResult implements Serializable {
    /**
     * success|failure
     */
    private String flag;
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 出库单仓储系统编码
     */
    private String deliveryOrderId;
    /**
     * 订单创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
