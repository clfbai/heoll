package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.basic.Vendee;
import com.boyu.erp.platform.usercenter.model.sales.VdeAttrModel;

import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 采购商自定义属性定义接口
 * @author: wz
 * @create: 2019-09-02 15:36
 */
public interface VdeAttrService {

    /**
     * 查询采购商相应得采购商属性
     * @param
     * @param
     * @return
     */
    List<Map<String,Object>> vdeAttrList(Vendee ven) throws  Exception;

    /**
     * 添加 修改 删除采购商自定义属性
     * @param vdeAttrModel
     * @return
     * @throws Exception
     */
    int updateVdeAttr( VdeAttrModel vdeAttrModel) throws Exception;
}
