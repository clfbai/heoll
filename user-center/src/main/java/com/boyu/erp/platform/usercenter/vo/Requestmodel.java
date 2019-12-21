package com.boyu.erp.platform.usercenter.vo;

import javax.servlet.http.HttpServletRequest;

public interface Requestmodel {


    /***
     * 验证数据模型
     *
     * @return
     */
    void validateModel(HttpServletRequest request) throws RuntimeException;

}
