package com.boyu.erp.platform.usercenter.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.components.FieldAuthority;
import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.UserModel;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.refttable.ReftTable;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnGoodsDtl;
import com.google.common.base.CaseFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class TestModel implements Serializable {

    @FieldAuthority(name = "123")
    private String id;
    @FieldAuthority(name = "你好")
    private String name;


    public static void mains(String[] args) throws Exception {
        String name = "TestStringName";
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        System.out.println(name);
        String z = "s_ty_hy_ki";
        String[] slz = z.split("_");
        String res = "";
        if (slz.length > 1) {
            for (int i = 1; i < slz.length; i++) {

                String string = slz[i].substring(0, 1).toUpperCase().concat(slz[i].substring(1).toLowerCase());
                res = res + string;
                System.out.println(string);
                System.out.println(res);
            }
        }
        res = slz[0] + res;
        System.out.println(res);
        String str = "|0|1|62|";
        String bg = "|0|1|62|56|6|";
        System.out.println("===" + bg.substring(0, str.length()));

        String tt = "prsl+List<SysPrsnlVo>";

        String gt = "";

        int a = tt.indexOf("+");
        System.out.println("" + a);
        System.out.println(tt.length() - a);
        gt = tt.substring(a + 1, tt.length());

        System.out.println("111  " + gt);


        for (String string : ReftTable.getTableField(new GrnGoodsDtl())) {
            System.out.print(string + ",");
        }
        for (String string : ReftTable.getTableField(new Wareh())) {
            System.out.println("w." + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string) + " AS " + string + ",");
        }
        System.out.println("不转驼峰");
        for (String string : ReftTable.getTableField(new WarehRcvTask())) {

            System.out.println("a." + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string) + " AS " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string) + ",");
        }
        for (String string : ReftTable.getTableField(new Stb())) {
            System.out.println("\"" + string + "\"" + ":" + "\"\"" + ",");
        }


        String st = "unit_id, prod_cls_id, sales_mode, rcmd_lvl, rt_unit_price, ws_unit_price, ws_tax_rate, \n" +
                "    pu_unit_price, pu_tax_rate, vender_id";

        System.out.println(st.trim());

        for (String s : st.trim().split(",")) {
            System.out.println("c." + s.trim() + "  as " + s.trim() + ",");
        }


        String s = "8999999999999999999";
        System.out.println("" + s.length());

        List<Integer> list = new ArrayList<>();
        list.add(50);
        list.add(100);
        list.add(100);
        int as = 234;
        List<Integer> i = getLista(list, as);
        i.stream().forEach(System.out::println);


        int index = (int) (Math.random() * 26 + 1);

        System.out.println(index);
        String sff = "A、B、C、D、E、F、G、H、I、J、K、L、M、N、O、P、Q、R、S、T、U、V、W、X、Y、Z";

        Arrays.stream(sff.split("、")).forEach(sf -> System.out.print("\"" + sf + "\"" + ","));
        String[] strggg = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int indexcg = (int) (Math.random() * 25 + 0);
        System.out.println(strggg[25]);


        String ss = "AB_F";

        String sg = "ab_f";
        System.out.println(ss.equalsIgnoreCase(sg));


        String shu = "dtlRemarks,colorName,fnlPrice,prodId,synSn,prodName,unitId,mkv,editionCp,specId,unitPrice,val,inputCode,cost,stbNum,tax,mkUnitPrice,discRate,appPrice,prodCode,taxRate,qty,unitCost,uomName,expdQty,rwd,specNum,proportion,colorId,specCmt,fnlPrice,edition,prodId,stkAdopted,intlBc,updateBy,innerBc,rowNum,unitId,mkv,prodClsId,unitPrice,val,specId,pdDtlProp2,inputCode,pdDtlProp1,cost,colorCmt,stbNum,skuStatus,tax,updateTime,pdDtlDesc,mkUnitPrice,discRate,appPrice,taxRate,prodCode,maxSn,createBy,editionCmt,deleted,specName,createTime,qty,unitCost,lineNum,expdQty,isDel,rwd,remarks,";
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(shu.split(",")));
        for (String sty : set) {
            System.out.print(sty + ",");
        }
    }


    private static List<Integer> getLista(List<Integer> list, int a) {
        List<Integer> is = new ArrayList<>();
        m:
        for (int i = 0; i < list.size(); i++) {
            a = a - list.get(i);
            if (a <= 0) {
                break m;
            }
            is.add(list.get(i));
        }
        if (a > 0) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "供应商商批次已售完，检查你的出库、或购买数量");
        }
        return is;
        //
    }


    public static void maint(String[] args) {
        Long a = Long.parseLong("010160000608905");
        System.out.println(a);
        int max = 10, min = 1;
        int ran2 = (int) (Math.random() * (max - min) + min);
        System.out.println(ran2);
        String str = "0.0.0";
        String ce = "0";
        List<String> list = new ArrayList<>();
        System.out.println(str.substring(0, str.lastIndexOf(".")));
        SysUser user = new SysUser();
        user.setOwnerId(100L);
        String json = String.valueOf(JSONObject.toJSON(user));
        CwmsUrlParamModel cwmsUrlParamModel = new CwmsUrlParamModel();
        cwmsUrlParamModel.setAppKey("123");
        //app_key
        cwmsUrlParamModel.setSecret("123");
        cwmsUrlParamModel.setRequestMapping("/api/qm2");
        cwmsUrlParamModel.setMethod("taobao.qimen");
        cwmsUrlParamModel.setCustomerid("stub-cust-code");
        cwmsUrlParamModel.setTimestamp(DateUtil.dateToString(new Date()));

        String s = JSON.toJSONString(cwmsUrlParamModel);

        CwmsUrlParamModel cws = JSON.parseObject(s, CwmsUrlParamModel.class);
        System.out.println("222 " + s);
        System.out.println("1111 " + cws);
    }

    public static List<String> textString(String s, String s2, List<String> list) {
        if (s.indexOf("|") < 0) {
            s = s + "|";
        }
        String rest = s.substring(0, s.lastIndexOf("|"));
        list.add(rest);
        if (!rest.equalsIgnoreCase(s2)) {
            textString(rest, s2, list);
        }
        return list;
    }


    public static String upperCharToUnderLine(String param) {
        Pattern p = Pattern.compile("[A-Z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        BigDecimal c = new BigDecimal("8.000000000000000000000000000000000000000000000000000000000");
        BigDecimal c3 = new BigDecimal("8");
        BigDecimal c4 = new BigDecimal("8");

        UserModel userModel = new UserModel(1L, 100L, "123");
        UserModel userModel2 = new UserModel(2L, 100L, "123");
        UserModel userModel3 = new UserModel(3L, 1L, "123");
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(userModel);
        userModels.add(userModel2);
        userModels.add(userModel3);
        Long[] list = {1L, 2L};
        List<Long> ps = new ArrayList<>();
        ps.add(1L);
        ps.add(2L);
        userModels.stream().filter(s -> ps.contains(s.getUserId())).collect(Collectors.toList()).forEach(System.out::println);

    }
}
