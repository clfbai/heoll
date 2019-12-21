package com.boyu.erp.platform.usercenter.vo.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname PuaTypeVo
 * @Description TODO
 * @Date 2019/6/18 19:23
 * @Created wz
 */
@Data
public class PuaTypeVo extends PuaType implements Serializable {

    /**
     * 购销申请类别描述
     */
    private String psxDescription;

}
