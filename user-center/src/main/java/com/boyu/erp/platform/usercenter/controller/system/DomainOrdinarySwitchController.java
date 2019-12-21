package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.DomainSwitchSession;
import com.boyu.erp.platform.usercenter.model.system.OrdinaryDomainModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysDomainService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.common.JsonUtils.JsonRemove;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.DomainVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述: 普通用户切换领域
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/14 10:39
 */
@Slf4j
@RestController
@RequestMapping("/user/ordinary")
public class DomainOrdinarySwitchController extends BaseController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysDomainService domainService;

    /**
     * 普通用户切换领域列表查询
     *
     * @param
     * @return
     */
    @GetMapping(value = "/list")
    public JsonResult ordinaryList(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                   @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                   OrdinaryDomainModel model, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            if (usersService.getIsAdmin(user) || usersService.getIsLeader(user)) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, "");
            }
            model.setUserId(user.getUserId());
            //去掉用户当前组织
            model.setUnitId(user.getOwnerId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, domainService.getPage(page, size, model));
        } catch (ServiceException ex) {
            log.error("[SysDomainService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DomainOrdinarySwitchController][ordinaryList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 普通用户切换领域
     *
     * @param
     * @return
     */
    @SystemLog(name = "普通用户切换领域")
    @PostMapping(value = "/login/domain")
    public JsonResult ordinaryLogin(@RequestBody OrdinaryDomainModel model, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            //先注销当前session
            request.getSession().removeAttribute(GlobalConstants.SESSION_VALIDATE_CODE);
            if (user.getUserId().equals(model.getUserId())) {
                //获取对象
                SysUser sysUser = usersService.getOrdinaryLogin(model);
                //登陆成功
                if (sysUser != null) {
                    //普通登录非切换领域
                    DomainSwitchSession s = new DomainSwitchSession();
                    s.setIsDomainSwitch(CommonFainl.NORMALLOGINUSER);
                    sysUser.setDomainSwitchSession(s);
                    //设置session
                    request.getSession().setAttribute(GlobalConstants.SESSION_SYSTEM_USER, sysUser);
                    return new JsonResult(JsonResultCode.SUCCESS, "切换成功", sysUser);
                }
            }
            return JsonRemove.result(JsonResultCode.FAILURE, "您没有领域可切换", 0);
        } catch (ServiceException ex) {
            log.error("[UsersService][getOrdinaryLogin] ServiceException", ex);
            return JsonRemove.result(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DomainOrdinarySwitchController][ordinaryLogin] exception", ex);
            return JsonRemove.result(JsonResultCode.FAILURE, "普通用户切换领域失败:DomainOrdinarySwitchController.ordinaryLogin", "");
        }
    }


    /**
     * 管理员查看普通用户切换领域列表
     *
     * @param
     * @return
     */
    @PostMapping(value = "/adminList")
    public JsonResult adminOrdinaryList(@RequestBody OrdinaryDomainModel model, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (usersService.getIsAdmin(user) || usersService.getIsLeader(user)) {
                //管理员查看范围
                model.setUnitId(user.getOwnerId());
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(domainService.getAdminPage(model)));
            }
            return new JsonResult(JsonResultCode.FAILURE, "非管理员用户", "");
        } catch (ServiceException ex) {
            log.error("[SysDomainService][getAdminPage] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DomainOrdinarySwitchController][adminOrdinaryList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询普通用户领域信息异常:DomainOrdinarySwitchController.adminOrdinaryList", "");
        }
    }

    /**
     * 管理员修改普通用户领域
     *
     * @param
     * @return
     */
    @SystemLog(name = "修改普通用户领域信息")
    @PostMapping(value = "/updateOrdinary")
    public JsonResult updateOrdinaryList(@RequestBody OrdinaryDomainModel model, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (usersService.getIsAdmin(user) || usersService.getIsLeader(user)) {
                return new JsonResult(JsonResultCode.SUCCESS, "修改用户领域信息成功", domainService.updateOrdinary(model, user));
            }
            return new JsonResult(JsonResultCode.FAILURE, "非管理员用户不能进行此项操作", "");
        } catch (ServiceException ex) {
            log.error("[SysDomainService][updateOrdinary] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DomainOrdinarySwitchController][updateOrdinaryList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查修改普通用户领域信息异常:DomainOrdinarySwitchController.updateOrdinaryList", "");
        }
    }


    /**
     * 管理员或系统管理员查询可以授予某个用户的领域信息
     *
     * @param
     * @return
     */
    @PostMapping(value = "/getdomain")
    public JsonResult getDomainList(@RequestBody  DomainVo vo, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (usersService.getIsAdmin(user) || usersService.getIsLeader(user)) {
                vo.setUnitId(user.getOwnerId());
                return new JsonResult(JsonResultCode.SUCCESS, "查询领域信息成功",RestulMap.getResut(domainService.getDomain(vo)) );
            }
            return new JsonResult(JsonResultCode.FAILURE, "", "");
        } catch (ServiceException ex) {
            log.error("[SysDomainService][updateOrdinary] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DomainOrdinarySwitchController][getDomainList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查修改普通用户领域信息异常:DomainOrdinarySwitchController.updateOrdinaryList", "");
        }
    }

}
