package com.boyu.erp.platform.usercenter.controller.shop;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.shop.ShopAttrDef;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.shop.ShopAttrDefModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.shop.ShopAttrDefServicer;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 类描述:  门店属性控制层
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/20 15:04
 */
@Slf4j
@RestController
@RequestMapping("/shop/attr")
public class ShopAttrDefController extends BaseController {
    @Autowired
    private ShopAttrDefServicer shopAttrDefServicer;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService codeDtlService;

    /**
     * 查询门店所有自定义属性列表
     *
     * @param
     * @return
     */
    @GetMapping(value = "/list")
    public JsonResult shopAttrDefList(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                      @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                      ShopAttrDef shopAttrDef, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, shopAttrDefServicer.getShopAttr(page, size, shopAttrDef));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][shopAttrDefList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店自定义属性信息失败:ShopAttrDefController.shopAttrDefList,Exception Null", "");
        }
    }

    /**
     * 查询门店所有自定义属性名称和其下拉值
     *
     * @param
     * @return
     */
    @PostMapping(value = "/AttrDefName")
    public JsonResult shopAttrDefNameList(ShopAttrDef shopAttrDef, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, shopAttrDefServicer.getShopAttrName());
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][shopAttrDefNameList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店自定义属性名称信息失败:ShopAttrDefController.shopAttrDefNameList(),Exception Null", "");
        }
    }

    /**
     * 修改门店自定义属性列表
     *
     * @param model (包含增加、删除、修改)
     * @return
     */
    @SystemLog(name = "修改门店属性定义")
    @PostMapping(value = "/update")
    public JsonResult updateShopAttrDef(@RequestBody ShopAttrDefModel model, HttpServletRequest request) {
        try {
            if (StringUtils.NullEmpty(model.getAttrDeftype())) {
                return new JsonResult(JsonResultCode.FAILURE, "请传入修改类型:attrDeftype值", "");
            }
            if (CommonFainl.ADD.equalsIgnoreCase(model.getAttrDeftype()) && StringUtils.NullEmpty(model.getCodeType())) {
                return new JsonResult(JsonResultCode.FAILURE, "请填写下拉codeType值", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, shopAttrDefServicer.updateShopAttrDef(model));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][updateShopAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][updateShopAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改门店自定义属性信息失败:ShopAttrDefController.updateShopAttrDef(),Exception Null", "");
        }
    }


    /**
     * 查询门店自定义属性下拉列表
     *
     * @param
     * @return
     */
    @PostMapping(value = "/option")
    public JsonResult shopAttrDefOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            //数据类型
            map.put("dataType", codeDtlService.optionGet("DATA_TYPE"));
            //大小写
            map.put("charCase", codeDtlService.optionGet("CHAR_CASE"));
            //是否必须
            map.put("valReqd", codeDtlService.optionGet("TandF"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][shopAttrDefOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店自定义属性下拉失败:ShopAttrDefController.shopAttrDefOption(),Exception Null", "");
        }
    }





}
