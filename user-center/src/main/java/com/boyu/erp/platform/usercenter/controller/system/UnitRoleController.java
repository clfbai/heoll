package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUnitRoleService;
import com.boyu.erp.platform.usercenter.vo.system.UnitRoleVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/unitRole")
@Slf4j
public class UnitRoleController extends BaseController {

    @Autowired
    private SysUnitRoleService unitRoleService;

    /**
     * 查询组织角色列表
     */
    @RequestMapping(value = "/getlist", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getList(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest request,
                              UnitRoleVo unitRoleVo) {
        try {
            if (this.getSessionSysUser(request) == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "");
            }
            PageInfo<SysRole> pageInfo = unitRoleService.getList(page, size, unitRoleVo, this.getSessionSysUser(request));
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (Exception ex) {
            log.error("[SysUnitRoleController][listgoods] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

}
