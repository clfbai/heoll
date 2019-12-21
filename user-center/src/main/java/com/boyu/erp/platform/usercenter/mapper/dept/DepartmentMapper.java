package com.boyu.erp.platform.usercenter.mapper.dept;

import com.boyu.erp.platform.usercenter.entity.dept.Department;
import com.boyu.erp.platform.usercenter.vo.dept.DepartmentVo;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long deptId);

    int insertDept(Department record);

    Department selectByPrimaryKey(Long deptId);

    int updateByDept(Department record);

    /**
     * 功能描述:  查询属主Id所有部门
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/2 12:01
     */
    List<DepartmentVo> getDepts(DepartmentVo department);
}