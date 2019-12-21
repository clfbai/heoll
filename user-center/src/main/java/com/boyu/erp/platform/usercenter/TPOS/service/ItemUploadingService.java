package com.boyu.erp.platform.usercenter.TPOS.service;

import com.boyu.erp.platform.usercenter.TPOS.common.goods.listgoods.ListItems;
import com.boyu.erp.platform.usercenter.TPOS.common.goods.singgoods.SingleItem;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;

/**
 * 生成上传商品对象接口
 */
public interface ItemUploadingService {
    /**
     * 功能描述: 生成批量上传商品
     *
     * @param prodCls (商品品种集合)
     * @param type    (枚举 UPDATE ,ADD)
     * @return:
     * @auther: CLF
     * @date: 2019/11/26 10:56
     */
    ListItems createItems(ProdCls prodCls, String type, SysUser user) throws ServiceException;

    /**
     * 功能描述: 发送同步商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/27 16:33
     */
    void sendCwmsItme(ListItems listItems,SysUser user) throws Exception;

    /**
     * 功能描述: 生成单条上传商品
     *
     * @param product (单条商品明细)
     * @param type    (枚举 UPDATE ,ADD)
     * @return:
     * @auther: CLF
     * @date: 2019/11/26 10:56
     */
    SingleItem createItem(Product product, String type,SysUser user) throws ServiceException;
}
