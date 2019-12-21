package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysRoleScope;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.RoleScopeSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 组织角色数据范围控制层
 * @author: clf
 * @create: 2019-06-01 16:36
 */
@Slf4j
@RestController
@RequestMapping("/user/scope")
public class UnitRoleScopeController extends BaseController {
    @Autowired
    private RoleScopeSerivce roleScopeSerivce;
    @Autowired
    private SysUnitService unitService;


    @RequestMapping("/unitRole")
    public JsonResult getUnitRoleScope(@RequestBody SysRoleScope roleScope, HttpServletRequest re) {
        try {
            this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", roleScopeSerivce.getUnitRoleScope(roleScope));
        } catch (ServiceException ex) {
            log.error("[RoleScopeSerivce][getUnitRoleScope] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitRoleScopeController][getUnitRoleScope] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    @SystemLog(name = "修改组织角色范围")
    @RequestMapping("/update/unitRole")
    public JsonResult updateUnitRoleScope(@RequestBody SysRoleScope roleScope, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            SysUnit unit = unitService.selectByPrimaryKey(roleScope.getUnitId());
            List<SysUnit> list = unitService.selectHierarchy(unit);
            if (StringUtils.equalsIgnoreCase(roleScope.getRoleScope(), "all") || StringUtils.equalsIgnoreCase(roleScope.getRoleScope(), "or")) {
                if (list.size() <= 1) {
                    return new JsonResult(JsonResultCode.FAILURE, "该组织还没有下属组织，不能设置角色范围为多选和所有", "");
                }
            }
            if (StringUtils.equalsIgnoreCase(roleScope.getRoleScope(), "all") || StringUtils.equalsIgnoreCase(roleScope.getRoleScope(), "ol")) {
                roleScope.setRoleBelongUnit(unit.getUnitHierarchy());
            }

            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", roleScopeSerivce.updateUnitRoleScope(roleScope, user));
        } catch (ServiceException ex) {
            log.error("[RoleScopeSerivce][updateUnitRoleScope ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitRoleScopeController][updateUnitRoleScope] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    @RequestMapping("/open/roleScope")
    public JsonResult openRoleScope(@RequestBody SysUnit unit, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            List<OptionVo> roleScope = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            SysUnit un = unitService.selectByPrimaryKey(unit.getUnitId());
            List<SysUnit> list = unitService.selectHierarchy(un);
            if (list.size() <= 1) {
                roleScope.add(new OptionVo("当前组织", "ol"));
            } else {
                roleScope.add(new OptionVo("本组织及下属组织范围", "all"));
                roleScope.add(new OptionVo("本组织下多组织", "or"));
                roleScope.add(new OptionVo("当前组织", "ol"));
            }
            map.put("roleUnit", roleScope);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[RoleScopeSerivce][updateUnitRoleScope ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitRoleScopeController][openRoleScope] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }
}
