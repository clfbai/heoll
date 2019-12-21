package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.basic.Vender;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttr;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.mapper.purchase.VdrAttrDefMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.VdrAttrMapper;
import com.boyu.erp.platform.usercenter.model.purchase.VdrAttrModel;
import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrAttrService;
import com.boyu.erp.platform.usercenter.utils.purchase.AttriButeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Classname VdrAttrServiceimpl
 * @Description TODO
 * @Date 2019/7/9 14:11
 * @Created wz
 * 供应商属性
 */
@Slf4j
@Service
@Transactional
public class VdrAttrServiceimpl implements VdrAttrService {

    @Autowired
    private VdrAttrMapper vdrAttrMapper;
    @Autowired
    private VdrAttrDefMapper vdrAttrDefMapper;
    @Autowired
    private AttriButeUtils attriButeUtils;

    @Override
    public int updateVdrAttr( VdrAttrModel vdrAttrModel){
        int a = 0;
        if(vdrAttrModel.getAddVdrAttrr()!=null && vdrAttrModel.getAddVdrAttrr().size()>0){
            for (VdrAttr v : vdrAttrModel.getAddVdrAttrr()) {
                a = vdrAttrMapper.deleteByPrimaryKey(v);
                a += vdrAttrMapper.insertSelective(v);
            }
        }
        if (vdrAttrModel.getDeleteVdrAttr() != null && vdrAttrModel.getDeleteVdrAttr().size() > 0) {
            for (VdrAttr v : vdrAttrModel.getDeleteVdrAttr()) {
                a += vdrAttrMapper.deleteByPrimaryKey(v);
            }
        }
        if (vdrAttrModel.getUpdateVdrAttr() != null && vdrAttrModel.getUpdateVdrAttr().size() > 0) {
            for (VdrAttr v: vdrAttrModel.getUpdateVdrAttr()) {
                a += vdrAttrMapper.updateByPrimaryKeySelective(v);
            }
        }
        return a;
    }

    @Override
    public List<Map<String, Object>> vdrAttrList(Vender ven) throws  Exception {
        Map<String, Object> map = new HashMap<>();
        /**
         * 获取所有商品自定义 和商品原有属性
         * */
        List<VdrAttrDef> list = vdrAttrDefMapper.selectALL(new VdrAttrDef());
        String buffer = "";
        for (VdrAttrDef v : list) {
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
        map.put("venderId", ven.getVenderId());
        map.put("ownerId", ven.getOwnerId());
        List<Map<String, Object>> resultList = vdrAttrMapper.vdrAttrList(map);
        if(resultList.get(0)==null){
            return attriButeUtils.Attrs(list);
        }else{
            return resultList;
        }

    }
}
