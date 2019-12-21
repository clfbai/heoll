package com.boyu.erp.platform.usercenter.result;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Controller层的 json格式对象
 *
 * @author administrator
 */
@Data
@ToString
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回的编码
     */
    private String code;

    /**
     * 返回的信息
     */
    private String message;

    /***
     * 返回的对象
     */
    private Object object;


    public JsonResult() {
        super();
    }

    public JsonResult(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public JsonResult(String code, String message, Object object) {
        super();
        this.code = code;
        this.message = message;
        this.object = object;
    }


}
