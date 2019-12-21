package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.SpecScpDtlKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SpecScpDtlService;
import com.boyu.erp.platform.usercenter.vo.SpecScpDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  规格范围明细
 */
@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductSpecScpDtlController extends BaseController {

    @Autowired
    private SpecScpDtlService specScpDtlService;

    /**
     * 查询规格范围明细
     * @param specScpDtlKey
     * @return
     */
    @RequestMapping(value = "/specScpDtl/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specScpDtlList(@RequestBody SpecScpDtlKey specScpDtlKey) {
        try
        {
            List<SpecScpDtlVo> resultList = specScpDtlService.selectAll(specScpDtlKey);

            Map<String,List<SpecScpDtlVo>> resultMap=new HashMap<String,List<SpecScpDtlVo>>();

            resultMap.put("list",resultList);

            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultMap);

        } catch (ServiceException ex)
        {
            log.error("[ProductSpecScpDtlController][specScpDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[ProductSpecScpDtlController][specScpDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 添加规格范围明细
     * @param specScpDtlKey
     * @return
     */
    @SystemLog(name = "添加规格范围明细")
    @RequestMapping(value = "/add/specScpDtl", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult addSpecScpDtl(@RequestBody SpecScpDtlKey specScpDtlKey) {
        try
        {
            int result = specScpDtlService.insertSpecScpDtlKey(specScpDtlKey);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        } catch (ServiceException ex)
        {
            log.error("[ProductSpecScpDtlController][addSpecScpDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[ProductSpecScpDtlController][addSpecScpDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改规格范围明细
     * @param specScpDtlKey
     * @return
     */
    @SystemLog(name = "修改规格范围明细")
    @RequestMapping(value = "/update/specScpDtl", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult updateSpecScpDtl(@RequestBody SpecScpDtlKey specScpDtlKey) {
        try
        {
            int result = specScpDtlService.updateSpecScpDtlKey(specScpDtlKey);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        } catch (ServiceException ex)
        {
            log.error("[ProductSpecScpDtlController][updateSpecScpDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[ProductSpecScpDtlController][updateSpecScpDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除规格范围明细
     * @param specScpDtlKey
     * @return
     */
    @SystemLog(name = "删除规格范围明细")
    @RequestMapping(value = "/delete/specScpDtl", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult deleteSpecScpDtl(@RequestBody SpecScpDtlKey specScpDtlKey) {
        try
        {
            int result = specScpDtlService.deleteSpecScpDtlKey(specScpDtlKey);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        }catch (ServiceException ex)
        {
            log.error("[ProductSpecScpDtlController][deleteSpecScpDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecScpDtlController][deleteSpecScpDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }
}