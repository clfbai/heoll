package com.boyu.erp.platform.usercenter.service.emp;

import com.boyu.erp.platform.usercenter.entity.employee.*;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

import java.util.List;

/**
 * 类描述:  员工履历
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/5 15:04
 */
public interface EmplWorkExpSerivcer {

    List<EmplWorkExp> selectEmplWorkExp(EmpKey empKey);

    int updateEmplWorkExp(EmplWorkExp emplWorkExp, SysUser user);

    int addEmplWorkExp(EmplWorkExp emplWorkExp, SysUser user);

    int deleteEmplWorkExp(EmpKey empKey, SysUser user);

    void addEmplEmplWorkExpList(List<EmplWorkExp> list, SysUser user);

    void updateEmplWorkExpList(List<EmplWorkExp> list, SysUser user);

    List<EmplChg> selectEmplChg(EmpKey mode);

    void addEmplChg(List<EmplChg> list, SysUser user);

    void updateEmplChg(List<EmplChg> list, SysUser user);

    int deleteEmplChg(EmpKey empKey, SysUser user);

    List<EmplEduH> selectEmplEduH(EmpKey mode);

    void addEmplEduH(List<EmplEduH> list, SysUser user);

    void updateEmplEduH(List<EmplEduH> list, SysUser user);

    int deleteEmplEduH(EmpKey empKey, SysUser user);

    /**
     * 功能描述: 员工考核
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 17:25
     */
    List<EmplEval> selectEmplEval(EmpKey mode);

    void addEmplEval(List<EmplEval> list, SysUser user);

    void updateEmplEval(List<EmplEval> list, SysUser user);

    int deleteEmplEval(EmpKey empKey, SysUser user);

    /**
     * 功能描述: 查询员工薪资变动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 17:56
     */
    List<EmplSalChg> selectEmplSalChg(EmpKey mode);

    void addEmplSalChg(List<EmplSalChg> list, SysUser user);

    void updateEmplSalChg(List<EmplSalChg> list, SysUser user);

    int deleteEmplSalChg(EmpKey empKey, SysUser user);

    /**
     * 功能描述: 查询员工调职记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 19:28
     */
    List<EmplPosTf> selectEmplPosTf(EmpKey mode);

    void addEmplPosTf(List<EmplPosTf> list, SysUser user);

    void updateEmplPosTf(List<EmplPosTf> list, SysUser user);

    int deleteEmplPosTf(EmpKey mode, SysUser user);
}
