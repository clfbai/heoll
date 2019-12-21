package com.boyu.erp.platform.usercenter.mapper.sysmapper.table;

import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;

import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 数据库表、表字段
 * @author: clf
 * @create: 2019-06-27 15:07
 */
public interface TableFlieMapper {
     /**
      * 查询指定数据库指定表：表的字段、字段注释
      * */
    List<TableFile> selectAll(TableFile tableFile);
     /**
      * 查询指定数据库所有表的表名
      * */
    List<TableFile> findByTable(TableFile tableFile);
    /**
     * 查询指定数据库指定表的表名
     * */
    TableFile tableName(TableFile tableFile);
}
