package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.service.base.BaseFactory;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述: 通用弹窗工具类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/22 18:29
 */
@Component
public class CommonWindowUtils {
    @Autowired
    BaseFactory baseFactory;

    @Autowired
    private FiledUtils filedUtils;

    /**
     * 功能描述: 根据字段生成Sql
     *
     * @param: fields字段=值 集合
     * @param: tableName表别名
     * @return:
     * @auther: CLF
     * @date: 2019/9/22 18:31
     */
    public String getWindowSql(List<PurKeyValue> fields, String tableName) {
        String sql = "";
        String and = "  AND  ";
        if (StringUtils.isNotEmpty(tableName)) {
            tableName = tableName + ".";
        } else {
            tableName = "";
        }
        if (CollectionUtils.isNotEmpty(fields)) {
            for (PurKeyValue keyValue : fields) {
                if (fields.size() == 1) {
                    return tableName + filedUtils.underLine(keyValue.getOptionName()) + "='" + keyValue.getOptionValue()+"'";
                }
                sql += tableName + filedUtils.underLine(keyValue.getOptionName()) + "='" + keyValue.getOptionValue() +"'"+ and;
            }
        }
        sql = sql.substring(0, sql.lastIndexOf(and));
        return sql;
    }

    /**
     * 功能描述: 通用弹窗查询单条记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 14:39
     */
    public Object getSingle(CommonWindowModel model, SysUser user) throws Exception{
        model.setSql(this.getWindowSql(model.getFields(), model.getTableNameAliase()));
        BaseService baseService = baseFactory.creatBaseService(model);
        if (baseService != null) {
            return baseService.selectObject(model, user);
        }
        return null;
    }
}
