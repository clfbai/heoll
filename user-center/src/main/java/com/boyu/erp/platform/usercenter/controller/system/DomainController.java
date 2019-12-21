package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.model.UnitModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysDomainService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.common.ParamDomainCode;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/domain")
public class DomainController extends BaseController {
    private static final String table = "sys_domain|";

    private static final String Operations = "DomainController|";
    @Autowired
    private OperationAuthorityUtils operationAuthorityUtils;
    @Autowired
    private SysDomainService domainService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysUnitService unitService;
    @Autowired
    private SysDomainMapper domainMapper;
    @Autowired
    private SysUnitMapper unitMapper;

    /**
     * 添加
     *
     * @param u
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addDomain(@RequestBody UnitModel u,
                                HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (!(CommonFainl.USER_STATUS.equalsIgnoreCase(user.getIsType()) || "AS".equalsIgnoreCase(user.getIsType()))) {
                return new JsonResult(JsonResultCode.FAILURE, "只有系统管理或者系统组织用户能增加领域", "");
            }
            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.ADD, user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, "你还没有增加领域的权限", "");
            }
            if (!(boolean) operationAuthorityUtils.isPriv(table, Operations, user).get("bo")) {
                return new JsonResult(JsonResultCode.FAILURE_GOODS_1, (String) operationAuthorityUtils.isPriv(table, Operations, user).get("privId"), "");
            }
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            if (!ParamDomainCode.findDomainCode(u.getUnitCode(), domainMapper, unitMapper)) {
                return new JsonResult(JsonResultCode.FAILURE, "领域Id已存在", "");
            }
            if (StringUtils.isBlank(user.getUnit().getUnitHierarchy())) {
                user.setUnit(unitService.selectByPrimaryKey(this.getSessionSysUser(request).getOwnerId()));
            }
            SysUnit unit = new SysUnit();
            BeanUtils.copyProperties(u, unit);
            if (unitService.selectCodeAndName(unit) != null) {
                return new JsonResult(JsonResultCode.FAILURE, unitService.selectCodeAndName(unit), unitService.selectCodeAndName(unit));
            }
            domainService.insertDomain(unit, user);
            return new JsonResult(JsonResultCode.SUCCESS, "添加成功", "添加成功");
        } catch (Exception ex) {
            log.error("[DomainController][addDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加失败", "");
        }
    }

    /**
     * 查询用户能看到领域
     *
     * @param domainid
     * @param request
     * @return
     */
    @RequestMapping(value = "/upselectdomain", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult upSelectDomain(@RequestBody SysDomain domainid, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            SysUnit unit = new SysUnit();
            unit.setUnitCode(domainid.getUnitCode());
            unit.setInputCode(domainid.getInputCode());
            unit.setUnitStatus(domainid.getUnitStatus());
            user.setUnit(unit);
            SysDomain domain = new SysDomain();
            domain.setDomainId(domainid.getDomainId());
            user.setDomain(domain);
            Map<String, Object> map = new HashMap<>();
            List<DomainAndUnitVo> ms = domainService.findDomianAll(user);
            map.put("size", ms == null ? 0 : ms.size());
            map.put("list", ms == null ? "" : ms);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (Exception ex) {
            log.error("[DomainController][upSelectDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 修改
     *
     * @param domainAndUnitVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateDomain(@RequestBody(required = false) DomainAndUnitVo domainAndUnitVo,
                                   HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);

            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, "你还没有修改领域的权限", "");
            }
            if (!domainAndUnitVo.getUnitStatus().equalsIgnoreCase("A")) {
                if (!(CommonFainl.USER_STATUS.equalsIgnoreCase(user.getIsType()) || "AS".equalsIgnoreCase(user.getIsType()))) {
                    return new JsonResult(JsonResultCode.FAILURE, "只有系统管理或者系统组织用户能删除领域组织", "");
                }
            }
            domainAndUnitVo.setOprId(user.getUserId());
            domainAndUnitVo.setUpdTime(new Date());
            domainService.updateDomain(domainAndUnitVo);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "修改成功");
        } catch (Exception ex) {
            log.error("[DomianController][updateDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * 删除
     *
     * @param domainAndUnitVo
     * @param request
     * @return
     */
    @SystemLog(name = "删除领域")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteDomain(@RequestBody(required = false) DomainAndUnitVo domainAndUnitVo,
                                   HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (!(CommonFainl.USER_STATUS.equalsIgnoreCase(user.getIsType()) || "AS".equalsIgnoreCase(user.getIsType()))) {
                return new JsonResult(JsonResultCode.FAILURE, "只有系统管理或者系统组织用户能删除领域", "");
            }
            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, "你还没有删除领域的权限", "");
            }
            if (!usersService.getIsAdmin(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "请联系，系统管理员删除领域", "");
            }
            if (domainAndUnitVo.getUnitId().equals(user.getOwnerId())) {
                return new JsonResult(JsonResultCode.FAILURE, "确定要删除系统领域吗?", "");
            }
            domainAndUnitVo.setOprId(user.getUserId());
            domainAndUnitVo.setUpdTime(new Date());
            int a = domainService.deleteDomain(domainAndUnitVo, this.getSessionSysUser(request));
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", a);
        } catch (Exception ex) {
            log.error("[DomainController][deleteDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除失败", "");
        }
    }

    /**
     * 查询组织的管理员
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUnitSa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult queryUnitSa(SysPrsnlVo VO, HttpServletRequest request) {
        try {
            List<SysPrsnl> sysPrsnls = domainService.queryUnitSa(VO);
            PageInfo<SysPrsnl> domainAndUnitVoPageInfo = new PageInfo<SysPrsnl>(sysPrsnls);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", domainAndUnitVoPageInfo);
        } catch (Exception ex) {
            log.error("[DomainController][queryUnitSa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 查询当前领域
     *
     * @return
     */
    @RequestMapping(value = "/selectDomain", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult selectDomain(HttpServletRequest re) {
        try {
            DomainAndUnitVo vo = new DomainAndUnitVo();
            vo.setUnitId(this.getSessionSysUser(re).getOwnerId());
            DomainAndUnitVo currentdomain = domainService.currentdomain(vo);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", currentdomain);
        } catch (Exception ex) {
            log.error("[DomainController][selectDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 查询所有(激活)领域 切换领域使用
     *
     * @return
     */
    @RequestMapping(value = "/getStatusDomain", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getStatusDomain(@RequestBody SysDomain domain, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", RestulMap.getResut(domainService.getSysDomain(domain, this.getSessionSysUser(request))));
        } catch (Exception ex) {
            log.error("[DomainController][getStatusDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 查询所有活动领域
     *
     * @return
     */
    @RequestMapping(value = "/getActivityDomain", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getIsDomain(@RequestBody SysDomain domain, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", RestulMap.getResut(domainService.getActivityDomain(domain)));
        } catch (Exception ex) {
            log.error("[DomainController][getStatusDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 查询组织是否是领域
     *
     * @return
     */
    @RequestMapping(value = "/getIsDomain", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getActivityDomain(@RequestBody SysDomain domain, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", domainService.selectByPrimaryKey(domain.getUnitId())!=null?1:0);
        } catch (Exception ex) {
            log.error("[DomainController][getStatusDomain] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


}
