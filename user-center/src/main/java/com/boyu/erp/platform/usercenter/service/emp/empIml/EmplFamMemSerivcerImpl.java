package com.boyu.erp.platform.usercenter.service.emp.empIml;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplFamMem;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.employye.EmplFamMemMapper;
import com.boyu.erp.platform.usercenter.service.emp.EmplFamMemSerivcer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class EmplFamMemSerivcerImpl implements EmplFamMemSerivcer {
    @Autowired
    private EmplFamMemMapper emplFamMemMapper;

    @Override
    public List<EmplFamMem> selectEmplFamMemList(EmpKey empKey) {
        return emplFamMemMapper.selectEmplFamMem(empKey);
    }

    @Override
    public int addEmplFamMem(EmplFamMem emplFamMem, SysUser user) {
        emplFamMem.setOprId(user.getUserId());
        return emplFamMemMapper.insertSelective(emplFamMem);
    }

    @Override
    public int updateEmplFamMem(EmplFamMem emplFamMem, SysUser user) {
        emplFamMem.setOprId(user.getOprId());
        return emplFamMemMapper.updateByPrimaryKey(emplFamMem);
    }

    @Override
    public int addEmplFamMemList(List<EmplFamMem> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(s -> this.addEmplFamMem(s, user));
        }
        return 0;
    }

    @Override
    public int updateEmplFamMemList(List<EmplFamMem> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(s -> this.updateEmplFamMem(s, user));
        }
        return 0;
    }

    @Override
    public int deleteFamMem(EmpKey emplFamMem, SysUser user) {
        return emplFamMemMapper.deleteByPrimaryKey(emplFamMem);
    }
}
