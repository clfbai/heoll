package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsAttr;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.product.ProductMessage;
import com.boyu.erp.platform.usercenter.entity.mq.product.ProductSellState;
import com.boyu.erp.platform.usercenter.entity.mq.product.ProductSku;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.model.goods.ProdClsDisable;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsAttrService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductClsAuditSerivce;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.RandomStringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductClsAuditSerivceImpl implements ProductClsAuditSerivce {

    static final String YW = "ER_";
    @Autowired
    private ProdClsMapper prodClsMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProdClsAttrService prodClsAttrService;

    /**
     * 功能描述: 审核商品
     * 1. 更改商品状态为审核
     * 2. 生成商品消息对象返回
     *
     * @param model (审核商品品种集合)
     * @return List<MessageObject> 消息集合对象
     * @auther: CLF
     * @date: 2019/9/25 17:40
     */
    @Override
    public List<MessageObject> productClsAudit(ProdClsModel model) throws ServiceException {
        if (model.getProdClsModels().size() > 0) {
            List<MessageObject> list = new ArrayList<>();
            for (ProdClsModel m : model.getProdClsModels()) {
                ProdCls cls = new ProdCls();
                cls.setProdClsId(m.getProdClsId());
                cls.setProdClsCode(m.getProdClsCode());
                //审核同时修改商品审核状态
                cls.setAuditType("am");
                cls.setIsAudit(1);
                int a = prodClsMapper.updateAuditType(cls);
                if (a <= 0) {
                    throw new ServiceException("403", "审核商品参数有误");
                }
                ProdClsAttr prodClsAttr = prodClsAttrService.selectProdCls(m.getProdClsId());
                if (prodClsAttr != null) {
                    //找出上传商品品种
                    if (CommonFainl.TRUE.equalsIgnoreCase(prodClsAttr.getAttrVal())) {
                        list.add(this.createProdCls(m));
                    }
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 功能描述: 反审商品并返回手动下架或删除消息对象
     *
     * @param sellState=4 是删除对象  sellState=5 是禁用对象
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 17:35
     */
    @Override
    public List<MessageObject> productClsNotAudit(ProdClsModel model, Integer sellState) throws ServiceException {
        if (model.getProdClsModels().size() > 0) {
            List<MessageObject> list = new ArrayList<>();
            for (ProdClsModel m : model.getProdClsModels()) {
                ProdCls cls = new ProdCls();
                cls.setProdClsId(m.getProdClsId());
                cls.setProdClsCode(m.getProdClsCode());
                //反审商品同时修改商品审核状态
                cls.setAuditType("se");
                //只有需要上传的商品才需要推送消息
                ProdClsAttr prodClsAttr = prodClsAttrService.selectProdCls(m.getProdClsId());
                //取原有的审核值
                cls.setIsAudit(m.getIsAudit());
                if (prodClsAttr != null) {
                    //找出上传商品品种
                    if (CommonFainl.TRUE.equalsIgnoreCase(prodClsAttr.getAttrVal())) {
                        if (sellState == 5) {
                            cls.setIsAudit(1);
                        }
                        if (sellState == 4) {
                            //删除吧是否审核改为 0
                            cls.setIsAudit(0);
                        }
                        list.add(this.createOutProdCls(m, sellState));
                    }
                }
                int a = prodClsMapper.updateAuditType(cls);
                if (a <= 0) {
                    throw new ServiceException("403", "审商品参数有误");
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 获得单条禁用消息对象
     */
    @Override
    public MessageObject productClsSellstate(ProdClsDisable prodClsDisable) throws ServiceException {
        ProdClsWithBLOBs cls = new ProdClsWithBLOBs();
        cls.setProdClsId(prodClsDisable.getProdClsId());
        cls.setProdClsCode(prodClsDisable.getProdClsCode());
        cls.setProdStatus("I");
        //修改状态
        prodClsMapper.updateByPrimaryKeySelective(cls);
        ProductSellState sellState = new ProductSellState();
        sellState.setId(YW + prodClsDisable.getProdClsId());
        sellState.setProdClsCode(prodClsDisable.getProdClsCode());
        //5手动下架
        sellState.setSellState(5);
        MessageObject messageObject = new MessageObject("product.update.state", sellState);
        //生成当前唯一id
        messageObject.setUuid(sellState.getId() + "_" + RandomStringUtils.crateUuid(6));
        messageObject.setRequestMessage("商品禁用");
        return messageObject;
    }

    /**
     * 获得禁用消息对象集合
     */
    @Override
    public List<MessageObject> productClsSellstateList(List<ProdClsDisable> list) throws ServiceException {
        List<MessageObject> messageObjects = new ArrayList<>();
        for (ProdClsDisable d : list) {
            messageObjects.add(this.productClsSellstate(d));
        }
        return messageObjects;
    }

    /**
     * 功能描述: 修改商品上传属性生成单条消息对象
     * 1. 审核过的商品修改为上传 生成添加商品消息对象
     * 2. 审核过的商品修改为不上传
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/9 15:43
     */
    @Override
    public MessageObject productClsHtml(ProdCls cls, ProdClsAttr attr) throws ServiceException {
        ProdClsModel model = new ProdClsModel();
        model.setProdClsId(cls.getProdClsId());
        model.setProdClsCode(cls.getProdClsCode());
        //推送下架
        if (CommonFainl.FALSE.equalsIgnoreCase(attr.getAttrVal())) {
            return createOutProdCls(model, 5);
        }//推送添加
        if (CommonFainl.TRUE.equalsIgnoreCase(attr.getAttrVal())) {
            return createProdCls(model);
        }
        return null;
    }


    /**
     * 生成下架商品消息对象
     * 反审 删除
     * *
     */
    public MessageObject createOutProdCls(ProdClsModel model, Integer sellStates) throws ServiceException {
        ProductMessage message = getMessage(model);
        ProductSellState sellState = new ProductSellState();
        sellState.setId(YW + message.getProdClsId());
        sellState.setProdClsCode(message.getCode());
        //手动下架 或删除  4删除，5手动下架
        sellState.setSellState(sellStates);
        MessageObject messageObject = new MessageObject("product.update.state", sellState);
        messageObject.setUuid(sellState.getId() + "_" + RandomStringUtils.crateUuid(6));
        return messageObject;
    }

    /**
     * 生成增加商品消息对象
     * *
     */
    public MessageObject createProdCls(ProdClsModel model) throws ServiceException {
        ProductMessage message = getMessage(model);
        if (message.getCreateTime() == null) {
            message.setCreateTime(DateUtil.dateToString(new Date()));
        }
        message.setId(YW + message.getProdClsId());
        List<ProductSku> skus = productMapper.selectProdClsIdMessge(model);
        if (CollectionUtils.isEmpty(skus)) {
            throw new ServiceException("406", "没有查询到商品明细");
        }
        skus.stream().forEach(s -> s.setId(YW + s.getProdId()));
        message.setSkus(skus);
        MessageObject messageObject = new MessageObject("product.add", message);
        //业务编码Id
        messageObject.setUuid(message.getId() + "_" + RandomStringUtils.crateUuid(6));
        return messageObject;
    }

    /**
     * 功能描述: 生成商品消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 17:12
     */
    private ProductMessage getMessage(ProdClsModel model) {

        ProdCls cls = new ProdCls();
        cls.setProdClsId(model.getProdClsId());
        cls.setProdClsCode(model.getProdClsCode());
        ProductMessage message = prodClsMapper.selectProdClsId(cls);
        if (message == null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种信息不存在");
        }
        return message;
    }
}
