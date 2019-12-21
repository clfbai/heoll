package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserPrivKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.system.UserPrivModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysPrivilegeSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysUserPrivService;
import com.boyu.erp.platform.usercenter.service.system.UgServer;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user/userPriv")
public class UserPrivController extends BaseController {


    @Autowired
    private SysUserPrivService userPrivService;

    @Autowired
    private SysPrivilegeSerivce privilegeSerivce;
    @Autowired
    private UgServer ugServer;

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult list(@RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "15") Integer size,
                           SysUser user, HttpServletRequest rq) {

        try {
            this.isNullUser(rq);
            PageInfo<SysPrivilege> pageInfo = privilegeSerivce.getRrivAll(page, size, user);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserPrivController][listgoods] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改用户权限
     */
    @SystemLog(name = "修改用户权限")
    @PostMapping(value = "/update")
    public JsonResult update(@RequestBody UserPrivModel model, HttpServletRequest rq) {
        try {
            this.isNullUser(rq);
            if (CollectionUtils.isNotEmpty(model.getAddUserPriv())) {
                SysUser user = new SysUser();
                user.setUserId(model.getAddUserPriv().get(0).getUserId());
                user.setOwnerId(model.getAddUserPriv().get(0).getOwnerId());
                for (SysUserPrivKey key : model.getAddUserPriv()) {
                    if (key.getUgId() != -1L && key.getUnitId() != 0L) {
                        if (!(boolean) ugServer.seletUgUser(user, key.getUgId()).get("bo")) {
                            return new JsonResult(JsonResultCode.FAILURE, (String) ugServer.seletUgUser(user, key.getUgId()).get("listgoods"), "");
                        }
                    }
                }
            }
            userPrivService.updateUserPriv(model, this.getSessionSysUser(rq));
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
        } catch (ServiceException ex) {
            log.error("[UserPrivService][updateUserPriv] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserPrivController][delete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改用户权限失败", "");
        }
    }
}
