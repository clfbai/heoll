package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysDomainSwitch;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.SysDomainSwitchModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysDomainSwitchService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

/**
 * @program: boyu-platform
 * @description: 切换领域控制层(管理员切换领域)
 * @author: clf
 * @create: 2019-06-04 11:52
 */
@Slf4j
@RestController
@RequestMapping("/user/cutDomain")
public class DomainSwitchController extends BaseController {
    @Autowired
    private SysDomainSwitchService domainSwitchService;
    @Autowired
    private UsersService usersService;

    /**
     * @param : domainSwitch
     * @description: 查询管理员能切换的领域
     * @author: CLF
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getCutDomainList(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "15") Integer size,
                                       SysDomainSwitch domainSwitch,
                                       HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PageInfo<SysDomain> list = domainSwitchService.getList(page, size, domainSwitch, user);
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", list);
        } catch (Exception ex) {
            log.error("[DomainSwitchController][getCutDomainList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败,请联系管理员", "");
        }
    }

    /**
     * @param : domainSwitch
     * @description: 查询组织已授予切换领域列表
     * @author: CLF
     */
    @RequestMapping(value = "/setList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult setCutDomainList(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "15") Integer size,
                                       SysDomainSwitch domainSwitch,
                                       HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (usersService.getAdmin(user) == null) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", new ArrayList<>());
            }
            PageInfo<SysDomain> list = domainSwitchService.setCutDomainList(page, size, domainSwitch, user);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", list);
        } catch (Exception ex) {
            log.error("[DomainSwitchController][setCutDomainList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询切换领域失败,请联系管理员", "");
        }
    }


    /**
     * 授权切换领域
     */
    @SystemLog(name = CommonFainl.CUT_DOMINA_ADD)
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addCutDomainList(@RequestBody SysDomainSwitchModel domainSwitch, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (usersService.getAdmin(user) == null) {
                return new JsonResult(JsonResultCode.FAILURE, "如有需要请联系组织管理进行该项操作", "");
            }
            SysDomainSwitch sysDomainSwitch = new SysDomainSwitch();
            BeanUtils.copyProperties(domainSwitch, sysDomainSwitch);
            sysDomainSwitch.setDomainAccreditId(user.getOwnerId());
            sysDomainSwitch.setCreateBy(user.getUserId());
            sysDomainSwitch.setIsDel(CommonFainl.BTYPESTATUS);
            sysDomainSwitch.setCreateTime(new Date());
            sysDomainSwitch.setDomainUserId(domainSwitch.getUserId());
            log.info("添加对象:    " + sysDomainSwitch);
            int a = domainSwitchService.addDomainSwitchService(sysDomainSwitch);
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", a);
        } catch (Exception ex) {
            log.error("[DomainSwitchController][addCutDomainList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加失败", "");
        }
    }

    @SystemLog(name = CommonFainl.CUT_DOMINA_UPDAT)
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateCutDomainList(@RequestBody SysDomainSwitchModel domainSwitch, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            SysUser adminUser = usersService.getAdmin(user);
            if (adminUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "如有需要请联系组织管理进行该项操作", "");
            }
            SysDomainSwitch domain = new SysDomainSwitch();

            domain.setDomainUserId(domainSwitch.getUserId());
            domain.setDomainAccreditId(user.getOwnerId());
            domain.setDomainPresentId(domainSwitch.getDomainPresentId());

            if (!adminUser.getOwnerId().equals(domainSwitchService.findById(domain).getDomainAccreditId()) || !adminUser.getUserId().equals(domainSwitchService.findById(domain).getCreateBy())) {
                return new JsonResult(JsonResultCode.FAILURE, "你不能修改其他组织切换领域关系", "");
            }
            domainSwitch.setDomainAccreditId(user.getOwnerId());
            domainSwitch.setUpdateBy(user.getUserId());
            domainSwitch.setUpdateTime(new Date());
            System.out.println("       " + domainSwitch);
            int a = domainSwitchService.updateDomainSwitchService(domainSwitch);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", a);
        } catch (ServiceException ex) {
            log.error("[DomainSwitchService][updateDomainSwitchService] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DomainSwitchController][updateCutDomainList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    @SystemLog(name = CommonFainl.CUT_DOMINA_DELETE)
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteDomainList(@RequestBody SysDomainSwitchModel domainSwitch, HttpServletRequest request) {
        try {
            SysDomainSwitch sysDomainSwitch = new SysDomainSwitch();
            BeanUtils.copyProperties(domainSwitch, sysDomainSwitch);
            sysDomainSwitch.setDomainUserId(domainSwitch.getUserId());
            sysDomainSwitch.setDomainAccreditId((this.isNullUser(request)).getOwnerId());
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", domainSwitchService.deleteDomainSwitchService(sysDomainSwitch));
        } catch (ServiceException ex) {
            log.error("[DomainSwitchService][updateDomainSwitchService] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DomainSwitchController][deleteDomainList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败,请联系管理员", "");
        }
    }
}
