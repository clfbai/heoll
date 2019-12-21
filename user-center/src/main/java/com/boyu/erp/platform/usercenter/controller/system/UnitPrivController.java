package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUnitPrivService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivModel;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user/UnitPriv")
public class UnitPrivController extends BaseController {


    @Autowired
    private SysUnitPrivService unitPrivService;

    @Autowired
    private SysUnitService unitService;

    @Autowired
    private UsersService usersService;

    /**
     * 查询组织权限列表
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unltPrivList(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "15") Integer size,
                                   HttpServletRequest re, UnitPrivVo unitPrivVo) {
        try {
            SysUser sessionSysUser = this.getSessionSysUser(re);
            if (sessionSysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登陆", "");
            }
            PageInfo<SysPrivilege> pageInfo = unitPrivService.selectAll(page, size, unitPrivVo, sessionSysUser);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        }catch (ServiceException ex) {
            log.error("[TAreaServcie][getTArea] exception", ex);
            return new JsonResult("600800", ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitPrivController][unltPrivList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }

    }


    @SystemLog(name = "修改组织权限及角色")
    @RequestMapping(value = "/updateUnitRriv", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUnitRriv(@RequestBody UnitPrivModel model, HttpServletRequest re) {
        try {
            SysUser user=this.getSessionSysUser(re);
            SysUnit unit = unitService.selectByPrimaryKey(model.getUnitId());
            if (model.getDeletePrivilege() != null) {
                if (usersService.getAdmin(this.getSessionSysUser(re)) == null || usersService.getAdmin(user).getOprId() != -1L) {
                    if (model.getUnitId().equals(this.getSessionSysUser(re).getOwnerId())) {
                        return new JsonResult(JsonResultCode.FAILURE, "不能回收本组织权限", "");
                    }
                }
            }

            int a = unitPrivService.updateUnitRriv(model, this.getSessionSysUser(re));
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", a);
        }catch (ServiceException ex) {
            log.error("[TAreaServcie][getTArea] exception", ex);
            return new JsonResult("600800", ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitPrivController][updateUnitRriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


}
