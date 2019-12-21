package com.boyu.erp.platform.usercenter.config.PrivConfig;

import com.boyu.erp.platform.usercenter.utils.refttable.ReftTable;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 表字段权限配置
 * @author: clf
 * @create: 2019-06-13 11:38
 */
@Data
public class TablePriv {
    /**
     * 人员表返回全类名
     */
    private static final String SysPrsnlVo = "com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo";

    /**
     * 商品品种表返回全类名
     */
    private static final String ProdClsVo = "com.boyu.erp.platform.usercenter.vo.goods.ProdClsVo";


    //内部类的初始化需要依赖主类，需要先等主类实例化之后，内部类才能开始实例化
    private static class SingletonTablePriv {
        //这里加final是为了防止内部将这个属性覆盖掉
        private static final TablePriv TABLE_PRIV = new TablePriv();
    }

    //这里加final是为了防止子类重写父类
    public static final TablePriv getInstance() {
        return SingletonTablePriv.TABLE_PRIV;
    }

    /**
     * @return map 返回需要过滤对象的全类名Map
     * @program:
     * @description:
     * @author: CLF
     */
    public Map<String, Object> init() throws Exception {
        return ReftTable.getTableFieldAndValue(TablePriv.getInstance());

    }

    /**
     * @return 对象属性长度 size
     * @program: ${PROJECT_NAME}
     * @description: 查看对象属性是否被修改(修改size增加)
     * @author: CLF
     */
    public static int TablePrivFiledsSize() {
        Field[] fields = TablePriv.getInstance().getClass().getDeclaredFields();
        return fields.length;
    }


}
