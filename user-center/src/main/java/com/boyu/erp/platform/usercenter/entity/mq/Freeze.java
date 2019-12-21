package com.boyu.erp.platform.usercenter.entity.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname Freeze
 * 冻结/解冻
 * @Description TODO
 * @Date 2019/10/8 15:17
 * @Created by wz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Freeze  implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * true-->冻结
     * false-->解冻
     */
    private boolean freeze;
}
