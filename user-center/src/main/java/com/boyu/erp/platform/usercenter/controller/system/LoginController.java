package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.CutDomainModel;
import com.boyu.erp.platform.usercenter.model.DomainSwitchSession;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysDomainService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.PasswordUtils;
import com.boyu.erp.platform.usercenter.utils.common.JsonUtils.JsonRemove;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.LoginModel;
import com.boyu.erp.platform.usercenter.vo.OriginDomainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户登录与退出
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController extends BaseController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private SysDomainService domainService;


    /**
     * 登录
     *
     * @param
     * @return
     */
    @SystemLog(name = CommonFainl.USERLONG)
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult login(@RequestBody LoginModel loginModel, HttpSession session) {
        try {
            removeSession(session);
            //验证码
            String ValidateCode = (String) session.getAttribute(GlobalConstants.SESSION_VALIDATE_CODE);

            log.info("[LoginController][login] session中获取的验证码为:{}", ValidateCode);
            String code = loginModel.getValidateCode();
            log.info("[LoginController][login] 请求中获取的验证码为:{}", code);

           /*if (StringUtils.isBlank(code) || !ValidateCode.equalsIgnoreCase(loginModel.getValidateCode())) {
                return new JsonResult(JsonResultCode.FAILURE, "验证码错误", "");
            }*/

            loginModel.setUserPwd(PasswordUtils.encryptionPassword(loginModel.getUserPwd()));

            //获取对象
            SysUser sysUser = usersService.systemLogin(loginModel);

            //登陆成功
            if (sysUser != null) {
                isNull(CommonFainl.NORMALLOGINUSER, sysUser);
                session.setAttribute(GlobalConstants.SESSION_SYSTEM_USER, sysUser);
                return new JsonResult(JsonResultCode.SUCCESS, "登陆成功", sysUser);
            } else {
                return new JsonResult(JsonResultCode.FAILURE_LOGIN, "账号或者密码错误,请重试", "");
            }
        } catch (ServiceException ex) {
            log.error("[SysFunctionSerivce][addFunction] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[LoginController][login] exception", ex);
            return JsonRemove.result(JsonResultCode.FAILURE_LOGIN, "登陆失败,请联系管理员", "");
        }
    }

    /**
     * 切换领域(登陆)
     *
     * @param
     * @return
     */
    @SystemLog(name = CommonFainl.USERLONGCAT)
    @RequestMapping(value = "/cutDomain/login", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult cutLogin(@RequestBody CutDomainModel cutDomainModel, HttpServletRequest request, HttpSession session) {
        try {

            SysUser user =  this.isNullUser(request);
            LoginModel loginModel = domainService.sysCutLog(cutDomainModel.getUnitId());

            removeSession(session);

            //在登陆获取对象
            SysUser sysUser = usersService.systemLogin(loginModel);

            //登陆成功
            if (sysUser != null) {
                isNull(CommonFainl.ISCUTDOMAIN, sysUser);
                session.setAttribute(GlobalConstants.SESSION_SYSTEM_USER, sysUser);
                //切换领域用户Id 用于操作日志记录与追踪
                session.setAttribute(GlobalConstants.CUTDOMAIN_USERID, user.getUserId());
                session.setAttribute(GlobalConstants.CUTDOMAIN_USER_UNIT, user.getOwnerId());
                return new JsonResult(JsonResultCode.SUCCESS, "切换领域成功", sysUser);
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "切换失败,请重试", "");
            }
        }catch (ServiceException ex) {
            log.error("[SysFunctionSerivce][addFunction] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[LoginController][cutLogin] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "切换失败,请联系管理员", "");
        }
    }


    /**
     * 回到原领域领域(登陆)
     *
     * @param
     * @return
     */
    @SystemLog(name = CommonFainl.USERLONGORIGIN)
    @RequestMapping(value = "/origin/login", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult originLogin(@RequestBody CutDomainModel cutDomainModel, HttpServletRequest request, HttpSession session) {
        try {
            /**
             * 用户Id
             * */
            Long userId = (Long) session.getAttribute(GlobalConstants.CUTDOMAIN_USERID);
            Long unitId = (Long) session.getAttribute(GlobalConstants.CUTDOMAIN_USER_UNIT);

            OriginDomainModel originDomainModel = new OriginDomainModel();
            originDomainModel.setUnitId(unitId);
            originDomainModel.setUserId(userId);

            LoginModel loginModel = domainService.originDomain(originDomainModel);
            removeSession(session);
            SysUser sysUser = usersService.systemLogin(loginModel);

            //登陆成功
            if (sysUser != null) {
                isNull(CommonFainl.LOGIN, sysUser);
                session.setAttribute(GlobalConstants.SESSION_SYSTEM_USER, sysUser);
                return new JsonResult(JsonResultCode.SUCCESS, "切回到原领域成功", sysUser);
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "切回失败,请重试", "");
            }
        } catch (Exception ex) {
            log.error("[LoginController][originLogin] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "切回失败,请联系管理员", "");
        }
    }

    /**
     * 用户退出
     *
     * @return
     */
    @SystemLog(name = "用户退出")
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult logout(HttpSession session) {
        removeSession(session);
        session.invalidate();
        return new JsonResult(JsonResultCode.SUCCESS, "退出成功", "");
    }


    private void removeSession(HttpSession session) {
        //先注销当前session
        session.removeAttribute(GlobalConstants.SESSION_VALIDATE_CODE);
        //清空切换领域用户Id
        session.removeAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        //清空切换领域用户Id
        session.removeAttribute(GlobalConstants.CUTDOMAIN_USERID);
        //清空切换领域用户组织Id
        session.removeAttribute(GlobalConstants.CUTDOMAIN_USER_UNIT);
    }

    /**
     * 功能描述:  设置是否切换领域登陆的值 用于日志记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 10:22
     */
    private void isNull(String str, SysUser user) {
        DomainSwitchSession s = new DomainSwitchSession();
        if (str.equalsIgnoreCase(CommonFainl.LOGIN)) {
            s.setIsDomainSwitch(CommonFainl.FALSE);
        }
        if (str.equalsIgnoreCase(CommonFainl.NORMALLOGINUSER)) {
            s.setIsDomainSwitch(CommonFainl.NORMALLOGIN);
        }
        if (str.equalsIgnoreCase(CommonFainl.ISCUTDOMAIN)) {
            s.setIsDomainSwitch(CommonFainl.TRUE);
        }
        user.setDomainSwitchSession(s);
    }


}