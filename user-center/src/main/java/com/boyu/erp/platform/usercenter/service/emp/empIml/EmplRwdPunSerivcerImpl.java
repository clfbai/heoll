package com.boyu.erp.platform.usercenter.service.emp.empIml;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplRwdPun;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.employye.EmplRwdPunMapper;
import com.boyu.erp.platform.usercenter.service.emp.EmplRwdPunSerivcer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmplRwdPunSerivcerImpl implements EmplRwdPunSerivcer {
    @Autowired
    private EmplRwdPunMapper emplRwdPunMapper;

    @Override
    public List<EmplRwdPun> selectEmplRwdPun(EmpKey empKey) {
        return emplRwdPunMapper.selectList(empKey);
    }

    @Override
    public int updateEmplRwdPun(EmplRwdPun emplRwdPun, SysUser user) {
        emplRwdPun.setOprId(user.getUserId());
        return emplRwdPunMapper.updateByPrimaryKeySelective(emplRwdPun);
    }

    @Override
    public int addEmplRwdPun(EmplRwdPun emplRwdPun, SysUser user) {
        emplRwdPun.setOprId(user.getUserId());
        return emplRwdPunMapper.insertSelective(emplRwdPun);
    }

    @Override
    public int deleteEmplRwdPun(EmpKey empKey, SysUser user) {
        return emplRwdPunMapper.deleteByPrimaryKey(empKey);
    }

    /**
     * 功能描述: 批量增加
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:05
     */
    @Override
    public int addEmplRwdPunList(List<EmplRwdPun> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(s -> this.addEmplRwdPun(s,user));
        }
        return 0;
    }

    /**
     * 功能描述: 批量修改
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 11:05
     */
    @Override
    public int updateEmplRwdPunList(List<EmplRwdPun> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(s -> this.updateEmplRwdPun(s,user));
        }
        return 0;
    }




}
