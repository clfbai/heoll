package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsAttr;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProdClsDisable;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;

import java.util.List;

/**
 * 类描述: 商品审核接口
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/25 17:31
 */
public interface ProductClsAuditSerivce {
    /**
     * 功能描述: 审核商品并返回消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 17:35
     */
    List<MessageObject> productClsAudit(ProdClsModel model) throws ServiceException;


    /**
     * 功能描述: 反审商品并返回手动下架消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 17:35
     */
    List<MessageObject> productClsNotAudit(ProdClsModel model, Integer sellState) throws ServiceException;

    /**
     * 获得单条禁用消息对象
     */
    MessageObject productClsSellstate(ProdClsDisable prodClsDisable) throws ServiceException;

    /**
     * 获得禁用消息对象集合
     */
    List<MessageObject> productClsSellstateList(List<ProdClsDisable> list) throws ServiceException;


    /**
     * 功能描述: 修改商品上传属性生成单条消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/9 15:43
     */
    MessageObject productClsHtml(ProdCls cls, ProdClsAttr attr) throws ServiceException;


}
