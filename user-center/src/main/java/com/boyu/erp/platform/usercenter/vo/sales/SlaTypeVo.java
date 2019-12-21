package com.boyu.erp.platform.usercenter.vo.sales;

import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SlaTypeVo
 * @Description TODO
 * @Date 2019/11/9 11:41
 * @Created by wz
 */
@Data
public class SlaTypeVo extends SlaType implements Serializable {

    /**
     * 购销申请类别描述
     */
    private String psxDescription;

}
