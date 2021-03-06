package com.boyu.erp.platform.usercenter.constants;

public class ResultCode {

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 失败
     */
    public static final int FAILURE = -1;

    /**
     * 参数错误
     */
    public static final int ARGUMENT_ERROR = -11;

    /**
     * 数据库错误
     */
    public static final int DB_ERROR = 2;

    /**
     * 未登录
     */
    public static final int NOT_LOGIN = 3;

    /**
     * 充值完成页面判断是否是历史订单
     */
    public static final int HISTORY = 4;

    ///////////////////////控制层返回//////////////////////////////
    public static final String PRIV_ARGUMENT_ERROR = "405"; //缺少权限

    public static final String RETURN_SUCCESS = "200"; //成功

    public static final String RETURN_FAILURE = "400"; //失败

    public static final String OTHER_ERROR = "500";//系统错误

    public static final String GATEWAY_ERROR = "501";//网关错误

    public static final String LIKE_EXPIRED_ERROR = "401";   //链接失效,即链接超时

    public static final String PRIMARY_KEY_ERROR = "402";     //秘钥错误

    public static final String ILLEGAL_ARGUMENT_ERROR = "403"; //传递参数不合法

    public static final String ARGUMENT_LACK_ERROR = "405"; //参数缺失错误,不完整

    public static final String SING_ERROR = "406"; //数字签名有误

    public static final String UNIQUE_KEY_ERROR = "407"; //唯一键冲突

    public static final String DB_FAILURE = "410";//数据操作错误

    public static final String DATA_REPEAT = "411"; //数据重复
}