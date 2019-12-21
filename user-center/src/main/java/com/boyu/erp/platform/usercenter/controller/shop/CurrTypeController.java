package com.boyu.erp.platform.usercenter.controller.shop;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.shop.CurrType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.shop.CurrTypeServicer;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述: 币种控制层
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/20 20:15
 */
@Slf4j
@RestController
@RequestMapping("/shop/curr")
public class CurrTypeController extends BaseController {
    @Autowired
    private CurrTypeServicer currTypeServicer;

    /**
     * 查询支付方式明细
     *
     * @param
     * @return
     */
    @PostMapping(value = "/list")
    public JsonResult currTypeList(@RequestBody CurrType mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(currTypeServicer.getCurrTyrp(mode)));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CurrTypeController][currTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询币种信失败:CurrTypeController.currTypeList(),Exception Null", "");
        }
    }
}
