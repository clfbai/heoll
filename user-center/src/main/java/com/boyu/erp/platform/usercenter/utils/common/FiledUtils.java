package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFilePrivKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: boyu-platform_text
 * @description: 生成驼峰命名字段
 * @author: clf
 * @create: 2019-06-24 17:41
 */
@Component
public class FiledUtils {

    public boolean getFlie(String str, Object o) throws Exception {
        List<String> spl = StringSlip(str);
        List<String> list = creatHump(spl);
        return isNotFile(list, o);
    }

    /**
     * 分割字段(获得必填项字段名集合)
     */
    public List<String> StringSlip(String str) {
        if (str.indexOf(";") > 0) {
            String[] list = str.split(";");
            List<String> one = new ArrayList<>();
            for (String s : list) {
                one.add(s);
            }
            return one;
        }
        return null;
    }


    /**
     * 获得关键字段和对应的权限
     */
    public TableFilePrivKey stringSlipPriv(String str) {
        TableFilePrivKey tableFieldPriv = new TableFilePrivKey();
        if (str.indexOf("=") > 0) {
            String[] spli = str.split("=");
            if (spli[0].indexOf("+") > 0) {
                List<TableFile> listTable = new ArrayList<>();
                String[] st = spli[0].split("\\+");
                List<String> list = new ArrayList();
                for (String s : st) {
                    TableFile tableFile = new TableFile();
                    tableFile.setTableFlie(this.getConvert(s));
                    listTable.add(tableFile);
                }
                tableFieldPriv.setTableFiles(listTable);
            }
            tableFieldPriv.setPrivKey(spli[1]);
        }
        return tableFieldPriv;
    }


    public List<String> creatHump(List<String> spl) {
        List<String> list = new ArrayList<>();
        if(!spl.isEmpty()){
            for (String s : spl) {
                list.add(getConvert(s));
            }
        }
        return list;
    }

    /**
     * 下划线生成驼峰命名字段
     */
    public String getConvert(String str) {
        String[] gt = str.split("_");
        List<String> list = new ArrayList<>();
        String first = "";
        String after = "";
        for (int i = 1; i < gt.length; i++) {
            first = gt[i].substring(0, 1);
            after = gt[i].substring(1);
            first = first.toUpperCase();
            after = after.toLowerCase();
            list.add(first + after);
        }
        first = "";
        for (String s : list) {
            first += s;
        }
        return gt[0].toLowerCase() + first;
    }

    /**
     * 功能描述: 驼峰转下划线
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/22 19:40
     */
    public StringBuffer underLine(String str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        } else {
            return sb;
        }
        return underLine(sb.toString());
    }

    /**
     * 判断是否有必填项为空
     */
    public boolean isNotFile(List<String> list, Object o) throws Exception {
        Field[] field = o.getClass().getDeclaredFields();
        if (list.size() > 0) {
            for (String s : list) {
                for (Field f : field) {
                    f.setAccessible(true);
                    if (s.equals(f.getName())) {
                        if (f.get(o) == null && f.get(o) instanceof String || "".equals(f.get(o))) {
                            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, f.getName() + ":  值为空");
                        }
                        if (f.get(o) == null && f.get(o) instanceof Integer) {
                            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, f.getName() + ":  值为空");
                        }
                    }
                }
            }
        }
        return true;
    }


}
