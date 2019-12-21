package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProdDetailModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerUnitModel;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.*;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.SpringUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 出库相关工具类
 */
@Component
public class DelivUtil {
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SysPrsnlService sysPrsnlService;
    @Autowired
    private SysRefNumDtlSerivce sysRefNumDtlSerivce;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private WarehService warehService;
    @Autowired
    private SysUnitOwnerSerivcer sysUnitOwnerSerivcer;
    @Autowired
    private SysPrsnlOwnerSerivce sysPrsnlOwnerSerivce;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProdClsService prodClsService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SpecService specService;
    @Autowired
    private FunRuleService funRuleService;

    /**
     * 根据type字符串数组，查询并封装成map集合
     * 结构：{"":[{"":""},{"":""}]}
     *
     * @param
     * @return
     */
    public Map<String, Object> getCodeDtlMap(String[] strv, String[] strk) {
        Map<String, Object> totMap = new LinkedHashMap<>();
        for (int i = 0; i < strv.length; i++) {
            List<Map<String, String>> mapList = sysCodeDtlService.getCodeDtlMap(strv[i]);
            totMap.put(strk[i], mapList);
        }
        return totMap;
    }

    /**
     * 将参数map中的值查询并且返回
     *
     * @version 1.0
     * @author HHe
     * @date 2019/10/7 10:06
     */
    public Map<String, Object> getCodeDtlMap1(Map<String, String> fMap) {
        Map<String, Object> totMap = new LinkedHashMap<>();
        for (String s : fMap.keySet()) {
            List<Map<String, String>> mapList = sysCodeDtlService.getCodeDtlMap(fMap.get(s));
            totMap.put(s, mapList);
        }
        return totMap;
    }

    /**
     * 将参数map中的值查询并且返回
     * @param fMap key:返回下拉的名称 val:对应到code_dtl中的类型
     * @version 1.1
     * @author HHe
     * @date 2019/10/7 10:07
     */
    public Map<String, Object> getCodeDtlMap2(Map<String, String> fMap) {
        Map<String, Object> totMap = new LinkedHashMap<>();
        //将map中的val取出存set查询
        Set<String> typeSet = new TreeSet<>();
        fMap.forEach((k, v) -> typeSet.add(v));
        //查询出所有type集合
        List<SysCodeDtl> sysCodeDtlList = sysCodeDtlService.queryCodeDtlByTypeSet(typeSet);
        fMap.forEach((k, v) -> {
            List<OptionVo> optionVos = new ArrayList<>();
            for (SysCodeDtl sysCodeDtl : sysCodeDtlList) {
                if (v.equals(sysCodeDtl.getCodeType())) {
                    optionVos.add(new OptionVo(sysCodeDtl.getDescription(),sysCodeDtl.getCode()));
                }
            }
            totMap.put(k,optionVos);
        });
        return totMap;
    }

    /**
     * 根据strk在fMap追加类型为BOOLEA的map
     *
     * @param fMap
     * @param strk
     * @return
     */
    public Map<String, Object> getCodeDtlMap4Boolea(Map<String, Object> fMap, String[] strk) {
        Map<String, Object> totMap = new LinkedHashMap<>();
        List<Map<String, String>> mapList = sysCodeDtlService.getCodeDtlMap("BOOLEA");
        for (int i = 0; i < strk.length; i++) {
            fMap.put(strk[i], mapList);
        }
        return fMap;
    }

    /**
     * 去_右边变大写
     *
     * @param str
     * @return
     */
    public String out_(String str) {
        if (str.contains("_")) {
            String[] s = str.split("_");
            String tfname = "";
            for (int i = 0; i < s.length; i++) {
                if (i < 1) {
                    tfname = tfname + s[i];
                    continue;
                }
                tfname = tfname + s[i].substring(0, 1).toUpperCase() + s[i].substring(1);
            }
            return tfname;
        } else {
            return str;
        }

    }

    /**
     * 根据对象中所有的Id对应子表查询code和name封装到该对象的codeNameMap中；
     * 自动判断unit、wareh人物需要手输对象中的属性名
     * 在map中注入Key为unitProp的map；键为需要obj中哪个字段作为匹配子表的id，值为sysUnit中对应子表的属性名
     *
     * @param map
     * @version 1.1
     * @author HHe
     * @date 2019/9/11 11:28
     */
    public <T> List<T> loadCodeName2Map(Map<String, Object> map, List<T> objects) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Set<Long> sysUnitIds = new HashSet<>();
        Set<Long> sysPrsnlIds = new HashSet<>();
        //对象中需要查询人员表的属性名称
        String[] sysPrsnlIdNames = (String[]) map.get("sys_prsnl");
        //需要封装到每个类中对应sys_unit中的属性名字
        Map<String, String> unitProp = (Map<String, String>) map.get("unitProp");
        //取出集合中所有带id的值添加到集合，用于查询对应id的子类对象，而不是查询全部，影响效率
        for (T obj : objects) {
            Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("Id") && (field.getName().toLowerCase().contains("unit") || field.getName().toLowerCase().contains("wareh"))) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());//(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
                    Method rM = pd.getReadMethod();//获得读方法
                    sysUnitIds.add((Long) rM.invoke(obj));
                    continue;
                }
                if (field.getName().contains("Id") && (ArrayUtils.contains(sysPrsnlIdNames, field.getName()))) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                    Method rM = pd.getReadMethod();//获得读方法
                    sysPrsnlIds.add((Long) rM.invoke(obj));
                }
            }
        }
        //开关，用于控制空集合情况
        boolean sus = false;
        boolean sps = false;
        List<SysUnit> sysUnits = new ArrayList<>();
        List<SysPrsnl> sysPrsnls = new ArrayList<>();
        if (sysUnitIds != null && sysUnitIds.size() > 0) {
            sysUnits = sysUnitService.queryUnitByIds(sysUnitIds);
            sus = true;
        }
        if (sysPrsnlIds != null && sysPrsnlIds.size() > 0) {
            sysPrsnls = sysPrsnlService.queryPrsnlByIds(sysPrsnlIds);
            sps = true;
        }
        if (sus || sps) {
            for (T obj : objects) {
                Map<String, String> codeNameMap = new LinkedHashMap<>();
                Map<String, Object> unitInjectVal = new HashMap<>();
                Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
                for (Field field : fields) {
                    if (sus) {
                        if (field.getName().contains("Id") && (field.getName().toLowerCase().contains("unit") || field.getName().toLowerCase().contains("wareh"))) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            for (SysUnit sysUnit : sysUnits) {
                                Field[] unitFields = sysUnit.getClass().getDeclaredFields();
                                if (sysUnit.getUnitId().equals(rM.invoke(obj))) {
                                    String[] ids = field.getName().split("Id");
                                    codeNameMap.put(ids[0] + "Code", sysUnit.getUnitCode());
                                    codeNameMap.put(ids[0] + "Name", sysUnit.getUnitName());
                                    //循环出定义的需要set到vo中的属性
                                    for (String key : unitProp.keySet()) {
                                        if (key.equals(field.getName())) {
                                            for (Field f : unitFields) {
                                                if (f.getName().equals(unitProp.get(key))) {
                                                    PropertyDescriptor uPd = new PropertyDescriptor(f.getName(), SysUnit.class);
                                                    Method uRM = uPd.getReadMethod();//获得读方法
                                                    Object invoke = uRM.invoke(sysUnit);
                                                    unitInjectVal.put(f.getName(), invoke);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                    if (sps) {
                        if (field.getName().contains("Id") && ArrayUtils.contains(sysPrsnlIdNames, field.getName())) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            for (SysPrsnl sysPrsnl : sysPrsnls) {
                                if (sysPrsnl.getPrsnlId().equals(rM.invoke(obj))) {
                                    String[] ids = field.getName().split("Id");
                                    codeNameMap.put(ids[0] + "Code", sysPrsnl.getPrsnlCode());
                                    codeNameMap.put(ids[0] + "Name", sysPrsnl.getFullName());
                                }
                            }
                        }
                    }
                }
                Method codeNameMapMe = obj.getClass().getDeclaredMethod("setCodeNameMap", obj.getClass().getDeclaredField("codeNameMap").getType());
                codeNameMapMe.invoke(obj, codeNameMap);
                for (String key : unitInjectVal.keySet()) {
                    String meName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                    Method mapMe = obj.getClass().getDeclaredMethod(meName, obj.getClass().getDeclaredField(key).getType());
                    mapMe.invoke(obj, unitInjectVal.get(key));
                }
            }
        }
        return objects;
    }

    /**
     * 根据VO类继承的实体类中的Id到子表中查询出对应的数据封装到VO类中的Code和cp中
     *
     * @version 2.0
     * @author HHe
     * @date 2019/9/21 17:50
     */
    public <T> List<T> loadCodeName2VO(Map<String, Object> map, List<T> objects) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Set<Long> sysUnitIds = new HashSet<>();
        Set<Long> sysPrsnlIds = new HashSet<>();
        //对象中需要查询人员表的属性名称
        String[] sysPrsnlIdNames = (String[]) map.get("sys_prsnl");
        //需要封装到每个类中对应sys_unit中的属性名字
        Map<String, String> unitProp = (Map<String, String>) map.get("unitProp");
        //取出集合中所有带id的值添加到集合，用于查询对应id的子类对象，而不是查询全部，影响效率
        for (T obj : objects) {
            Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("Id") && (field.getName().toLowerCase().contains("unit") || field.getName().toLowerCase().contains("wareh"))) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());//(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
                    Method rM = pd.getReadMethod();//获得读方法
                    sysUnitIds.add((Long) rM.invoke(obj));
                    continue;
                }
                if (field.getName().contains("Id") && (ArrayUtils.contains(sysPrsnlIdNames, field.getName()))) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                    Method rM = pd.getReadMethod();//获得读方法
                    sysPrsnlIds.add((Long) rM.invoke(obj));
                }
            }
        }
        //开关，用于控制空集合情况
        boolean sus = false;
        boolean sps = false;
        List<SysUnit> sysUnits = new ArrayList<>();
        List<SysPrsnl> sysPrsnls = new ArrayList<>();
        if (sysUnitIds != null && sysUnitIds.size() > 0) {
            sysUnits = sysUnitService.queryUnitByIds(sysUnitIds);
            sus = true;
        }
        if (sysPrsnlIds != null && sysPrsnlIds.size() > 0) {
            sysPrsnls = sysPrsnlService.queryPrsnlByIds(sysPrsnlIds);
            sps = true;
        }
        if (sus || sps) {
            for (T obj : objects) {
                Map<String, Object> unitInjectVal = new HashMap<>();
                Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
                for (Field field : fields) {
                    if (sus) {
                        if (field.getName().contains("Id") && (field.getName().toLowerCase().contains("unit") || field.getName().toLowerCase().contains("wareh"))) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            for (SysUnit sysUnit : sysUnits) {
                                Field[] unitFields = sysUnit.getClass().getDeclaredFields();
                                if (sysUnit.getUnitId().equals(rM.invoke(obj))) {
                                    String[] ids = field.getName().split("Id");
                                    //改成用vo类中定义的属性名接收
                                    String codeName = ids[0] + "Code";
                                    String cpName = ids[0] + "CP";
                                    unitInjectVal.put(codeName, sysUnit.getUnitCode());
                                    unitInjectVal.put(cpName, sysUnit.getUnitName());
                                    //循环出定义的需要set到vo中的属性
                                    if (unitProp != null) {
                                        for (String key : unitProp.keySet()) {
                                            if (key.equals(field.getName())) {
                                                for (Field f : unitFields) {
                                                    if (f.getName().equals(unitProp.get(key))) {
                                                        PropertyDescriptor uPd = new PropertyDescriptor(f.getName(), SysUnit.class);
                                                        Method uRM = uPd.getReadMethod();//获得读方法
                                                        Object invoke = uRM.invoke(sysUnit);
                                                        unitInjectVal.put(f.getName(), invoke);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (sps) {
                        if (field.getName().contains("Id") && ArrayUtils.contains(sysPrsnlIdNames, field.getName())) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            for (SysPrsnl sysPrsnl : sysPrsnls) {
                                if (sysPrsnl.getPrsnlId().equals(rM.invoke(obj))) {
                                    String[] ids = field.getName().split("Id");
                                    String codeName = ids[0] + "Code";
                                    String cpName = ids[0] + "CP";
                                    unitInjectVal.put(codeName, sysPrsnl.getPrsnlCode());
                                    unitInjectVal.put(cpName, sysPrsnl.getFullName());
                                }
                            }
                        }
                    }
                }
                for (String key : unitInjectVal.keySet()) {
                    boolean b = false;
                    for (Field f : obj.getClass().getDeclaredFields()) {
                        if (f.getName().equals(key)) {
                            b = true;
                        }
                    }
                    if (b) {
                        String meName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                        Method mapMe = obj.getClass().getDeclaredMethod(meName, obj.getClass().getDeclaredField(key).getType());
                        mapMe.invoke(obj, unitInjectVal.get(key));
                    }
                }
            }
        }
        return objects;
    }

    /**
     * 根据VO类继承的实体类中的Id到子表中查询出对应的数据封装到VO类中的Num和CP中
     *
     * @version 2.1
     * @author HHe
     * @date 2019/9/21 17:50
     */
    public <T> List<T> loadCodeName2VO1(Map<String, Object> map, List<T> objects) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Set<Long> sysPrsnlIds = new HashSet<>();
        List<OwnerUnitModel> ownerUnitModelList = new ArrayList<>();
        //对象中需要查询人员表的属性名称
        String[] sysPrsnlIdNames = (String[]) map.get("sys_prsnl");
        //需要封装到每个类中对应sys_unit中的属性名字
        Map<String, String> unitProp = (Map<String, String>) map.get("unitProp");
        //取出集合中所有带id的值添加到集合，用于查询对应id的子类对象，而不是查询全部，影响效率
        for (T obj : objects) {
            //取出obj中的unitid作为属主
            Method mapMe = obj.getClass().getSuperclass().getDeclaredMethod("getUnitId", null);//obj.getClass().getSuperclass().getDeclaredField("unitId").getType()
            Long unitId = (Long) mapMe.invoke(obj);
            Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("Id") && (field.getName().toLowerCase().contains("unit") || field.getName().toLowerCase().contains("wareh"))) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());//(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
                    Method rM = pd.getReadMethod();//获得读方法
                    OwnerUnitModel ownerUnitModel = new OwnerUnitModel(unitId, (Long) rM.invoke(obj));
                    ownerUnitModelList.add(ownerUnitModel);
                    continue;
                }
                if (field.getName().contains("Id") && (ArrayUtils.contains(sysPrsnlIdNames, field.getName()))) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                    Method rM = pd.getReadMethod();//获得读方法
                    sysPrsnlIds.add((Long) rM.invoke(obj));
                }
            }
        }
        //开关，用于控制空集合情况
        boolean sus = false;
        boolean sps = false;
        List<SysPrsnl> sysPrsnls = new ArrayList<>();
        if (ownerUnitModelList != null && ownerUnitModelList.size() > 0) {
            ownerUnitModelList = sysUnitOwnerSerivcer.packNumAndName2List(ownerUnitModelList);
            sus = true;
        }
        if (sysPrsnlIds != null && sysPrsnlIds.size() > 0) {
            sysPrsnls = sysPrsnlService.queryPrsnlByIds(sysPrsnlIds);
            sps = true;
        }
        if (sus || sps) {
            for (T obj : objects) {
                Method mapMe = obj.getClass().getSuperclass().getDeclaredMethod("getUnitId", null);
                Long unitId = (Long) mapMe.invoke(obj);
                Map<String, Object> unitInjectVal = new HashMap<>();
                Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
                for (Field field : fields) {
                    if (sus) {
                        if (field.getName().contains("Id") && (field.getName().toLowerCase().contains("unit") || field.getName().toLowerCase().contains("wareh"))) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            for (OwnerUnitModel ownerUnitModel : ownerUnitModelList) {
                                Field[] unitFields = ownerUnitModel.getClass().getDeclaredFields();
                                if (ownerUnitModel.getUnitId() == rM.invoke(obj) && ownerUnitModel.getOwnerId().equals(unitId) ) {
                                    String[] ids = field.getName().split("Id");
                                    String numName = ids[0] + "Num";
                                    String cpName = ids[0] + "CP";
                                    unitInjectVal.put(numName, ownerUnitModel.getUnitNum());
                                    unitInjectVal.put(cpName, ownerUnitModel.getUnitName());
                                    if (unitProp != null) {
                                        for (String key : unitProp.keySet()) {
                                            if (key.equals(field.getName())) {
                                                for (Field f : unitFields) {
                                                    if (f.getName().equals(unitProp.get(key))) {
                                                        PropertyDescriptor uPd = new PropertyDescriptor(f.getName(), SysUnit.class);
                                                        Method uRM = uPd.getReadMethod();//获得读方法
                                                        Object invoke = uRM.invoke(ownerUnitModel);
                                                        unitInjectVal.put(f.getName(), invoke);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (sps) {
                        if (field.getName().contains("Id") && ArrayUtils.contains(sysPrsnlIdNames, field.getName())) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            for (SysPrsnl sysPrsnl : sysPrsnls) {
                                if (sysPrsnl.getPrsnlId().equals(rM.invoke(obj))) {
                                    String[] ids = field.getName().split("Id");
                                    String codeName = ids[0] + "Code";
                                    String cpName = ids[0] + "Cp";
                                    unitInjectVal.put(codeName, sysPrsnl.getPrsnlCode());
                                    unitInjectVal.put(cpName, sysPrsnl.getFullName());
                                }
                            }
                        }
                    }
                }
                for (String key : unitInjectVal.keySet()) {
                    boolean b = false;
                    for (Field f : obj.getClass().getDeclaredFields()) {
                        if (f.getName().equals(key)) {
                            b = true;
                        }
                    }
                    if (b) {
                        String meName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                        Method setMe = obj.getClass().getDeclaredMethod(meName, obj.getClass().getDeclaredField(key).getType());
                        setMe.invoke(obj, unitInjectVal.get(key));
                    }
                }
            }
        }
        return objects;
    }

    /**
     * 封装vo类
     *
     * @version 3.0
     * @author HHe
     * @date 2019/9/30 10:31
     */
    public <T> List<T> loadCodeName2VO2(Map<String, Object> map, List<T> objects) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        //需要去组织表查询并封装编号和名字的属性
        String[] sysUnitIdNames = (String[]) map.get("sys_unit");
        //需要去人员表查询并封装编号和名字的属性
        String[] sysPrsnlIdNames = (String[]) map.get("sys_prsnl");
        //需要组织表中的其他属性；key:obj的属性，val:sys_unit的属性属性名
        Map<String, String> unitProp = (Map<String, String>) map.get("unitProp");
        //需要人员表的其他属性；同上
        Map<String, String> prsnlProp = (Map<String, String>) map.get("prsnlProp");
        //循环obj对应sysUnitIdNames中的id的值；
        List<OwnerUnitModel> ownerUnitModelList = new ArrayList<>();
        //循环obj对应sysPrsnlIdNames中的id的值；
        List<OwnerPrsnlModel> ownerPrsnlModelList = new ArrayList<>();
        for (T obj : objects) {
            Method mapMe = obj.getClass().getSuperclass().getDeclaredMethod("getUnitId", null);//obj.getClass().getSuperclass().getDeclaredField("unitId").getType()
            Long unitId = (Long) mapMe.invoke(obj);
            Field[] allFields = this.getAllFields(obj);
            for (Field field : allFields) {
                if (sysUnitIdNames != null && sysUnitIdNames.length > 0) {
                    for (String unitIdName : sysUnitIdNames) {
                        if (unitIdName.equals(field.getName())) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            Long fName = (Long) rM.invoke(obj);
                            if (fName!=null) {
                                if(ownerUnitModelList.size()<=0){
                                    ownerUnitModelList.add(new OwnerUnitModel(unitId,fName));
                                }
                                boolean b = true;
                                for (int i = 0; i < ownerUnitModelList.size(); i++) {
                                    if(fName.equals(ownerUnitModelList.get(i).getUnitId())){
                                        b = false;
                                    }
                                }
                                if(b){
                                    ownerUnitModelList.add(new OwnerUnitModel(unitId,fName));
                                }
                                //终止sysUnitIdNames循环
                                break;
                            }
                        }
                    }
                }
                if (sysPrsnlIdNames != null && sysPrsnlIdNames.length > 0) {
                    for (String prsnlIdName : sysPrsnlIdNames) {
                        if (prsnlIdName.equals(field.getName())) {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                            Method rM = pd.getReadMethod();//获得读方法
                            Long fName = (Long) rM.invoke(obj);
                            if(fName!=null){
                                if(ownerPrsnlModelList.size()<=0){
                                    ownerPrsnlModelList.add(new OwnerPrsnlModel(unitId,fName));
                                }
                                boolean b = true;
                                for (int i = 0; i < ownerPrsnlModelList.size(); i++) {
                                    if(fName.equals(ownerPrsnlModelList.get(i).getPrsnlId())){
                                        b = false;
                                    }
                                }
                                if(b){
                                    ownerPrsnlModelList.add(new OwnerPrsnlModel(unitId,fName));
                                }
                            }
//                            ownerPrsnlModelList.add(new OwnerPrsnlModel(unitId, (Long) rM.invoke(obj)));
                            break;
                        }
                    }
                }
            }
        }
        boolean sus = false;
        boolean sps = false;
        //查询出对应的组织的num、code、name等字段
        if (ownerUnitModelList != null && ownerUnitModelList.size() > 0) {
            ownerUnitModelList = sysUnitOwnerSerivcer.packNumAndName2List(ownerUnitModelList);
            sus = true;
        }
        if (ownerPrsnlModelList != null && ownerPrsnlModelList.size() > 0) {
            ownerPrsnlModelList = sysPrsnlOwnerSerivce.packNumAndName2List(ownerPrsnlModelList);
            sps = true;
        }
        if (sus || sps) {
            for (T obj : objects) {
                Method mapMe = obj.getClass().getSuperclass().getDeclaredMethod("getUnitId", null);//obj.getClass().getSuperclass().getDeclaredField("unitId").getType()
                Long unitId = (Long) mapMe.invoke(obj);
                Field[] fields = this.getAllFields(obj);
                //一个对象中的一个字段
                for (Field field : fields) {
                    if (sus) {
                        for (String sysUnitIdName : sysUnitIdNames) {
                            if (sysUnitIdName.equals(field.getName())) {
                                //获取名称
                                String fieldNmae = field.getName().split("Id")[0];
                                String numName = fieldNmae + "Num";
                                String cpName = fieldNmae + "Name";
                                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                                Method rM = pd.getReadMethod();//获得读方法
                                for (OwnerUnitModel ownerUnitModel : ownerUnitModelList) {
                                    if (unitId.equals(ownerUnitModel.getOwnerId()) && ownerUnitModel.getUnitId().equals(rM.invoke(obj))) {
                                        //判断非空赋值
                                        if (this.whetherHaveProp(obj, numName)) {
                                            Method numMe = obj.getClass().getDeclaredMethod("set" + numName.substring(0, 1).toUpperCase() + numName.substring(1), obj.getClass().getDeclaredField(numName).getType());
                                            numMe.invoke(obj, ownerUnitModel.getUnitNum());
                                        }
                                        if (this.whetherHaveProp(obj, cpName)) {
                                            Method cpMe = obj.getClass().getDeclaredMethod("set" + cpName.substring(0, 1).toUpperCase() + cpName.substring(1), obj.getClass().getDeclaredField(cpName).getType());
                                            cpMe.invoke(obj, ownerUnitModel.getUnitName());
                                        }
                                        //循环unitProp，判断key与本Id是否相符
                                        if (unitProp != null) {
                                            for (String key : unitProp.keySet()) {
                                                if (key.equals(sysUnitIdName)) {
                                                    //将子表中对应属性名的值注入到相同属性名的obj中
                                                    //unitProp的val格式是：obj的属性名-对应子表对象属性名
                                                    String objName = unitProp.get(key).split("-")[0];
                                                    String vName = unitProp.get(key).split("-")[1];
                                                    Method getMe = ownerUnitModel.getClass().getDeclaredMethod("get" + vName.substring(0, 1).toUpperCase() + vName.substring(1), null);
                                                    Method setMe = obj.getClass().getDeclaredMethod("set" + objName.substring(0, 1).toUpperCase() + objName.substring(1), obj.getClass().getDeclaredField(objName).getType());
                                                    setMe.invoke(obj, getMe.invoke(ownerUnitModel));
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    if (sps) {
                        for (String sysPrsnlIdName : sysPrsnlIdNames) {
                            if (sysPrsnlIdName.equals(field.getName())) {
                                //获取名称
                                String fieldNmae = field.getName().split("Id")[0];
                                String numName = fieldNmae + "Num";
                                String cpName = fieldNmae + "Name";
                                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                                Method rM = pd.getReadMethod();//获得读方法
                                for (OwnerPrsnlModel ownerPrsnlModel : ownerPrsnlModelList) {
                                    //根绝单据的unitId作为ownerId,把属性作为id对应ownerPrsnlModel中的值，匹配出对象
                                    if (unitId.equals(ownerPrsnlModel.getOwnerId()) && ownerPrsnlModel.getPrsnlId().equals(rM.invoke(obj))) {
                                        //判断对象中是否有该方法，有则注入
                                        if (this.whetherHaveProp(obj, numName)) {
                                            Method numMe = obj.getClass().getDeclaredMethod("set" + numName.substring(0, 1).toUpperCase() + numName.substring(1), obj.getClass().getDeclaredField(numName).getType());
                                            numMe.invoke(obj, ownerPrsnlModel.getPrsnlNum());
                                        }
                                        if (this.whetherHaveProp(obj, cpName)) {
                                            Method cpMe = obj.getClass().getDeclaredMethod("set" + cpName.substring(0, 1).toUpperCase() + cpName.substring(1), obj.getClass().getDeclaredField(cpName).getType());
                                            cpMe.invoke(obj, ownerPrsnlModel.getFullName());
                                        }
                                        //循环prsnlProp，判断key与本Id是否相符
                                        if (prsnlProp != null) {
                                            for (String key : prsnlProp.keySet()) {
                                                if (key.equals(sysPrsnlIdName)) {
                                                    //将子表中对应属性名的值注入到相同属性名的obj中
                                                    //unitProp的val格式是：obj的属性名-对应子表对象属性名
                                                    String objName = prsnlProp.get(key).split("-")[0];
                                                    String vName = prsnlProp.get(key).split("-")[1];
                                                    Method getMe = ownerPrsnlModel.getClass().getDeclaredMethod("get" + vName.substring(0, 1).toUpperCase() + vName.substring(1), null);
                                                    Method setMe = obj.getClass().getDeclaredMethod("set" + objName.substring(0, 1).toUpperCase() + objName.substring(1), obj.getClass().getDeclaredField(objName).getType());
                                                    setMe.invoke(obj, getMe.invoke(ownerPrsnlModel));
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return objects;
    }

    /**
     * 封装vo类
     *
     * @version 3.1
     * @author HHe
     * @date 2019/9/30 10:31
     */
//    public <T> List<T> loadCodeName2VO3(Map<String, Object> map, List<T> objects) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
//        //需要去组织表查询并封装编号和名字的属性
////        String[] sysUnitIdNames = (String[]) map.get("sys_unit");
//        //属主对应关系（主表中类似于收货仓库的属主为收货方的属主对应关系）
//        Map<String,String> ownerUnit = (Map<String, String>) map.get("ownerUnit");
//        //需要去人员表查询并封装编号和名字的属性
//        String[] sysPrsnlIdNames = (String[]) map.get("sys_prsnl");
//        //需要组织表中的其他属性；key:obj的属性，val:sys_unit的属性属性名
//        Map<String, String> unitProp = (Map<String, String>) map.get("unitProp");
//        //需要人员表的其他属性；同上
//        Map<String, String> prsnlProp = (Map<String, String>) map.get("prsnlProp");
//        //循环obj对应sysUnitIdNames中的id的值；
//        List<OwnerUnitModel> ownerUnitModelList = new ArrayList<>();
//        //循环obj对应sysPrsnlIdNames中的id的值；
//        List<OwnerPrsnlModel> ownerPrsnlModelList = new ArrayList<>();
//        for (T obj : objects) {
//            Method mapMe = obj.getClass().getSuperclass().getDeclaredMethod("getUnitId", null);//obj.getClass().getSuperclass().getDeclaredField("unitId").getType()
//            Long unitId = (Long) mapMe.invoke(obj);
//            Field[] allFields = this.getAllFields(obj);
//            for (Field field : allFields) {
////                if (sysUnitIdNames != null && sysUnitIdNames.length > 0) {
////                    for (String unitIdName : sysUnitIdNames) {
////                        if (unitIdName.equals(field.getName())) {
////                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
////                            Method rM = pd.getReadMethod();//获得读方法
////                            ownerUnitModelList.add(new OwnerUnitModel(unitId, (Long) rM.invoke(obj)));
////                            //终止sysUnitIdNames循环
////                            break;
////                        }
////                    }
////                }
//                if(ownerUnit!=null&&ownerUnit.size()>0){
//                    for (String key : ownerUnit.keySet()) {
//                        if(key.equals(field.getName())){
//                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
//                            Method rM = pd.getReadMethod();//获得读方法
//                            String ownerName = ownerUnit.get(key);
//                            Method ownerId = obj.getClass().getSuperclass().getDeclaredMethod(ReflexUtils.getHumpName(ownerName),null);
//                            ownerUnitModelList.add(new OwnerUnitModel((Long) ownerId.invoke(obj), (Long) rM.invoke(obj)));
//                            break;
//                        }
//                    }
//                }
//                if (sysPrsnlIdNames != null && sysPrsnlIdNames.length > 0) {
//                    for (String prsnlIdName : sysPrsnlIdNames) {
//                        if (prsnlIdName.equals(field.getName())) {
//                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
//                            Method rM = pd.getReadMethod();//获得读方法
//                            ownerPrsnlModelList.add(new OwnerPrsnlModel(unitId, (Long) rM.invoke(obj)));
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        boolean sus = false;
//        boolean sps = false;
//        //查询出对应的组织的num、code、name等字段
//        if (ownerUnitModelList != null && ownerUnitModelList.size() > 0) {
//            ownerUnitModelList = sysUnitOwnerSerivcer.packNumAndName2List(ownerUnitModelList);
//            sus = true;
//        }
//        if (ownerPrsnlModelList != null && ownerPrsnlModelList.size() > 0) {
//            ownerPrsnlModelList = sysPrsnlOwnerSerivce.packNumAndName2List(ownerPrsnlModelList);
//            sps = true;
//        }
//        if (sus || sps) {
//            for (T obj : objects) {
//                Method mapMe = obj.getClass().getSuperclass().getDeclaredMethod("getUnitId", null);//obj.getClass().getSuperclass().getDeclaredField("unitId").getType()
//                Long unitId = (Long) mapMe.invoke(obj);
//                Field[] fields = this.getAllFields(obj);
//                //一个对象中的一个字段
//                for (Field field : fields) {
//                    if (sus) {
//                        for (String ownerKey : ownerUnit.keySet()) {
//                            if (ownerKey.equals(field.getName())) {
//                                //获取名称
//                                String fieldNmae = field.getName().split("Id")[0];
//                                String numName = fieldNmae + "Num";
//                                String cpName = fieldNmae + "Name";
//                                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
//                                Method rM = pd.getReadMethod();//获得读方法
//                                for (OwnerUnitModel ownerUnitModel : ownerUnitModelList) {
//                                    String getName = ReflexUtils.getHumpName(ownerUnit.get(ownerKey));
//                                    Method ownerMe = obj.getClass().getSuperclass().getDeclaredMethod(getName, null);
//                                    Long ownerId = (Long) ownerMe.invoke(obj);
//                                    if (ownerUnitModel.getOwnerId().equals(ownerId) && ownerUnitModel.getUnitId().equals(rM.invoke(obj))) {
//                                        //判断非空赋值
//                                        if (this.whetherHaveProp(obj, numName)) {
//                                            Method numMe = obj.getClass().getDeclaredMethod("set" + numName.substring(0, 1).toUpperCase() + numName.substring(1), obj.getClass().getDeclaredField(numName).getType());
//                                            numMe.invoke(obj, ownerUnitModel.getUnitNum());
//                                        }
//                                        if (this.whetherHaveProp(obj, cpName)) {
//                                            Method cpMe = obj.getClass().getDeclaredMethod("set" + cpName.substring(0, 1).toUpperCase() + cpName.substring(1), obj.getClass().getDeclaredField(cpName).getType());
//                                            cpMe.invoke(obj, ownerUnitModel.getUnitName());
//                                        }
//                                        //循环unitProp，判断key与本Id是否相符
//                                        if (unitProp != null) {
//                                            for (String key : unitProp.keySet()) {
//                                                if (key.equals(ownerKey)) {
//                                                    //将子表中对应属性名的值注入到相同属性名的obj中
//                                                    //unitProp的val格式是：obj的属性名-对应子表对象属性名
//                                                    String objName = unitProp.get(key).split("-")[0];
//                                                    String vName = unitProp.get(key).split("-")[1];
//                                                    Method getMe = ownerUnitModel.getClass().getDeclaredMethod("get" + vName.substring(0, 1).toUpperCase() + vName.substring(1), null);
//                                                    Method setMe = obj.getClass().getDeclaredMethod("set" + objName.substring(0, 1).toUpperCase() + objName.substring(1), obj.getClass().getDeclaredField(objName).getType());
//                                                    setMe.invoke(obj, getMe.invoke(ownerUnitModel));
//                                                }
//                                            }
//                                        }
//                                        break;
//                                    }
//                                }
//                                break;
//                            }
//                        }
//                    }
//                    if (sps) {
//                        for (String sysPrsnlIdName : sysPrsnlIdNames) {
//                            if (sysPrsnlIdName.equals(field.getName())) {
//                                //获取名称
//                                String fieldNmae = field.getName().split("Id")[0];
//                                String numName = fieldNmae + "Num";
//                                String cpName = fieldNmae + "Name";
//                                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
//                                Method rM = pd.getReadMethod();//获得读方法
//                                for (OwnerPrsnlModel ownerPrsnlModel : ownerPrsnlModelList) {
//                                    //根绝单据的unitId作为ownerId,把属性作为id对应ownerPrsnlModel中的值，匹配出对象
//                                    if (unitId.equals(ownerPrsnlModel.getOwnerId()) && ownerPrsnlModel.getPrsnlId().equals(rM.invoke(obj))) {
//                                        //判断对象中是否有该方法，有则注入
//                                        if (this.whetherHaveProp(obj, numName)) {
//                                            Method numMe = obj.getClass().getDeclaredMethod("set" + numName.substring(0, 1).toUpperCase() + numName.substring(1), obj.getClass().getDeclaredField(numName).getType());
//                                            numMe.invoke(obj, ownerPrsnlModel.getPrsnlNum());
//                                        }
//                                        if (this.whetherHaveProp(obj, cpName)) {
//                                            Method cpMe = obj.getClass().getDeclaredMethod("set" + cpName.substring(0, 1).toUpperCase() + cpName.substring(1), obj.getClass().getDeclaredField(cpName).getType());
//                                            cpMe.invoke(obj, ownerPrsnlModel.getFullName());
//                                        }
//                                        //循环prsnlProp，判断key与本Id是否相符
//                                        if (prsnlProp != null) {
//                                            for (String key : prsnlProp.keySet()) {
//                                                if (key.equals(sysPrsnlIdName)) {
//                                                    //将子表中对应属性名的值注入到相同属性名的obj中
//                                                    //unitProp的val格式是：obj的属性名-对应子表对象属性名
//                                                    String objName = prsnlProp.get(key).split("-")[0];
//                                                    String vName = prsnlProp.get(key).split("-")[1];
//                                                    Method getMe = ownerPrsnlModel.getClass().getDeclaredMethod("get" + vName.substring(0, 1).toUpperCase() + vName.substring(1), null);
//                                                    Method setMe = obj.getClass().getDeclaredMethod("set" + objName.substring(0, 1).toUpperCase() + objName.substring(1), obj.getClass().getDeclaredField(objName).getType());
//                                                    setMe.invoke(obj, getMe.invoke(ownerPrsnlModel));
//                                                }
//                                            }
//                                        }
//                                        break;
//                                    }
//                                }
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return objects;
//    }

    /**
     * 根据map的key对应的字段对应集合对象中不同的属性名；
     *
     * @param map     key:对象中的属性名称；val:对应code_dtl的code_type
     * @param objects 对象集合
     * @author HHe
     * @date 2019/9/24 20:03
     */
    public <T> List<T> loadCPByCodeDtl(Map<String, Object> map, List<T> objects) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Set<String> codetype = new TreeSet<>();
        for (String key : map.keySet()) {
            codetype.add((String) map.get(key));
        }
        //查询出codetype对应的sys_code_dtl集合
        List<SysCodeDtl> sysCodeDtlList = sysCodeDtlService.queryCodeDtlByTypeSet(codetype);
        //根据type分类
        Map<String, List<SysCodeDtl>> typeObjectMap = new LinkedHashMap<>();
        for (String type : codetype) {
            List<SysCodeDtl> list = new ArrayList<>();
            for (SysCodeDtl sysCodeDtl : sysCodeDtlList) {
                if (type.equals(sysCodeDtl.getCodeType())) {
                    list.add(sysCodeDtl);
                }
            }
            typeObjectMap.put(type, list);
        }
        for (T obj : objects) {
            //获取父类对象属性
            Field[] fields = this.getAllFields(obj);//obj.getClass().getSuperclass().getDeclaredFields()
            for (Field field : fields) {
                //循环map定义的属性
                for (String key : map.keySet()) {
                    //匹配属性
                    if (key.equals(field.getName())) {
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                        Method rM = pd.getReadMethod();//获得读方法
                        //或得map中属性对应type的list值
                        List<SysCodeDtl> list = typeObjectMap.get(map.get(key));
                        //匹配对象的值和list中的code
                        for (SysCodeDtl sysCodeDtl : list) {
                            //匹配将中文set到vo类的带CP属性中
                            if (sysCodeDtl.getCode().equals(rM.invoke(obj))) {
                                String name = field.getName() + "Cp";
                                String setName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                                Method setMe = obj.getClass().getDeclaredMethod(setName, obj.getClass().getDeclaredField(name).getType());
                                setMe.invoke(obj, sysCodeDtl.getDescription());
                            }
                        }
                    }
                }
            }
        }
        return objects;
    }

    /**
     * 获取对象类和所有父类的字段
     *
     * @author HHe
     * @date 2019/9/30 11:29
     */
    public Field[] getAllFields(Object obj) {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = obj.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        Field[] fields = fieldList.stream().toArray(Field[]::new);
        return fields;
    }

    /**
     * 判断是否含有属性
     *
     * @author HHe
     * @date 2019/10/6 11:24
     */
    public boolean whetherHaveProp(Object obj, String prop) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(prop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 编号类
     *
     * @param sysRefNumDtl 需要set unitId、refNumId
     * @author HHe
     * @date 2019/9/19 19:35
     */
    @Transactional
    public String createDocNum(SysRefNumDtl sysRefNumDtl) {
        //获取增量
        SysRefNum sysRefNum = sysRefNumService.querySysRefNumByNumId(sysRefNumDtl.getRefNumId());
        Long stepSize;
        String toNum;
        if (sysRefNum == null) {
            stepSize = 1L;
            toNum = "99999999";
            sysRefNumDtl.setUnitId(0L);
        } else {
            stepSize = sysRefNum.getStepSize();
            toNum = sysRefNum.getToNum().toString();
        }
        //根据refNumId和unitId查询dtl,如果没有，返回默认的编号信息；
        SysRefNumDtl refNumDtl = sysRefNumDtlSerivce.querySysRefDtlByNumIdAndUnitId(sysRefNumDtl);
        //编号自增1并且返回编号，比对newNum如果相同则可用，否则递归查询、自增方法
        sysRefNumDtlSerivce.updateAutoIncrementLastNum(refNumDtl, stepSize);
        SysRefNumDtl refNumDtl1 = sysRefNumDtlSerivce.querySysRefDtlByNumIdAndUnitId(sysRefNumDtl);
        Long newLastNum = refNumDtl1.getLastNum();
        String num = this.getLengthString(newLastNum.toString(), toNum.length());
        String prefix = "";
        if (refNumDtl.getPrefix() != null && !"".equals(refNumDtl.getPrefix())) {
            prefix = refNumDtl.getPrefix();
        }
        return prefix + num;
    }


    /**
     * 生成长度为length的字符串，如果oriString长度大于length，抛出异常 用于生成长度不够8位的顺序码，不够8位的用"0"补充
     *
     * @param oriString 原始字符串
     * @param length    要返回的字符串长度
     * @return 一定长度的字符串
     * @throws Exception
     */
    private String getLengthString(String oriString, int length) {
        String retString = oriString;
        if (oriString.length() == length) {
            return retString;
        } else {
            for (int i = 0; i < length - oriString.length(); i++) {
                retString = "0" + retString;
            }
        }
        return retString;
    }

    /**
     * 根据用户输入仓库编号和其组织判断是否可用
     *
     * @author HHe
     * @date 2019/9/24 11:40
     */
    public Wareh judgeWareh(String warehCode, SysUser sysUser) {
        //判断仓库是否为空
        if (StringUtils.NullEmpty(warehCode)) {
            throw new ServiceException("201", "仓库编号不可为空");
        }
        SysUnit sysUnit = sysUnitService.queryUnitByCode(warehCode);
        Wareh wareh = warehService.querywarehByUnitId(sysUnit.getUnitId(), sysUser.getDomain().getUnitId());
        if (wareh == null) {
            throw new ServiceException("201", "仓库不可用");
        }
        return wareh;
    }

    /**
     * 封装商品集合
     *
     * @author HHe
     * @date 2019/10/7 16:56
     */
    public <T> List<T> queryProdDetailsByProdIdList(List<T> objects, Map<String, Object> map) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IntrospectionException, NoSuchFieldException {
        if(objects==null||objects.size()<=0){
            return objects;
        }
        List<Long> prodIds = new ArrayList<>();
        for (T obj : objects) {
            Method getMe = obj.getClass().getSuperclass().getDeclaredMethod("getProdId", null);
            prodIds.add((Long) getMe.invoke(obj));
        }
        //查询商品集合信息
        List<ProdDetailModel> detailModels = new ArrayList<>();
        List<Product> products = productService.queryProductListByProdList(prodIds);
        Set<Long> prodClsIds = new TreeSet<>();
        Set<Long> colorIds = new TreeSet<>();
        Set<Long> specIds = new TreeSet<>();
        for (Product p : products) {
            prodClsIds.add(p.getProdClsId());
            ProdDetailModel prodDetailModel = new ProdDetailModel();
            prodDetailModel.setProdId(p.getProdId());//商品Id
            prodDetailModel.setProdCode(p.getProdCode());//商品代码
            prodDetailModel.setProdClsId(p.getProdClsId());//品种Id
            prodDetailModel.setColorId(p.getColorId());//颜色Id
            prodDetailModel.setEdition(p.getEdition());//版型
            prodDetailModel.setSpecId(p.getSpecId());//规格Id
            detailModels.add(prodDetailModel);
            colorIds.add(p.getColorId());
            specIds.add(p.getSpecId());
        }
        //查询品种信息
        List<ProdCls> prodClsList = prodClsService.queryProdClsListByIds(prodClsIds);
        //查询颜色集合
        List<Color> colorList = colorService.queryColorListByIds(colorIds);
        //查询规格集合
        List<Spec> specList = specService.querySpecListByIds(specIds);
        for (ProdDetailModel detailModel : detailModels) {
            for (ProdCls prodCls : prodClsList) {
                if (detailModel.getProdClsId().equals(prodCls.getProdClsId())) {
                    detailModel.setProdName(prodCls.getProdName());//商品名称
                    detailModel.setInputCode(prodCls.getInputCode());//助记码
                    detailModel.setUom(prodCls.getUom());//计量单位
                    detailModel.setSeqNum(prodCls.getSeqNum());//序号
                    break;
                }
            }
            for (Color color : colorList) {
                if (detailModel.getColorId().equals(color.getColorId())) {
                    detailModel.setColorCp(color.getColorName());
                    break;
                }
            }
            for (Spec spec : specList) {
                if (detailModel.getSpecId().equals(spec.getSpecId())) {
                    detailModel.setSpecCp(spec.getSpecName());
                    break;
                }
            }
        }
        Map<String, Object> codeDtlMap = new HashMap<>();
        codeDtlMap.put("uom","UOM");
        codeDtlMap.put("edition","EDITION");
        detailModels = this.loadCPByCodeDtl(codeDtlMap, detailModels);
        for (T obj : objects) {
            for (ProdDetailModel detailModel : detailModels) {
                Method getMe = obj.getClass().getSuperclass().getDeclaredMethod("getProdId", null);
                Long prodId = (Long) getMe.invoke(obj);
                if (prodId.equals(detailModel.getProdId())) {
                    Field[] fields = obj.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if(this.whetherHaveProp(detailModel,field.getName())){
                            Method getDMe = detailModel.getClass().getDeclaredMethod("get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1),null);
                            Method setOMe = obj.getClass().getDeclaredMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), obj.getClass().getDeclaredField(field.getName()).getType());
                            setOMe.invoke(obj,getDMe.invoke(detailModel));
                        }
                    }
                }
            }
        }
        return objects;
    }
    /**
     * 功能列表展示-判断状态
     * @author HHe
     * @date 2019/10/14 20:15
     */
    public List<OperationVo> judgeFunction(String url,String docType,String cancelled,String suspended,String progress){
        //查询出规则集合
        FunRule funRule = new FunRule();
        funRule.setDocType(docType);
        List<FunRule> funRuleList = funRuleService.queryFunctionList(funRule);
        FunRule fr = new FunRule();
        for (FunRule rule : funRuleList) {
            //判断撤销
            if("T".equals(cancelled)){
                if("T".equals(rule.getCancelled())){
                    fr.setFunction(rule.getFunction());
                    break;
                }
                continue;
            }
            //判断挂起
            if("T".equals(suspended)){
                if("T".equals(rule.getSuspended())){
                    fr.setFunction(rule.getFunction());
                    break;
                }
                continue;
            }
            //匹配进度
            if(rule.getProgress().toUpperCase().equals(progress.toUpperCase())){
                fr.setFunction(rule.getFunction());
            }
        }
        return this.parseFun(fr.getFunction(),url);
    }
    /**
     * 功能列表展示-解析规则
     * @author HHe
     * @date 2019/10/14 20:15
     */
    public List<OperationVo> parseFun(String function, String url){
        List<OperationVo> operationVoList = new ArrayList<>();
        List<String> funCodeList = new ArrayList<>();
        //以|分隔成code:status
        String[] aFun = function.split("\\|");
        for (String f : aFun) {
            //以:分隔并ser到对象
            String[] mess = f.split(":");
            OperationVo operationVo = new OperationVo();
            operationVo.setOperationCode(mess[0]);
            operationVo.setOperationStutas(mess[1]);
            operationVo.setOperationUrl(url+"/"+mess[0]);
            operationVoList.add(operationVo);
            funCodeList.add(mess[0]);
        }
        List<SysCodeDtl> sysCodeDtlList = sysCodeDtlService.queryCodeDtlByCodesType(funCodeList,"BILL_FUNCTION");
        for (OperationVo operationVo : operationVoList) {
            for (SysCodeDtl sysCodeDtl : sysCodeDtlList) {
                if(operationVo.getOperationCode().equals(sysCodeDtl.getCode())){
                    operationVo.setOperationName(sysCodeDtl.getDescription());
                    break;
                }
            }
        }
        return operationVoList;
    }
    /**
     * 动态加载方法
     * @author HHe
     * @date 2019/10/9 15:36
     */
    public Object dynamicRequest(String beanName, String meName, Object... obj) throws IllegalAccessException {
        //获取bean
        Object bean = SpringUtils.getBean(beanName);
        //获取方法返回类型
        Method method1 = null;
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(meName.equals(method.getName())){
                method1 = method;
                break;
            }
        }
        Object o;
        try {
             o = method1.invoke(bean,obj);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Throwable targetException = e.getTargetException();
            throw new ServiceException(JsonResultCode.FAILURE,targetException.getMessage());
        }
        return o;
    }
    /**
     * equals判断是否含有某值
     * @author HHe
     * @date 2019/10/10 10:43
     */
    public static boolean equalsHave(String prim,String... comp){
        for (String c : comp) {
            if(c.equals(prim)){
                return true;
            }
        }
        return false;
    }
    /**
     * 打印对象中的字段
     */
    public String Attr(Object o) throws Exception {
        Set<String> strs = new HashSet<>();
        Field[] field = this.getAllFields(o);
        for (Field f : field) {
            strs.add(f.getName());
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : strs) {
            stringBuffer.append(str).append(",");
        }
        return stringBuffer.toString();
    }
    /**
     * 如果是空字符串则返回对象null
     * @author HHe
     * @date 2019/11/26 10:08
     */
    public static<T> T returnNotNullString(T o){
        if("".equals(o)){
            return o;
        }
        return null;
    }
}
