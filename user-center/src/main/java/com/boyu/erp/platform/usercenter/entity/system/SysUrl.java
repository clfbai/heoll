package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_url
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class SysUrl extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    private Long id;


    /**
     * 路径
     */
    private String url;
    /**
     * 路径描述
     */
    private String description;


}