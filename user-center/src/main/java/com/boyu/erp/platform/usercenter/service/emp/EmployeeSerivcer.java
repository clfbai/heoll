package com.boyu.erp.platform.usercenter.service.emp;

import com.boyu.erp.platform.usercenter.entity.employee.EmplAttrDef;
import com.boyu.erp.platform.usercenter.entity.employee.Employee;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.emp.EmployeeModel;
import com.boyu.erp.platform.usercenter.vo.Emp.EmployeeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 员工接口
 * @auther: CLF
 * @date: 2019/9/3 17:15
 */
public interface EmployeeSerivcer {

    PageInfo<EmployeeVo> getEmployeeList(Integer page, Integer size, EmployeeModel model, SysUser user);

    /**
     * 功能描述: 增加员工
     *
     * @param:
     * @return:
     * @auther: CLF     * @date: 2019/9/4 11:36
     */
    int addEmployee(EmployeeModel model, SysUser user) throws Exception;

    /**
     * 功能描述:  修改员工基本信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:01
     */
    int updateEmployee(EmployeeVo mode, SysUser user);

    /**
     * 功能描述: 删除员工
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 10:25
     */
    int deleteEmployee(Employee mode, SysUser user);

    /**
     * 功能描述:  员工属性定义查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/6 10:07
     */
    List<EmplAttrDef> selectEmpAttlDef();

    /**
     * 功能描述: 增加员工定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/30 14:20
     */
    int addEmpAttrDef(EmplAttrDef mode);

    /**
     * 功能描述:修改员工定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/30 14:20
     */
    int updateEmpAttrDef(EmplAttrDef mode);
    /**
     * 功能描述: 删除员工定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/30 14:20
     */
    int deleteEmpAttrDef(EmplAttrDef mode);



}
