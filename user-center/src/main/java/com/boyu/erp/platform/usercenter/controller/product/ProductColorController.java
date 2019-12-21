package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.ColorService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 颜色
 */
@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductColorController extends BaseController {

    @Autowired
    private ColorService colorService;

    /**
     * 颜色
     *
     * @param page
     * @param size
     * @param color
     * @return
     */
    @RequestMapping(value = "/color/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult colorList(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "15") Integer size,
                                Color color) {
        try {
            PageInfo<Color> resultList = colorService.selectALL(page, size, color);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", resultList);
        } catch (ServiceException ex) {
            log.error("[ColorController][colorList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ColorController][colorList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 添加颜色
     *
     * @param color
     * @return
     */
    @SystemLog(name = "添加颜色")
    @RequestMapping(value = "/add/color", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addColor(@RequestBody Color color) {
        try {
            int result = colorService.insertColor(color);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ColorController][addColor] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ColorController][addColor] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 修改颜色
     *
     * @param color
     * @return
     */
    @SystemLog(name = "修改颜色")
    @RequestMapping(value = "/update/color", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateColor(@RequestBody Color color) {
        try {
            int result = colorService.updateColor(color);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ColorController][updateColor] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ColorController][updateColor] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 删除颜色
     *
     * @param color
     * @return
     */
    @SystemLog(name = "删除颜色")
    @RequestMapping(value = "/delete/color", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteColor(@RequestBody Color color) {
        try {
            int result = colorService.deleteColor(color);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ColorController][deleteColor] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ColorController][deleteColor] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }


    /**
     * 颜色下拉选择
     *
     * @return
     */
    @RequestMapping(value = "/option/color", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult optionColor() {
        try {
            List<Color> list = colorService.getOpentin();
            List<OptionVo> lvo = new ArrayList<>();
            if (list.size() > 0) {
                for (Color c : list) {
                    OptionVo vo = new OptionVo( c.getColorName(),c.getColorCode());
                    lvo.add(vo);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", lvo);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[ColorController][optionColor] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ColorController][optionColor] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


}
