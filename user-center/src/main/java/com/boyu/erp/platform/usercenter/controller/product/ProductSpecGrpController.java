package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.SpecGrp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SpecGrpService;
import com.boyu.erp.platform.usercenter.service.system.SpecService;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品规格组
 */
@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductSpecGrpController extends BaseController {

    @Autowired
    private SpecService specService;

    @Autowired
    private SpecGrpService specGrpService;

    /**
     * 获取规格组列表
     * @param page  当前页面
     * @param size  页码
     * @return
     */
    @RequestMapping(value = "/specGrp/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specGrpList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  SpecGrp specGrp) {
        try
        {
            PageInfo<SpecGrp> pageInfo = specGrpService.selectAll(page, size, specGrp);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        }catch (ServiceException ex)
        {
            log.error("[ProductSpecGrpController][specGrpList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecGrpController][specGrpList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 获取规格范围列表下拉
     * @return
     */
    @RequestMapping(value = "/specGrp/options", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult specGrpSelect()
    {
        try
        {
            List<OptionVo> optionVos = new ArrayList<OptionVo>();

            List<SpecGrp> resultList = specGrpService.getSelectAll();

            for(SpecGrp sg:resultList)
            {
                optionVos.add(new OptionVo(sg.getSpecGrpName(),sg.getSpecGrpId()));
            }

            Map<String, List<OptionVo>> resultMap = new HashMap<>();

            resultMap.put("sgList",optionVos);

            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultMap);

        }catch (ServiceException ex)
        {
            log.error("[ProductSpecScpController][specGrpSelect] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecScpController][specGrpSelect] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 添加规格组
     * @return
     */
    @SystemLog(name = "添加规格组")
    @RequestMapping(value = "/add/specGrp", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult addSpecGrp(@RequestBody SpecGrp specGrp) {
        try
        {
            int result=specGrpService.insertSpecGrp(specGrp);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        } catch (ServiceException ex)
        {
            log.error("[ProductSpecGrpController][addSpecGrp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[ProductSpecGrpController][addSpecGrp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }
    /**
     * 修改规格组
     * @return
     */
    @SystemLog(name = "修改规格组")
    @RequestMapping(value = "/update/specGrp", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult updateSpecGrp(@RequestBody SpecGrp specGrp) {
        try
        {
            int result=specGrpService.updateSpecGrp(specGrp);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        }catch (ServiceException ex)
        {
            log.error("[ProductSpecGrpController][updateSpecGrp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecGrpController][updateSpecGrp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除规格组
     *
     *  1.先判断改规格组下面是否有活动的规格.
     *  2.存在则不允许删除，否则允许物理删除.
     * @return
     */
    @SystemLog(name = "删除规格组")
    @RequestMapping(value = "/delete/specGrp", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult deleteSpecGrp(@RequestBody SpecGrp specGrp)
    {
        try
        {
            //获取规格组下面的规格
            List<Spec> list=this.specService.getListSpecBySpecGrpId(specGrp.getSpecGrpId());

            if(CollectionUtils.isNotEmpty(list))
            {
                return new JsonResult(JsonResultCode.FAILURE, "该规格组下面有子规格，不允许删除", "");
            }

            int result=specGrpService.deleteSpecGrp(specGrp);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        }catch (ServiceException ex)
        {
            log.error("[ProductSpecGrpController][deleteSpecGrp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE,ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductSpecGrpController][deleteSpecGrp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }
}