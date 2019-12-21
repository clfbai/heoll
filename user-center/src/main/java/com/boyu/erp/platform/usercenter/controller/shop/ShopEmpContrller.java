package com.boyu.erp.platform.usercenter.controller.shop;

import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.UserModel;
import com.boyu.erp.platform.usercenter.model.shop.ShopEmpModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.base.InterfaceLog;
import com.boyu.erp.platform.usercenter.service.shop.ShopServicer;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * 类描述: 门店店员
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/10/17 19:58
 */
@Slf4j
@RestController
@RequestMapping("/shop/emp")
public class ShopEmpContrller extends BaseController {
    private static final String table = "shop_emp|";
    private static final String Operations = "ShopEmpContrller|";
    @Autowired
    private ShopServicer shopServicer;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private RequestTPOService requestTPOService;
    @Autowired
    private InterfaceLog interfaceLog;

    /**
     * 修改门店店员
     *
     * @param
     * @return
     */
    @SystemLog(name = "修改门店店员")
    @PostMapping(value = "/updateEmp")
    public JsonResult updateShopEmp(@RequestBody ShopEmpModel shopEmp, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, shopEmp.getShopId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, shopServicer.updateShopEmp(shopEmp));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][updateShopEmp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopEmpContrller][shopEmp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改门店店员信息失败ShopController.updateShopEmp() exception Null", "");
        }
    }


    /**
     * 查询可以添加的店员(导购)
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/selectEmp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult selectEmp(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                OwnerPrsnlModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            CwmsUrlParamModel cwmsUrlParamModel = new CwmsUrlParamModel();
            String secretKey = "RA8wjgCNocNo99IAd5wFFW93Wll1TuRC"; // 生产 将另行告知
            //前缀 目前按照给的 demo 就是 app_key
            cwmsUrlParamModel.setAppKey(secretKey);
            //app_key
            cwmsUrlParamModel.setSecret(secretKey);
            cwmsUrlParamModel.setRequestMapping("/api/qm2");
            cwmsUrlParamModel.setMethod("taobao.qimen");
            cwmsUrlParamModel.setCustomerid("stub-cust-code");
            cwmsUrlParamModel.setTimestamp(DateUtil.dateToString(new Date()));
            UserModel userModel = new UserModel(1L, 100L, "完整");
            cwmsUrlParamModel.setObjXml(requestTPOService.createObjXml(userModel));
            ResponseOrder responseOrder = requestTPOService.createCwmsURL(cwmsUrlParamModel, UUID.randomUUID().toString(), ResponseOrder.class);
            interfaceLog.CWMSLog(cwmsUrlParamModel, responseOrder, "添加店员", "uuid_6565" + DateUtil.dateToString(new Date()), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, shopServicer.getEmpVo(page, size, model, user));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][getEmpVo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopEmpContrller][selectEmp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询可以添加的店员信息失败ShopController.selectEmp() Exception Null", "");
        }
    }

}
