package com.boyu.erp.platform.usercenter.TPOS.service.impl;

import com.boyu.erp.platform.usercenter.TPOS.entity.CancelOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import com.boyu.erp.platform.usercenter.TPOS.entity.common.ExtendProps;
import com.boyu.erp.platform.usercenter.TPOS.entity.common.SenderAndReceiverInfo;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.OrderLine;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.OrderLines;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.EntryOrderCreate;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.OrderCrate;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.OrderLineCreate;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.sales.ReturnOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.sales.SalesOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.model.EntryOrderModel;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpGoDowmWarehServicer;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpNumService;
import com.boyu.erp.platform.usercenter.TPOS.utils.CwmsUrlParamModelUtils;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;
import com.boyu.erp.platform.usercenter.service.base.InterfaceLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述: 入库单创建、入库单取消服务(推送C-WMS)
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/12/2 11:25
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class WmsErpGoDowmWarehServicerImpl implements WmsErpGoDowmWarehServicer {
    @Autowired
    private CwmsUrlParamModelUtils cwmsUtils;
    @Autowired
    private SysUnitMapper sysUnitMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private WmsErpNumService wmsErpNumService;
    @Autowired
    private RequestTPOService requestTPOService;
    @Autowired
    private InterfaceLog interfaceLog;

    //根据单据生成上传到C-WMS 的 入库单对象
    @Override
    public EntryOrderModel createrEntryOrder(GrnDtlModel grnDtlModel, SysUser user) throws ServiceException, Exception {
        //请求WMS单号(生成规则：源单所在领域ID+源单类型+仓库所在领域id+源单单号)
        WarehRcvTask warehRcvTask = grnDtlModel.getWarehRcvTask();
        List<StbDtl> stbDtlList = grnDtlModel.getStbDtls();
        //上传的库存单编号
        String erpId = wmsErpNumService.createErpCwmsId(warehRcvTask);
        //货主编号
        String ownerCode = sysUnitMapper.selectByPrimaryKey(warehRcvTask.getUnitId()).getUnitCode();
        //仓库编号
        String warehouseCode = sysUnitMapper.selectByPrimaryKey(warehRcvTask.getWarehId()).getUnitCode();
        //入库单对象
        EntryOrderCreate entryOrderCreate = setEntryOrderCreate(grnDtlModel, ownerCode, warehouseCode, erpId, user);
        //收件人信息
        entryOrderCreate.setReceiverInfo(setReceiverInfo(grnDtlModel, ownerCode, warehouseCode));
        //扩展属性
        ExtendProps extendProps = new ExtendProps();
        EntryOrderModel model = new EntryOrderModel();
        //入库单赋值
        model.setEntryOrder(entryOrderCreate);
        //商品信息对象赋值
        model.setOrderLines(setOrderLine(stbDtlList, ownerCode));
        //扩展属性赋值
        model.setExtendProps(extendProps);
        //添加对应关系
        addWsmNum(erpId, grnDtlModel.getGrn().getGrnNum(), "R");
        return model;
    }

    /**
     * 功能描述: 创建退货入库单对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/5 19:41
     */
    @Override
    public SalesOrder createSalesOrder(GrnDtlModel grnDtlModel, SysUser user) throws ServiceException, Exception {
        //上传的库存单编号
        String erpId = wmsErpNumService.createErpCwmsId(grnDtlModel.getWarehRcvTask());
        String warehCode = sysUnitMapper.selectByPrimaryKey(grnDtlModel.getWarehRcvTask().getWarehId()).getUnitCode();
        SalesOrder salesOrder = new SalesOrder();
        OrderCrate order = setOrderLine(grnDtlModel.getStbDtls(), warehCode);
        OrderLines orderLines = new OrderLines();
        List<OrderLine> list = new ArrayList<>();
        for (OrderLineCreate line : order.getOrderLine()) {
            OrderLine l = new OrderLine();
            BeanUtils.copyProperties(line, l);
            list.add(l);
        }
        orderLines.setOrderLine(list);
        salesOrder.setOrderLines(orderLines);
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setWarehouseCode(warehCode);
        returnOrder.setReturnOrderCode(erpId);
        returnOrder.setPreDeliveryOrderCode("UNKNOWN");
        returnOrder.setOrderType("THRK");
        SenderAndReceiverInfo senderInfo = sendType("unit", grnDtlModel.getWarehRcvTask());
        returnOrder.setSenderInfo(senderInfo);
        //这里分为组织退货和顾客退货
        return salesOrder;
    }

    /**
     * 功能描述: 入库单创建推送
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 11:36
     */
    @Override
    public ResponseOrder createEntryOrderPostURL(EntryOrderModel entryOrderModel, SysUser user) throws ServiceException, Exception {
        log.info("业务编码 uuid:" + entryOrderModel.getEntryOrder().getEntryOrderCode());
        CwmsUrlParamModel cwmsUrlParamModel = cwmsUtils.cretaeComm("/api/qm2", "taobao.qimen");
        ResponseOrder resp = requestTPOService.requestCWMS2XMLStr(cwmsUrlParamModel, entryOrderModel, ResponseOrder.class);
        interfaceLog.CWMSLog(cwmsUrlParamModel, resp, "入库单创建推送C-WMS", entryOrderModel.getEntryOrder().getEntryOrderCode(), user);
        return resp;
    }

    /**
     * @param cancelOrder (取消单据信息)
     * @param name        (操作描述)
     * @return resp
     * @auther CLF
     * @@date: 2019/11/19 10:36
     */
    @Override
    public ResponseOrder createOrderCancelURL(CancelOrder cancelOrder, String name, SysUser user) throws Exception {
        CwmsUrlParamModel cwmsUrlParamModel = cwmsUtils.cretaeComm("/api/qm2", "taobao.qimen");
        ResponseOrder resp = requestTPOService.requestCWMS2XMLStr(cwmsUrlParamModel, cancelOrder, ResponseOrder.class);
        //注意取消单据时uuid 为原单据+"_oc" 这样区分添加和取消时唯一id;
        interfaceLog.CWMSLog(cwmsUrlParamModel, resp, name, cancelOrder.getOrderCode() + "_oc", user);
        return resp;
    }


    /**
     * 根据入库任务生成入库取消单对象
     */
    @Override
    public CancelOrder createGrnCancelOrder(String orderId, WarehRcvTask warehRcvTask) {
        CancelOrder cancelOrder = new CancelOrder();
        cancelOrder.setOrderCode(orderId);
        cancelOrder.setOrderType("QTRK");
        if (warehRcvTask.getRcvMode().equalsIgnoreCase("PURC")) {
            cancelOrder.setOrderType("CGRK");
        }
        if (warehRcvTask.getRcvMode().equalsIgnoreCase("RTRT") ||
                warehRcvTask.getRcvMode().equalsIgnoreCase("SLRT")) {
            cancelOrder.setOrderType("XTRK");
        }
        //属主代码
        cancelOrder.setOwnerCode(sysUnitMapper.selectByPrimaryKey(warehRcvTask.getUnitId()).getUnitCode());
        //仓库代码
        cancelOrder.setWarehouseCode(sysUnitMapper.selectByPrimaryKey(warehRcvTask.getWarehId()).getUnitCode());
        return cancelOrder;
    }

    /**
     * 功能描述: 增加对应关系
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 11:36
     */
    public int addWsmNum(String erpId, String docNum, String type) {
        WmsErpNum wmsErpNum = new WmsErpNum(erpId, docNum, type);
        return wmsErpNumService.addRelation(wmsErpNum);
    }

    /**
     * 功能描述: 收货信息赋值
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 11:24
     */
    private SenderAndReceiverInfo setReceiverInfo(GrnDtlModel grnDtlModel, String ownerCode, String warehouseCode) {
        SenderAndReceiverInfo receiverInfo = new SenderAndReceiverInfo();
        receiverInfo.setCountryCode("ZH");
        //公司编码
        receiverInfo.setCompany(ownerCode);
        //仓库代码
        receiverInfo.setName(warehouseCode);
        return receiverInfo;
    }

    /**
     * 功能描述: 商品信息赋值
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 11:24
     */
    private OrderCrate setOrderLine(List<StbDtl> stbDtlList, String ownerCode) {
        //商品信息对象
        OrderCrate orderCrate = new OrderCrate();
        List<OrderLineCreate> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(stbDtlList)) {
            for (StbDtl stbDtl : stbDtlList) {
                int i = 1;
                OrderLineCreate orderLineCreate = new OrderLineCreate();
                Product p = productMapper.selectByProduct(stbDtl.getProdId());
                //商品代码
                orderLineCreate.setItemCode(p.getProdCode());
                //商品Id 这个需要商议后更改
                orderLineCreate.setItemId(p.getProdId() + "");
                //货主代码
                orderLineCreate.setOwnerCode(ownerCode);
                //应收数量
                orderLineCreate.setPlanQty(stbDtl.getExpdQty() + "");
                //采购价
                orderLineCreate.setPurchasePrice(stbDtl.getUnitPrice() + "");
                //零售价
                orderLineCreate.setRetailPrice(stbDtl.getMkUnitPrice() + "");
                orderLineCreate.setOrderLineNo(i + "");
                list.add(orderLineCreate);
                i++;
            }
        } else {
            throw new ServiceException("403", "检查商品数量等信息");
        }
        orderCrate.setOrderLine(list);
        return orderCrate;
    }

    /**
     * 功能描述: 订单信息赋值
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 11:24
     */
    private EntryOrderCreate setEntryOrderCreate(GrnDtlModel mmodel, String ownerCode, String warehouseCode, String erpId, SysUser user) {
        EntryOrderCreate entryOrderCreate = new EntryOrderCreate();
        //上传单据ID
        entryOrderCreate.setEntryOrderCode(erpId);
        //货主编号
        entryOrderCreate.setOwnerCode(ownerCode);
        //仓库编号
        entryOrderCreate.setWarehouseCode(warehouseCode);
        //操作员编码
        entryOrderCreate.setOperatorCode(user.getPrsnl().getPrsnlCode());
        //操作员姓名  中文乱码 user.getPrsnl().getFullName()
        entryOrderCreate.setOperatorName(user.getPrsnl().getFullName());
        //单据总行数
        entryOrderCreate.setTotalOrderLines(1);
        //单据类型  需要取参数赋值
        if (mmodel.getWarehRcvTask().getRcvMode().equals("PURC")) {
            //采购单据
            entryOrderCreate.setPurchaseOrderCode(mmodel.getWarehRcvTask().getUnitId() + "_" + mmodel.getWarehRcvTask().getTaskDocType() + "_" + mmodel.getWarehRcvTask().getTaskDocNum());
        }
        //单据类型 待编写
        entryOrderCreate.setOrderType("CGRK");
        //入对(创建)时间
        entryOrderCreate.setOrderCreateTime(mmodel.getWarehRcvTask().getJoinTime());
        //快递公司代码
        entryOrderCreate.setLogisticsCode("OTHER");
        return entryOrderCreate;
    }


    public SenderAndReceiverInfo sendType(String type, WarehRcvTask warehRcvTask) {
        SenderAndReceiverInfo senderInfo = new SenderAndReceiverInfo();
        //组织
        if (type.equalsIgnoreCase("unit")) {
            //退货组织
            SysUnit unit = sysUnitMapper.selectByPrimaryKey(warehRcvTask.getTaskDocUnitId());
            if (unit == null) {
                throw new ServiceException("403", "退货组织为空");
            }
            if (StringUtils.isBlank(unit.getFaxNum())) {
                throw new ServiceException("403", "退货组织移动电话为空，组织代码:" + unit.getUnitCode());
            }
            if (StringUtils.isBlank(unit.getAddress())) {
                throw new ServiceException("403", "退货组织详细地址为空，组织代码:" + unit.getUnitCode());
            }
            if (StringUtils.isBlank(unit.getProvince())) {
                throw new ServiceException("403", "退货组织省份为空，组织代码:" + unit.getUnitCode());
            }
            if (StringUtils.isBlank(unit.getCity())) {
                throw new ServiceException("403", "退货组织城市为空，组织代码:" + unit.getUnitCode());
            }
            senderInfo.setName(unit.getUnitCode());
            senderInfo.setCity(unit.getCity());
            senderInfo.setCountryCode("zh");
            senderInfo.setTel(unit.getTelNum());
            //移动电话
            senderInfo.setMobile(unit.getFaxNum());
            senderInfo.setProvince(unit.getProvince());
            senderInfo.setCity(unit.getCity());
            senderInfo.setDetailAddress(unit.getAddress());

        }
        return senderInfo;
    }


}
