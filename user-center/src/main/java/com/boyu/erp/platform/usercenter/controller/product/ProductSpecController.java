package com.boyu.erp.platform.usercenter.controller.product;

import com.alibaba.fastjson.JSON;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.SpecGrp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SpecService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品规格(商品的规格明细)
 */
@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductSpecController extends BaseController {

    @Autowired
    private SpecService specService;

    /**
     * 获取规格列表
     *
     * @return
     */
    @RequestMapping(value = "/spec/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specList(@RequestBody SpecGrp specGrp) {
        try {
            log.info("[ProductSpecController][specList] 根据规格组获取规格：{}", JSON.toJSON(specGrp));

            String specGrpId = specGrp.getSpecGrpId();

            if (StringUtils.isBlank(specGrpId)) {
                return new JsonResult(JsonResultCode.FAILURE, "规格组ID不能为空", "");
            }

            List<Spec> resultList = this.specService.getListSpecBySpecGrpId(specGrpId);

            Map<String, List> resultMap = new HashMap<String, List>();

            resultMap.put("list", resultList);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultMap);
        } catch (ServiceException ex) {
            log.error("[ProductSpecController][specList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecController][specList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }


    /**
     * 单获所有规格
     *
     * @return
     */
    @RequestMapping(value = "/spec/getAll", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specGetAll(@RequestBody SpecGrp specGrp) {
        try {
            List<Spec> singleSpec = this.specService.getAll();
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", singleSpec);
        } catch (ServiceException ex) {
            log.error("[ProductSpecController][specSingle] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecController][specSingle] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }


    /**
     * 获取单个规格
     *
     * @return
     */
    @RequestMapping(value = "/spec/single", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specSingle(@RequestBody Spec spec) {
        try {
            log.info("[ProductSpecController][specSingle] 根据规格组获取规格：{}", JSON.toJSON(spec));
            //获取单个对象
            Spec singleSpec = this.specService.getSpecById(spec);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", singleSpec);
        } catch (ServiceException ex) {
            log.error("[ProductSpecController][specSingle] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecController][specSingle] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }


    /**
     * 规格的下拉列表
     *
     * @return
     */
    @RequestMapping(value = "/spec/options", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specOptions(@RequestBody Spec spec) {
        try {
            //获取单个对象
            List<Spec> resultList = this.specService.getOptionsList(spec);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultList);
        } catch (ServiceException ex) {
            log.error("[ProductSpecController][specOptions] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecController][specOptions] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 添加规格
     *
     * @return
     */
    @SystemLog(name = "添加规格")
    @RequestMapping(value = "/add/spec", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult addSpec(@RequestBody Spec spec) {
        try {
            int result = specService.insertSpec(spec);
            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductSpecController][addSpec] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecController][addSpec] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改规格
     *
     * @return
     */
    @SystemLog(name = "修改规格")
    @RequestMapping(value = "/update/spec", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult updateSpec(@RequestBody Spec spec) {
        try {
            int result = specService.updateSpec(spec);
            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductSpecController][updateSpec] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecController][updateSpec] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除规格
     *
     * @return
     */
    @SystemLog(name = "删除规格")
    @RequestMapping(value = "/delete/spec", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult deleteSpec(@RequestBody Spec spec) {
        try {
            int result = specService.deleteSpec(spec);
            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductSpecController][deleteSpec] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecController][deleteSpec] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


}