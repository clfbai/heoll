package com.boyu.erp.platform.usercenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * 类描述:  基类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/13 11:03
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseData {
    /**
     * 是否删除,1表示可用，-1表示不可用
     */
    private Byte isDel;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 控制范围sql 拼接map
     */
    private Map<String, Object> map;

    /**
     * 查询类别
     */
    private String selectType;
}
