package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.exception.ServiceException;

import java.util.List;

public interface ProductSkuAmqpSerivce {

    /**
     * 批量插入明细生成批量消息对象
     */
    List<MessageObject> createProductSkuList(List<Product> product)throws ServiceException;

    /**
     * 单条插入明细生成单条Sku消息对象
     */
    MessageObject createProductSku(Product product)throws ServiceException;

    /**
     * 审核单条商品品种生成商品消息对象
     */
    MessageObject createProdClsSku(ProdCls prodCls)throws ServiceException;
}
