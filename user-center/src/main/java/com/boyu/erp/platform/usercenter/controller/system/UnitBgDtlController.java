package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.GoodsBrandPrivAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.UnitBgModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.UnitBgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 组织品牌分组
 * @author: clf
 * @create: 2019-05-23 14:07
 */
@Slf4j
@RestController
@RequestMapping("/unit")
public class UnitBgDtlController extends BaseController {

    @Autowired
    private UnitBgService unitBgService;

    /**
     * 查询组织品牌分组(表)
     *
     * @param bgMode
     * @param
     * @return
     */
    @RequestMapping(value = "/bg/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitBgList(UnitBgModel bgMode, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            Map<String, Object> map = new HashMap<>();
            List<Bg> list = unitBgService.unitBgAll(bgMode);
            map.put("size", list == null ? 0 : list.size());
            map.put("list", list);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", map);
        } catch (ServiceException ex) {

            log.error("[UnitBgService][unitBgAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[BgController][ unitBgList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 增加修改组织品牌分组(表)
     *
     * @param bgMode
     * @param
     * @return
     */
    @SystemLog(name = "修改组织品牌分组")
    @GoodsBrandPrivAuthority(name = "updateUintBg")
    @RequestMapping(value = "/bg/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitBgUpdate(@RequestBody UnitBgModel bgMode, HttpServletRequest request) {
        try {

            unitBgService.unitBgUpdate(bgMode, this.isNullUser(request));
            return new JsonResult(JsonResultCode.SUCCESS, "添加成功", "");
        } catch (ServiceException ex) {
            log.error("[UnitBgService][unitBgAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[BgController][unitBgAdd] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


}
