package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.sales.SrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoTypeService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcTypeService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcTypeService;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 调配单类别控制层
 * @author: ck
 * @create: 2019-06-21 11:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales")
public class SalesRgoTypeController extends BaseController {
    @Autowired
    private RgoTypeService rgoTypeService;

    @Autowired
    private SlcTypeService slcTypeService;

    @Autowired
    private SrcTypeService srcTypeService;


    /**
     * 查询所有销售合同(支持模糊查询)
     * @return
     */
    @RequestMapping(value = "/rgoType/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getRgoTypeList(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size,
                                     HttpServletRequest re,
                                     RgoType rgoType) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            PageInfo<RgoType> pageInfo = rgoTypeService.getRgoTypeList(page, size, rgoType);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[rgoTypeService][getRgoTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[rgoTypeService][getRgoTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }



    /**
     * 创建调配单类别
     */
    @SystemLog(name = "创建调配单类别")
    @RequestMapping(value = "/rgoType/createRgoType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult creatRgoType(@RequestBody RgoType rgoType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", rgoTypeService.insert(rgoType));
        } catch (ServiceException ex) {
            log.error("[rgoTypeService][creatRgoType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[rgoTypeService][creatRgoType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }

    }

    /**
     * 更新调配单类别
     */
    @SystemLog(name = "更新调配单类别")
    @RequestMapping(value = "/rgoType/updateRgoType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateRgoType(@RequestBody RgoType rgoType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", rgoTypeService.updateByRgoType(rgoType));
        } catch (ServiceException ex) {
            log.error("[rgoTypeService][updateRgoType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[rgoTypeService][updateRgoType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 删除调配单类别
     */
    @SystemLog(name = "删除调配单类别")
    @RequestMapping(value = "/rgoType/deleteRgoType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteRgoType(@RequestBody RgoType rgoType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", rgoTypeService.deleteByRgoType(rgoType));
        } catch (ServiceException ex) {
            log.error("[rgoTypeService][deleteRgoType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[rgoTypeService][deleteRgoType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    @RequestMapping(value = "/rgoType/getRgoTypeOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSlcTypeOption() {
        try {
            Map<String, Object> map = new HashMap<>();

            /**
             * 获取须指定中转仓库option
             */
            List<OptionVo> tranWarehReqd = new ArrayList<>();
            tranWarehReqd.add(new OptionVo("是", "T"));
            tranWarehReqd.add(new OptionVo("否", "F"));

            /**
             * 获取须指定发货仓库option
             */
            List<OptionVo> delivWarehReqd = new ArrayList<>();
            delivWarehReqd.add(new OptionVo("是", "T"));
            delivWarehReqd.add(new OptionVo("否", "F"));

            /**
             * 获取退购合同自动生成option
             */
            List<OptionVo> prcAutoGen = new ArrayList<>();
            prcAutoGen.add(new OptionVo("是", "T"));
            prcAutoGen.add(new OptionVo("否", "F"));

            /**
             * 获取退购合同自动审核option
             */
            List<OptionVo> prcAutoChk = new ArrayList<>();
            prcAutoChk.add(new OptionVo("是", "T"));
            prcAutoChk.add(new OptionVo("否", "F"));

            /**
             * 获取须指定收货仓库option
             */
            List<OptionVo> recWarehReqd = new ArrayList<>();
            recWarehReqd.add(new OptionVo("是", "T"));
            recWarehReqd.add(new OptionVo("否", "F"));

            /**
             * 获取采购合同自动生成option
             */
            List<OptionVo> pucAutoGen = new ArrayList<>();
            pucAutoGen.add(new OptionVo("是", "T"));
            pucAutoGen.add(new OptionVo("否", "F"));

            /**
             * 获取采购合同自动审核option
             */
            List<OptionVo> pucAutoChk = new ArrayList<>();
            pucAutoChk.add(new OptionVo("是", "T"));
            pucAutoChk.add(new OptionVo("否", "F"));

            /**
             * 获取差异裁定方option
             */
            List<OptionVo> drDiffJgd = new ArrayList<>();
            drDiffJgd.add(new OptionVo("发货方", "D"));
            drDiffJgd.add(new OptionVo("收货方", "R"));

            /**
             * 获取差异裁定方可选option
             */
            List<OptionVo> drDiffJgdAlt = new ArrayList<>();
            drDiffJgdAlt.add(new OptionVo("是", "T"));
            drDiffJgdAlt.add(new OptionVo("否", "F"));

            /**
             * 获取固定单价option
             */
            List<OptionVo> fixedUnitPrice = new ArrayList<>();
            fixedUnitPrice.add(new OptionVo("是", "T"));
            fixedUnitPrice.add(new OptionVo("否", "F"));

            /**
             * 获取启动配码option
             */
            List<OptionVo> bxiEnabled = new ArrayList<>();
            bxiEnabled.add(new OptionVo("是", "T"));
            bxiEnabled.add(new OptionVo("否", "F"));

            /**
             * 获取启动配码可选option
             */
            List<OptionVo> bxiEnabledAlt = new ArrayList<>();
            bxiEnabledAlt.add(new OptionVo("是", "T"));
            bxiEnabledAlt.add(new OptionVo("否", "F"));

            /**
             * 获取活动option
             */
            List<OptionVo> active = new ArrayList<>();
            active.add(new OptionVo("是", "T"));
            active.add(new OptionVo("否", "F"));

            /**
             * 获取退销合同类别
             */
            List<OptionVo> srcType = new ArrayList<>();
            List<SrcType> srcTypeList = srcTypeService.optionGet();
            for (SrcType _srcType : srcTypeList) {
                srcType.add(new OptionVo(_srcType.getDescription(), _srcType.getSrcType()));
            }

            /**
             * 获取销售合同类别
             */
            List<PurKeyValue> slcType = slcTypeService.optionGet();

            map.put("tranWarehReqd", tranWarehReqd);
            map.put("delivWarehReqd", delivWarehReqd);
            map.put("prcAutoGen", prcAutoGen);
            map.put("prcAutoChk", prcAutoChk);
            map.put("recWarehReqd", recWarehReqd);
            map.put("pucAutoGen", pucAutoGen);
            map.put("pucAutoChk", pucAutoChk);
            map.put("drDiffJgd", drDiffJgd);
            map.put("drDiffJgdAlt", drDiffJgdAlt);
            map.put("fixedUnitPrice", fixedUnitPrice);
            map.put("bxiEnabled", bxiEnabled);
            map.put("bxiEnabledAlt", bxiEnabledAlt);
            map.put("active", active);
            map.put("srcType", srcType);
            map.put("slcType", slcType);

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }
}
