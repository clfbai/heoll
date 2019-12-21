package com.boyu.erp.platform.usercenter.utils.purchase;

import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname JsonObject
 * @Description 获取必填字段
 * @Date 2019/7/18 17:12
 * @Created by wz
 */
@Slf4j
@Component
public class FieldUtils {

    @Autowired
    private TableService tableService;
    @Autowired
    private FiledUtils filedUtils;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private SysParameterService parameterService;

    static  List<String> strList;

    /**
     * 唯一一个参数查询
     * @param tableName
     * @return
     */
    @Transactional(readOnly = true)
    public List<TableFile> flie(String tableName) {
        String[] str = tableName.split(",");
        List<TableFile> f = new ArrayList<TableFile>();
        for (int i = 0; i < str.length; i++) {
            TableFile tableFile = new TableFile();
            tableFile.setDatabaseName(CommonFainl.DATABEASNAME);
            tableFile.setTableName(str[i]);
            f.addAll(tableService.getTable(tableFile));
        }
        List<TableFile> list = new ArrayList<>();
        String bit = parameterMapper.findById(str[0] + ParameterString.CREAT_TABLE_FILEDS).getParmVal();
        strList = filedUtils.StringSlip(bit);
        if(("PSA").equals(tableName)){
            this.addFlie();
        }
        for (String s : strList) {
            for (TableFile file : f) {
                if (file.getTableFlie().equalsIgnoreCase(s)) {
                    file.setTableFlie(filedUtils.getConvert(file.getTableFlie()));
                    list.add(file);
                }
            }
        }
        return list;
    }

    @Transactional(readOnly = true)
    public List<TableFile> flie(String tableName,String type) {
        String[] str = tableName.split(",");
        List<TableFile> f = new ArrayList<TableFile>();
        for (int i = 0; i < str.length; i++) {
            TableFile tableFile = new TableFile();
            tableFile.setDatabaseName(CommonFainl.DATABEASNAME);
            tableFile.setTableName(str[i]);
            f.addAll(tableService.getTable(tableFile));
        }
        List<TableFile> list = new ArrayList<>();
        if(parameterMapper.findById(type + ParameterString.CREAT_TABLE_FILEDS)!=null){
            String bit = parameterMapper.findById(type + ParameterString.CREAT_TABLE_FILEDS).getParmVal();
            strList = filedUtils.StringSlip(bit);
            for (String s : strList) {
                for (TableFile file : f) {
                    if (file.getTableFlie().equalsIgnoreCase(s)) {
                        file.setTableFlie(filedUtils.getConvert(file.getTableFlie()));
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    private void addFlie() {
        if(parameterService.findByParameter("PSA_DR_PSC_TYPE_ENABLED").getParmVal().equals("F")
        &&parameterService.findByParameter("PSA_DR_PROD_CAT_ENABLED").getParmVal().equals("F")){
            strList.add("dflt_disc_rate");
        }
        if(parameterService.findByParameter("PSA_TR_PSC_TYPE_ENABLED").getParmVal().equals("F")
                &&parameterService.findByParameter("PSA_TR_PSC_TYPE_ENABLED").getParmVal().equals("F")){
            strList.add("dflt_rtna_rate");
        }
        if(parameterService.findByParameter("PSA_RTR_PSC_TYPE_ENABLED").getParmVal().equals("F")
                &&parameterService.findByParameter("PSA_DR_PROD_CAT_ENABLED").getParmVal().equals("F")){
            strList.add("dflt_tax_rate");
        }
    }

    /**
     * 查询不可修改字段
     */
    public List<String> isNotUpdateWareh(String type) {
        String str = type + ParameterString.TABLE_NOT_UPDATE;
        str = parameterMapper.findById(str) == null ? "" : parameterMapper.findById(str).getParmVal();
        if (StringUtils.isNotBlank(str)) {
            List<String> list = filedUtils.creatHump(filedUtils.StringSlip(str));
            return list;
        }
        return new ArrayList<>();
    }

}
