package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;

/**
 * @Description:
 * @auther: CLF
 * @date: 2019/7/13 9:32
 */
public class ParamLength {

    /**
     * 功能描述: 参数长度验证
     *
     * @param file   (字段名称)
     * @param value  (字段值)
     * @param length (指定长度)
     * @return:
     * @auther: CLF
     * @date: 2019/7/13 9:42
     */
    public static JsonResult isParamLength(String file, String value, Integer length) {
        if (value.length() > length) {
            return new JsonResult(JsonResultCode.FAILURE, file + "长度不能超过" + length + "位", "");
        }
        return null;
    }


}
