package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplRwdPun;

import java.util.List;

/**
 * 类描述:  员工奖惩记录mapper
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/4 12:14
 */
public interface EmplRwdPunMapper {
    int deleteByPrimaryKey(EmpKey key);

    List<EmplRwdPun> selectList(EmpKey key);

    int insertSelective(EmplRwdPun record);

    EmplRwdPun selectByPrimaryKey(EmpKey key);

    int updateByPrimaryKeySelective(EmplRwdPun record);


}