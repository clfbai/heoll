package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * @program: boyu-platform
 * @description: 参数表实体
 * @author: CLF
 * @create: 2019-05-14 11:31
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysParameter extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参数ID
     */
    private String parmId;

    /**
     * 描述
     */
    private String description;

    /**
     * 参数值
     */
    private String parmVal;

    }