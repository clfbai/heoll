package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.entity.goods.ProdAttrDef;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsAttr;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdAttrDefMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsAttrMapper;
import com.boyu.erp.platform.usercenter.model.goods.ProdClsAttrModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsAttrService;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsAttrLineVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 商品品种属性服务层
 * @author: clf
 * @create: 2019-06-14 18:43
 */
@Slf4j
@Service
@Transactional
public class ProdClsAttrServiceImpl implements ProdClsAttrService {
    @Autowired
    private ProdAttrDefMapper prodAttrDefMapper;
    @Autowired
    private ProdClsAttrMapper prodClsAttrMapper;

    @Override
    public List<ProdClsAttrLineVo> findByProdClsId(ProdClsWithBLOBs prodClsId) {
        log.info("传入值：  " + prodClsId.getProdClsId());
        return prodClsAttrMapper.getRoeAndLine(prodClsId);
    }

    /**
     * 商品属性行转列的结果集
     */
    @Override
    public List<Map<String, Object>> findProdAllAttr(ProdClsWithBLOBs prodClsId) {
        Map<String, Object> map = new HashMap<>();
        /**
         * 获取所有商品自定义 和商品原有属性
         * */
        List<ProdAttrDef> list = prodAttrDefMapper.selectAll(new ProdAttrDef());
        // List<String> l = new ArrayList<>();
        String buffer = "";

        for (ProdAttrDef p : list) {
            /**
             * Map 拼接Sql 入参
             * */
            String str = "MAX(IF( pa.`attr_type` = '" + p.getAttrType() + "' , pa.`attr_val`,'')) AS  `" + p.getAttrType() + "`,";
            buffer = str + buffer;
            /**
             * List 传入行数 入参
             * */
            // l.add(p.getAttrType());
        }
        map.put("sql", buffer);
        map.put("prodClsId", prodClsId.getProdClsId());
        //return prodClsAttrMapper.getRoeAndLineFroe(l);
        return prodClsAttrMapper.getRoeAndLineMap(map);
    }

    @Override
    public int updateProdAllAttr(ProdClsAttrModel prodClsAttr) {

        return updateAndDelte(prodClsAttr);

    }

    /**
     * 功能描述:  查询商品是否上传
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 19:36
     */
    @Override
    public ProdClsAttr selectProdCls(Long prodClsId) {
        ProdClsAttr key = new ProdClsAttr();
        key.setProdClsId(prodClsId);
        //是否上传列
        key.setAttrType("UPLOAD_HM");
        return prodClsAttrMapper.selectByProdClsAttr(key);
    }

    /**
     * 功能描述: 修改商品品种单个自定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/9 15:19
     */
    @Override
    public int updateProdClsAttr(ProdClsAttr prodClsAttr) {
        ProdClsAttr attr = prodClsAttrMapper.selectByProdClsAttr(prodClsAttr);
        //不存在增加
        if (attr == null) {
            return prodClsAttrMapper.insertProdClsAttrSelective(prodClsAttr);
        }
        return prodClsAttrMapper.updateByProdClsAttrSelective(prodClsAttr);
    }

    /**
     * 判断删除还是增加
     * 1. 删除时判断数据是否存在
     * 2. 增加先删除再增加
     * 3. 修改自定义属性值(这里只能修改自定义属性值)
     **/
    private int updateAndDelte(ProdClsAttrModel prodClsAttrModel) {
        int a = 0;
        if (prodClsAttrModel.getAddProdClsAttr() != null && prodClsAttrModel.getAddProdClsAttr().size() > 0) {
            for (ProdClsAttr p : prodClsAttrModel.getAddProdClsAttr()) {
                a = prodClsAttrMapper.deleteByProdClsAttr(p);
                a += prodClsAttrMapper.insertProdClsAttr(p);
            }
        }
        if (prodClsAttrModel.getDeleteProdClsAttr() != null && prodClsAttrModel.getDeleteProdClsAttr().size() > 0) {
            for (ProdClsAttr p : prodClsAttrModel.getDeleteProdClsAttr()) {
                a += prodClsAttrMapper.deleteByProdClsAttr(p);
            }
        }
        if (prodClsAttrModel.getUpdateProdClsAttr() != null && prodClsAttrModel.getUpdateProdClsAttr().size() > 0) {
            for (ProdClsAttr p : prodClsAttrModel.getUpdateProdClsAttr()) {
                a += prodClsAttrMapper.updateByProdClsAttrSelective(p);
            }
        }
        return a;
    }


}
