package com.boyu.erp.platform.usercenter.service.emp;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplFamMem;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 员工家庭成员接口
 * @auther: CLF
 * @date: 2019/9/5 12:09
 */
public interface EmplFamMemSerivcer {
    List<EmplFamMem> selectEmplFamMemList(EmpKey empKey);

    int addEmplFamMem(EmplFamMem emplFamMem, SysUser user);

    int updateEmplFamMem(EmplFamMem emplFamMem, SysUser user);

    int addEmplFamMemList(List<EmplFamMem> list, SysUser user);

    int updateEmplFamMemList(List<EmplFamMem> list, SysUser user);

    int deleteFamMem(EmpKey emplFamMem, SysUser user);
}
