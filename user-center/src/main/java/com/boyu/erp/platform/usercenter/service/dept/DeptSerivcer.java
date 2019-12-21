package com.boyu.erp.platform.usercenter.service.dept;

import com.boyu.erp.platform.usercenter.entity.dept.Department;
import com.boyu.erp.platform.usercenter.entity.dept.DeptAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.dept.DepartmentVo;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 部门接口
 * @auther: CLF
 * @date: 2019/9/2 11:41
 */
public interface DeptSerivcer {

    List<DepartmentVo> selectDept(DepartmentVo department, SysUser user);

    /**
     * 功能描述:
     *
     * @param: 查询单个门店信息
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 11:43
     */
    Department getDepartment(Department dept);

    /**
     * 功能描述: 增加部门信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 11:21
     */
    int addDept(DepartmentVo department, SysUser sysUser);

    /**
     * 功能描述: 修改部门
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 15:18
     */
    int updateDept(DepartmentVo department, SysUser user);

    /**
     * 功能描述: 删除部门信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/3 15:29
     */
    int deleteDept(Department department, SysUser user);

    /**
     * 功能描述:  查询部门属性定义
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/6 10:18
     */
    List<DeptAttrDef> selectDeptAttrDef();

    /**
     * 功能描述:  增加部门属性定义
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/6 10:18
     */
    int addDeptAttrDef(DeptAttrDef mode);

    /**
     * 功能描述:  修改部门属性定义
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/6 10:18
     */
    int updateDeptAttrDef(DeptAttrDef mode);

    /**
     * 功能描述:  删除部门属性定义
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/6 10:18
     */
    int deleteDeptAttrDef(DeptAttrDef mode);
}
