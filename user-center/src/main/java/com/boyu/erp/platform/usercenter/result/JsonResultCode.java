package com.boyu.erp.platform.usercenter.result;

/**
 * @author administrator
 */
public class JsonResultCode {


    /**
     * 登陆失败
     **/
    public static final String FAILURE_LOGIN = "201100";


    /**
     * 成功
     **/
    public static final String SUCCESS = "200";


    /**
     * 没有登录
     **/
    public static final String NO_LOGIN = "100";

    /**
     * 失败
     **/
    public static final String FAILURE = "201";
    /**
     * 多领域
     **/
    public static final String MANY_DOMAIN = "203";


    /**
     * 系统代码
     **/
    public static final String FAILURE_SYS = "100";
    /**
     * 系统组织 查询异常
     **/
    public static final String FAILURE_SYS_UNIT = "100100";

    /**
     * 系统人员查询
     **/
    public static final String FAILURE_SYS_PRSNL = "100400";
    /**
     * 系统组织分组查询异常
     **/
    public static final String FAILURE_SYS_UG = "100600";

    /**
     * 入库单异常
     **/
    public static final String FAILURE_GRN = "610";

    /**
     * 入库单增加异常
     **/
    public static final String FAILURE_GRN_1 = "611";

    public static final String FAILURE_GRN_2 = "612";

    public static final String FAILURE_GRN_3 = "613";


    /**
     * 入库任务查询异常
     **/
    public static final String FAILURE_RCVTASK = "620";
    /**
     * 入库任务查询异常
     **/
    public static final String FAILURE_RCVTASK_DTL = "6201";
    /**
     * 入库任务增加异常
     **/
    public static final String FAILURE_RCVTASK_1 = "621";
    /**
     * 入库任务修改异常
     **/
    public static final String FAILURE_RCVTASK_2 = "622";
    /**
     * 入库任务删除异常
     **/
    public static final String FAILURE_RCVTASK_3 = "623";

    /**
     * 仓库下拉查询异常
     **/
    public static final String FAILURE_WAERH_OPENT = "605";
    /**
     * 公司下拉查询异常
     **/
    public static final String FAILURE_WAERH_COMPANY = "604";
    /**
     * 仓库查询异常
     * CUD 1-3
     **/
    public static final String FAILURE_WAERH = "600";
    public static final String FAILURE_WAERH_1 = "601";
    public static final String FAILURE_WAERH_2 = "602";
    public static final String FAILURE_WAERH_3 = "603";

    /**
     * 查询商品品种失败
     * 1-3 CUD
     **/
    public static final String FAILURE_GOODS_CLS = "700";
    /**
     * 商品品种添加失败
     * 1-3 CUD
     **/
    public static final String FAILURE_GOODS_CLS_1 = "701";
    /**
     * 商品品种修改失败
     * 1-3 CUD
     **/
    public static final String FAILURE_GOODS_CLS_2 = "702";
    /**
     * 商品品种删除失败
     * 1-3 CUD
     **/
    public static final String FAILURE_GOODS_CLS_3 = "703";


    /**
     * 查询商品品种属性失败
     * 1-3 CUD
     **/
    public static final String FAILURE_GOODS_ATTR = "705";

    /**
     * 查询商品失败
     * 1-3 CUD
     **/
    public static final String FAILURE_GOODS = "800";
    /**
     * 增加商品失败
     **/
    public static final String FAILURE_GOODS_1 = "801";
    /**
     * 修改商品失败
     **/
    public static final String FAILURE_GOODS_2 = "802";
    /**
     * 删除商品失败
     **/
    public static final String FAILURE_GOODS_3 = "803";


    /**
     * 新增采购合同类别失败
     **/
    public static final String FAILURE_PUCTYPE_C = "900";
    /**
     * 查询采购合同类别失败
     **/
    public static final String FAILURE_PUCTYPE_R = "901";
    /**
     * 修改采购合同类别失败
     **/
    public static final String FAILURE_PUCTYPE_U = "902";
    /**
     * 删除采购合同类别失败
     **/
    public static final String FAILURE_PUCTYPE_D = "903";


    /**
     * 新增退购合同类别失败
     **/
    public static final String FAILURE_PRCTYPE_C = "904";
    /**
     * 查询退购合同类别失败
     **/
    public static final String FAILURE_PRCTYPE_R = "905";
    /**
     * 修改退购合同类别失败
     **/
    public static final String FAILURE_PRCTYPE_U = "906";
    /**
     * 删除退购合同类别失败
     **/
    public static final String FAILURE_PRCTYPE_D = "907";


    /**
     * 新增采购协议失败
     **/
    public static final String FAILURE_PSA_C = "908";
    /**
     * 查询采购协议失败
     **/
    public static final String FAILURE_PSA_R = "909";
    /**
     * 修改采购协议失败
     **/
    public static final String FAILURE_PSA_U = "910";
    /**
     * 删除采购协议失败
     **/
    public static final String FAILURE_PSA_D = "911";
    /**
     * 采购协议弹窗查询失败
     **/
    public static final String FAILURE_PSA_POPUP = "912";


    /**
     * 新增采购协议中折率失败
     **/
    public static final String FAILURE_PSA_DR_C = "913";
    /**
     * 查询采购协议中折率失败
     **/
    public static final String FAILURE_PSA_DR_R = "914";
    /**
     * 修改采购协议中折率失败
     **/
    public static final String FAILURE_PSA_DR_U = "915";
    /**
     * 删除采购协议中折率失败
     **/
    public static final String FAILURE_PSA_DR_D = "916";


    /**
     * 新增采购协议中可退率失败
     **/
    public static final String FAILURE_PSA_RTR_C = "917";
    /**
     * 查询采购协议中可退率失败
     **/
    public static final String FAILURE_PSA_RTR_R = "918";
    /**
     * 修改采购协议中可退率失败
     **/
    public static final String FAILURE_PSA_RTR_U = "919";
    /**
     * 删除采购协议中可退率失败
     **/
    public static final String FAILURE_PSA_RTR_D = "920";


    /**
     * 新增采购协议中税率失败
     **/
    public static final String FAILURE_PSA_TR_C = "921";
    /**
     * 查询采购协议中税率失败
     **/
    public static final String FAILURE_PSA_TR_R = "922";
    /**
     * 修改采购协议中税率失败
     **/
    public static final String FAILURE_PSA_TR_U = "923";
    /**
     * 删除采购协议中税率失败
     **/
    public static final String FAILURE_PSA_TR_D = "924";


    /**
     * 新增供应商失败
     **/
    public static final String FAILURE_VENDER_C = "925";
    /**
     * 查询供应商失败
     **/
    public static final String FAILURE_VENDER_R = "926";
    /**
     * 修改供应商失败
     **/
    public static final String FAILURE_VENDER_U = "927";
    /**
     * 删除供应商失败
     **/
    public static final String FAILURE_VENDER_D = "928";
    /**
     * 查询供应商中自定义属性失败
     **/
    public static final String FAILURE_VENDER_VDR_R = "929";
    /**
     * 修改供应商中自定义属性失败
     **/
    public static final String FAILURE_VENDER_VDR_U = "930";
    /**
     * 供应商中通过所选代码查询详细信息
     **/
    public static final String FAILURE_VENDER_UNIT_U = "931";


    /**
     * 新增采购合同失败
     **/
    public static final String FAILURE_PUC_C = "932";
    /**
     * 查询采购合同失败
     **/
    public static final String FAILURE_PUC_R = "933";
    /**
     * 修改采购合同失败
     **/
    public static final String FAILURE_PUC_U = "934";
    /**
     * 删除采购合同失败
     **/
    public static final String FAILURE_PUC_D = "935";
    /**
     * 查询采购合同供应商弹窗失败
     **/
    public static final String FAILURE_PUC_UNIT_R = "936";
    /**
     * 查询采购合同供应商弹窗失败
     **/
    public static final String FAILURE_PUC_PSA_R = "937";
    /**
     * 查询采购合同供应商弹窗失败
     **/
    public static final String FAILURE_PUC_OTHERUNIT_R = "938";
    /**
     * 选择采购合同类别后查询需要填充的数据
     **/
    public static final String FAILURE_PUC_PSC_R = "939";
    /**
     * 新增采购合同明细失败
     **/
    public static final String FAILURE_PSC_DTL_C = "940";
    /**
     * 查询采购合同明细失败
     **/
    public static final String FAILURE_PSC_DTL_R = "941";
    /**
     * 修改采购合同明细失败
     **/
    public static final String FAILURE_PSC_DTL_U = "942";
    /**
     * 删除采购合同明细失败
     **/
    public static final String FAILURE_PSC_DTL_D = "943";

    /**
     * 没查到购销协议相关数据
     **/
    public static final String FAILURE_PUC_OPTION_R = "944";
    /**
     * 没查到采购合同明细中该商品得信息
     **/
    public static final String FAILURE_PUC_PROD_R = "945";


    /**
     * 商品弹窗查询失败
     **/
    public static final String FAILURE_UNIT_PROD_CLS_R = "946";


    /**
     * 新增采购单失败
     **/
    public static final String FAILURE_PUO_C = "947";
    /**
     * 查询采购单失败
     **/
    public static final String FAILURE_PUO_R = "948";
    /**
     * 修改采购单失败
     **/
    public static final String FAILURE_PUO_U = "949";
    /**
     * 删除采购单失败
     **/
    public static final String FAILURE_PUO_D = "950";

    /**
     * 新增采购单明细失败
     **/
    public static final String FAILURE_PSO_DTL_C = "951";
    /**
     * 查询采购单明细失败
     **/
    public static final String FAILURE_PSO_DTL_R = "952";
    /**
     * 修改采购单明细失败
     **/
    public static final String FAILURE_PSO_DTL_U = "953";
    /**
     * 删除采购单明细失败
     **/
    public static final String FAILURE_PSO_DTL_D = "954";


    /**
     * 新增采购申请失败
     **/
    public static final String FAILURE_PUA_C = "955";
    /**
     * 查询采购申请失败
     **/
    public static final String FAILURE_PUA_R = "956";
    /**
     * 修改采购申请失败
     **/
    public static final String FAILURE_PUA_U = "957";
    /**
     * 删除采购申请失败
     **/
    public static final String FAILURE_PUA_D = "958";

    /**
     * 新增购销申请明细失败
     **/
    public static final String FAILURE_PSX_DTL_C = "959";
    /**
     * 查询购销申请明细失败
     **/
    public static final String FAILURE_PSX_DTL_R = "960";
    /**
     * 修改购销申请明细失败
     **/
    public static final String FAILURE_PSX_DTL_U = "961";
    /**
     * 删除购销申请明细失败
     **/
    public static final String FAILURE_PSX_DTL_D = "962";
    /**
     * 选择采购申请类别后查询需要填充的数据
     **/
    public static final String FAILURE_PSX_TYPE_R = "963";

    /**
     * 新增退购合同失败
     **/
    public static final String FAILURE_PRC_C = "964";
    /**
     * 查询退购合同失败
     **/
    public static final String FAILURE_PRC_R = "965";
    /**
     * 修改退购合同失败
     **/
    public static final String FAILURE_PRC_U = "966";
    /**
     * 删除退购合同失败
     **/
    public static final String FAILURE_PRC_D = "967";

    /**
     * 新增退购合同明细失败
     **/
    public static final String FAILURE_RTC_DTL_C = "968";
    /**
     * 查询退购合同明细失败
     **/
    public static final String FAILURE_RTC_DTL_R = "969";
    /**
     * 修改退购合同明细失败
     **/
    public static final String FAILURE_RTC_DTL_U = "970";
    /**
     * 删除退购合同明细失败
     **/
    public static final String FAILURE_RTC_DTL_D = "971";

    /**
     * 单据确认失败
     */
    public static final String FAILURE_BILL_CONFIRM = "972";

    /**
     * 单据审核失败
     */
    public static final String FAILURE_BILL_EXAMINE = "973";

    /**
     * 供应商供货信息查询失败
     */
    public static final String FAILURE_VDR_SUPPLY_PROD_R = "974";

    /**
     * 供应商供货信息添加或删除失败
     */
    public static final String FAILURE_VDR_SUPPLY_PROD_C_D = "975";
    /**
     * 采购添加明细时验证该供应商是否供货
     */
    public static final String FAILURE_VDR_SUPPLY_PROD_VERIFICATION = "976";
    /**
     * 销售合同查询失败
     */
    public static final String FAILURE_SLC_R = "977";
    /**
     * 销售单查询失败
     */
    public static final String FAILURE_SLO_R = "978";
    /**
     * 查询退销合同失败
     **/
    public static final String FAILURE_SRC_R = "979";

    /**
     * 销售合同新增失败
     */
    public static final String FAILURE_SLC_C = "980";

    /**
     * 销售合同修改失败
     */
    public static final String FAILURE_SLC_U = "981";
    /**
     * 销售合同删除失败
     */
    public static final String FAILURE_SLC_D = "982";
    /**
     * 选择销售合同类别后查询需要填充的数据
     **/
    public static final String FAILURE_SLC_PSC_R = "983";
    /**
     * 销售单新增失败
     */
    public static final String FAILURE_SLO_C = "984";
    /**
     * 销售单修改失败
     */
    public static final String FAILURE_SLO_U = "985";
    /**
     * 销售单删除失败
     */
    public static final String FAILURE_SLO_D = "986";
    /**
     * 退销合同新增失败
     */
    public static final String FAILURE_SRC_C = "987";
    /**
     * 退销合同修改失败
     */
    public static final String FAILURE_SRC_U = "988";
    /**
     * 退销合同删除失败
     */
    public static final String FAILURE_SRC_D = "989";
    /**
     * 选择退购合同类别后查询需要填充的数据
     **/
    public static final String FAILURE_PRC_RTC_R = "990";
    /**
     * 选择退销合同类别后查询需要填充的数据
     **/
    public static final String FAILURE_SRC_RTC_R = "991";



    /**
     * 出库任务添加异常
     */
    public static final String FAILURE_DELIVTASKT_I = "1001";
    /**
     * 进入出库任务查询待处理任务失败
     */
    public static final String FAILURE_DELIVTASKT_PENDING_Q = "1010";


    /**
     * 新增购销合同类别失败
     **/
    public static final String FAILURE_PSCTYPE_C = "1100";
    /**
     * 查询购销合同类别失败
     **/
    public static final String FAILURE_PSCTYPE_R = "1101";
    /**
     * 修改购销合同类别失败
     **/
    public static final String FAILURE_PSCTYPE_U = "1102";
    /**
     * 删除购销合同类别失败
     **/
    public static final String FAILURE_PSCTYPE_D = "1103";

    /**
     * 新增购销申请类别失败
     **/
    public static final String FAILURE_PSXTYPE_C = "1104";
    /**
     * 查询购销申请类别失败
     **/
    public static final String FAILURE_PSXTYPE_R = "1105";
    /**
     * 修改购销申请类别失败
     **/
    public static final String FAILURE_PSXTYPE_U = "1106";
    /**
     * 删除购销申请类别失败
     **/
    public static final String FAILURE_PSXTYPE_D = "1107";

    /**
     * 新增退货合同类别失败
     **/
    public static final String FAILURE_RTCTYPE_C = "1108";
    /**
     * 查询退货合同类别失败
     **/
    public static final String FAILURE_RTCTYPE_R = "1109";
    /**
     * 修改退货合同类别失败
     **/
    public static final String FAILURE_RTCTYPE_U = "1110";
    /**
     * 删除退货合同类别失败
     **/
    public static final String FAILURE_RTCTYPE_D = "1111";

    /**
     * 新增领用单类别失败
     **/
    public static final String FAILURE_ARQTYPE_C = "1112";
    /**
     * 查询领用单类别失败
     **/
    public static final String FAILURE_ARQTYPE_R = "1113";
    /**
     * 修改领用单类别失败
     **/
    public static final String FAILURE_ARQTYPE_U = "1114";
    /**
     * 删除领用单类别失败
     **/
    public static final String FAILURE_ARQTYPE_D = "1115";

    /**
     * 新增采购价格单失败
     **/
    public static final String FAILURE_PPN_C = "1116";
    /**
     * 查询采购价格单失败
     **/
    public static final String FAILURE_PPN_R = "1117";
    /**
     * 修改采购价格单失败
     **/
    public static final String FAILURE_PPN_U = "1118";
    /**
     * 删除采购价格单失败
     **/
    public static final String FAILURE_PPN_D = "1119";

    /**
     * 单据重做失败
     */
    public static final String FAILURE_BILL_REDO = "1120";

    /**
     * 单据重审失败
     */
    public static final String FAILURE_BILL_REITERATE = "1121";

    /**
     * 单据挂起失败
     */
    public static final String FAILURE_BILL_HANGUP = "1122";

    /**
     * 单据恢复失败
     */
    public static final String FAILURE_BILL_RECOVERY = "1123";

    /**
     * 单据作废失败
     */
    public static final String FAILURE_BILL_TOVOID = "1124";

    /**
     * 单据执行失败
     */
    public static final String FAILURE_BILL_IMPLEMENT = "1125";

    /**
     * 新增采购价格单明细失败
     **/
    public static final String FAILURE_PPNDTL_C = "1126";
    /**
     * 查询采购价格单明细失败
     **/
    public static final String FAILURE_PPNDTL_R = "1127";
    /**
     * 修改采购价格单明细失败
     **/
    public static final String FAILURE_PPNDTL_U = "1128";
    /**
     * 删除采购价格单明细失败
     **/
    public static final String FAILURE_PPNDTL_D = "1129";

    /**
     * 新增采购价格单明细失败
     **/
    public static final String FAILURE_PPNSCP_C = "1130";
    /**
     * 查询采购价格单明细失败
     **/
    public static final String FAILURE_PPNSCP_R = "1131";
    /**
     * 删除采购价格单明细失败
     **/
    public static final String FAILURE_PPNSCP_D = "1132";

    /**
     * 单据关闭失败
     */
    public static final String FAILURE_BILL_CLOSE = "1133";

    /**
     * 单据重用失败
     */
    public static final String FAILURE_BILL_REUSING = "1134";


    /**
     * 单据结束入库失败
     */
    public static final String FAILURE_BILL_CLOSEGOODS = "1135";

    /**
     * 单据重启入库失败
     */
    public static final String FAILURE_BILL_RESTARTGOODS = "1136";

    /**
     * 查询采购合同/销售合同超额比例失败
     **/
    public static final String FAILURE_PUC_EXBL = "1137";

    /**
     * 查询采购合同/销售合同固定单价失败
     **/
    public static final String FAILURE_PUC_FIXED = "1138";

    /**
     * 单据导入失败
     */
    public static final String FAILURE_BILL_IMPORTBILL = "1139";

    /**
     * 单据导入更新失败
     */
    public static final String FAILURE_BILL_IMPORTUPDATE = "1140";

    /**
     * 弹窗查询异常
     **/
    public static final String FAILURE_OPTION_R = "1141";

    /**
     * 查询购销协议异常
     **/
    public static final String FAILURE_PUR_R = "1142";

    /**
     * 查询组织异常
     **/
    public static final String FAILURE_PUR_UNIT_R = "1143";

    /**
     * 查询原始单据异常
     **/
    public static final String FAILURE_PUR_SRC_R = "1144";


    /**
     * 出入库查询明细异常
     **/
    public static final String FAILURE_COMMONDTL_R = "1146";

    /**
     * 新增采购商失败
     **/
    public static final String FAILURE_VENDEE_C = "1147";
    /**
     * 查询采购商失败
     **/
    public static final String FAILURE_VENDEE_R = "1148";
    /**
     * 修改采购商失败
     **/
    public static final String FAILURE_VENDEE_U = "1149";
    /**
     * 删除采购商失败
     **/
    public static final String FAILURE_VENDEE_D = "1150";

}