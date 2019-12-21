package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.SpecScp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SpecScpService;
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
 * 规格范围
 */
@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductSpecScpController extends BaseController {

    @Autowired
    private SpecScpService specScpService;

    /**
     * 获取规格范围列表
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/specScp/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specScpList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  SpecScp specScp) {
        try {
            PageInfo<SpecScp> pageInfo = specScpService.selectAll(page, size, specScp);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[ProductSpecScpController][specScpList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecScpController][specScpList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 添加规格范围
     *
     * @return
     */
    @SystemLog(name = "添加规格范围")
    @RequestMapping(value = "/add/specScp", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult addSpecScp(@RequestBody SpecScp specScp) {
        try {
            int result = specScpService.insertSpecScp(specScp);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductSpecScpController][addSpecScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecScpController][addSpecScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改规格范围
     *
     * @return
     */
    @SystemLog(name = "修改规格范围")
    @RequestMapping(value = "/update/specScp", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult updateSpecScp(@RequestBody SpecScp specScp) {
        try {
            int result = specScpService.updateSpecScp(specScp);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductSpecScpController][updateSpecScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecScpController][updateSpecScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除规格范围
     *
     * @return
     */
    @SystemLog(name = "删除规格范围")
    @RequestMapping(value = "/delete/specScp", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult deleteSpecScp(@RequestBody SpecScp specScp) {
        try {
            int result = specScpService.deleteSpecScp(specScp);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductSpecScpController][deleteSpecScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecScpController][deleteSpecScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 规格范围下拉
     *
     * @return
     */
    @RequestMapping(value = "/specScp/getOption", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getOptionSpecScp(@RequestBody SpecScp specScp) {
        try {
            List<OptionVo> optionVos = new ArrayList<OptionVo>();

            List<SpecScp> resultList = specScpService.getSelectOption();

            for (SpecScp sg : resultList) {
                optionVos.add(new OptionVo(sg.getSpecScpName(), sg.getSpecScpId()));
            }
            Map<String, List<OptionVo>> resultMap = new HashMap<>();
            resultMap.put("specGrp", optionVos);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultMap);

        } catch (ServiceException ex) {
            log.error("[ProductSpecScpController][deleteSpecScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecScpController][deleteSpecScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }
}