package com.boyu.erp.platform.usercenter.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Classname OperationVo
 * @Description TODO
 * @Date 2019/9/23 14:22
 * @Created by wz
 */
@Data
@AllArgsConstructor
public class OperationVo {
    public OperationVo() {
    }

    public OperationVo(String operationName, String operationUrl) {
        this.operationName = operationName;
        this.operationUrl = operationUrl;
    }

    public OperationVo(String operationName, String operationStutas, String operationUrl) {
        this.operationName = operationName;
        this.operationStutas = operationStutas;
        this.operationUrl = operationUrl;
    }

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 操作状态
     */
    private String operationStutas;

    /**
     * 操作编号
     */
    private String operationCode;


    /**
     * 操作路径
     */
    private String operationUrl;


}
