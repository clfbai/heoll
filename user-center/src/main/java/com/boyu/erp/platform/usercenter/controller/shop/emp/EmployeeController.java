package com.boyu.erp.platform.usercenter.controller.shop.emp;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.employee.EmplAttrDef;
import com.boyu.erp.platform.usercenter.entity.employee.Employee;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.emp.EmployeeModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.emp.EmployeeSerivcer;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.Emp.EmployeeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user/emp")
public class EmployeeController extends BaseController {
    private static final String table = "employee|";
    private static final String Operations = "EmployeeController|";
    private static final String tableName = "employee";
    @Autowired
    private EmployeeSerivcer employeeSerivcer;
    @Autowired
    private OperationAuthorityUtils privUtils;


    /**
     * 查询员工
     *
     * @param
     * @return
     */
    @GetMapping(value = "/list")
    public JsonResult empList(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                              @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                              EmployeeModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (StringUtils.NullEmpty(mode.getEmpStatus())) {
                //默认查询活动员工
                mode.setEmpStatus(CommonFainl.USER_STATUS);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, employeeSerivcer.getEmployeeList(page, size, mode, user));
        } catch (ServiceException ex) {
            log.error("[EmployeeSerivcer][getEmployeeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][empList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工:EmployeeController.empList(),Exception Null", "");
        }
    }

    /**
     * 增加员工
     *
     * @param
     * @return
     */
    @SystemLog(name = "增加员工")
    @PostMapping(value = "/add")
    public JsonResult addEmp(@RequestBody @Validated EmployeeModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.ADD, mode.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }


            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, employeeSerivcer.addEmployee(mode, user));
        } catch (ServiceException ex) {
            log.error("EmployeeSerivcer][addEmployee] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][addEmp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工异常:EmployeeController.addEmp(),Exception Null", "");
        }
    }

    @SystemLog(name = "修改员工基本信息")
    @PostMapping(value = "/update")
    public JsonResult updateEmp(@RequestBody EmployeeVo mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, mode.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            mode.setPrsnlCode(null);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, employeeSerivcer.updateEmployee(mode, user));
        } catch (ServiceException ex) {
            log.error("EmployeeSerivcer][updateEmployee] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][updateEmp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工基本信息异常:EmployeeController.updateEmp(),Exception Null", "");
        }
    }

    /**
     * 功能描述:  打标删除员工
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:22
     */
    @SystemLog(name = "删除员工")
    @PostMapping(value = "/delete")
    public JsonResult deleteEmp(@RequestBody Employee mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.DELETE, mode.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, employeeSerivcer.deleteEmployee(mode, user));
        } catch (ServiceException ex) {
            log.error("EmployeeSerivcer][deleteEmployee] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][deleteEmp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工异常:EmployeeController.deleteEmp(),Exception Null", "");
        }
    }

    /**
     * 功能描述:  员工属性定义查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:22
     */
    @PostMapping(value = "/empattldef")
    public JsonResult selectEmpAttlDef(HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(employeeSerivcer.selectEmpAttlDef()));
        } catch (ServiceException ex) {
            log.error("EmployeeSerivcer][selectEmpAttlDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][selectEmpAttlDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "员工属性定义查询异常:EmployeeController.selectEmpAttlDef(),Exception Null", "");
        }
    }

    /**
     * 功能描述:  员工属性定义增加
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:22
     */
    @PostMapping(value = "/add/empattldef")
    public JsonResult addEmpAttlDef(@RequestBody EmplAttrDef mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv("updateEmpAttrDef", "修改员工属性定义", user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, employeeSerivcer.addEmpAttrDef(mode));
        } catch (ServiceException ex) {
            log.error("EmployeeSerivcer][addEmpAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][addEmpAttlDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "员工属性定义增加异常:EmployeeController.addEmpAttlDef(),Exception", "");
        }
    }

    /**
     * 功能描述:  员工属性定义修改
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:22
     */
    @PostMapping(value = "/update/empattldef")
    public JsonResult updateEmpAttlDef(@RequestBody EmplAttrDef mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv("updateEmpAttrDef", "修改员工属性定义", user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, employeeSerivcer.updateEmpAttrDef(mode));
        } catch (ServiceException ex) {
            log.error("EmployeeSerivcer][addEmpAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][addEmpAttlDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "员工属性定义修改异常:EmployeeController.addEmpAttlDef(),Exception", "");
        }
    }

    /**
     * 功能描述:  员工属性定义删除
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:22
     */
    @PostMapping(value = "/delete/empattldef")
    public JsonResult deleteEmpAttlDef(@RequestBody EmplAttrDef mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv("updateEmpAttrDef", "修改员工属性定义", user.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, employeeSerivcer.deleteEmpAttrDef(mode));
        } catch (ServiceException ex) {
            log.error("EmployeeSerivcer][addEmpAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmployeeController][addEmpAttlDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "员工属性定义删除异常:EmployeeController.addEmpAttlDef(),Exception", "");
        }
    }
}
