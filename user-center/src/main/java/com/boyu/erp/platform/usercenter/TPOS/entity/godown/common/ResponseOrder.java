package com.boyu.erp.platform.usercenter.TPOS.entity.godown.common;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "flag",
        "code",
        "message",
        "entryOrderId"
})
@XmlRootElement(name = "response")
public class ResponseOrder {
    /**
     * success(成功)、failure(失败)
     */
    @XmlElement(required = true)
    protected String flag;
    /**
     * 200 成功
     */
    @XmlElement(required = true)
    protected String code;
    /**
     * 响应信息
     */
    @XmlElement(required = true)
    protected String message;
    /**
     * 仓储系统入库单编码, string (50)
     */
    private String entryOrderId;
}
