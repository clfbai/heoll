package com.boyu.erp.platform.usercenter.TPOS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * WMS返回状态
 *
 * @author HHe
 * @date 2019/11/8 19:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "response")
public class XmlResult implements Serializable {
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
}
