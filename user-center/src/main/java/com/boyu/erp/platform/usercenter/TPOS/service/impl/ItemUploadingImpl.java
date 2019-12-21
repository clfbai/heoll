package com.boyu.erp.platform.usercenter.TPOS.service.impl;

import com.boyu.erp.platform.usercenter.TPOS.common.goods.Item;
import com.boyu.erp.platform.usercenter.TPOS.common.goods.ItemExtendProps;
import com.boyu.erp.platform.usercenter.TPOS.common.goods.listgoods.Items;
import com.boyu.erp.platform.usercenter.TPOS.common.goods.listgoods.ListItems;
import com.boyu.erp.platform.usercenter.TPOS.common.goods.singgoods.SingleItem;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.service.ItemUploadingService;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.service.base.InterfaceLog;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.RandomStringUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class ItemUploadingImpl implements ItemUploadingService {
    @Autowired
    private InterfaceLog interfaceLog;
    @Autowired
    private RequestTPOService requestTPOService;
    @Autowired
    private ProdClsMapper prodClsMapper;
    @Value("${secret}")
    private String secret;
    @Value("${app_key}")
    private String appKey;
    @Value("${customer_id}")
    private String customerId;

    /**
     * 功能描述: 批量同步
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/26 11:22
     */
    @Override
    public ListItems createItems(ProdCls prodCls, String type, SysUser user) throws ServiceException {
        List<Map<String, Object>> list = prodClsMapper.selectCwmsUploading(prodCls);
        if (CollectionUtils.isEmpty(list)) {
            log.info("没有商品明细无须同步");
            return null;
        }
        ListItems listItems = this.MapItems(list);
        listItems.setActionType(type);
        return listItems;
    }

    @Override
    public void sendCwmsItme(ListItems listItems, SysUser user) throws Exception {
        CwmsUrlParamModel cwmsUrlParamModel = new CwmsUrlParamModel();
        //前缀 目前按照给的 demo 就是 app_key
        cwmsUrlParamModel.setSecret(secret);
        cwmsUrlParamModel.setAppKey(appKey);
        cwmsUrlParamModel.setCustomerid(customerId);
        cwmsUrlParamModel.setRequestMapping("/api/qm2");
        cwmsUrlParamModel.setMethod("taobao.qimen");
        cwmsUrlParamModel.setTimestamp(DateUtil.dateToString(new Date()));
        cwmsUrlParamModel.setObjXml(requestTPOService.createObjXml(listItems));
        String uuid = RandomStringUtils.crateUuid(6);
        ResponseOrder responseOrder = requestTPOService.createCwmsURL(cwmsUrlParamModel, uuid, ResponseOrder.class);
        interfaceLog.CWMSLog(cwmsUrlParamModel, responseOrder, "CMS同步商品", uuid, user);
    }


    /**
     * 功能描述: 单条同步
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/26 11:22
     */
    @Override
    public SingleItem createItem(Product product, String type, SysUser user) {
        return null;
    }


    public ListItems MapItems(List<Map<String, Object>> maps) throws ServiceException {
        ListItems listItems = new ListItems();
        List<Item> itemList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            if (StringUtils.NullEmpty(String.valueOf(map.get("warehouseCode")))) {
                throw new ServiceException("403", "商品存储仓库为空");
            }
            if (StringUtils.NullEmpty(String.valueOf(map.get("ownerCode")))) {
                throw new ServiceException("403", "商品管理组织为空");
            }
            if (StringUtils.NullEmpty(String.valueOf(map.get("barCode")))) {
                throw new ServiceException("403", "商品code为空");
            }
            Item goods = new Item();
            goods.setColor(String.valueOf(map.get("color")));
            goods.setItemId(String.valueOf(map.get("itemId")));
            goods.setBarCode(String.valueOf(map.get("barCode")));
            goods.setItemCode(String.valueOf(map.get("itemCode")));
            goods.setItemName(String.valueOf(map.get("itemName")));
            goods.setItemType("ZC");
            goods.setIsSku(CommonFainl.TRUE);
            Double pr = StringUtils.NullEmpty(map.get("purchasePrice") + "") == true ? 0d : (Double.parseDouble(map.get("purchasePrice") + ""));
            goods.setPurchasePrice(pr);
            goods.setBrandCode(Double.parseDouble(map.get("brandCode") + ""));
            goods.setBrandName(String.valueOf(map.get("brandName")));
            Double gr = StringUtils.NullEmpty(map.get("grossWeight") + "") == true ? 0d : (Double.parseDouble(map.get("grossWeight") + ""));
            goods.setGrossWeight(gr);
            goods.setSeasonCode(String.valueOf(map.get("seasonCode")));
            itemList.add(goods);
            listItems.setWarehouseCode(String.valueOf(map.get("warehouseCode")));
            listItems.setOwnerCode(String.valueOf(map.get("ownerCode")));
            ItemExtendProps extendProps = new ItemExtendProps();
            extendProps.setBoxLength(0);
            goods.setExtendProps(extendProps);
        }
        Items items = new Items();
        items.setItem(itemList);
        listItems.setItems(items);
        return listItems;
    }

}
