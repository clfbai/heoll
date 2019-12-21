package com.boyu.erp.platform.usercenter.service.table.tableimpl;

import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFilePrivKey;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.table.TableFlieMapper;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: boyu-platform_text
 * @description: 查询数据库表名、表字段、表注释 服务
 * @author: clf
 * @create: 2019-06-27 15:27
 */
@Slf4j
@Service
@Transactional
public class TabelServiceImpl implements TableService {

    @Autowired
    private TableFlieMapper tableFlieMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private FiledUtils filedUtils;

    /**
     * 查询指定数据库指定表：表的字段、字段注释
     */
    @Override
    @Transactional(readOnly = true)
    public List<TableFile> getTable(TableFile tableFile) {
        return tableFlieMapper.selectAll(tableFile);
    }

    /**
     * 查询指定数据库所有表的表名
     */
    @Override
    @Transactional(readOnly = true)
    public List<TableFile> findBydatabaseName(TableFile tableFile) {
        return tableFlieMapper.findByTable(tableFile);
    }

    /**
     * 查询指定数据库指定表的表名
     */
    @Override
    @Transactional(readOnly = true)
    public TableFile findtableName(TableFile tableFile) {
        return tableFlieMapper.tableName(tableFile);
    }

    /**
     * 功能描述:  查询模块必填项(表名)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/29 15:10
     */
    @Override
    public List<TableFile> selectTableFile(List<String> tableNames) {
        Set<TableFile> f = this.getTableSet(tableNames);
        List<TableFile> list = new ArrayList<>();
        //必填字段参数集合
        List<String> bits = new ArrayList<>();

        //prod_cls_CREAT_TABLE_FIELDS
        for (String tableName : tableNames) {
            String bit = parameterMapper.findById(tableName + ParameterString.CREAT_TABLE_FILEDS) == null ? "" : parameterMapper.findById(tableName + ParameterString.CREAT_TABLE_FILEDS).getParmVal();
            if (StringUtils.isNotEmpty(bit)) {
                bits.add(bit);
            }
        }
        if (CollectionUtils.isNotEmpty(bits)) {
            for (String s : bits) {
                this.getTableBit(s, f, list);
            }
        }
        return list;
    }

    /**
     * 功能描述: 获取字段名，注释名集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/30 15:55
     */
    public List<TableFile> getTableBit(String bit, Set<TableFile> set, List<TableFile> list) {
        if (CollectionUtils.isNotEmpty(filedUtils.StringSlip(bit))) {
            for (String s : filedUtils.StringSlip(bit)) {
                for (TableFile file : set) {
                    if (file.getTableFlie().equalsIgnoreCase(s)) {
                        file.setTableFlie(filedUtils.getConvert(file.getTableFlie()));
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    public List<TableFile> getTableList(String tableName) {
        TableFile tableFile = new TableFile();
        tableFile.setDatabaseName(CommonFainl.DATABEASNAME);
        tableFile.setTableName(tableName);
        List<TableFile> f = this.getTable(tableFile);
        return f;
    }

    /**
     * 查询多表字段
     */
    public Set<TableFile> getTableSet(List<String> tableName) {
        Set<TableFile> set = new HashSet<>();
        for (String s : tableName) {
            TableFile tableFile = new TableFile();
            tableFile.setDatabaseName(CommonFainl.DATABEASNAME);
            tableFile.setTableName(s);
            List<TableFile> f = this.getTable(tableFile);
            set.addAll(f);
        }
        return set;
    }

    /**
     * 功能描述:  模块关键字段
     * 关键字段格式  参数Id: 表名+_UPDATA__TABLE_FILE
     * val 字段名+字段名=权限Id;字段名+字段名=权限Id;
     *
     * @param tableName (模块名称)
     * @param tables    (表名集合)
     * @return:
     * @auther: CLF
     * @date: 2019/8/30 16:27
     */
    @Override
    public List<TableFilePrivKey> selectTableFilePrivKey(String tableName, List<String> tables) {
        Set<TableFile> set = this.getTableSet(tables);
        String bit = parameterMapper.findById(tableName + ParameterString.UPDATA_TABLE_FILE) == null ? "" : parameterMapper.findById(tableName + ParameterString.UPDATA_TABLE_FILE).getParmVal();
        List<String> strings = filedUtils.StringSlip(bit);
        List<TableFilePrivKey> listTable = new ArrayList<>();
        if ("".equals(bit) || CollectionUtils.isEmpty(strings)) {
            return null;
        }
        List<TableFilePrivKey> tableFieldPrivs = new ArrayList<>();
        for (String s : strings) {
            TableFilePrivKey key = filedUtils.stringSlipPriv(s);
            tableFieldPrivs.add(key);
        }

        for (TableFilePrivKey key : tableFieldPrivs) {
            if (CollectionUtils.isNotEmpty(key.getTableFiles()))
                for (TableFile t : key.getTableFiles()) {
                    if (CollectionUtils.isNotEmpty(set))
                        for (TableFile s : set) {
                            if (t.getTableFlie().equalsIgnoreCase(filedUtils.getConvert(s.getTableFlie()))) {
                                t.setFileName(s.getFileName());
                            }
                        }
                }
            listTable.add(key);
        }

        return listTable;

    }
}