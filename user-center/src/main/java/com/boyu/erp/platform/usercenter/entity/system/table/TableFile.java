package com.boyu.erp.platform.usercenter.entity.system.table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform_text
 * @description: 数据库表字段
 * @author: clf
 * @create: 2019-06-27 15:09
 */
@Data
@ToString
@NoArgsConstructor
public class TableFile {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表名注释名称
     */
    private String tableChineseName;

    /**
     * 数据库名
     */
    private String databaseName;
    /**
     * 字段
     */

    private String tableFlie;

    /**
     * 字段名
     */
    private String fileName;
}
