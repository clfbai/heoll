package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.*;
import com.boyu.erp.platform.usercenter.utils.PasswordUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.*;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private UsersService usersService;

    @Autowired
    private SysPrsnlService prsnlService;

    @Autowired
    private SysDomainService domainService;


    @Autowired
    private SysUnitRoleService unitRoleService;

    @Autowired
    private SysUnitPrivService unitPrivService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 登录 用户是否存在于多个领域
     *
     * @return
     */
    @RequestMapping(value = "/multipleUserDomains", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult multipleUserDomains(@RequestBody LoginModel loginModel, HttpServletRequest request) {
        try {

            SysPrsnl sysPrsnl = prsnlService.selectByPrsnlCode(loginModel.getPrsnlCode());
            List<OptionVo> optionVos = new ArrayList<>();
            //存在该用户名
            if (sysPrsnl != null) {
                List<SysUser> sysUsers = usersService.selectByUserId(new SysUser(sysPrsnl.getPrsnlId()));
                if (sysUsers != null && sysUsers.size() > 0) {
                    for (SysUser us : sysUsers) {
                        if (us.getUserStatus().equalsIgnoreCase(CommonFainl.USER_STATUS)) {
                            SysDomain sysDomain = domainService.selectByPrimaryKey(us.getOwnerId());
                            optionVos.add(new OptionVo(sysDomain.getDomainId(), sysDomain.getUnitId().toString()));
                        }
                    }
                    if (optionVos.size() > 1) {
                        return new JsonResult(JsonResultCode.SUCCESS, "成功！多领域", optionVos);
                    } else if (optionVos.size() == 1) {
                        return new JsonResult(JsonResultCode.SUCCESS, "成功！单领域", optionVos);
                    } else {
                        return new JsonResult(JsonResultCode.SUCCESS, "该用户尚未开设领域或已被禁用", optionVos);
                    }
                }
            }
            return new JsonResult(JsonResultCode.FAILURE, "用户名不存在", "");
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserController][userList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult userList(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "15") Integer size,
                               SysUser user, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            PageInfo<SysUser> pageInfo = usersService.selectAll(page, size, user);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][userList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @SystemLog(name = "添加用户")
    @RequestMapping(value = "/addUser", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addUser(@RequestBody SysUser user, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            usersService.insertSelective(user);
            return new JsonResult(JsonResultCode.SUCCESS, "添加用户成功", "");
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][addUser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加用户失败", "");
        }
    }

    /**
     * 修改密码
     *
     * @return
     */
    @SystemLog(name = "修改密码")
    @RequestMapping(value = "/changePswd", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult changePswd(@RequestBody LoginModel model, HttpServletRequest re) {
        try {
            this.isNullUser(re);
            SysUser user = new SysUser();
            user.setUserPswd(PasswordUtils.encryptionPassword(model.getUserPwd()));

            model.setUserPwd(PasswordUtils.encryptionPassword(model.getRawPwd()));
            SysUser user1 = usersService.systemByUser(model);
            if (user1 == null) {
                return new JsonResult(JsonResultCode.FAILURE, "原密码错误", "");
            }
            user.setUpdTime(new Date());
            user.setOprId(this.getSessionSysUser(re).getUserId());
            user.setUserId(user1.getUserId());
            user.setOwnerId(user1.getOwnerId());
            usersService.updatePswd(user);
            return new JsonResult(JsonResultCode.SUCCESS, "修改密码成功", "");
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][changePswd] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改密码失败", "");
        }
    }


    /**
     * 设置密码  设置密码需要用户编号及用户密码及领域
     *
     * @return
     */
    @SystemLog(name = "设置密码登陆")
    @RequestMapping(value = "/setPswd", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult setPswd(@RequestBody LoginModel Model, HttpServletRequest re) {
        try {
            this.isNullUser(re);
            SysUser user = new SysUser();
            //密码加密
            user.setUserPswd(PasswordUtils.encryptionPassword(Model.getUserPwd()));

            Model.setUserPwd("");
            SysUser user1 = usersService.systemByUser(Model);
            if (user1 == null) {
                return new JsonResult(JsonResultCode.FAILURE, Model.getDomainId() + "领域不存在用户编号【" + Model.getPrsnlCode() + "】", "");
            }

            user.setUpdTime(new Date());
            user.setOprId(this.getSessionSysUser(re).getUserId());
            user.setUserId(user1.getUserId());
            user.setOwnerId(user1.getOwnerId());
            usersService.updatePswd(user);
            return new JsonResult(JsonResultCode.SUCCESS, "设置密码成功", "");
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][changePswd] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改密码失败", "");
        }
    }

    /**
     * 修改用户 禁用用户
     *
     * @return
     */
    @SystemLog(name = "修改用户")
    @RequestMapping(value = "/changeUser", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult changeUser(@RequestBody(required = false) SysUser user, HttpServletRequest re) {
        try {
            this.isNullUser(re);
            usersService.updateUser(user);
            return new JsonResult(JsonResultCode.SUCCESS, "修改用户信息成功", "");
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][changeUser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改用户信息失败", "");
        }
    }


    /**
     * 删除用户
     */
    @SystemLog(name = "删除用户")
    @RequestMapping(value = "/deleteUser", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteUser(@RequestBody(required = false) SysUser user, HttpServletRequest re) {
        try {
            SysUser sessionSysUser = this.getSessionSysUser(re);
            user.setOprId(sessionSysUser.getUserId());
            user.setUpdTime(new Date());
            usersService.deleteUser(user);
            return new JsonResult(JsonResultCode.SUCCESS, "删除用户成功", "");
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][deleteUser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除用户失败", "");
        }
    }


    /**
     * 用户装入
     *
     * @return
     */
    @SystemLog(name = "用户装入")
    @RequestMapping(value = "/userLoader", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult userLoader(@RequestBody(required = false) List<SysUser> user, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            int i = usersService.userLoader(user);
            return new JsonResult(JsonResultCode.SUCCESS, "用户装入成功", i);
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][userLoader] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "用户装入失败", "");
        }
    }

    /**
     * 修改用户权限
     *
     * @return
     */
    @SystemLog(name = "修改用户权限")
    @RequestMapping(value = "/updateuserpriv", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateuserpriv(HttpServletRequest request, @RequestBody RolePrivModel model) {
        try {
            SysUser user = this.getSessionSysUser(request);
            int bo = 0;
            if (StringUtils.isNotBlank(model.getAdd()) && usersService.getAdmin(model.getUser()) == null && model.getUser().getOwnerId() != 1L) {
                if (model.getPrivadd().size() > unitPrivService.getUnitPrivAll(model.getUser().getOwnerId()).size()) {
                    return new JsonResult(JsonResultCode.FAILURE, "请先将权限授予用户所在组织", "");
                }
                for (SysPrivilege pg : unitPrivService.getUnitPrivAll(model.getUser().getOwnerId())) {
                    for (SysPrivilege pgs : model.getPrivadd()) {
                        if (pgs.getPrivId().equalsIgnoreCase(pg.getPrivId())) {
                            bo++;
                        }
                    }
                }
                if (bo < model.getPrivadd().size()) {
                    return new JsonResult(JsonResultCode.FAILURE, "请先将权限授予用户所在组织", "");
                }
            }

            if (user.getOwnerId().equals(model.getUser().getOwnerId()) && user.getUserId().equals(model.getUser().getUserId())) {
                return new JsonResult(JsonResultCode.FAILURE, "请联系管理员修改您的权限", "");
            }
            usersService.updateUserPriv(model, user);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "");
        }catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][updateuserpriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }


    /**
     * 修改用户角色
     *
     * @return
     */
    /**
     * 如果是用户授角色
     * 1. 查看用户组织是否拥有该角色
     * 2. 查看角色数据范围
     * 3. 判断是否能授予用户
     */
    @SystemLog(name = "修改用户角色")
    @RequestMapping(value = "/updateUserRole", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUserRole(HttpServletRequest request, @RequestBody PrivModel model) {
        try {
            SysUser user = this.getSessionSysUser(request);
            //判断不是系统管理员不能修改自己拥有的角色
            if (user.getOwnerId().equals(model.getUser().getOwnerId()) && user.getUserId().equals(model.getUser().getUserId())) {
                return new JsonResult(JsonResultCode.FAILURE, "请联系管理员修改您的角色", "");
            }
          /*  if (!user.getOwnerId().equals(model.getUser().getOwnerId())) {
                return new JsonResult(JsonResultCode.FAILURE, "不能将本组织角色授予下级", "");
            }*/

            Long unitId = model.getUser().getOwnerId();
            List<SysRole> list = model.getRoleadd() == null || model.getRoleadd().size() <= 0 ? model.getRoledelete() : model.getRoleadd();
            //1L是系统组织
            if (list != null && unitId != 1L) {
                List<SysRole> bij = new ArrayList<>();
                for (SysRole role : list) {
                    for (SysUnitRoleKey key : unitRoleService.findByUnitRole(unitId)) {
                        if (key.getRoleId().equalsIgnoreCase(role.getRoleId())) {
                            bij.add(role);
                        }
                    }
                    if (bij == null || bij.size() != list.size()) {
                        return new JsonResult(JsonResultCode.FAILURE, "请先将该角色授予该用户所在组织", "");
                    }
                }

            }
            usersService.updateUserRole(model, user);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "");
        }catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            logger.error("[UserController][updateUserRole] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * 返回是否共享 人员状态 性别 证件类型
     *
     * @return
     */
    @RequestMapping(value = "/getUserOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUserOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            List<PurKeyValue> userStatus = codeDtlService.optionGet("PRSNL_STATUS");
            map.put("shared", prodClsUtils.getList());
            map.put("prsnlStatus", userStatus);
            map.put("userStatus", userStatus);
            map.put("gender", codeDtlService.optionGet("GENDER"));
            map.put("idType", codeDtlService.optionGet("ID_TYPE"));
            map.put("machCtrl", prodClsUtils.getList());
            //目录
            map.put("menuId", codeDtlService.optionGet("menu_name"));
            //用户类别
            map.put("userType", codeDtlService.optionGet("USER_TYPE"));

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            logger.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }


}
