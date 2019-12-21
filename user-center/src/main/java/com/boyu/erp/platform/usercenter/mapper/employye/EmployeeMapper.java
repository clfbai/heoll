package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.Employee;
import com.boyu.erp.platform.usercenter.entity.mq.emp.EmpMsg;
import com.boyu.erp.platform.usercenter.entity.shop.ShopEmp;
import com.boyu.erp.platform.usercenter.model.emp.EmployeeModel;
import com.boyu.erp.platform.usercenter.vo.Emp.EmployeeVo;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(EmpKey key);

    int updateEmployee(Employee record);


    List<EmployeeVo> getEmpList(EmployeeModel model);


    /**
     * 功能描述: 查询新增导购消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/10 16:27
     */
    EmpMsg selectEmpMsg(ShopEmp key);
}