package com.boyu.erp.platform.usercenter.service.table;

import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFilePrivKey;

import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 查询数据库表名、表字段、表注释接口
 * @author: clf
 * @create: 2019-06-27 15:24
 */
public interface TableService {
    List<TableFile> getTable(TableFile tableFile);

    List<TableFile> findBydatabaseName(TableFile tableFile);

    TableFile findtableName(TableFile tableFile);

    /**
     * 功能描述:  查询模块必填项(表名)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/29 15:10
     */
    List<TableFile> selectTableFile(List<String> tableNames);


    /**
     * 功能描述:  查询模块关键字段(表关键字段)
     *
     * @param: tableName (模块名 或表名)
     * @return:
     * @auther: CLF
     * @date: 2019/8/29 15:10
     */
    List<TableFilePrivKey> selectTableFilePrivKey(String tableName,List<String> tables);


}
