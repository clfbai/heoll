package com.boyu.erp.platform.usercenter.vo;

import org.springframework.beans.factory.annotation.Value;

public class CommonFainl {

    //入库类型 (采购入库(供应商未介入)
    public static final String BatchTypePe = "pe";
    //入库类型 (采购入库(供应商介入)
    public static final String BatchTypeIl = "il";
    //入库类型 (生产入库批次)
    public static final String BatchTypePn = "pn";
    //组织销售
    public static final String BatchTypeUTMT = "utmt";
    //组织采购
    public static final String BatchTypeUTPE = "utpe";
    //顾客购买
    public static final String BatchTypeUNCT = "unct";
    //正常批次
    public static final String BatchType_NOL = "nol";
    //异常批次
    public static final String BatchType_SPI = "spi";

    public static final String OADescription = "操作权限";
    public static final String OADescriptionADD = "增加操作权限";
    public static final String OADescriptionUPDATE = "修改操作权限";
    public static final String OADescriptionDELETE = "删除操作权限";

    public static final String PRIV_TYPE_BT = "BT";
    public static final String UNITTYPE_SH = "SH";
    //权限范围(本组织及及下属)
    public static final String SCOPEALL = "all";
    //权限范围(本组织下多选)
    public static final String SCOPEOR = "or";
    //权限范围(本组织)
    public static final String SCOPEOL = "ol";

    public static final String DATABEASNAME = "thjdb";

    public static final String COSTNAME = "缺省成本组";

    public static final String PARAMRESTU = "请输入参数";

    public static final String UPDATESTUS = "修改成功";

    public static final String PAGE = "1";

    public static final String SIZE = "15";

    public static final String CODESTUS = "代码生成成功";

    public static final String CODEFIANL = "代码已存在";

    public static final String UPDATEFANS = "修改失败";

    public static final String UNABLEUPDATEFANS = "当前状态下无法修改";

    public static final String DELETEFANS = "删除失败";

    public static final String UNABLEDELETEFANS = "当前状态下无法删除";

    public static final String DELETESTUAS = "删除成功";

    public static final String ADDSTUS = "增加成功";

    public static final String ADDFIANL = "增加失败";

    public static final String SELECTSTUS = "查询成功";

    public static final String SELECTFIANL = "查询异常";

    /**
     * 输入框输入查询异常
     */
    public static final String SELECTPOPUPFIANL = "查询异常";


    /**
     * 字段权限 key
     */
    public static final String TABLE_PRIV = "TABLE_PRIV";

    public static final String CUT_DOMINA_UPDAT = "修改授权切换领域";

    public static final String USERLONGORIGIN = "回到原领域";

    public static final String CUT_DOMINA_DELETE = "删除授权切换领域";

    public static final String CUT_DOMINA_ADD = "授权切换领域";

    public static final String USERLONG = "用户登陆";

    public static final String USERLONGCAT = "用户切换领域";
    //正常
    public static final String STATUS = "1";
    //删除
    public static final String FAIL = "-1";

    public static final Byte BTYPESTATUS = 1;

    public static final Byte FAILSTATUS = -1;

    public static final int ADMIN = -1;

    public static final int LOWSER = 1;

    public static final String FALSE = "F";
    //正常登陆
    public static final String NORMALLOGIN = "L";

    public static final String TRUE = "T";

    public static final String USER_STATUS = "A";

    public static final String FILAN_STATUS = "D";

    //直接操作权限表(主要是添加和删除)
    public static final String PRIV_MAX = "maxpriv";

    //直接操作角色表(主要是添加和删除)
    public static final String ROLE_MAX = "maxrole";

    //切换领域
    public static final String ISCUTDOMAIN = "cutdomain";
    //正常登陆
    public static final String LOGIN = "login";

    public static final String NORMALLOGINUSER = "normaluser";

    public static final String ADD = "ADD";

    public static final String DELETE = "DELETE";

    public static final String UPDATE = "UPDATE";
    public static final String SELECT = "SELECT";

    public static final String CONFIRM = "CONFIRM";

    public static final String REDO = "REDO";

    public static final String EXAMINE = "EXAMINE";

    public static final String REITERATE = "REITERATE";

    public static final String HANGUP = "HANGUP";

    public static final String RECOVERY = "RECOVERY";

    public static final String TOVOID = "TOVOID";

    public static final String CLOSEGOODS = "CLOSEGOODS";

    public static final String RESTARTGOODS = "RESTARTGOODS";

    public static final String CLOSE = "CLOSE";

    public static final String REUSING = "REUSING";

    //查询集合
    public static final String SELECTLIST = "SELECTLIST";
    //查询详情
    public static final String SELECTDETAILS = "SELECTDETAILS";
    public static final String PRIV_SCOPE_STS = "SYSTEM";

    public static final String TEXT = "你尚未获得操作权限: ";

    public static final String OPTIONfAIL = "该供应商与采购商不存在购销协议";

    public static final String PRODBYPUOFAIL = "采购合同中不存在该商品";

    public static final String PRODBYSLOFAIL = "销售合同中不存在该商品";

    public static final String BILLCONFIRM = "单据已确认";

    public static final String BILLCONFIRMFIANL = "单据确认失败";

    public static final String BILLREDO = "单据已重做";

    public static final String BILLREDOFIANL = "单据重做失败";

    public static final String BILLEXAMINE = "单据已审核";

    public static final String BILLEXAMINEFIANL = "单据审核失败";

    public static final String VDRSUPPLYPRODVERFIAIL = "供应商供货信息中不存在该商品";

    public static final String VDRSUPPLYPRODVER = "供应商供货信息中存在该商品";

    public static final String CREATE_GDN_NUM_EX="生成出库单数量异常";

    public static final String BILLREITERATE = "单据已反审";

    public static final String BILLREITERATEFIANL = "单据反审失败";

    public static final String BILLHANGUP = "单据已挂起";

    public static final String BILLHANGUPFIANL = "单据挂起失败";

    public static final String BILLRECOVERY = "单据已恢复";

    public static final String BILLRECOVERYFIANL = "单据恢复失败";

    public static final String BILLTOVOID = "单据已作废";

    public static final String BILLTOVOIDFIANL = "单据作废失败";

    public static final String BILLIMPLEMENT = "单据已执行";

    public static final String BILLIMPLEMENTFIANL = "单据执行失败";

    public static final String BILLCLOSE = "单据已关闭";

    public static final String BILLCLOSEFIANL = "单据关闭失败";

    public static final String BILLREUSING = "单据已重用";

    public static final String BILLREUSINGFIANL = "单据重用失败";

    public static final String BILLCLOSEGOODS = "单据已结束入库";

    public static final String BILLCLOSEGOODSFIANL = "结束入库失败";

    public static final String BILLRESTARTGOODS = "单据已重启入库";

    public static final String BILLRESTARTGOODSFIANL = "重启入库失败";

    public static final String BILLIMPORTBILL = "单据已导入";

    public static final String BILLIMPORTBILLFIANL = "单据导入失败";

    public static final String BILLIMPORTUPDATE = "单据导入更新";

    public static final String BILLIMPORTUPDATEFIANL = "单据导入更新失败";

    public static final String CHANGESTATUSFAIL = "状态切换失败";

    public static final String FUNCTIONOPSTUS = "功能操作成功";

    public static final String FUNCTIONOPFANS = "功能操作失败";

    public static final String WMS_RECEIVE_SUCC = "接收成功";

    public static final String WMS_RECEIVE_FIANL = "接收失败";

    public static final String GENERATE_SUCC = "生成成功";

    public static final String GENERATE_FIANL = "生成失败";

    public static final String FORMAT_ERROR = "格式错误";
}
