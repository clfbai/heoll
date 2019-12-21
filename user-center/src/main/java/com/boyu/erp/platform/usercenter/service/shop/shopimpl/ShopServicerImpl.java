package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.shop.ShopMsg;
import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.entity.shop.ShopAttr;
import com.boyu.erp.platform.usercenter.entity.shop.ShopAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.shop.*;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysCodeDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitOwnerMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehMapper;
import com.boyu.erp.platform.usercenter.model.shop.ShopEmpModel;
import com.boyu.erp.platform.usercenter.model.shop.ShopModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;
import com.boyu.erp.platform.usercenter.service.base.SendSerivce;
import com.boyu.erp.platform.usercenter.service.emp.EmpMsgSerivcer;
import com.boyu.erp.platform.usercenter.service.shop.ShopServicer;
import com.boyu.erp.platform.usercenter.service.system.impl.VendeeServiceimpl;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.RandomStringUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.WarehUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.shop.ShopEmpVo;
import com.boyu.erp.platform.usercenter.vo.shop.ShopPayVo;
import com.boyu.erp.platform.usercenter.vo.shop.ShopVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 类描述:
 *
 * @Description: 门店服务
 * @auther: CLF
 * @date: 2019/8/21 16:21
 */
@Slf4j
@Service
@Transactional
public class ShopServicerImpl implements ShopServicer {
    @Autowired
    private ShopAttrDefMapper shopAttrDefMapper;
    @Autowired
    private WarehMapper warehMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private WarehUtils warehUtils;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysUnitOwnerMapper unitOwnerMapper;
    @Autowired
    private ShopEmpMapper shopEmpMapper;
    @Autowired
    private ShopAttrMapper shopAttrMapper;
    @Autowired
    private ShopRentValMapper shopRentValMapper;
    @Autowired
    private ShopSpFmlMapper shopSpFmlMapper;
    @Autowired
    private ShopPayModeMapper shopPayModeMapper;
    @Autowired
    private ShopGpFmlMapper shopGpFmlMapper;
    @Autowired
    private SysCodeDtlMapper sysCodeDtlMapper;
    @Autowired
    private SendSerivce sendSerivce;
    @Autowired
    private EmpMsgSerivcer empMsgSerivcer;
    @Autowired
    private SysParameterMapper parameterMapper;

    @Override
    public PageInfo<ShopVo> getShop(Integer page, Integer size, ShopModel shop, SysUser user) {
        PageHelper.startPage(page, size);
        List<ShopVo> list = shopMapper.selectShop(shop);
        PageInfo<ShopVo> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 功能描述: 单条增加门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/29 15:34
     */
    @Override
    public int add(ShopModel shop, SysUser user) throws ServiceException {
        SysUnit unit = shop.getShopUnit();
        Long shopId = unit.getUnitId();
        Shop shops = new Shop();
        BeanUtils.copyProperties(shop, shops);
        shops.setShopId(shopId);
        shops.setShopStatus(CommonFainl.USER_STATUS);
        shopMapper.insertSelective(shops);
        if (shopId != null) {
            Wareh w = warehMapper.selectByWarehId(shopId);
            //仓库存在判断属主信息
            if (!w.getOwnerId().equals(shop.getOwnerId())) {
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "仓库不能属于多个属主");
            } else {
                //仓库存在修改状态为A
                w.setWarehStatus(CommonFainl.USER_STATUS);
            }
        } else {

            //新增组织
            shopId = shops.getShopId();
            unit.setUnitId(shopId);
            //赋值给消息对象使用
            shop.setShopId(shopId);

            unit.setUnitHierarchy(unitMapper.selectByPrimaryKey(shops.getOwnerId()).getUnitHierarchy() + "|" + unit.getUnitCode());
            unit.setOprId(user.getUserId());
            unit.setUpdTime(new Date());
            unit.setUnitType("SH");

            unitMapper.insertSelective(unit);
            WarehVo warehVo = new WarehVo();
            Wareh w = new Wareh();
            warehUtils.setParam(w);
            BeanUtils.copyProperties(w, warehVo);
            warehVo.setOwnerId(shops.getOwnerId());
            warehVo.setUnitNum(shop.getShopNum());
            warehVo.setNegAtk(CommonFainl.FALSE);
            warehVo.setClnAreaAdopted(CommonFainl.FALSE);
            warehVo.setClnRckReqd(CommonFainl.FALSE);
            warehUtils.createWareh(unit, user, warehVo);
        }
        if (CollectionUtils.isNotEmpty(shop.getShopEmps())) {
            //门店店员
            shop.getShopEmps().stream().forEach(se -> se.setShopId(shops.getShopId()));
            //增加店员(导购)
            shopEmpMapper.addList(shop.getShopEmps());
            //发送消息
            shop.getShopEmps().stream().forEach(s -> sendSerivce.send("exh.master.up", "master.up",empMsgSerivcer.getMsgEmp(s)));

        }
        if (CollectionUtils.isNotEmpty(shop.getShopAtts())) {
            //门店属性集合
            shop.getShopAtts().parallelStream().forEach(se -> se.setShopId(shops.getShopId()));
            shop.getShopAtts().parallelStream().forEach(ss -> shopAttrMapper.insertSelective(ss));

        }
        if (CollectionUtils.isNotEmpty(shop.getShopPayModes())) {
            //门店付款方式集合
            shop.getShopPayModes().parallelStream().forEach(se -> se.setShopId(shops.getShopId()));
            shop.getShopPayModes().parallelStream().forEach(se -> shopPayModeMapper.insertSelective(se));
        }
        if (CollectionUtils.isNotEmpty(shop.getShopRentVals())) {
            //门店租金集合
            shop.getShopRentVals().parallelStream().forEach(se -> se.setShopId(shops.getShopId()));
            shop.getShopRentVals().parallelStream().forEach(se -> shopRentValMapper.insertSelective(se));
        }
        if (CollectionUtils.isNotEmpty(shop.getShopSpFmls())) {
            // 门店扣点公式
            shop.getShopSpFmls().parallelStream().forEach(se -> se.setShopId(shops.getShopId()));
            shop.getShopSpFmls().parallelStream().forEach(se -> shopSpFmlMapper.insertSelective(se));
        }
        if (CollectionUtils.isNotEmpty(shop.getShopGpFmls())) {
            // 门店促销公式
            shop.getShopGpFmls().parallelStream().forEach(se -> se.setShopId(shops.getShopId()));
            shop.getShopGpFmls().parallelStream().forEach(se -> shopGpFmlMapper.insertSelective(se));
        }
        //发送增加店铺消息
        MessageObject msg = getShopMsg(shop);
        msg.setRequestMessage("增加门店");
        ShopMsg shopMsg = (ShopMsg) msg.getContent();
        msg.setUuid(RandomStringUtils.crateUuid(6) + "_" + shopMsg.getId());
        sendSerivce.send("exh.master.up", "master.up", msg);
        return 1;
    }


    /**
     * 功能描述:  门店属性列表行转列
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 16:55
     */
    @Override
    public List<Map<String, Object>> getShopAtrrDef(Shop model) {
        Map<String, Object> map = new HashMap<>();
        /**
         * 获取所有商品自定义 和商品原有属性
         * */
        List<ShopAttrDef> list = shopAttrDefMapper.getShopAttrDef(new ShopAttrDef());
        List<String> l = new ArrayList<>();
        String buffer = "";
        for (ShopAttrDef p : list) {
            String str = "MAX(IF( pa.`attr_type` = '" + p.getAttrType() + "' , pa.`attr_val`,'')) AS  `" + p.getAttrType() + "`,";
            buffer = str + buffer;

        }
        map.put("sql", buffer);
        map.put("shopId", model.getShopId());
        return shopMapper.getRoeAndLineMap(map);
    }

    /**
     * 功能描述:  查询门店店员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 19:20
     */
    @Override
    public List<ShopEmpVo> getShopEmp(Shop shop) {

        return shopEmpMapper.getShopEmp(shop);
    }

    /**
     * 功能描述:  门店付款方式查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 19:47
     */
    @Override
    public List<ShopPayVo> getShopPay(Shop shop) {
        return shopPayModeMapper.getShopPayMode(shop);
    }


    /**
     * 功能描述: 批量增加门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/29 15:34
     */
    @Override
    public int addList(ShopModel shop) {
        return 0;
    }

    /**
     * 功能描述: 修改门店基础信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/30 14:59
     */
    @Override
    public int updateShop(ShopModel shop) {
        if (StringUtils.isNotEmpty(shop.getUpdateShopNum())) {
            //修改编号
            SysUnitOwner unitOwner = new SysUnitOwner();
            unitOwner.setUnitNum(shop.getUpdateShopNum());
            unitOwner.setUnitId(shop.getShopId());
            unitOwner.setOwnerId(shop.getOwnerId());
            unitOwnerMapper.updateUnitOwner(unitOwner);
        }
        if (shop.getShopUnit() != null && shop.getShopUnit().getUnitId() != null) {
            //修改组织信息
            SysUnit unit = shop.getShopUnit();
            unit.setUnitHierarchy(null);
            unitMapper.updateByPrimaryKeySelective(unit);
        }
        //修改门店信息
        Shop shop1 = new Shop();
        BeanUtils.copyProperties(shop, shop1);
        return shopMapper.updateByPrimaryKeySelective(shop);
    }

    /**
     * 功能描述:  修改门店店员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/30 14:55
     */
    @Override
    public int updateShopEmp(ShopEmpModel shop) {
        if (CollectionUtils.isNotEmpty(shop.getAddEmp())) {
            shopEmpMapper.addList(shop.getAddEmp());
            //发送增加导购消息
            shop.getAddEmp().stream().forEach(s -> sendSerivce.send("exh.master.up", "master.up",empMsgSerivcer.getMsgEmp(s)));
        }
        if (CollectionUtils.isNotEmpty(shop.getUpdateEmp())) {
            shop.getUpdateEmp().stream().forEach(s -> shopEmpMapper.updateByPrimaryKey(s));
        }
        if (CollectionUtils.isNotEmpty(shop.getDeleteEmp())) {

            shop.getDeleteEmp().stream().forEach(s -> shopEmpMapper.deleteByPrimaryKey(s));
            //发送冻结导购消息
            shop.getDeleteEmp().stream().forEach(s ->  sendSerivce.send("exh.master.up", "master.up",empMsgSerivcer.getFreezeEmp(s)));

        }
        return 0;
    }

    /**
     * 功能描述: 打标删除门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/31 16:45
     */
    @Override
    public int deleteShop(ShopModel shop) {
        return 0;
    }

    /**
     * 功能描述: 查询门店可选员工(可添加的导购)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/12 11:15
     */
    @Override
    public PageInfo<ShopEmpVo> getEmpVo(Integer page, Integer size, OwnerPrsnlModel model, SysUser user) {
        //员工能否属于多个门店(导购) 参数 默认 T 能
        SysParameter parameter = parameterMapper.findById("emplUnitAllshoppingGuide");
        String t = parameter == null ? "T" : parameter.getParmVal() == null ? "T" : parameter.getParmVal();
        model.setEmpAll(t);
        System.out.println("  " + model.getEmpAll());
        PageHelper.startPage(page, size);
        List<ShopEmpVo> list = shopMapper.selectShopEmpVo(model);
        PageInfo<ShopEmpVo> pageInfo = new PageInfo(list);
        return pageInfo;

    }


    public MessageObject getShopMsg(ShopModel shop) {
        ShopMsg shopMsg = new ShopMsg();
        shopMsg.setRegion(shop.getShopUnit().getProvince() + shop.getShopUnit().getCity() + shop.getShopUnit().getDistrict());
        shopMsg.setAddress(shop.getShopUnit().getAddress());
        if (shop.getShopId() == null || shop.getOwnerId() == null) {
            throw new ServiceException("403", "门店Id为空，或门店属主Id为空");
        }
        shopMsg.setFranchiseeId(VendeeServiceimpl.YW + shop.getOwnerId());
        shopMsg.setId(VendeeServiceimpl.YW + shop.getShopId());
        shopMsg.setContacts(shop.getShopUnit().getFullName());
        shopMsg.setPhone(shop.getShopUnit().getTelNum());
        SysCodeDtl dtl = new SysCodeDtl();
        dtl.setCode(shop.getShopMode());
        dtl.setCodeType("SHOP_MODE");
        dtl = sysCodeDtlMapper.selectByPrimaryKey(dtl);
        String str = dtl == null ? "" : dtl.getDescription() == null ? "" : dtl.getDescription();
        //分公司
        shopMsg.setAreaName(str);
        int a = 3;
        //专卖店
        String s = shop.getShopType();
        if (StringUtils.NullEmpty(s) || !"SS".equalsIgnoreCase(s)) {
            a = 2;
        }
        //直营店
        for (ShopAttr attr : shop.getShopAtts()) {
            if ("CANALS".equalsIgnoreCase(attr.getAttrType()) || "01".equals(attr.getAttrVal())) {
                a = 1;
                break;
            }
        }
        shopMsg.setType(a);
        String cateTime = DateUtil.dateToString(shop.getOpenDate(), "yyyy-MM-dd HH:mm:ss");
        shopMsg.setCreateTime(cateTime);
        shopMsg.setName(shop.getShopUnit().getUnitName());
        MessageObject object = new MessageObject("shop.add", shopMsg);
        return object;
    }
}
