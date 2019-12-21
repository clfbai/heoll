package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserRoleKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.system.UserRoleModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUserRoleService;
import com.boyu.erp.platform.usercenter.service.system.UgServer;
import com.boyu.erp.platform.usercenter.vo.system.UserRoleVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user/userRole")
public class UserRoleController extends BaseController {


    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private UgServer ugServer;

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult list(@RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "15") Integer size,
                           HttpServletRequest re,
                           UserRoleVo userRoleVo) {
        try {
            SysUser sessionSysUser = this.getSessionSysUser(re);
            if (userRoleVo == null || userRoleVo.getOwnerId() == null && userRoleVo.getUserId() == null) {
                userRoleVo.setOwnerId(sessionSysUser.getOwnerId());
                userRoleVo.setUserId(sessionSysUser.getUserId());
            }
            PageInfo<SysRole> pageInfo = userRoleService.selectAll(page, size, userRoleVo);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[UserPrivService][updateUserPriv] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserRoleController][listgoods] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 批量修改用户角色
     */
    @SystemLog(name = "批量修改用户角色")
    @PostMapping(value = "/update")
    public JsonResult update(@RequestBody UserRoleModel userRole, HttpServletRequest re) {
        try {
            if (CollectionUtils.isNotEmpty(userRole.getAddRoleKeys())) {
                SysUser user = new SysUser();
                user.setUserId(userRole.getAddRoleKeys().get(0).getUserId());
                user.setOwnerId(userRole.getAddRoleKeys().get(0).getOwnerId());
                for (SysUserRoleKey key : userRole.getAddRoleKeys()) {
                    if (key.getUgId() != null && key.getUnitId() != -1L) {
                        if (!(boolean) ugServer.seletUgUser(user, key.getUgId()).get("bo")) {
                            return new JsonResult(JsonResultCode.FAILURE, (String) ugServer.seletUgUser(user, key.getUgId()).get("listgoods"), "");
                        }
                    }
                }
            }
            userRoleService.updateUserRole(userRole, this.getSessionSysUser(re));
            return new JsonResult(JsonResultCode.SUCCESS, "修改用户角色成功", "");
        }  catch (ServiceException ex) {
            log.error("[UserPrivService][updateUserPriv] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UserRoleController][delete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改用户角色失败", "");
        }
    }


}
