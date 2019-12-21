package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.product.ProductMessage;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductSkuAmqpSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductSkuAmqpSerivceImpl implements ProductSkuAmqpSerivce {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProdClsMapper prodClsMapper;

    /**
     * 功能描述:批量插入明细生成批量消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/24 15:37
     */
    @Override
    public List<MessageObject> createProductSkuList(List<Product> product) throws ServiceException {
        return null;
    }

    /**
     * 功能描述:  单条插入明细生成单条Sku消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/24 15:37
     */
    @Override
    public MessageObject createProductSku(Product product) throws ServiceException {

        return null;
    }

    /**
     * 功能描述: 审核单条商品品种生成商品消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/24 17:12
     */
    @Override
    public MessageObject createProdClsSku(ProdCls prodCls) throws ServiceException {
        ProductMessage message = prodClsMapper.selectProdClsId(prodCls);

        message.setId(message.getProdClsId() + "");
        if (message == null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种信息不存在");
        }
        MessageObject messageObject = new MessageObject("product.add", message);
        return messageObject;
    }
}
