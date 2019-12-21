package com.boyu.erp.platform.usercenter.controller.shop.dept;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.dept.Department;
import com.boyu.erp.platform.usercenter.entity.dept.DeptAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitOwner;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.dept.DeptSerivcer;
import com.boyu.erp.platform.usercenter.service.system.SysUnitOwnerSerivcer;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.dept.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/unit/dept")
public class DeptController extends BaseController {

    private static final String table = "department|";
    private static final String Operations = "DeptController|";
    private static final String tableName = "department";
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private DeptSerivcer deptSerivcer;
    @Autowired
    private SysUnitOwnerSerivcer unitOwnerSerivcer;
    @Autowired
    private SysUnitService unitSerivcer;

    /**
     * 部门查询
     *
     * @param department
     * @param re
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public JsonResult deptList(DepartmentVo department, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            department.setDeptStatus(CommonFainl.USER_STATUS);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, deptSerivcer.selectDept(department, user));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][selectDept] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][deptList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "部门信息" + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加部门
     *
     * @param department
     * @param re
     * @return
     */
    @SystemLog(name = "添加部门信息")
    @PostMapping(value = "/add")
    public JsonResult addDept(@RequestBody DepartmentVo department, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.ADD, department.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            if (department.getDeptId() != null) {
                Department depts = new Department();
                depts.setDeptId(department.getDeptId());
                depts.setOwnerId(department.getOwnerId());
                depts = deptSerivcer.getDepartment(depts);
                if (depts != null && depts.getOwnerId().equals(department.getOwnerId()) && CommonFainl.USER_STATUS.equalsIgnoreCase(depts.getDeptStatus())) {
                    return new JsonResult(JsonResultCode.FAILURE, "部门已存在", "");
                }
                List<SysUnitOwner> unitOwners = unitOwnerSerivcer.getListUnitOwner(department.getDeptId());
                if (CollectionUtils.isNotEmpty(unitOwners)) {
                    return new JsonResult(JsonResultCode.FAILURE, "组织不能属于多个属主", "");
                }
            }
            SysUnit unit = new SysUnit();
            unit.setUnitCode(department.getUnitCode());
            unit.setUnitName(department.getUnitName());
            if (unitSerivcer.getCodeAndName(unit,CommonFainl.ADD)) {
                return new JsonResult(JsonResultCode.FAILURE, "部门代码或部门名称已存在", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, deptSerivcer.addDept(department, user));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][addDept] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][addDept] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加部门异常 DeptController.addDept()", "");
        }
    }

    /**
     * 修改部门信息
     *
     * @param department
     * @param re
     * @return
     */
    @SystemLog(name = "修改部门信息")
    @PostMapping(value = "/update")
    public JsonResult updateDept(@RequestBody DepartmentVo department, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, department.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            SysUnit unit = new SysUnit();
            unit.setUnitCode(department.getUnitCode());
            unit.setUnitName(department.getUnitName());
            if (unitSerivcer.getCodeAndName(unit,CommonFainl.UPDATE)) {
                return new JsonResult(JsonResultCode.FAILURE, "部门代码或部门名称已存在", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, deptSerivcer.updateDept(department, user));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][selectDept] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][deptList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改部门异常 DeptController.updateDept()", "");
        }
    }

    /**
     * 打标删除部门信息
     *
     * @param department
     * @param re
     * @return
     */
    @SystemLog(name = "删除部门信息")
    @PostMapping(value = "/delete")
    public JsonResult deleteDept(@RequestBody Department department, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.DELETE, department.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, deptSerivcer.deleteDept(department, user));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][eleteDept] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][deptList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除部门异常 DeptController.deleteDept()", "");
        }
    }


    /**
     * 部门属性定义
     *
     * @param re
     * @return
     */
    @PostMapping(value = "/deptAttrDef")
    public JsonResult selectDeptAttrDef(HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(deptSerivcer.selectDeptAttrDef()));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][selectDeptAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][selectDeptAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询部门属性定义异常 DeptController.selectDeptAttrDef()", "");
        }
    }


    /**
     * 增加部门属性定义
     *
     * @param re
     * @return
     */
    @PostMapping(value = "/add/deptAttrDef")
    public JsonResult addDeptAttrDef(@RequestBody DeptAttrDef mode, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv("updateDeptAttrDef", "修改部门属性定义", user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, deptSerivcer.addDeptAttrDef(mode));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][selectDeptAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][selectDeptAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加部门属性定义异常 DeptController.addDeptAttrDef()", "");
        }
    }

    /**
     * 修改部门属性定义
     *
     * @param re
     * @return
     */
    @PostMapping(value = "/update/deptAttrDef")
    public JsonResult updateDeptAttrDef(@RequestBody DeptAttrDef mode, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv("updateDeptAttrDef", "修改部门属性定义", user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, deptSerivcer.updateDeptAttrDef(mode));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][updateDeptAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][updateDeptAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改部门属性定义异常 DeptController.updateDeptAttrDef()", "");
        }
    }

    /**
     * 删除部门属性定义
     *
     * @param re
     * @return
     */
    @PostMapping(value = "/delete/deptAttrDef")
    public JsonResult deleteDeptAttrDef(@RequestBody DeptAttrDef mode, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv("updateDeptAttrDef", "修改部门属性定义", user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, deptSerivcer.deleteDeptAttrDef(mode));
        } catch (ServiceException ex) {
            log.error("[DeptSerivcer][deleteDeptAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[DeptController][deleteDeptAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除部门属性定义异常 DeptController.deleteDeptAttrDef()", "");
        }
    }
}
