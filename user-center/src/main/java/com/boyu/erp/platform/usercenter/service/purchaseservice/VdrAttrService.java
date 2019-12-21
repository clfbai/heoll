package com.boyu.erp.platform.usercenter.service.purchaseservice;


import com.boyu.erp.platform.usercenter.entity.basic.Vender;
import com.boyu.erp.platform.usercenter.model.purchase.VdrAttrModel;

import java.util.*;

/**
 * @Classname BgService
 * @Description TODO
 * @Date 2019/7/9 14:11
 * @Created wz
 */
public interface VdrAttrService {

    int updateVdrAttr( VdrAttrModel vdrAttrModel) throws Exception;

    /**
     * 查询供应商相应得供应商属性
     * @param
     * @param
     * @return
     */
    List<Map<String,Object>> vdrAttrList(Vender ven) throws  Exception;
}
