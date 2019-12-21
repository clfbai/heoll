package com.boyu.erp.platform.usercenter.service.emp;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplRwdPun;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 员工奖惩接口
 * @auther: CLF
 * @date: 2019/9/5 10:51
 */
public interface EmplRwdPunSerivcer {

    List<EmplRwdPun> selectEmplRwdPun(EmpKey empKey);

    int updateEmplRwdPun(EmplRwdPun emplRwdPun, SysUser user);

    int addEmplRwdPun(EmplRwdPun emplRwdPun, SysUser user);

    int deleteEmplRwdPun(EmpKey empKey, SysUser user);

    int addEmplRwdPunList(List<EmplRwdPun> list, SysUser user);

    int updateEmplRwdPunList(List<EmplRwdPun> list, SysUser user);

}
