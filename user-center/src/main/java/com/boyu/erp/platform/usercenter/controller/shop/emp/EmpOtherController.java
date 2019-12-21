package com.boyu.erp.platform.usercenter.controller.shop.emp;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.emp.EmpOtherModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.emp.EmplFamMemSerivcer;
import com.boyu.erp.platform.usercenter.service.emp.EmplRwdPunSerivcer;
import com.boyu.erp.platform.usercenter.service.emp.EmplWorkExpSerivcer;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述:
 *
 * @Description: 员工其他信息查询修改
 * @auther: CLF
 * @date: 2019/9/5 10:47
 */
@Slf4j
@RestController
@RequestMapping("/user/emp/other")
public class EmpOtherController extends BaseController {
    @Autowired
    private EmplRwdPunSerivcer emplRwdPunSerivcer;
    @Autowired
    private EmplWorkExpSerivcer emplWorkExpSerivcer;
    @Autowired
    private EmplFamMemSerivcer emplFamMemSerivcer;

    /**
     * 功能描述: 查询员工奖惩记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmpRwdPunlist")
    public JsonResult seelctEmpRwdPun(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplRwdPunSerivcer.selectEmplRwdPun(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][selectEmplRwdPun] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmpRwdPun] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工奖惩记录异常:EmployeeController.deleteEmp(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工奖惩记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmpRwdPun")
    public JsonResult addEmpRwdPun(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (mode.getAddEmplRwdPun().size() > 1) {
                emplRwdPunSerivcer.addEmplRwdPunList(mode.getAddEmplRwdPun(), user);
            }
            if (mode.getAddEmplRwdPun().size() == 1) {
                emplRwdPunSerivcer.addEmplRwdPun(mode.getAddEmplRwdPun().get(0), user);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, "");
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplRwdPunList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmpRwdPun] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工奖惩记录异常:EmployeeController.addEmpRwdPun(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工奖惩记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmpRwdPun")
    public JsonResult updateEmpRwdPun(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (mode.getUpdateEmplRwdPun().size() > 1) {
                emplRwdPunSerivcer.updateEmplRwdPunList(mode.getUpdateEmplRwdPun(), user);
            }
            if (mode.getUpdateEmplRwdPun().size() == 1) {
                emplRwdPunSerivcer.updateEmplRwdPun(mode.getUpdateEmplRwdPun().get(0), user);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, "");
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplRwdPunList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][updateEmpRwdPun] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工奖惩记录异常:EmployeeController.updateEmpRwdPun(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工奖惩记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmpRwdPun")
    public JsonResult deleteEmpRwdPun(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, emplRwdPunSerivcer.deleteEmplRwdPun(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplRwdPunList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][updateEmpRwdPun] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工奖惩记录异常:EmployeeController.deleteEmpRwdPun(),Exception Null", "");
        }
    }


    /**
     * 功能描述: 查询员工家庭成员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmplFamMemlist")
    public JsonResult seelctEmplFamMem(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplFamMemSerivcer.selectEmplFamMemList(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][selectEmplRwdPun] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmpRwdPun] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工家庭成员异常:EmployeeController.seelctEmplFamMem(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工家庭成员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmplFamMem")
    public JsonResult addEmplFamMem(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (mode.getAddEmplFamMem().size() > 1) {
                emplFamMemSerivcer.addEmplFamMemList(mode.getAddEmplFamMem(), user);
            }
            if (mode.getAddEmplFamMem().size() == 1) {
                emplFamMemSerivcer.addEmplFamMem(mode.getAddEmplFamMem().get(0), user);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, "");
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplRwdPunList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmpRwdPun] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工家庭成员异常:EmployeeController.addEmplFamMem(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工家庭成员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmplFamMem")
    public JsonResult updateEmplFamMem(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (mode.getUpdateEmplFamMem().size() > 1) {
                emplFamMemSerivcer.updateEmplFamMemList(mode.getUpdateEmplFamMem(), user);
            }
            if (mode.getUpdateEmplFamMem().size() == 1) {
                emplFamMemSerivcer.updateEmplFamMem(mode.getUpdateEmplFamMem().get(0), user);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, "");
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplFamMemList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][updateEmplFamMem] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工家庭成员异常:EmployeeController.updateEmplFamMem(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工家庭成员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmplFamMem")
    public JsonResult deleteEmplFamMem(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, emplFamMemSerivcer.deleteFamMem(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][deleteFamMem] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][deleteEmplFamMem] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工家庭成员异常:EmployeeController.deleteEmplFamMem(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 查询员工履历集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmplWorkExplist")
    public JsonResult seelctEmplWorkExp(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplWorkExpSerivcer.selectEmplWorkExp(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][selectEmplRwdPun] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工履历异常:EmployeeController.seelctEmplWorkExp(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工履历
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmplWorkExp")
    public JsonResult addEmplWorkExp(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (mode.getAddEmplWorkExp().size() > 1) {
                emplWorkExpSerivcer.addEmplEmplWorkExpList(mode.getAddEmplWorkExp(), user);
            }
            if (mode.getAddEmplWorkExp().size() == 1) {
                emplWorkExpSerivcer.addEmplWorkExp(mode.getAddEmplWorkExp().get(0), user);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工履历异常:EmployeeController.addEmplWorkExp(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工履历
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmplWorkExp")
    public JsonResult updateEmplWorkExp(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (mode.getUpdateEmplWorkExp().size() > 1) {
                emplWorkExpSerivcer.updateEmplWorkExpList(mode.getUpdateEmplWorkExp(), user);
            }
            if (mode.getUpdateEmplWorkExp().size() == 1) {
                emplWorkExpSerivcer.updateEmplWorkExp(mode.getUpdateEmplWorkExp().get(0), user);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工履历异常:EmployeeController.updateEmplWorkExp(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工履历
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmplWorkExp")
    public JsonResult deleteEmplWorkExp(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, emplWorkExpSerivcer.deleteEmplWorkExp(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工履历异常:EmployeeController.deleteEmplWorkExp((),Exception Null", "");
        }
    }


    /**
     * 功能描述: 查询员工异动集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmplChglist")
    public JsonResult seelctEmplChg(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplWorkExpSerivcer.selectEmplChg(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][selectEmplRwdPun] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工异动异常:EmployeeController.seelctEmplWorkExp(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工异动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmplChg")
    public JsonResult addEmplChg(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.addEmplChg(mode.getAddEmplChg(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工异动异常:EmployeeController.addEmplChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工异动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmplChg")
    public JsonResult updateEmplChg(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.updateEmplChg(mode.getUpdateEmplChg(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工异动异常:EmployeeController.updateEmplChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工异动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmplChg")
    public JsonResult deleteEmplChg(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, emplWorkExpSerivcer.deleteEmplChg(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工履历异常:EmployeeController.deleteEmplChg((),Exception Null", "");
        }
    }


    /**
     * 功能描述: 查询员工教育经历集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmplEduHlist")
    public JsonResult seelctEmplEduH(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplWorkExpSerivcer.selectEmplEduH(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][selectEmplRwdPun] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工教育异常:EmployeeController.seelctEmplWorkExp(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工教育经历集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmplEduH")
    public JsonResult addEmplEduH(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.addEmplEduH(mode.getAddEmplEduH(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工教育经历集合异常:EmployeeController.addEmplChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工教育经历集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmplEduH")
    public JsonResult updateEmplEduH(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.updateEmplEduH(mode.getAddEmplEduH(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplWorkExp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][updateEmplEduH] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工教育经历集合异常:EmployeeController.EmplEduH(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工教育经历集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmplEduH")
    public JsonResult deleteEmplEduH(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, emplWorkExpSerivcer.deleteEmplEduH(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][deleteEmplEduH] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][deleteEmplEduH] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工教育经历集合异常:EmployeeController.deleteEmplEduH((),Exception Null", "");
        }
    }


    /**
     * 功能描述: 查询员考核集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmplEvallist")
    public JsonResult seelctEmplEval(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplWorkExpSerivcer.selectEmplEval(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][selectEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工教育异常:EmployeeController.seelctEmplWorkExp(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工考核
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmplEval")
    public JsonResult addEmplEval(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.addEmplEval(mode.getAddEmplEval(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工考核异常:EmployeeController.addEmplEval(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工考核
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmplEval")
    public JsonResult updateEmplEval(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.updateEmplEval(mode.getUpdateEmplEval(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工考核异常:EmployeeController.updateEmplEval(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工考核
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmplEval")
    public JsonResult deleteEmplEval(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, emplWorkExpSerivcer.deleteEmplEval(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][deleteEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][deleteEmplEval] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工教育经历集合异常:EmployeeController.deleteEmplEval(),Exception Null", "");
        }
    }



    /**
     * 功能描述: 查询员工薪资变动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmplSalChglist")
    public JsonResult seelctEmplSalChg(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplWorkExpSerivcer.selectEmplSalChg(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][seelctEmplSalChg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmplSalChg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工薪资变动异常:EmployeeController.seelctEmplSalChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工薪资变动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmplSalChg")
    public JsonResult addEmplSalChg(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.addEmplSalChg(mode.getAddEmplSalChg(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工薪资变动异常:EmployeeController.addEmplSalChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工薪资变动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmplSalChg")
    public JsonResult updateEmplSalChg(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.updateEmplSalChg(mode.getUpdateEmplSalChg(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplSalChg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][updateEmplSalChg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工薪资变动异常:EmployeeController.updateEmplSalChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工薪资变动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmplSalChg")
    public JsonResult deleteEmplSalChg(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, emplWorkExpSerivcer.deleteEmplSalChg(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][deleteEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][deleteEmplSalChg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工薪资变动异常:EmployeeController.deleteEmplSalChg(),Exception Null", "");
        }
    }


    /**
     * 功能描述: 查询员工调职记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/EmplPosTflist")
    public JsonResult seelctEmplPosTf(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(emplWorkExpSerivcer.selectEmplPosTf(mode)));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][seelctEmplSalChg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][seelctEmplSalChg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询员工调职记录异常:EmployeeController.seelctEmplPosTf(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 增加员工调职记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/addEmplPosTf")
    public JsonResult addEmplPosTf(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.addEmplPosTf(mode.getAddEmplPosTf(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][addEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][addEmplWorkExp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加员工薪资变动异常:EmployeeController.addEmplSalChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 修改员工调职记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/updateEmplPosTf")
    public JsonResult updateEmplPosTf(@RequestBody EmpOtherModel mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            emplWorkExpSerivcer.updateEmplPosTf(mode.getUpdateEmplPosTf(), user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, 0);
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][updateEmplSalChg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][updateEmplSalChg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改员工薪资变动异常:EmployeeController.updateEmplSalChg(),Exception Null", "");
        }
    }

    /**
     * 功能描述: 删除员工调职记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:09
     */
    @PostMapping(value = "/deleteEmplPosTf")
    public JsonResult deleteEmplPosTf(@RequestBody EmpKey mode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, emplWorkExpSerivcer.deleteEmplPosTf(mode, user));
        } catch (ServiceException ex) {
            log.error("EmplRwdPunSerivcer][deleteEmplEval] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[EmpOtherController][deleteEmplSalChg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除员工薪资变动异常:EmployeeController.deleteEmplPosTf(),Exception Null", "");
        }
    }

}
