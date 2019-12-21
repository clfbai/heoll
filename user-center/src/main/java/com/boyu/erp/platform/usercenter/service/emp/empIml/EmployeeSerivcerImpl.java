package com.boyu.erp.platform.usercenter.service.emp.empIml;

import com.boyu.erp.platform.usercenter.entity.employee.*;
import com.boyu.erp.platform.usercenter.entity.shop.ShopEmp;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnlOwner;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.employye.*;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysPrsnlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysPrsnlOwnerMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.model.emp.EmployeeModel;
import com.boyu.erp.platform.usercenter.service.base.RabbitTemplateSerivce;
import com.boyu.erp.platform.usercenter.service.emp.EmpMsgSerivcer;
import com.boyu.erp.platform.usercenter.service.emp.EmployeeSerivcer;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.BaseDateUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.Emp.EmployeeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EmployeeSerivcerImpl implements EmployeeSerivcer {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmplRwdPunMapper emplRwdPunMapper;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysPrsnlMapper sysPrsnlMapper;
    @Autowired
    private SysPrsnlOwnerMapper prsnlOwnerMapper;
    @Autowired
    private EmplFamMemMapper emplFamMemMapper;
    @Autowired
    private EmplWorkExpMapper emplWorkExpMapper;
    @Autowired
    private EmplChgMapper emplChgMapper;
    @Autowired
    private EmplEduHMapper emplEduHMapper;
    @Autowired
    private EmplEvalMapper emplEvalMapper;
    @Autowired
    private EmplSalChgMapper emplSalChgMapper;
    @Autowired
    private EmplPosTfMapper emplPosTfMapper;
    @Autowired
    private SysPrsnlMapper prsnlMapper;

    @Autowired
    private EmplAttrDefMapper emplAttrDefMapper;

    @Autowired
    private RabbitTemplateSerivce templateSerivce;
    @Autowired
    private EmpMsgSerivcer empMsgSerivcer;

    @Override
    public PageInfo<EmployeeVo> getEmployeeList(Integer page, Integer size, EmployeeModel model, SysUser user) {
        PageHelper.startPage(page, size);
        List<EmployeeVo> list = employeeMapper.getEmpList(model);
        PageInfo<EmployeeVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 功能描述: 增加员工 分为从已有人员扩展为员工，和添加新人员为员工
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/4 12:15
     */
    @Override
    public int addEmployee(EmployeeModel mode, SysUser user) throws Exception {
        if (StringUtils.NullEmpty(String.valueOf(mode.getEmpvo().getEmplId()))) {
            String prsnlCode = mode.getEmpvo().getPrsnlCode();
            if (StringUtils.NullEmpty(prsnlCode)) {
                throw new ServiceException("403", "人员代码不能为空");
            }
            if (sysPrsnlMapper.selectByPrsnlCode(prsnlCode) != null) {
                throw new ServiceException("403", "人员代码已存在");
            }
        }
        EmployeeVo employeeVo = mode.getEmpvo();
        SysPrsnl prsnl = new SysPrsnl();
        BeanUtils.copyProperties(employeeVo, prsnl);
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVo, employee);
        Long empId = null;
        if (employeeVo.getEmplId() != null) {
            empId = employeeVo.getEmplId();
        } else {
            prsnl.setOprId(user.getUserId());
            prsnl.setUpdTime(new Date());
            //人员层级
            prsnl.setUnitHierarchy(unitMapper.selectByPrimaryKey(employee.getOwnerId()).getUnitHierarchy());
            prsnl.setCtrlUnitId(employeeVo.getOwnerId());
            //新增人员为员工
            sysPrsnlMapper.insertPrsnl(prsnl);
            empId = prsnl.getPrsnlId();
        }
        employee.setEmplId(empId);
        employee.setUpdTime(new Date());
        employee.setOprId(user.getOprId());
        int a = employeeMapper.insertSelective(employee);
        SysPrsnlOwner prsnlOwner = new SysPrsnlOwner();
        prsnlOwner.setPrsnlId(empId);
        prsnlOwner.setOwnerId(employeeVo.getOwnerId());
        prsnlOwner.setPrsnlNum(employeeVo.getEmpNum());
        if (prsnlOwnerMapper.selectByPrimaryKey(prsnlOwner) != null) {
            prsnlOwner.setIsDel(CommonFainl.BTYPESTATUS);
            prsnlOwner.setUpdateTime(new Date());
            a += prsnlOwnerMapper.updateByPrimaryKeySelective(prsnlOwner);
        } else {
            BaseDateUtils.setBeasDate(prsnlOwner, user, CommonFainl.ADD);
            a += prsnlOwnerMapper.insertSelective(prsnlOwner);
        }
        mode.getEmpvo().setEmplId(empId);
        insetrOther(mode, user);
        return a;
    }

    /**
     * 功能描述:  修改员工基本信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:01
     */
    @Override
    public int updateEmployee(EmployeeVo mode, SysUser user) {
        SysPrsnl prsnl = new SysPrsnl();
        Employee emp = new Employee();
        BeanUtils.copyProperties(mode, prsnl);
        BeanUtils.copyProperties(mode, emp);
        prsnl.setUnitHierarchy(null);
        int a = employeeMapper.updateEmployee(emp);
        a += prsnlMapper.updateByPrimaryKeySelective(prsnl);
        if (StringUtils.isNotEmpty(mode.getEmpNum())) {
            SysPrsnlOwner prsnlOwner = new SysPrsnlOwner();
            prsnlOwner.setPrsnlId(emp.getEmplId());
            prsnlOwner.setOwnerId(emp.getOwnerId());
            prsnlOwner.setPrsnlNum(mode.getEmpNum());
            a += prsnlOwnerMapper.updateByPrimaryKeySelective(prsnlOwner);
        }
        if ("A".equalsIgnoreCase(mode.getUpdatePrsnlStatus()) && !"A".equalsIgnoreCase(mode.getPrsnlStatus())) {
            //推送冻结消息
            if (a > 0) {
                setSendEmp(mode.getEmplId());
            }
        }
        return a;
    }

    /**
     * 功能描述:  删除员工
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:25
     */
    @Override
    public int deleteEmployee(Employee mode, SysUser user) {
        mode.setEmplStatus(CommonFainl.FILAN_STATUS);
        int a = employeeMapper.updateEmployee(mode);
        if (a > 0) {
            //推送冻结消息
            setSendEmp(mode.getEmplId());
        }
        return a;
    }

    /**
     * 功能描述: 推送冻结导购消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/11 16:45
     */
    private void setSendEmp(Long emplId) {
        //推送导购冻结消息
        ShopEmp key = new ShopEmp();
        key.setEmplId(emplId);
        templateSerivce.send(empMsgSerivcer.getFreezeEmp(key));
    }

    @Override
    public List<EmplAttrDef> selectEmpAttlDef() {
        return emplAttrDefMapper.getList();
    }

    /**
     * 功能描述: 增加员工定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/30 14:20
     */
    @Override
    public int addEmpAttrDef(EmplAttrDef mode) {
        return emplAttrDefMapper.insertSelective(mode);
    }

    @Override
    public int updateEmpAttrDef(EmplAttrDef mode) {
        return emplAttrDefMapper.updateByPrimaryKeySelective(mode);
    }

    @Override
    public int deleteEmpAttrDef(EmplAttrDef mode) {
        return emplAttrDefMapper.deleteByPrimaryKey(mode.getAttrType());
    }


    private void insetrOther(EmployeeModel mode, SysUser user) {
        /*员工奖惩对象集合*/
        if (CollectionUtils.isNotEmpty(mode.getAddEmplRwdPun())) {
            for (EmplRwdPun p : mode.getAddEmplRwdPun()) {
                if (StringUtils.isNotEmpty(p.getRwdPunType()) && p.getRwdPunDate() != null) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    emplRwdPunMapper.insertSelective(p);
                }

            }
        }/*家庭成员*/
        if (CollectionUtils.isNotEmpty(mode.getAddEmplFamMem())) {
            for (EmplFamMem p : mode.getAddEmplFamMem()) {
                String name = p.getFullName();
                if (StringUtils.isNotEmpty(name)) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    p.setFullName(name);
                    emplFamMemMapper.insert(p);
                }
            }
        }/*员工履历对象集合*/
        if (CollectionUtils.isNotEmpty(mode.getAddEmplWorkExp())) {
            for (EmplWorkExp p : mode.getAddEmplWorkExp()) {
                if (p.getFromDate() != null && StringUtils.isNotEmpty(p.getJobPos())) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    emplWorkExpMapper.insertSelective(p);
                }
            }
        }/*员工异动记录对象集合*/
        if (CollectionUtils.isNotEmpty(mode.getAddEmplChg())) {
            for (EmplChg p : mode.getAddEmplChg()) {
                if (p.getChgDate() != null && StringUtils.isNotEmpty(p.getChgCnt())) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    emplChgMapper.insertSelective(p);
                }
            }
        }/*工教育经历对象集合*/
        if (CollectionUtils.isNotEmpty(mode.getAddEmplEduH())) {
            for (EmplEduH p : mode.getAddEmplEduH()) {
                if (p.getFromDate() != null && StringUtils.isNotEmpty(p.getEduOrg())) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    emplEduHMapper.insertSelective(p);
                }
            }
        }
        /*员工考核对象集合*/
        if (CollectionUtils.isNotEmpty(mode.getAddEmplEval())) {
            for (EmplEval p : mode.getAddEmplEval()) {
                if (p.getEvalDate() != null && StringUtils.isNotEmpty(p.getEvalCnt())) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    emplEvalMapper.insertSelective(p);
                }
            }
        }
        /*员工薪资变动对象集合*/
        if (CollectionUtils.isNotEmpty(mode.getAddEmplSalChg())) {
            for (EmplSalChg p : mode.getAddEmplSalChg()) {
                if (p.getChgDate() != null && StringUtils.isNotEmpty(p.getSalary() + "")) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    emplSalChgMapper.insertSelective(p);
                }
            }
        }/*员工调职记录 */
        if (CollectionUtils.isNotEmpty(mode.getAddEmplPosTf())) {
            for (EmplPosTf p : mode.getAddEmplPosTf()) {
                if (StringUtils.isNotEmpty(p.getNewJobPos())) {
                    p.setOprId(user.getUserId());
                    BeanUtils.copyProperties(mode.getEmpvo(), p);
                    emplPosTfMapper.insertSelective(p);
                }
            }
        }
    }
}
