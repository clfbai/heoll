package com.boyu.erp.platform.usercenter.controller.shop;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.shop.PayMode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.shop.PayModelS;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.shop.PayModelServicer;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类描述: 支付方式控制层
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/20 17:42
 */
@Slf4j
@RestController
@RequestMapping("/shop/pay")
public class PayModelController extends BaseController {
    @Autowired
    private PayModelServicer payModelServer;
    @Autowired
    private ProdClsUtils prodClsUtils;

    /**
     * 查询支付方式
     *
     * @param
     * @return
     */
    @PostMapping(value = "/list")
    public JsonResult payModelList(@RequestBody PayMode mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(payModelServer.getParyMode(mode)));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][payModelList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询支付方式信息失败:PayModelController.payModelList(),Exception Null", "");
        }
    }

    /**
     * 增加支付方式
     *
     * @param
     * @return
     */
    @SystemLog(name = "增加支付方式")
    @PostMapping(value = "/addPayModel")
    public JsonResult addPayModel(@RequestBody PayModelS mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, payModelServer.addPayModel(mode));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][addPayModel] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加付款方式失败:PayModelController.addPayModel(),Exception Null", "");
        }
    }

    /**
     * 查询支付方式明细
     *
     * @param
     * @return
     */
    @PostMapping(value = "/getPayId")
    public JsonResult getPayModelCurr(@RequestBody PayMode mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(payModelServer.getParyModeCurr(mode)));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][getPayModelCurr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询币种明细信息失败:PayModelController.etPayModelCurr(),Exception Null", "");
        }
    }
    /**
     * 修改支付方式明细
     *
     * @param
     * @return
     */
    @SystemLog(name = "修改支付方式")
    @PostMapping(value = "/updatePayModel")
    public JsonResult updatePayModel(@RequestBody PayModelS mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, payModelServer.updatePayModel(mode));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][updatePayModel] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "改支付方式明细失败:PayModelController.updatePayModel(),Exception Null", "");
        }
    }
    /**
     * 修改支付方式明细
     *
     * @param
     * @return
     */
    @SystemLog(name = "修改支付方式明细")
    @PostMapping(value = "/updatePayModelCurr")
    public JsonResult updatePayModelCurr(@RequestBody PayModelS mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, payModelServer.updatePayModelCurr(mode));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][updatePayModelCurr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "改支付方式明细失败:PayModelController.updatePayModelCurr(),Exception Null", "");
        }
    }

    /**
     * 支付方式下拉
     *
     * @param
     * @return
     */
    @PostMapping(value = "/option")
    public JsonResult payModelOption() {
        try {
            List<String> list = prodClsUtils.getListString();
            list.add("exPmtd");
            list.add("giveChg");
            list.add("fixed");
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prodClsUtils.getMapOffOn(list));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][payModelOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询支付方式下拉信息失败:PayModelController.payModelOption(),Exception Null", "");
        }
    }

}
