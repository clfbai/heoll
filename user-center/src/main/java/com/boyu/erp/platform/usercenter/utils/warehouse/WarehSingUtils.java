package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.TPOS.common.confirm.EntryOrder;
import com.boyu.erp.platform.usercenter.TPOS.common.confirm.FimOrderLines;
import com.boyu.erp.platform.usercenter.TPOS.common.confirm.FirmOrderLine;
import com.boyu.erp.platform.usercenter.TPOS.common.confirm.WarehSingConfirm;
import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import com.boyu.erp.platform.usercenter.TPOS.mapper.WmsErpNumMapper;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehCostMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehRmodeMapper;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRmodeMode;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehStkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 类描述:
 *
 * @Description 入库工具类型
 * @auther: CLF
 * @date: 2019/11/13 20:07
 */
@Slf4j
@Component
public class WarehSingUtils {
    @Autowired
    private WarehRmodeMapper warehRmodeMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private WarehStkService warehStkService;
    @Autowired
    private WarehCostMapper warehCostMapper;
    @Autowired
    private WarehMapper warehMapper;
    @Autowired
    private WmsErpNumMapper wmsErpNumMapper;
    @Autowired
    private StbDtlService stbDtlService;


    /**
     * 功能描述: 根据入库单的仓库入库控制判断入库单能否生效
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/13 20:12
     */
    public void wareRomde(Grn grn, Stb stb, List<StbDtl> list) throws ServiceException {
        WarehRmode warehRmode = new WarehRmodeMode();
        warehRmode.setRcvMode(grn.getRcvMode());
        warehRmode.setWarehId(stb.getWarehId());
        warehRmode = warehRmodeMapper.selectByPrimaryKey(warehRmode);
        System.out.println("入库方式:" + warehRmode);
        if (warehRmode == null) {
            throw new ServiceException("403", "仓库" + stb.getWarehId() + ":入库方式" + grn.getRcvMode() + "为空");
        }
        if (StringUtils.isBlank(warehRmode.getDiffCtrl())) {
            throw new ServiceException("403", "入库差异不能为空");
        }
        if (CollectionUtils.isNotEmpty(list))
            for (StbDtl dtl : list) {
                if (warehRmode.getDiffCtrl().equalsIgnoreCase("EQ")) {
                    //等于
                    if (dtl.getQty().compareTo(dtl.getExpdQty()) != 0) {
                        throw new ServiceException("403", "差异控制为等于:预期数量不等于收货数量");
                    }
                }
                if (warehRmode.getDiffCtrl().equalsIgnoreCase("N")) {
                    //不控制  不能多 只能少
                    //-1：小于；   0 ：等于；   1 ：大于；
                    if (dtl.getQty().compareTo(dtl.getExpdQty()) > 0) {
                        throw new ServiceException("403", "差异控制为不控制:收货数量不能大于预期数量");
                    }
                    //todo 这里需要判断收货差异
                }
                if (warehRmode.getDiffCtrl().equalsIgnoreCase("NG")) {
                    //不大于
                    if (dtl.getQty().compareTo(dtl.getExpdQty()) > 0) {
                        throw new ServiceException("403", "差异控制为不大于:收货数量不能大于预期数量");
                    }
                }
                if (warehRmode.getDiffCtrl().equalsIgnoreCase("NL")) {
                    //不小于
                    if (dtl.getQty().compareTo(dtl.getExpdQty()) != 0) {
                        throw new ServiceException("403", "差异控制为不小于:收货数量只能等于预期数量");
                    }
                }
            }
    }

    /**
     * 功能描述:根据 入库单明细、入库单组织Id、入库单 仓库Id返回库存成本集合
     * 1.
     *
     * @param warehId (入库单仓库)
     * @param unitId  (仓库属主、入库单组织)
     * @param list    (需要进行库存成本核算的库存明细单 商品如果没有开启库存管理则不需要进行成本核算)
     * @return:
     * @auther: CLF
     * @date: 2019/11/13 20:37
     */
    public List<WarehCost> countWarehCost(Long warehId, Long unitId, List<StbDtl> list, Boolean b) throws ServiceException {

        if (CollectionUtils.isNotEmpty(list)) {
            //将库存单明细安prodClsId 归类赋值
            List<StbDtl> maxList = setStbDtlProdClsId(list);
            Set<Long> set = maxList.stream().map(g -> g.getProdClsId()).collect(Collectors.toSet());
            //转换后List 商品品种Id集合
            List<Long> prodClsId = new ArrayList<>(set);
            //查询仓库所有商品成本
            List<WarehCost> warehCosts = warehCostMapper.queryWarehCostListByClsAndWareh(set, warehId);
            if (CollectionUtils.isNotEmpty(warehCosts)) {
                //需要修改的成本
                List<WarehCost> costsUpdate = warehCosts.stream().filter(s -> prodClsId.contains(s.getProdClsId())).collect(Collectors.toList());
                //修改库存成本集合
                List<WarehCost> retunCost = iscountWarehCost(maxList, costsUpdate, b);
                //找出剩余需要新增的商品
                prodClsId.removeAll(costsUpdate.stream().map(WarehCost::getProdClsId).collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(prodClsId)) {
                    List<StbDtl> stbDtlAdd = list.stream().filter(s -> prodClsId.contains(s.getProdClsId())).collect(Collectors.toList());
                    //新增的库存成本
                    retunCost.addAll(creatWareCost(warehId, unitId, stbDtlAdd, b));
                }
                return retunCost;
            }
            //没有查询到库存成本 代表第一次产生成本
            return creatWareCost(warehId, unitId, list, b);
        }
        return null;


    }

    /**
     * 将库存单按照商品品种归类
     * 因为库存成本核算时安商品品种，需要找出同一品种下商品库存单归类
     */
    public List<StbDtl> setStbDtlProdClsId(List<StbDtl> list) throws ServiceException {
        List<Product> products = productMapper.queryProductListByProdList(list.stream().map(s -> s.getProdId()).collect(Collectors.toList()));
        List<StbDtl> restList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("403", "库存单明细为空");
        }
        if (CollectionUtils.isEmpty(products)) {
            throw new ServiceException("403", "库存单明细对应商品明细为空");
        }
        Set<Long> prodClsIds = products.stream().map(Product::getProdClsId).collect(Collectors.toSet());
        List<Long> clsId = new ArrayList<>(prodClsIds);
        for (StbDtl s : list) {
            //总金额为数量*折后价
            s.setVal(s.getQty().multiply(s.getFnlPrice()));
            for (Product p : products) {
                if (s.getProdId().equals(p.getProdId())) {
                    s.setProdClsId(p.getProdClsId());
                }
            }
        }
        for (Long prodClsId : clsId) {
            StbDtl stb = new StbDtl();
            stb.setQty(new BigDecimal("0"));
            stb.setVal(new BigDecimal("0"));
            stb.setProdClsId(prodClsId);
            for (StbDtl s : list) {
                if (s.getProdClsId().equals(prodClsId)) {
                    //总数量
                    stb.setQty(s.getQty().add(stb.getQty()));
                    //总金额
                    stb.setVal(s.getVal().add(stb.getQty()));
                }
            }
            log.info(stb.getQty() + "[]" + stb.getVal());
            restList.add(stb);
        }
        //商品品种Id升序
        restList.sort(Comparator.comparingLong(StbDtl::getProdClsId));
        return restList;
    }

    /**
     * 根据商品品种Id集合 和商品库存明细获取库存成本集合
     * 这是用来获取 第一次记录库存成本商品 对应的库存成本对象
     * 不会出现数量为0 也不会有未决金额
     *
     * @param warehId (仓库Id)
     * @param list    (入库单明细)
     * @return 需要新增库存成本集合
     */
    public List<WarehCost> creatWareCost(Long warehId, Long unitId, List<StbDtl> list, Boolean b) {
        if (b) {
            throw new ServiceException("403", "没有库存成本不能去反");
        }
        List<WarehCost> rest = new ArrayList<>();
        for (StbDtl s : list) {
            WarehCost cost = new WarehCost();
            //总金额相减
            cost.setTotVal(s.getVal());
            //总数量
            cost.setTotQty(s.getQty());
            //取4位小数正无穷大
            cost.setWarehCost(cost.getTotVal().divide(cost.getTotQty(), 4, RoundingMode.CEILING));
            cost.setProdClsId(s.getProdClsId());
            cost.setOwnerId(unitId);
            cost.setWarehId(warehId);
            cost.setPgVal(new BigDecimal("0"));
            rest.add(cost);
        }
        return rest;
    }


    /**
     * (部分商品有库存成本记录)
     * 根据库存明细、库存成本集合生成要修改的库存成本和要增加的库存成本
     * 1.找出库存明细与库存成本有差异的商品 新增的库存成本
     * 2.找出库存明细与库存成本相同的商品  修改计算的库存成本
     * 3 返回1.2 步骤相加集合
     *
     * @param list       修改的明细
     * @param warehCosts 原有的库存成本
     * @param b
     */
    public List<WarehCost> iscountWarehCost(List<StbDtl> list, List<WarehCost> warehCosts, Boolean b) throws ServiceException {
        //这里需要考虑未决金额
        if (CollectionUtils.isNotEmpty(warehCosts)) {
            //交集不为空 (修改计算的库存成本)
            for (WarehCost c : warehCosts) {
                for (StbDtl s : list) {
                    if (s.getProdClsId().equals(c.getProdClsId())) {
                        if (b) {
                            //总数量
                            c.setTotQty(c.getTotQty().subtract(s.getQty()));
                            if (c.getTotQty().compareTo(new BigDecimal("0")) == 0) {
                                //当数量为0时,成本不变总金额不变,未决=未决金额-金额
                                c.setPgVal(c.getPgVal().subtract(s.getVal()));
                                c.setTotVal(new BigDecimal("0"));
                            } else {
                                //总金额+未决-金额
                                c.setTotVal(c.getTotVal().add(c.getPgVal()).subtract(s.getVal()));
                                //未决设为0
                                c.setPgVal(new BigDecimal("0"));
                            }
                            //成本
                            c.setWarehCost(c.getTotVal().divide(c.getTotQty(), 4, BigDecimal.ROUND_CEILING));
                        } else {
                            c.setTotQty(c.getTotQty().add(s.getQty()));
                            if (c.getTotQty().compareTo(new BigDecimal("0")) == 0) {
                                //当数量为0时,成本不变总金额不变,未决=未决金额-金额
                                c.setPgVal(c.getPgVal().add(s.getVal()));
                                c.setTotVal(new BigDecimal("0"));
                            } else {
                                //总金额+未决+金额
                                c.setTotVal(c.getTotVal().add(c.getPgVal()).add(s.getVal()));
                                //未决设为0
                                c.setPgVal(new BigDecimal("0"));
                            }
                            //成本
                            c.setWarehCost(c.getTotVal().divide(c.getTotQty(), 4, BigDecimal.ROUND_CEILING));
                        }
                        continue;
                    }
                }
            }
        }
        return warehCosts;
    }

    /**
     * 功能描述: 查询库存中指定商品集合信息
     *
     * @param warehId (仓库Id)
     * @param stbDtls (入库单明细)
     * @return:
     * @auther: CLF
     * @date: 2019/11/18 16:43
     */
    public List<WarehStk> isViefWarehCost(List<StbDtl> stbDtls, Long warehId) {
        if (CollectionUtils.isNotEmpty(stbDtls)) {
            //库存商品Id集合
            List<Long> prodIds = stbDtls.stream().map(StbDtl::getProdId).collect(Collectors.toList());
            //库存集合
            List<WarehStk> list = warehStkService.queryWarehStkListByProdIdList(warehId, prodIds);
            List<Long> min = new ArrayList<>();
            List<WarehStk> returnList = new ArrayList<>();
            //找出所有存在库存的商品并修改库存
            if (CollectionUtils.isNotEmpty(list)) {
                for (StbDtl s : stbDtls) {
                    for (WarehStk stk : list) {
                        if (s.getProdId().equals(stk.getProdId())) {
                            stk.setStkOnHand(s.getQty().add(stk.getStkOnHand()));
                            returnList.add(stk);
                            min.add(s.getProdId());
                        }
                    }
                }
            }
            List<StbDtl> stub;
            if (CollectionUtils.isNotEmpty(min)) {
                //找出不相等明细
                stub = stbDtls.stream().filter(s -> !min.contains(s.getProdId())).collect(Collectors.toList());
            } else {
                stub = stbDtls;
            }
            for (StbDtl stbDtl : stub) {
                WarehStk warehStk = new WarehStk();
                warehStk.setWarehId(warehId);
                warehStk.setProdId(stbDtl.getProdId());
                warehStk.setStkOnHand(stbDtl.getQty());
                setBgins(warehStk);
                returnList.add(warehStk);
            }
            return returnList;
        }
        return null;
    }

    private void setBgins(WarehStk warehStk) {
        //预期库存
        warehStk.setQtyExpd(new BigDecimal("0"));
        /**
         * 在途库存
         */
        warehStk.setQtyInTran(new BigDecimal("0"));
        /**
         * 预订库存
         */
        warehStk.setQtyBkd(new BigDecimal("0"));
        /**
         * 已配库存
         */
        warehStk.setQtyCmtd(new BigDecimal("0"));

        /**
         * 挂账库存
         */
        warehStk.setQtyHldn(new BigDecimal("0"));

        /**
         * 配码库存
         */
        warehStk.setQtyAst(new BigDecimal("0"));
        /**
         * 装箱库存
         */
        warehStk.setQtyBxd(new BigDecimal("0"));

        /**
         * 包装库存
         */
        warehStk.setQtyPckd(new BigDecimal("0"));
    }


    /**
     * 验证C-WMS入库单不能为空项
     */
    public Map<String, Object> isWarehSingConfirm(WarehSingConfirm confirm) throws ServiceException {
        EntryOrder order = confirm.getEntryOrder();
        FimOrderLines lines = confirm.getOrderLines();
        //回执编号
        String erpCwmsOrderId = order.getEntryOrderCode();
        if (StringUtils.isBlank(erpCwmsOrderId)) {
            throw new ServiceException(JsonResultCode.FAILURE, "回执编号为空");
        }
        if (StringUtils.isBlank(order.getWarehouseCode())) {
            throw new ServiceException(JsonResultCode.FAILURE, "回执入库单仓库代码为空");
        }
        if (StringUtils.isBlank(order.getEntryOrderType())) {
            throw new ServiceException(JsonResultCode.FAILURE, "回执入库单类型为空");
        }
        if (StringUtils.isBlank(order.getStatus())) {
            throw new ServiceException(JsonResultCode.FAILURE, "回执入库单状态为空");
        }
        if (CollectionUtils.isEmpty(lines.getOrderLine())) {
            throw new ServiceException(JsonResultCode.FAILURE, "回执入库单商品信息为空");
        }
        WmsErpNum wmsErpNum = new WmsErpNum();
        wmsErpNum.setTaskDocNum(order.getEntryOrderCode());
        wmsErpNum = wmsErpNumMapper.queryByBill(wmsErpNum);
        if (StringUtils.isBlank(wmsErpNum.getOriginNum())) {
            throw new ServiceException(JsonResultCode.FAILURE, "回执入库单对应ERP单据不存在，回执单据编号: " + order.getEntryOrderCode());
        }
        Map<String, Object> map = new HashMap<>();
        //关联关系
        map.put("wmsErpNum", wmsErpNum);
        Wareh wareh = warehMapper.selectWarehCode(order.getWarehouseCode());
        if (wareh == null || !"A".equals(wareh.getWarehStatus())) {
            throw new ServiceException(JsonResultCode.FAILURE, "回执入库单仓库不存或仓库被禁用，回执仓库代码: " + order.getWarehouseCode());
        }
        //组织Id
        map.put("unitId", wareh.getOwnerId());
        //仓库Id
        map.put("warehId", wareh.getWarehId());
        //验证商品并赋值
        setProud(lines, map, wareh.getOwnerId(), wmsErpNum.getOriginNum());
        return map;

    }

    /**
     * 根据传过来的参数 封装StbDtl信息
     *
     * @param os     (CWM推送确认商品信息集合)
     * @param unitId (单据属主)
     * @param grnNum (线下关联的库存单编号)
     */
    public void setProud(FimOrderLines os, Map<String, Object> map, Long unitId, String grnNum) {
        Set<Product> set = new HashSet<>();
        List<StbDtl> stbDtls = new ArrayList<>();
        for (FirmOrderLine orderLine : os.getOrderLine()) {
            System.out.println(orderLine);
            if (StringUtils.isBlank(orderLine.getItemCode())) {
                throw new ServiceException(JsonResultCode.FAILURE, "回执入库单商品code为空");
            }
            Product product = new Product();
            product.setProdCode(orderLine.getItemCode());
            Product ps = productMapper.findByProdcutClsCode(product);
            if (ps == null || !"A".equalsIgnoreCase(ps.getSkuStatus())) {
                throw new ServiceException(JsonResultCode.FAILURE, "回执入库单商品不存在或商品被禁用，商品code:" + orderLine.getItemCode());
            }
            //回执商品
            set.add(ps);
        }
        //根据商品设置回执库存单明细
        for (Product prod : set) {
            StbDtl stbDtl = new StbDtl();
            stbDtl.setProdId(prod.getProdId());
            stbDtl.setProdClsId(prod.getProdClsId());
            stbDtl.setUnitId(unitId);
            stbDtl.setStbNum(grnNum);
            int totqty = 0;
            for (FirmOrderLine orderLine : os.getOrderLine()) {
                if (prod.getProdCode().equalsIgnoreCase(orderLine.getItemCode())) {
                    totqty += orderLine.getActualQty();
                }
            }
            //最后商品总数量
            stbDtl.setQty(new BigDecimal(totqty + ""));
            stbDtls.add(stbDtl);
        }
        map.put("stbDtl", stbDtls);
    }

    /**
     * 确认入库前比对
     */
    public void confirmWarehSing(Map<String, Object> map) {
        Long unitId = (Long) map.get("unitId");
        List<StbDtl> wmsStbDtls = (List<StbDtl>) map.get("stbDtl");
        WmsErpNum wmsErpNum = (WmsErpNum) map.get("wmsErpNum");
        //订单状态
        String status = (String) map.get("status");
        //总行数
        String totalOrderLines = (String) map.get("totalOrderLines");
        //多次收货
        String confirmType = (String) map.get("confirmType");
        List<StbDtl> erpStbs = stbDtlService.queryStbDtlsByNumAndUnit(wmsErpNum.getOriginNum(), unitId);
        if (CollectionUtils.isNotEmpty(erpStbs)) {
            chayiList(erpStbs, wmsStbDtls, map);
            return;
        }
        log.info("没有查询到本地库存单明细");

    }

    /**
     * 返回差异并修改库存单、库存单明细、入库单状态
     */
    public void chayiList(List<StbDtl> erpStbDtls, List<StbDtl> wmsStbDtls, Map<String, Object> map) {
        //返回的差异结果集
        List<StbDtl> difference = new ArrayList<>();
        //修改的库存明细
        List<StbDtl> updateStbDtl = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(erpStbDtls)) {
            //只能少收不能多收
            if (wmsStbDtls.size() > erpStbDtls.size()) {
                throw new ServiceException("403", "入库控制不允许多收");
            }
            //WMS中明细
            List<StbDtl> listTow = wmsStbDtls;
            //比对后相等的商品(WMS商品)
            for (StbDtl w : wmsStbDtls) {
                System.out.println("WMS:" + w);
                for (StbDtl e : erpStbDtls) {
                    System.out.println("ERPS:" + e);
                }
            }

            List<StbDtl> listOne = erpStbDtls.stream().filter(s -> wmsStbDtls.contains(s)).collect(Collectors.toList());
            //剩下是确认单中没有的(差异)
            erpStbDtls.removeAll(listOne);
            difference.addAll(erpStbDtls);
            String st = "";
            if (CollectionUtils.isNotEmpty(listOne)) {
                for (StbDtl erp : listOne) {
                    for (StbDtl wms : listTow) {
                        if (wms.equals(erp)) {
                            //实际数量多余预期数量
                            log.info("回传数量  " + wms);
                            log.info("本地数量  " + erp);
                            if (wms.getQty().compareTo(erp.getExpdQty()) > 0) {
                                throw new ServiceException("403", "入库控制不允许多收");
                            }
                            erp.setQty(wms.getQty());
                            //实际数量小于预期数量
                            if (wms.getQty().compareTo(erp.getExpdQty()) < 0) {
                                //预期数量减实际收货数量
                                st = erp.getExpdQty().subtract(wms.getQty()) + "";
                                BeanUtils.copyProperties(erp, wms);
                                wms.setExpdQty(new BigDecimal(st));
                                wms.setQty(new BigDecimal("0"));
                                //这里返回差异  差异数量为预期数量
                                difference.add(wms);
                                updateStbDtl.add(erp);
                            }
                            updateStbDtl.add(erp);
                        }
                    }
                }
            } else {
                throw new ServiceException("403", "没有找到任何预入库单中商品");
            }
        }
        //修改ERP 库存单   这个集合updateStbDtl
        map.put("updateStbDtl", updateStbDtl);
        //差异结果集
        map.put("difference", difference);
        return;
    }
}
