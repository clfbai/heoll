package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysPrivilegeSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysRoleService;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.PrivModel;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/privilege")
public class PrivilegeController extends BaseController {

    @Autowired
    private SysPrivilegeSerivce sysPrivilegeSerivce;


    @Resource
    private SysRoleService roleService;

    /**
     * 查询用户权限列表分页
     */
    @RequestMapping(value = "/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult login(@RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "15") Integer size,
                            SysPrivilege priv,
                            HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            user.setPrivilege(priv);
            PageInfo<SysPrivilege> privilege = sysPrivilegeSerivce.getRrivAll(page, size, user);
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", privilege);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][login] exception", ex);

            return new JsonResult(JsonResultCode.FAILURE, "登陆失败,请联系管理员", "");
        }
    }

    /**
     * 查询用户权限列表
     */
    @RequestMapping(value = "/getall", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getall(HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            List<SysPrivilege> privilege = sysPrivilegeSerivce.getAuthoritys(user);
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", privilege);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][login] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "登陆失败,请联系管理员", "");
        }
    }

    /**
     * 增加权限
     * 1.增加权限表
     * 2.增加组织权限分配表
     * 3.增加用户权限分配表
     * 4.增加用户权限对应表
     * 5.增加组织权限对应表
     */
    @SystemLog(name = "新增权限")
    @RequestMapping(value = "/addpriv", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addpriv(@RequestBody SysPrivilege privilege, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (!sysPrivilegeSerivce.selectPriv(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "你还没有该项增加权限", "增加失败");
            }
            List<SysPrivilege> list = sysPrivilegeSerivce.getByAllauthoritys(this.getSessionSysUser(request));
            for (SysPrivilege sysPrivilege : list) {
                if (sysPrivilege.getPrivId().equals(privilege.getPrivId())) {
                    return new JsonResult(JsonResultCode.FAILURE, "权限已存在", "");
                }
            }
            sysPrivilegeSerivce.addAuthority(privilege, user);
            return new JsonResult(JsonResultCode.SUCCESS, "添加成功", "添加成功");


        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][addpriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加失败,请联系管理员", "");
        }
    }

    /**
     * 修改权限
     * 说明： 修改权限表
     */
    @SystemLog(name = "修改权限")
    @RequestMapping(value = "/updatepriv", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatepriv(@RequestBody List<SysPrivilege> privilege, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (!sysPrivilegeSerivce.selectPriv(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "你还没有该项修改权限", "修改失败");
            }
            if (sysPrivilegeSerivce.updateByPrimaryAuthority(user, privilege) > 0) {
                return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "修改成功");
            }
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][updatepriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改异常,请联系管理员", "");
        }
    }

    /**
     * 删除权限
     * 说明：1. 物理删除权限表 权限(delete语句)
     * 2. 物理删除角色权限表 角色权限对应关系(delete语句)
     * 3. 物理删除组织权限表 组织权限对应关系(delete语句)
     * 4. 组织权限分配表打标
     * 5. 用户权限分配表打标
     * 6. 删除相应权限依赖
     */
    @SystemLog(name = "删除权限")
    @RequestMapping(value = "/deletepriv", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletepriv(@RequestBody SysPrivilege privilege, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (privilege.getPrivId().equalsIgnoreCase(CommonFainl.PRIV_MAX) || privilege.getPrivId().equalsIgnoreCase(CommonFainl.ROLE_MAX)) {
                return new JsonResult(JsonResultCode.FAILURE, "此项权限为基础权限不能删除", "删除失败");
            }
            if (!sysPrivilegeSerivce.selectPriv(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "你还没有该项删除权限", "删除失败");
            }
            sysPrivilegeSerivce.deletePriv(privilege, user);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", "删除成功");
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][deletepriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除异常,请稍后再试", "");
        }
    }

    /**
     * 查询权限类别
     */
    @RequestMapping(value = "/privtype", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getPrivType(HttpServletRequest request) {

        try {

            SysUser user = this.getSessionSysUser(request);
            List<OptionVo> list = new ArrayList<OptionVo>();
            for (SysPrivilege privilege : sysPrivilegeSerivce.getAllType()) {
                OptionVo vo = new OptionVo(privilege.getPrivType(), privilege.getPrivType());
                list.add(vo);
            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询权限类别", list);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][getPrivType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除异常,请稍后再试", "");
        }
    }

    /**
     * 权限所属角色查询
     */
    @RequestMapping(value = "/selectprivrole", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getPrivRole(@RequestBody SysPrivilege privilege, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", roleService.selectPrivRoles(privilege, user));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RoleController][selectprivrole] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }

    /**
     * 权限范围查询
     */
    @RequestMapping(value = "/privscope", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getPrivScope(HttpServletRequest request) {

        try {
            SysUser user = this.getSessionSysUser(request);

            List<OptionVo> list = new ArrayList<OptionVo>();
            for (SysPrivilege privilege : sysPrivilegeSerivce.getAllTypeScope()) {
                if (privilege.getPrivScp().equals("ol")) {
                    OptionVo vo = new OptionVo("联线", privilege.getPrivScp());
                    list.add(vo);
                } else if (privilege.getPrivType().equals("bt")) {
                    OptionVo vo = new OptionVo("全部", privilege.getPrivScp());
                    list.add(vo);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询权限类别", list);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][getPrivScope] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常,请稍后再试", "");
        }
    }

    /**
     * 权限对应角色修改
     */
    @SystemLog(name = "权限对应角色修改")
    @RequestMapping(value = "/privroleupdate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult privroleupdate(HttpServletRequest request, @RequestBody PrivModel privilege) {

        try {
            SysUser user = this.getSessionSysUser(request);
            sysPrivilegeSerivce.updatePrivRole(privilege);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "操作成功");
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][privroleupdate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "权限对应角色操作异常,请稍后再试", "");
        }
    }


    /**
     * 查询角色的权限
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/getRolePriv", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getRolePriv(@RequestBody(required = false) SysRole role, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            List<SysPrivilege> resultList = sysPrivilegeSerivce.getRolePriv(role);
            return new JsonResult(JsonResultCode.SUCCESS, "查询权限类别", resultList);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][getRolePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常,请稍后再试", "");
        }
    }

    /**
     * 查询权限对应用户
     *
     * @param privilege
     * @return
     */
    @RequestMapping(value = "/priv/user", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult privUser(@RequestBody SysPrivilege privilege) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, "查询权限类别", RestulMap.getResut(sysPrivilegeSerivce.getPrivuser(privilege)));
        } catch (Exception ex) {
            log.error("[PrivilegeController][getRolePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常,请稍后再试", "");
        }
    }

    /**
     * 查询权限对应组织
     *
     * @param privilege
     * @return
     */
    @RequestMapping(value = "/priv/unit", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult privUnit(@RequestBody(required = false) SysPrivilege privilege) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "查询权限类别", RestulMap.getResut(sysPrivilegeSerivce.getPrivUnit(privilege)));
        } catch (Exception ex) {
            log.error("[PrivilegeController][getRolePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常,请稍后再试", "");
        }
    }
}
