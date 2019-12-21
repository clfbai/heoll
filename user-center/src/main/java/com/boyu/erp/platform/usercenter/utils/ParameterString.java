package com.boyu.erp.platform.usercenter.utils;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: boyu-platform
 * @description: 参数配置字段
 * @author: clf
 * @create: 2019-05-18 09:49
 */
@Component
public class ParameterString {
    @Autowired
    private SysParameterMapper sysParameterMapper;
    //操作权限ID后缀
    public static final String OPERATION_AUTHORITY = "_OPERATION_CRUD";

    //表字段不可直接修改参数ID后缀
    public static final String TABLE_NOT_UPDATE = "_TABLE_NOT_UPDATE";

    //表字段必填项参数ID后缀
    public static final String CREAT_TABLE_FILEDS = "_CREAT_TABLE_FIELDS";

    //新建表字段权限参数ID后缀(表字段权限 拥有对应权限才可见对应的字段)
    public static final String CREAT_TABLE = "_CRITICAL_FIELDS";

    //修改表字段权限参数ID后缀(关键字段 拥有对应权限才能修改)
    public static final String UPDATA_TABLE_FILE = "_UPDATA_TABLE_FILE";

    //否启用组织品牌权限控制 参数值
    public static final String UNIT_BRAND_PRIV = "UNIT_BRAND_CONTROL_ENABLED";

    //否启用用户品牌权限控制 参数值
    public static final String USER_BRAND_PRIV = "USER_BRAND_CONTROL_ENABLED";

    // 品牌资源权限Id
    public static final String GOODS_BRAND_PRIV = "goods_priv_crud";

    // 品牌分组源权权限Id
    public static final String BRAND_BG_PRIV = "brand_bg_priv_crud";


    // 商品品种 生成商品品种代码参数
    public static final String PROC_CLS = "PRODUCT_CLASS_CODING_RULE";

    // 生成商品代码 参数
    public static final String PRODUCT_CODE = "PRODUCT_CODING_RULE";


    public Boolean UploginCwms() {
        SysParameter parameter = sysParameterMapper.findById("UploginCwms");
        if (parameter == null) {
            SysParameter sys = new SysParameter();
            sys.setParmVal("T");
            sys.setParmId("UploginCwms");
            sys.setDescription("上传C-WMS平台开关");
            sys.setIsDel(CommonFainl.BTYPESTATUS);
            sysParameterMapper.insertSelective(sys);
            return true;
        }else {
            if ("T".equalsIgnoreCase(parameter.getParmVal())) {
                return true;
            }
        }
        return false;
    }

}
