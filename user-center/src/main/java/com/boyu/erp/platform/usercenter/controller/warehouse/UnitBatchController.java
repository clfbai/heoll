package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.TPOS.common.confirm.EntryOrder;
import com.boyu.erp.platform.usercenter.TPOS.common.confirm.FimOrderLines;
import com.boyu.erp.platform.usercenter.TPOS.common.confirm.FirmOrderLine;
import com.boyu.erp.platform.usercenter.TPOS.common.confirm.WarehSingConfirm;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.service.ItemUploadingService;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.batch.BatchModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.UnitBatchSerivce;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRmodeService;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.AsynchronizationUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 类描述:  商品批次号控制层
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/9 10:39
 */
@Slf4j
@RestController
@RequestMapping("/Batch")
public class UnitBatchController extends BaseController {
    @Autowired
    private UnitBatchSerivce unitBatchSerivce;
    @Autowired
    private ItemUploadingService itemUploadingService;
    @Autowired
    private AsynchronizationUtils numUtils;
    @Autowired
    private WarehRmodeService rmodeService;
    @Autowired
    private RequestTPOService requestTPOService;

    /**
     * 退购查询批次信息
     *
     * @param unitBatchNum
     * @return
     */

    @RequestMapping(value = "/refundList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult refundList(@RequestBody UnitBatchNum unitBatchNum, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, unitBatchSerivce.getUnitBatchNum(unitBatchNum));
        } catch (Exception ex) {
            log.error("[UnitBatchController][refundList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 退购查询批次(商品可退总数量)
     *
     * @param unitBatchNum
     * @return
     */

    @RequestMapping(value = "/refundSum", method = {RequestMethod.POST})
    public JsonResult refundSum(@RequestBody UnitBatchNum unitBatchNum, HttpServletRequest request) {
        try {
            int sum = unitBatchSerivce.getSumByUnitBatchNum(unitBatchNum);
            if (sum > 0) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, sum);
            } else {
                return new JsonResult(JsonResultCode.SUCCESS, "该商品暂无可退数量!", sum);
            }

        } catch (Exception ex) {
            log.error("[UnitBatchController][refundSum] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 增加批次号
     *
     * @param unitBatch
     * @return
     */
    @SystemLog(name = "增加批次号")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addUnitBatch(@RequestBody BatchModel unitBatch, HttpServletRequest request) {
        try {
            ProdCls ps = new ProdCls();
            ps.setProdClsId(14106L);
            WarehRmode rmode = rmodeService.selectWarehRmode(103L, "PURC");
            log.info("[rmode:]" + rmode);
            // unitBatchSerivce.addUnitBatch(unitBatch, unitBatch.getUnitBatchNum().getDocType(), this.getSessionSysUser(request))
            Integer u = 56;
            System.out.println(u);
            System.out.println(numUtils.TestAsyn(u));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("[UnitBatchSerivce][addUnitBatch] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitBatchController][addUnitBatch] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 修改批次号
     *
     * @param
     * @return
     */

    @RequestMapping(value = "/add/text", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult textUnitBatch(@RequestBody Stb gruNum, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            WarehSingConfirm confirm = new WarehSingConfirm();

            EntryOrder entryOrder = new EntryOrder();

            entryOrder.setWarehouseCode("WO1");
            entryOrder.setEntryOrderCode("100_PUC_100_TP00000000164");
            entryOrder.setEntryOrderType("PTCK");
            entryOrder.setOutBizCode("YGXX" + (long) (Math.random() * 1000000000));
            FimOrderLines orderLines = new FimOrderLines();
            List<FirmOrderLine> list = new ArrayList<>();
            FirmOrderLine item1 = new FirmOrderLine();
            item1.setItemCode("01.01.001006091");
            item1.setInventoryType("ZP");
            item1.setItemName("000001");
            item1.setOrderLineNo("1");
            item1.setOwnerCode("lt");
            item1.setActualQty(3);
            list.add(item1);
            FirmOrderLine item2 = new FirmOrderLine();
            item2.setItemCode("01.01.001006091");
            item2.setInventoryType("ZP");
            item2.setItemName("000002");
            item2.setOrderLineNo("2");
            item2.setOwnerCode("lt");
            item2.setActualQty(3);
            list.add(item2);
            orderLines.setOrderLine(list);
            confirm.setOrderLines(orderLines);
            confirm.setEntryOrder(entryOrder);

            CwmsUrlParamModel cwmsUrlParamModel = new CwmsUrlParamModel();
            String secretKey = "RA8wjgCNocNo99IAd5wFFW93Wll1TuRC"; // 生产 将另行告知
            //前缀 目前按照给的 demo 就是 app_key
            cwmsUrlParamModel.setAppKey(secretKey);
            //app_key
            cwmsUrlParamModel.setSecret(secretKey);
            cwmsUrlParamModel.setRequestMapping("/boyu/erp");
            cwmsUrlParamModel.setMethod("taobao.qimen.entryorder.confirm");
            cwmsUrlParamModel.setCustomerid("stub-cust-code");
            cwmsUrlParamModel.setTimestamp(DateUtil.dateToString(new Date()));
            cwmsUrlParamModel.setObjXml(requestTPOService.createObjXml(confirm));
            ResponseOrder responseOrder = requestTPOService.createCwmsURL(cwmsUrlParamModel, UUID.randomUUID().toString(), ResponseOrder.class);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, responseOrder);

        } catch (ServiceException ex) {
            log.error("[UnitBatchController][textUnitBatch] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitBatchController][textUnitBatch] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.ADDFIANL, "");
        }
    }


}
