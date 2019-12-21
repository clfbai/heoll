package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.basic.Vendee;
import com.boyu.erp.platform.usercenter.entity.sales.VdeAttr;
import com.boyu.erp.platform.usercenter.entity.sales.VdeAttrDef;
import com.boyu.erp.platform.usercenter.mapper.sales.VdeAttrDefMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.VdeAttrMapper;
import com.boyu.erp.platform.usercenter.model.sales.VdeAttrModel;
import com.boyu.erp.platform.usercenter.service.salesservice.VdeAttrService;
import com.boyu.erp.platform.usercenter.utils.purchase.AttriButeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 采购商自定义属性定义接口实现类
 * @author: wz
 * @create: 2019-09-02 10:14
 */
@Slf4j
@Service
@Transactional
public class VdeAttrServiceImpl implements VdeAttrService {

    @Autowired
    private VdeAttrDefMapper vdeAttrDefMapper;
    @Autowired
    private VdeAttrMapper vdeAttrMapper;
    @Autowired
    private AttriButeUtils attriButeUtils;

    @Override
    public int updateVdeAttr( VdeAttrModel vdeAttrModel){
        int a = 0;
        if (vdeAttrModel.getDeleteVdeAttr() != null && vdeAttrModel.getDeleteVdeAttr().size() > 0) {
            for (VdeAttr v : vdeAttrModel.getDeleteVdeAttr()) {
                a += vdeAttrMapper.deleteByPrimaryKey(v);
            }
        }
        if(vdeAttrModel.getAddVdeAttrr()!=null && vdeAttrModel.getAddVdeAttrr().size()>0){
            for (VdeAttr v : vdeAttrModel.getAddVdeAttrr()) {
                a = vdeAttrMapper.deleteByPrimaryKey(v);
                a += vdeAttrMapper.insertSelective(v);
            }
        }
        if (vdeAttrModel.getUpdateVdeAttr() != null && vdeAttrModel.getUpdateVdeAttr().size() > 0) {
            for (VdeAttr v: vdeAttrModel.getUpdateVdeAttr()) {
                a += vdeAttrMapper.updateByPrimaryKeySelective(v);
            }
        }
        return a;
    }

    @Override
    public List<Map<String, Object>> vdeAttrList(Vendee ven) throws Exception {
        Map<String, Object> map = new HashMap<>();
        /**
         * 获取所有商品自定义 和商品原有属性
         * */
        List<VdeAttrDef> list = vdeAttrDefMapper.getAllVdeAttrDefList(new VdeAttrDef());
        String buffer = "";
        for (VdeAttrDef v : list) {
            /**
             * Map 拼接Sql 入参
             * */
            String str = "MAX(IF( va.`attr_type` = '" + v.getAttrType() + "' , va.`attr_val`,'')) AS  `" + v.getAttrType() + "`,";
            buffer = str + buffer;
            /**
             * List 传入行数 入参
             * */
        }
        map.put("sql", buffer);
        map.put("vendeeId", ven.getVendeeId());
        map.put("ownerId", ven.getOwnerId());
        List<Map<String, Object>> resultList = vdeAttrMapper.vdeAttrList(map);
        if(resultList.get(0)==null){
            return attriButeUtils.Attrs(list);
        }else{
            return resultList;
        }

    }
}
