package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysRoleService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.RolePrivModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/user/role")
public class RoleController extends BaseController {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUnitService unitService;

    /**
     * 查询所有角色
     */
    @RequestMapping(value = "/all", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult gertRole(HttpServletRequest request,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "15") Integer size,
                               SysRole role) {
        try {

            SysUser user = this.getSessionSysUser(request);
            user.setRole(role);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", roleService.selectAll(page, size, user));

        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[RoleController][gertRole] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改异常", "");
        }
    }

    /**
     * 修改角色
     */
    @SystemLog(name = "修改角色")
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult updateRole(@RequestBody SysRole role, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (!roleService.selectRoleTable(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "您还没有修改角色权限", "修改失败");
            }
            if (roleService.upadteRole(role) > 0) {
                return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "修改成功");
            }
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");

        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][updateRole] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改异常", "");
        }
    }

    /**
     * 增加角色
     * 1.增加角色表
     * 2.增加用户角色表
     * 3.增加组织角色表
     * 4.增加组织角色分配表
     * 5.增加用户角色分配表
     */
    @SystemLog(name = "增加角色")
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult addRole(@RequestBody SysRole role, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (!roleService.selectRoleTable(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "您还没有修改角色权限", "修改失败");
            }
            if (roleService.addRole(role, user) > 0) {
                return new JsonResult(JsonResultCode.SUCCESS, "增加成功", "增加成功");
            }
            return new JsonResult(JsonResultCode.FAILURE, "增加失败", "");

        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[RoleController][addRole] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加异常", "");
        }
    }

    /**
     * 删除角色
     * 说明：1. 物理删除角色表 角色(delete语句)
     * 2. 物理删除角色权限表 角色权限对应关系(delete语句)
     * 3. 物理删除角色组织表 角色组织对应关系(delete语句)
     * 4.修改用户角色分配表(打标)
     * 5.修改组织角色分配表打标
     */
    @SystemLog(name = "删除角色")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult deleteRole(@RequestBody SysRole role, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (!roleService.selectRoleTable(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "您还没有修改角色权限", "修改失败");
            }
            roleService.deleteRole(role, user);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", "删除成功");
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[RoleController][deleteRole] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除异常，请联系管理员", "");
        }
    }

    /**
     * 查询角色权限
     */
    @RequestMapping(value = "/roleidpriv", method = {RequestMethod.GET, RequestMethod.POST})

    public JsonResult seelctRolePriv(SysRole role, HttpServletRequest request) {

        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "角色权限", roleService.selectByRoleIdPriv(role));
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][ seelctRolePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "请求异常", "");
        }
    }


    /**
     * 角色权限修改
     */
    @SystemLog(name = "角色权限修改")
    @RequestMapping(value = "/updaterolepriv", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult updateRolePriv(@RequestBody RolePrivModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            log.info("[ 传参：                    ]" + model);
            roleService.updateRolePriv(model);
            return new JsonResult(JsonResultCode.SUCCESS, "角色修改权限", "修改成功");
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][updateRolePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改异常", "");
        }
    }


    /**
     * 角色授权
     */
    @SystemLog(name = "角色授权")
    @RequestMapping(value = "/setrolepriv", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult setRolePriv(@RequestBody SysRole role, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            log.info("[ 传参：                    ]" + role);
            return new JsonResult(JsonResultCode.SUCCESS, "角色添加权限", roleService.addRolePriv(role));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[RoleController][setRolePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加异常", "");
        }
    }

    /**
     * 角色权限回收
     */
    @SystemLog(name = "角色权限回收")
    @RequestMapping(value = "/deleterolepriv", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult deleteRolePriv(@RequestBody SysRole role, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "角色权限回收成功", roleService.deleteRolePriv(role));
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][deleteRolePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "角色权限回收异常", "");
        }
    }


    /**
     * 查询角色类别列表
     */
    @RequestMapping(value = "/getall", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getRoleAll(HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            List<SysRole> roles = roleService.getRoleAll();
            List<OptionVo> restVo = new ArrayList<>();
            for (SysRole role : roles) {
                OptionVo vo = new OptionVo(role.getRoleType(), role.getRoleType());
                restVo.add(vo);
            }
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", restVo);
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][getRoleAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }

    /**
     * 查询角色对应用户
     *
     * @param role
     */
    @RequestMapping(value = "/role/user", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult roleuser(HttpServletRequest request, @RequestBody SysRole role) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", RestulMap.getResut(roleService.getRoleUser(role)));
        } catch (ServiceException ex) {
            log.error("[RoleService][getRoleUser] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][roleuser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }

    /**
     * 查询角色对应组织
     *
     * @param role
     */
    @RequestMapping(value = "/role/unit", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult roleUnit(HttpServletRequest request, @RequestBody SysRole role) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", RestulMap.getResut(roleService.getRoleUint(role)));
        } catch (ServiceException ex) {
            log.error("[RoleService][getRoleUint] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][roleuser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }


    /**
     * 角色范围选择
     *
     * @param unit
     */
    @RequestMapping(value = "/role/unit/open", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult roleUnitOpen(@RequestBody SysUnit unit) {
        try {
            Map<String, Object> map = new HashMap<>();
            SysUnit u = unitService.selectByPrimaryKey(unit.getUnitId());
            List<SysUnit> list = unitService.selectHierarchy(u);
            
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", RestulMap.getResut(list));
        } catch (ServiceException ex) {
            log.error("[SysUnitService][selectByPrimaryKey] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][roleuser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }


}
