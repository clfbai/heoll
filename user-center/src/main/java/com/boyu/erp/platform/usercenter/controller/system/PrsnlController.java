package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.FieldAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysPrsnlService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/prsnl")
public class PrsnlController extends BaseController {

    private static final String table = "sys_user|";

    private static final String Operations = "PrsnlController|";

    @Autowired
    private OperationAuthorityUtils operationAuthorityUtils;
    @Autowired
    private SysPrsnlService prsnlService;
    @Autowired
    private UsersService usersService;

    /**
     * 查询所有人员
     */

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult prsnlList(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "15") Integer size,
                                HttpServletRequest re,
                                SysPrsnlVo prsnl) {
        try {
            prsnl.setCtrlUnitId(this.getSessionSysUser(re).getOwnerId());
            PageInfo<SysPrsnlVo> pageInfo = prsnlService.selectPrsnlAndUser(page, size, prsnl);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][prsnlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 查询设计人员
     */

    @RequestMapping(value = "/esignd/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult designList(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re,
                                 SysPrsnl prsnl) {
        try {
            this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", prsnlService.findByDesign(page, size, prsnl));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][designList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 查询所有人员(修改)
     */

    @FieldAuthority(name = "SysPrsnl")
    @RequestMapping(value = "/all", method = {RequestMethod.POST, RequestMethod.GET})
    public Object prsnlAll(HttpServletRequest re, @RequestBody SysPrsnlVo prsnl) {
        try {
            prsnl.setCtrlUnitId(this.getSessionSysUser(re).getOwnerId());
            List<SysPrsnlVo> list = prsnlService.getPrsnlAndUser(this.getSessionSysUser(re), prsnl);

            Map<String, Object> map = new HashMap<>();
            map.put("size", list == null ? 0 : list.size());
            map.put("list", list == null ? null : list);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (Exception ex) {
            log.error("[PrsnlController][prsnlAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 查询所有人员(修改)最终版
     */
    @FieldAuthority(name = "SysPrsnl")
    @RequestMapping(value = "/getAll", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getAll(HttpServletRequest re,
                         @RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                         @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                         SysPrsnlVo prsnl) {
        try {
            SysUser user = this.getSessionSysUser(re);
            //没有传入组织筛选默认查看当前
            if (prsnl.getUnitId() == null) {
                prsnl.setUnitId(user.getOwnerId());
            }
            if (com.boyu.erp.platform.usercenter.utils.StringUtils.NullEmpty(prsnl.getUserStatus())) {
                //用户状态默认查询活动
                prsnl.setUserStatus(CommonFainl.USER_STATUS);
            }
            PageInfo<SysPrsnlVo> pageInfo = prsnlService.selectPrsnlAndUint(page, size, prsnl);
            if (!(user.getIsType().equalsIgnoreCase("A") || user.getIsType().equalsIgnoreCase("L"))) {
                //不是管理员和系统管理员需要权限
                if (prsnl.getUnitId() == null) {
                    prsnl.setOwnerId(user.getOwnerId());
                }
                PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.SELECT, prsnl.getUnitId(), user);
                //没查看权限只能查看当前登录用户
                if (!examine.getPrivBoolean()) {
                    List<SysPrsnlVo> list = pageInfo.getList();
                    List<SysPrsnlVo> rest = new ArrayList<>();
                    for (SysPrsnlVo v : list) {
                        if (v.getUserId().equals(user.getUserId())) {
                            rest.add(v);
                        }
                    }
                    pageInfo.setList(rest);
                    return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PrsnlController][getAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败用户信息失败 PrsnlController.getAll() Exception Null", "");
        }
    }


    /**
     * 单纯查人员
     */
    @RequestMapping(value = "/getprsnlList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getprsnlList(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "12") Integer size,
                                   HttpServletRequest re,
                                   SysPrsnl prsnl) {
        try {
            if (prsnl.getCtrlUnitId() == null) {
                prsnl.setCtrlUnitId(this.getSessionSysUser(re).getOwnerId());
            }
            PageInfo<SysPrsnl> pageInfo = prsnlService.selectAll(page, size, prsnl);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][getprsnlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }

    }

    /**
     * 供应商中查人员
     */
    @RequestMapping(value = "/getprsnlListAll", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getprsnlListAll(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "1000") Integer size,
                                      HttpServletRequest re,
                                      SysPrsnl prsnl) {
        try {
            this.isNullUser(re);
            PageInfo<SysPrsnl> pageInfo = prsnlService.selectAll(page, size, prsnl);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][getprsnlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }

    }


    /**
     * 根据UserId查询
     */
    @RequestMapping(value = "/prsnlAndUserById", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult prsnlAndUserById(@RequestBody(required = false) SysPrsnlVo prsnl, HttpServletRequest re) {
        try {
            SysUser sessionSysUser = this.getSessionSysUser(re);
            if (prsnl == null || prsnl.getUserId() == null || (StringUtils.isBlank(prsnl.getUserId().toString()))) {
                prsnl = new SysPrsnlVo();
                prsnl.setUserId(sessionSysUser.getUserId());
                prsnl.setPrsnlId(sessionSysUser.getUserId());
            }
            SysPrsnlVo sysPrsnlVo = prsnlService.selectPrsnlAndUserByid(prsnl);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", sysPrsnlVo);
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][prsnlList] prsnlAndUserById", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 修改人员信息
     */
    @SystemLog(name = "修改人员信息")
    @RequestMapping(value = "/changePrsnl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult changePrsnl(@RequestBody(required = false) SysPrsnlVo prsnl, HttpServletRequest re) {
        try {
            SysUser user = this.getSessionSysUser(re);
            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, prsnl.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            SysUser sessionSysUser = this.getSessionSysUser(re);
            prsnlService.changePrsnl(prsnl, sessionSysUser);
            return new JsonResult(JsonResultCode.SUCCESS, "修改人员信息成功", "");
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][changePrsnl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改人员信息失败", "");
        }
    }


    /**
     * 添加人员信息
     */
    @SystemLog(name = "添加人员信息")
    @RequestMapping(value = "/newLyPrsnl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult newLyPrsnl(@RequestBody(required = false) SysPrsnlVo prsnl, HttpServletRequest re) {
        try {
            SysUser user = this.getSessionSysUser(re);

            String prsnlCode = prsnl.getPrsnlCode();
            SysPrsnl sysPrsnl = prsnlService.selectByPrsnlCode(prsnlCode);

            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.ADD, prsnl.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            if (sysPrsnl != null) {
                return new JsonResult(JsonResultCode.FAILURE, "人员代码已存在", "");
            }

            if (StringUtils.isEmpty(prsnl.getPrsnlStatus())) {
                return new JsonResult(JsonResultCode.FAILURE, "请选择人员状态", "");
            }
            if (StringUtils.isEmpty(prsnl.getUserStatus())) {
                return new JsonResult(JsonResultCode.FAILURE, "请选择用户状态", "");
            }
            SysUser sessionSysUser = this.getSessionSysUser(re);
            prsnlService.insertPrsnl(prsnl, sessionSysUser);
            return new JsonResult(JsonResultCode.SUCCESS, "添加人员信息成功", "");
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][newLyPrsnl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加人员信息失败", "");
        }
    }


    /**
     * 用户装入查询人员
     */

    @RequestMapping(value = "/userList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult userList(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "15") Integer size,
                               HttpServletRequest re,
                               SysPrsnlVo prsnl) {
        try {
            if (prsnl.getUnitId() == null || prsnl.getUnitId() == 0L) {
                //默认查询当前用户组织下用户
                prsnl.setUnitId(this.getSessionSysUser(re).getOwnerId());
            }
            if (StringUtils.isBlank(prsnl.getUserStatus())) {
                //默认查询删除用户
                prsnl.setUserStatus(CommonFainl.FILAN_STATUS);
            }
            PageInfo<SysPrsnlVo> pageInfo = prsnlService.selectPrsnlUserList(page, size, prsnl);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrsnlController][prsnlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

}
