package com.boyu.erp.platform.usercenter.utils.common.JsonUtils;

import com.boyu.erp.platform.usercenter.result.JsonResult;

import java.io.Serializable;

/**
 * 类描述:  去除多余信息Json 工具类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/14 20:05
 */

public class JsonRemove implements Serializable {

    private static JsonResult jsonResult = null;

    public static JsonResult result(String code,String m,Object o) {
        if (jsonResult == null) {
            jsonResult = new JsonResult(code,m,o);
        }
        return jsonResult;
    }

}
