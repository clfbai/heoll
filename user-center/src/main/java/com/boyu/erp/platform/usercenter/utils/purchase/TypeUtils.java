package com.boyu.erp.platform.usercenter.utils.purchase;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: boyu-platform_text
 * @description: 类别中生成系统参数
 * @author: wz
 * @create: 2019-7-29 20:38
 */
@Slf4j
@Component
public class TypeUtils {

    @Autowired
    private SysParameterMapper parameterMapper;

    public void insertParameter(String typeName, Object obj1, SysUser user, String fielfs) throws Exception {
        String update = ";";

        Map<String, String> map = new HashMap<String, String>();

        Field[] field = obj1.getClass().getDeclaredFields();
        for (Field f : field) {
            f.setAccessible(true);
            if (f.get(obj1) != null) {
                map.put(f.getName(), f.get(obj1).toString());
            } else {
                map.put(f.getName(), "");
            }
        }

        for (String key : map.keySet()) {

            if (key.contains("delivWarehReqd")) {
                if (map.get(key).equals("T")) {
                    fielfs += "deliv_wareh_id;";
                }
            }
        }


        this.inserOrUpdate(typeName, fielfs, update, user);
    }

    public void inserOrUpdate(String typeName, String fielfs, String update, SysUser user) {
        if(StringUtils.isNotEmpty(fielfs)){
            fielfs = fielfs.substring(0, fielfs.length() - 1);
        }
        if(StringUtils.isNotEmpty(update)){
            update = update.substring(0, update.length() - 1);
        }
        //往参数表中取新增或者更新数据
        SysParameter sysFileds = parameterMapper.findById(typeName + ParameterString.CREAT_TABLE_FILEDS);
        //为空就新增 否则就修改
        if (sysFileds != null) {
            sysFileds.setIsDel((byte) 1);
            sysFileds.setParmVal(fielfs);
            sysFileds.setUpdateBy(user.getPrsnl().getPrsnlId());
            sysFileds.setUpdateTime(new Date());
            parameterMapper.updateByPrimaryKeySelective(sysFileds);
        } else {
            sysFileds = new SysParameter();
            sysFileds.setParmId(typeName + ParameterString.CREAT_TABLE_FILEDS);
            sysFileds.setDescription("必填字段");
            sysFileds.setParmVal(fielfs);
            sysFileds.setIsDel((byte) 1);
            sysFileds.setCreateBy(user.getPrsnl().getPrsnlId());
            sysFileds.setCreateTime(new Date());
            parameterMapper.insertSelective(sysFileds);
        }
        SysParameter sysUpdate = parameterMapper.findById(typeName + ParameterString.TABLE_NOT_UPDATE);
        //为空就新增 否则就修改
        if (sysUpdate != null) {
            sysUpdate.setIsDel((byte) 1);
            sysUpdate.setParmVal(update);
            sysUpdate.setUpdateBy(user.getPrsnl().getPrsnlId());
            sysUpdate.setUpdateTime(new Date());
            parameterMapper.updateByPrimaryKeySelective(sysUpdate);
        } else {
            sysUpdate = new SysParameter();
            sysUpdate.setParmId(typeName + ParameterString.TABLE_NOT_UPDATE);
            sysUpdate.setDescription("不可修改字段");
            sysUpdate.setParmVal(update);
            sysUpdate.setIsDel((byte) 1);
            sysUpdate.setCreateBy(user.getPrsnl().getPrsnlId());
            sysUpdate.setCreateTime(new Date());
            parameterMapper.insertSelective(sysUpdate);
        }
    }

    public void insertParameter(String typeName, Object obj1, Object obj2, SysUser user, String fielfs) throws Exception {
        String update = "";

        //将obj1和obj2的数据组装成一个list集合
        Map<String, String> map = new LinkedHashMap<String, String>();
        Field[] field = obj1.getClass().getDeclaredFields();
        for (Field f : field) {
            f.setAccessible(true);
            if (f.get(obj1) != null) {
                map.put(f.getName(), f.get(obj1).toString());
            } else {
                map.put(f.getName(), "");
            }
        }
        Field[] field1 = obj2.getClass().getDeclaredFields();
        for (Field f : field1) {
            f.setAccessible(true);
            if (f.get(obj2) != null) {
                map.put(f.getName(), f.get(obj2).toString());
            } else {
                map.put(f.getName(), "");
            }

        }
        for (String key : map.keySet()) {
            //包含指定的字段
            if (key.contains("vdeReqd")) {
                if (map.get(key).equals("T")) {
                    fielfs += "vendee_id;";
                }
            }
            if (key.contains("vdrReqd")) {
                if (map.get(key).equals("T")) {
                    fielfs += "vender_id;";
                }
            }
            if (key.contains("rcvWarehReqd")) {
                if (map.get(key).equals("T")) {
                    fielfs += "rcv_wareh_id;";
                    fielfs += "vde_wareh_id;";
                }
            }
            if (key.contains("delivWarehReqd")) {
                if (map.get(key).equals("T")) {
                    fielfs += "deliv_wareh_id;";
                    fielfs += "vdr_wareh_id;";
                }
            }
            if (key.contains("tranUnitReqd")) {
                if (map.get(key).equals("T")) {
                    fielfs += "tran_unit_id;";
                }
            }
            if (key.contains("pscReqd")) {
                if (map.get(key).equals("T")) {
                    fielfs += "psc_num;";
                }
            }

            if (key.substring(key.length() - 3, key.length()).contains("Alt")) {
                if (map.get(key).equals("F")) {
                    key = key.substring(0, key.indexOf("Alt"));
                    update += camel2Underline(key) + ";";
                }
            }
        }


        this.inserOrUpdate(typeName, fielfs, update, user);
    }

    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase()
                .concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
}

