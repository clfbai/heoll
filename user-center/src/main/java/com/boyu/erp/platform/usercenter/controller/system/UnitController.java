package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.model.UserModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.system.UnitTreeVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UnitController extends BaseController {

    @Autowired
    private SysUnitService unitService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysCodeDtlService codeDtlService;

    /**
     * 查询组织信息
     *
     * @param unit
     * @return
     */
    @RequestMapping(value = "/unit/unitList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitList(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "15") Integer size,
                               HttpServletRequest re,
                               SysUnit unit) {
        try {
            if (unit.getCtrlUnitId() == null) {
                //查询当前用户能看到的组织
                unit.setCtrlUnitId(this.getSessionSysUser(re).getOwnerId());
            }
            PageInfo<DomainAndUnitVo> pageInfo = unitService.selectAll(page, size, unit);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (Exception ex) {
            log.error("[UnitController][unitList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 功能描述: 组织树形结构查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 14:26
     */
    @PostMapping(value = "/unit/tree")
    public JsonResult unitTree(HttpServletRequest re, SysUnit unit) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            //查询当前用户能看到组织树形结构
            List<UnitTreeVo> list = new ArrayList<>();

            list.add(unitService.getUnitTree(unit));
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", list);
        } catch (Exception ex) {
            log.error("[UnitController]unitTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 功能描述: 组织树形结构查询
     * 查询相应类型的组织层级信息
     *
     * @param:
     * @return:
     * @auther: wz
     * @date: 2019/8/30 16:34
     */
    @PostMapping(value = "/unit/treeByType")
    public JsonResult treeByType(HttpServletRequest re, SysUnit unit) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            //查询当前用户能看到组织树形结构
            List<UnitTreeVo> list = new ArrayList<>();

            list.add(unitService.getUnitTreeByType(unit));
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", list);
        } catch (Exception ex) {
            log.error("[UnitController]unitTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 供应商组织查询
     *
     * @param unit
     * @return
     */
    @RequestMapping(value = "/vender/unitList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult venderUnitList(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                     @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                     HttpServletRequest re,
                                     SysUnit unit) {
        try {
            unit.setUnitId(this.getSessionSysUser(re).getOwnerId());
            PageInfo<SysUnit> pageInfo = unitService.findAll(page, size, unit);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (Exception ex) {
            log.error("[UnitController][venderUnitList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 厂商和设计方组织查询
     *
     * @param unit
     * @return
     */
    @RequestMapping(value = "/unitMrft/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitMrftAll(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                  @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                  HttpServletRequest re,
                                  SysUnit unit) {
        try {
            unit.setUnitId(this.getSessionSysUser(re).getOwnerId());
            PageInfo<SysUnit> pageInfo = unitService.getAll(page, size, unit);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (Exception ex) {
            log.error("[UnitController][unitMrftAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 新增组织  暂时没用到
     *
     * @return 说明: 1. 新增 sys_domain 表
     * 2. 新增 sys_unit 表
     * 3. 新增 sys_psnl
     * 4. 新增 sys_user
     * 5. 新增 sys_user_qs
     */
    @SystemLog(name = "新增组织 ")
    @RequestMapping(value = "/unit/newLyUnit", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult newLyUnit(@RequestBody(required = false) SysUnit unit, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            unitService.insertUnit(unit, user);
            return new JsonResult(JsonResultCode.SUCCESS, "添加组织成功", "");
        } catch (Exception ex) {
            log.error("[UnitController][newLyUnit] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加组织失败", "");
        }
    }

    /**
     * 修改组织信息 删除打标 暂时没用到
     *
     * @param unit
     * @return
     */
    @SystemLog(name = "修改组织信息 ")
    @RequestMapping(value = "/unit/changeUnit", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult changeUnit(@RequestBody SysUnit unit) {
        try {
            unitService.updateUnit(unit);
            return new JsonResult(JsonResultCode.SUCCESS, "修改组织成功", "");
        } catch (Exception ex) {
            log.error("[UnitController][changeUnit] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改组织失败", "");
        }

    }

    /**
     * 判断是否是本组织下用户
     *
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/unit/subordinateUser", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult changeUnit(@RequestBody UserModel userModel, HttpServletRequest request) {
        try {
            List<UserModel> list = unitService.getUnitUser(this.getSessionSysUser(request).getOwnerId());
            String str = CommonFainl.FALSE;
            for (UserModel user : list) {
                if (user.getUserId().equals(userModel.getUserId())) {
                    str = CommonFainl.TRUE;
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", str);
        } catch (Exception ex) {
            log.error("[UnitController][changeUnit] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }

    }

    /**
     * 下拉框数据
     */
    @RequestMapping(value = "/unit/getUnitOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUnitOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            List<PurKeyValue> list = codeDtlService.optionGet("UNIT_STATUS");
            //执照类别
            map.put("licType", codeDtlService.optionGet("LIC_TYPE"));
            //是否共享
            map.put("shared", prodClsUtils.getList());
            //可征募
            map.put("recruitable", prodClsUtils.getList());
            //组织状态
            map.put("unitStatus", list);
            //领域状态
            map.put("domainStatus", list);
            //部门状态
            map.put("deptStatus", list);
            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][getUnitOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败 PurchaseVenderController.getUnitOption()", "");
        }
    }

    /**
     * @return 组织弹窗
     */
    @RequestMapping(value = "/vender/unitOption", method = {RequestMethod.GET})
    public JsonResult unitOption(UnitOptionVo vo, @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            PageInfo<UnitOptionVo> list = null;
//            if (StringUtils.isNotEmpty(vo.getUnitType())) {
//                listgoods = unitService.selectByOption(vo, page, size, sysUser);
            list = unitService.selectByOptionCode(vo, page, size, sysUser);

//            } else {
//                listgoods = unitService.selectByOptionAll(vo, page, size);
//            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", list);
        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][unitOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "弹窗异常");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][unitOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 功能描述: 通过用户属主Id查询能看到组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 14:26
     */
    @GetMapping(value = "/unit/ownerid")
    public JsonResult unitOwnerid(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                  @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                  SysUnit unit) {
        try {
            Long ownerId = unit.getUnitId();
            unit.setUnitHierarchy(unitService.selectByPrimaryKey(ownerId).getUnitHierarchy());

            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", unitService.selectPageInfoHierarchy(page, size, unit));
        } catch (Exception ex) {
            log.error("[UnitController]unitTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败UnitController.unitOwnerid(),Exception Null", "");
        }
    }


    /**
     * 功能描述: 通过组织Id(用户属主Id)查询所有下级组织不区分删除组织
     * 主要是给前天过滤组织层级选择同
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 14:26
     */
    @PostMapping(value = "/unit/owneridAll")
    public JsonResult unitOwneridAll(@RequestBody SysUnit unit) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", RestulMap.getResut(unitMapper.getUnitId(unit.getUnitId())));
        } catch (Exception ex) {
            log.error("[UnitController]unitTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败UnitController.unitOwnerid(),Exception Null", "");
        }
    }


}