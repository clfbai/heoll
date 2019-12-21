package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * bg
 *
 * @author 品牌分组
 */
@Data
public class Bg implements Serializable {
    /**
     * 品牌分组ID
     */
    private String bgId;

    /**
     * 品牌分组名称
     */
    private String bgName;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;


}