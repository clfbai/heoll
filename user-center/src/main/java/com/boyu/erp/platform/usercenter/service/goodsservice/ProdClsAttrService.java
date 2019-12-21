package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.goods.ProdClsAttr;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.model.goods.ProdClsAttrModel;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsAttrLineVo;

import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 商品品种对应属性
 * @author: liu yan
 * @create: 2019-06-14 18:41
 */
public interface ProdClsAttrService {


    List<ProdClsAttrLineVo> findByProdClsId(ProdClsWithBLOBs ProdClsid);

    List<Map<String, Object>> findProdAllAttr(ProdClsWithBLOBs ProdClsid);

    int updateProdAllAttr(ProdClsAttrModel prodClsAttr) throws Exception;

    /**
     * 功能描述:  查询商品是否上传
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 19:36
     */
    ProdClsAttr selectProdCls(Long prodClsId);

    /**
     * 功能描述: 修改商品品种单个自定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/9 15:19
     */
    int updateProdClsAttr(ProdClsAttr prodClsAttr);
}
