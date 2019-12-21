package com.boyu.erp.platform.usercenter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 分页模型
 * @author: clf
 * @create: 2019-05-22 15:34
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageModel {
    /**
     * 页码
     */
    private Integer page=1;
    /**
     * 条数
     */
    private Integer size=100;
}
