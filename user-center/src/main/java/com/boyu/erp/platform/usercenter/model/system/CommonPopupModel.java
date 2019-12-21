package com.boyu.erp.platform.usercenter.model.system;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname CommonPopupModel
 * @Description TODO
 * @Date 2019/11/25 9:50
 * @Created by wz
 */
@Data
public class CommonPopupModel implements Serializable {

    /**
     * 查询类别
     */
    private String selectType;

    /**
     * 查询条件拼接的json
     */
    private String json;
}
