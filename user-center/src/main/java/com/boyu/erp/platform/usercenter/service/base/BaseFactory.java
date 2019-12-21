package com.boyu.erp.platform.usercenter.service.base;

import com.boyu.erp.platform.usercenter.components.SpringContextUtil;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.system.SysPrsnlService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述: 简单工厂
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/23 17:12
 */
@Component
public class BaseFactory {
    @Autowired
    SpringContextUtil springContextUtil;

    /**
     * 功能描述: 通过类型获取Bean工具  缺点:获取所有的 .class都是在当前项目下 改为微服务后 可能会找不到该类名
     * 获得对象可以不使用 static  方法
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 17:12
     */
    public BaseService creatBaseService(CommonWindowModel model) {

        if ("sys_unit".equalsIgnoreCase(model.getSelectType())) {
            return springContextUtil.getBean(SysUnitService.class);
        }
        if ("prod_cls".equalsIgnoreCase(model.getSelectType())) {
            return SpringContextUtil.getBean(ProdClsService.class);
        }
        if ("sys_prsnl".equalsIgnoreCase(model.getSelectType())) {
            //人员代码弹窗
            return SpringContextUtil.getBean(SysPrsnlService.class);
        }
        if("wareh".equalsIgnoreCase(model.getSelectType())){
            //仓库弹窗
            return springContextUtil.getBean(WarehService.class);
        }
        if("ca_sys_unit".equalsIgnoreCase(model.getSelectType())){
            //往来账户弹窗
            return SpringContextUtil.getBean(SysUnitService.class);
        }
        return null;
    }



}
