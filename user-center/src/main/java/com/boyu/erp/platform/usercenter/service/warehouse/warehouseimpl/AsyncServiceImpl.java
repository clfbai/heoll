package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.alibaba.fastjson.JSON;
import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTaskU;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.model.XmlResult;
import com.boyu.erp.platform.usercenter.TPOS.service.DhOrdTaskUService;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.TPOS.utils.JaxbUtil;
import com.boyu.erp.platform.usercenter.entity.basic.Vendee;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import com.boyu.erp.platform.usercenter.service.system.VendeeService;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 线程池异步处理接口
 *
 * @author HHe
 * @date 2019/9/27 9:41
 */
@Slf4j
@Service
@Transactional
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    private WarehStkXService warehStkXService;
    @Autowired
    private StlService stlService;
    @Autowired
    private StlDtlService stlDtlService;
    @Autowired
    private StbService stbService;
    @Autowired
    private VendeeService vendeeService;
    @Autowired
    private WarehStkService warehStkService;
    @Autowired
    private StkCostService stkCostService;
    @Autowired
    private WarehCostService warehCostService;
    @Autowired
    private RequestTPOService requestTPOService;
    @Autowired
    private DhOrdTaskUService dhOrdTaskUService;
    //调拨库存挂账方参数Id
    private final static String TRANSFER_STOCK_HOLDER = "TRANSFER_STOCK_HOLDER";
    //购销对应往来账户类别
    private final static String PS_CA_TYPE = "PS_CA_TYPE";
    /**
     * 原单发货
     */
    private final static String DELIVER = "deliver";

    /**
     * 盘点表过账损益（异步）
     *
     * @version 1.0
     * @author HHe
     * @date 2019/9/26 19:34
     */
    @Async
    @Override
    public void warehSttPAL(Stt stt, SysUser sysUser) {
        //查询仓库快照
        List<WarehStkX> warehStkXList = warehStkXService.queryWarehStkXList(stt.getWarehId(), stt.getSnptTime());
        //查询对应盘点表的盘点清单编号集合
        List<String> stlNumList = stlService.queryStlNumListBySttNumAndId(stt.getSttNum(), sysUser.getDomain().getUnitId());
        //查询明细集合
        List<StlDtl> stlDtlList = stlDtlService.queryStlDtlListByStlNumListAndId(stlNumList, sysUser.getDomain().getUnitId());
        //比对集合；根据快照数量-清单明细数量，如果是正数，表示盘亏，将差价绝对值作为数量生成库存调整出库单明细，
        //负数盘盈，将差价绝对值作为数量生成库存调整入库单明细；
        //差数集合：正数生成出库明细集合；负数生成入库明细集合
//        HashMap<String, BigDecimal> numMap = new HashMap<>();
        List<StbDtl> stbDtlList = new ArrayList<>();
        //需要修改库存数量集合
        HashMap<String, BigDecimal> warehStkMap = new HashMap<>();
        Long lineNum = 0L;
        for (WarehStkX warehStkX : warehStkXList) {//123
            boolean whether = true;
            for (StlDtl stlDtl : stlDtlList) {//可能有重复存在，112
                if (warehStkX.getProdId().equals(stlDtl.getProdId())) {
                    whether = false;
                    BigDecimal num = warehStkX.getStkOnHand().subtract(stlDtl.getQty());
                    //判断map中有有没有已存在的商品，有则在原基础上减少数量
                    boolean nb = true;
                    boolean wb = true;
                    for (int i = 0; i < stbDtlList.size(); i++) {
                        if (warehStkX.getProdId().equals(stbDtlList.get(i).getProdId())) {
                            stbDtlList.get(i).setExpdQty(stbDtlList.get(i).getExpdQty().subtract(stlDtl.getQty()));
                            break;
                        }
                    }
                    for (String key : warehStkMap.keySet()) {
                        if (warehStkX.getProdId().toString().equals(key)) {
                            warehStkMap.put(key, warehStkMap.get(key).add(stlDtl.getQty()));
                            wb = false;
                            break;
                        }
                    }
                    if (nb) {
                        if (!num.equals(BigDecimal.ZERO)) {
                            StbDtl stbDtl = new StbDtl();
                            stbDtl.setProdId(stlDtl.getProdId());
                            stbDtl.setExpdQty(num);
                            stbDtl.setLineNum(lineNum);
                            lineNum++;
                            stbDtlList.add(stbDtl);
                        }
                    }
                    if (wb) {
                        warehStkMap.put(warehStkX.getProdId().toString(), stlDtl.getQty());
                    }
                }
            }
            //没有盘到商品，表示盘亏，直接将快照数量生成出库单明细，并且把仓库实际库存存0；
            if (whether) {
                if (!warehStkX.getStkOnHand().equals(BigDecimal.ZERO)) {
                    StbDtl stbDtl = new StbDtl();
                    stbDtl.setProdId(warehStkX.getProdId());
                    stbDtl.setExpdQty(warehStkX.getStkOnHand());
                    stbDtl.setLineNum(lineNum);
                    lineNum++;
                    stbDtlList.add(stbDtl);
                }
                warehStkMap.put(warehStkX.getProdId().toString(), new BigDecimal("0"));
            }
        }
        //计算总数量
        BigDecimal totNum = new BigDecimal("0");
        for (StbDtl stbDtl : stbDtlList) {
            totNum = totNum.add(stbDtl.getExpdQty());
        }
        //生成库存单
        //生成出库单
        StbGdn stbGdn = new StbGdn();
        stbGdn.setWarehId(stt.getWarehId());
        stbGdn.setSrcDocUnitId(sysUser.getDomain().getUnitId());
        stbGdn.setSrcDocType("STT");
        stbGdn.setSrcDocNum(stt.getSttNum());
        stbGdn.setTtlExpdQty(totNum);
        stbGdn.setTtlQty(totNum);
        stbGdn.setEffective("T");
        stbGdn.setDelivMode("ADJS");
        stbGdn.setFsclDelivMode("ADJS");
        stbGdn.setFsclDateAptd("F");
        stbGdn.setPostCtrl("U");
        stbGdn.setRckReqd("F");
        stbGdn.setDlvrId(sysUser.getPrsnl().getPrsnlId());
        stbGdn.setDelivTime(new Timestamp(System.currentTimeMillis()));
        stbGdn.setProgress("DD");
//        String gdnNum = gdnService.addStbGdn(stbGdn,sysUser);
        //封装商品信息
        //生成出库单明细

        //修改库存数量

    }
    /**
     * 盘点表过账损益（异步）（盘点表和库存调整表调用，需要先将数据封装成损益父类对象传输）
     * @param
     * @return
     * @version 2.0
     * @author HHe
     * @date 2019/10/28 10:59
     */


    /**
     * 出库单出库异步处理
     *
     * @author HHe
     * @date 2019/10/28 11:23
     */
    @Async
    @Override
    public void gdnDeliver(List<CountCostModel> countCostModels, Long warehId, Long fsclUnitId) {
        //修改库存数量、仓库成本中的数量、库存成本中的数量
        if (countCostModels != null && countCostModels.size() > 0) {
            //将数量变负数
            for (CountCostModel countCostModel : countCostModels) {
                countCostModel.setQty(countCostModel.getQty().negate());
                countCostModel.setVal(countCostModel.getVal().negate());
            }
            warehStkService.insertUpdateWarehStkList(countCostModels, warehId);
            warehCostService.updateByQty(countCostModels, warehId);
            stkCostService.updateByQty(countCostModels, fsclUnitId);
        }
    }

    /**
     * 修改库存数量和仓库数量
     *
     * @param list       修改信息（共用）
     * @param costGrpId  成本组Id（组织成本）
     * @param warehId    仓库Id（仓库成本用）
     * @param fsclUnitId 会计组织Id（共用）
     * @author HHe
     * @date 2019/11/22 11:14
     */
    @Async
    @Override
    public void updateCost(List<CountCostModel> list, Long costGrpId, Long warehId, Long fsclUnitId) {
        //添加修改库存成本
        stkCostService.insertUpdateStkCost(list, costGrpId, fsclUnitId);
        //添加修改仓库成本
        warehCostService.insertUpdateWarehCost(list, warehId, fsclUnitId);
    }

    /**
     * 上传单据信息
     *
     * @param obj               请求体
     * @param cwmsUrlParamModel 请求信息
     * @author HHe
     * @date 2019/12/4 10:13
     */
    @Async
    @Override
    public void uploadingOrder(DhOrdTaskU dhOrdTaskU, Object obj, CwmsUrlParamModel cwmsUrlParamModel) {
        //异步登记上传信息
        //请求体转XML
//        JaxbUtil jaxbUtil = new JaxbUtil();
//        String strObj = "";
        JaxbUtil jaxbUtil = new JaxbUtil(obj.getClass());
        String strObj = jaxbUtil.toXml(obj, "UTF-8");
        dhOrdTaskU.setContent(strObj);
        //请求参数转JSON
        String jsonP = JSON.toJSONString(cwmsUrlParamModel);
        dhOrdTaskU.setParams(jsonP);
        dhOrdTaskU.setOpTime(new Date());
        dhOrdTaskU.setImplTimes(0);
        //异步登记
        dhOrdTaskUService.registerDhOrdTaskU(dhOrdTaskU);
        //上传请求
        String flag;
        String mess;
        try {
            String resultXml = requestTPOService.requestCWMS2XMLStr(cwmsUrlParamModel, obj, String.class);
            //转对象
            JaxbUtil resultUtil = new JaxbUtil(XmlResult.class);
            XmlResult xmlResult = resultUtil.fromXml(resultXml);
            mess = xmlResult.getMessage();
            flag = xmlResult.getFlag();
        } catch (Exception e) {
            e.printStackTrace();
            flag="failure";
            mess = e.getMessage();
        }
        dhOrdTaskU.setErrMsg(mess);
        dhOrdTaskU.setExecuteStatus(flag);
        //异步修改信息（状态、信息）
        dhOrdTaskUService.updateDhOrdTaskU(dhOrdTaskU);
    }


    /**
     * Assist
     * 会计出库方式待定判断
     *
     * @author HHe
     * @date 2019/10/19 10:50
     */
    private String judgePeding(Long fsclUnitId, Long rcvFsclUnitId) {
        //判断收货方会计组织Id是发货方会计组织ID的采购商
        Vendee pVendee = new Vendee();
        pVendee.setOwnerId(fsclUnitId);
        pVendee.setVendeeId(rcvFsclUnitId);
        Vendee vendee = vendeeService.queryVendeeByIdAndOwner(pVendee);
        if (vendee != null) {
            return "SELL";
        }
        return "PURT";
    }

    /**
     * 判断总单
     *
     * @author HHe
     * @date 2019/10/19 12:10
     */
    public boolean judgeTotDoc(String docNum, Long unitId) {
        //查询其他表中ldg_stb_num是否包含docNum
        List<String> childDocs = stbService.queryStbIsTotal(docNum, unitId);
        if (childDocs != null && childDocs.size() > 0) {
            return true;
        }
        return false;
    }

}
