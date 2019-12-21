package com.boyu.erp.platform.usercenter.service.emp.empIml;

import com.boyu.erp.platform.usercenter.entity.employee.*;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.employye.*;
import com.boyu.erp.platform.usercenter.service.emp.EmplWorkExpSerivcer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class

EmplWorkExpSerivcerImpl implements EmplWorkExpSerivcer {
    @Autowired
    private EmplWorkExpMapper emplWorkExpMapper;
    @Autowired
    private EmplChgMapper emplChgMapper;
    @Autowired
    private EmplEduHMapper emplEduHMapper;
    @Autowired
    private EmplEvalMapper emplEvalMapper;
    @Autowired
    private EmplSalChgMapper emplSalChgMapper;
    @Autowired
    private EmplPosTfMapper emplPosTfMapper;


    @Override
    public List<EmplWorkExp> selectEmplWorkExp(EmpKey empKey) {
        return emplWorkExpMapper.selectEmplWorkExp(empKey);
    }

    @Override
    public int updateEmplWorkExp(EmplWorkExp emplWorkExp, SysUser user) {
        emplWorkExp.setOprId(user.getUserId());
        return emplWorkExpMapper.updateByPrimaryKeySelective(emplWorkExp);
    }

    @Override
    public int addEmplWorkExp(EmplWorkExp emplWorkExp, SysUser user) {
        emplWorkExp.setOprId(user.getUserId());
        return emplWorkExpMapper.insertSelective(emplWorkExp);
    }

    @Override
    public int deleteEmplWorkExp(EmpKey empKey, SysUser user) {
        return emplWorkExpMapper.deleteByPrimaryKey(empKey);
    }

    @Override
    public void addEmplEmplWorkExpList(List<EmplWorkExp> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(s -> this.addEmplWorkExp(s, user));
        }
    }

    @Override
    public void updateEmplWorkExpList(List<EmplWorkExp> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(s -> this.updateEmplWorkExp(s, user));
        }
    }

    /**
     * 功能描述: 员工异动集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 15:58
     */
    @Override
    public List<EmplChg> selectEmplChg(EmpKey mode) {

        return emplChgMapper.selectList(mode);
    }

    @Override
    public void addEmplChg(List<EmplChg> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                list.get(0).setOprId(user.getUserId());
                emplChgMapper.insertSelective(list.get(0));
            } else {
                list.forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplChgMapper.insertSelective(s));
            }
        }
    }

    @Override
    public void updateEmplChg(List<EmplChg> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplChg h = list.get(0);
                h.setOprId(user.getUserId());
                emplChgMapper.updateByPrimaryKeySelective(h);
            } else {
                list.forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplChgMapper.updateByPrimaryKeySelective(s));
            }
        }
    }

    @Override
    public int deleteEmplChg(EmpKey empKey, SysUser user) {
        return emplChgMapper.deleteByPrimaryKey(empKey);
    }

    /**
     * 功能描述: 教育经历集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 16:56
     */
    @Override
    public List<EmplEduH> selectEmplEduH(EmpKey mode) {
        return emplEduHMapper.selectList(mode);
    }

    @Override
    public void addEmplEduH(List<EmplEduH> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplEduH e = list.get(0);
                e.setOprId(user.getUserId());
                emplEduHMapper.insertSelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplEduHMapper.insertSelective(s));
            }
        }
    }

    @Override
    public void updateEmplEduH(List<EmplEduH> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplEduH e = list.get(0);
                e.setOprId(user.getUserId());
                emplEduHMapper.updateByPrimaryKeySelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplEduHMapper.updateByPrimaryKeySelective(s));
            }
        }
    }


    @Override
    public int deleteEmplEduH(EmpKey empKey, SysUser user) {
        return emplEduHMapper.deleteByPrimaryKey(empKey);
    }

    /**
     * 功能描述: 员工考核
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 17:25
     */
    @Override
    public List<EmplEval> selectEmplEval(EmpKey mode) {
        return emplEvalMapper.selectList(mode);
    }

    @Override
    public void addEmplEval(List<EmplEval> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplEval e = list.get(0);
                e.setOprId(user.getUserId());
                emplEvalMapper.insertSelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplEvalMapper.insertSelective(s));
            }
        }
    }

    @Override
    public void updateEmplEval(List<EmplEval> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplEval e = list.get(0);
                e.setOprId(user.getUserId());
                emplEvalMapper.updateByPrimaryKeySelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplEvalMapper.updateByPrimaryKeySelective(s));
            }
        }
    }

    @Override
    public int deleteEmplEval(EmpKey empKey, SysUser user) {
        return emplEvalMapper.deleteByPrimaryKey(empKey);
    }

    /**
     * 功能描述: 查询员工薪资变动
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 17:56
     */
    @Override
    public List<EmplSalChg> selectEmplSalChg(EmpKey mode) {

        return emplSalChgMapper.selectList(mode);
    }

    @Override
    public void addEmplSalChg(List<EmplSalChg> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplSalChg e = list.get(0);
                e.setOprId(user.getUserId());
                emplSalChgMapper.insertSelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplSalChgMapper.insertSelective(s));
            }
        }
    }

    @Override
    public void updateEmplSalChg(List<EmplSalChg> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplSalChg e = list.get(0);
                e.setOprId(user.getUserId());
                emplSalChgMapper.updateByPrimaryKeySelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplSalChgMapper.updateByPrimaryKeySelective(s));
            }
        }
    }

    @Override
    public int deleteEmplSalChg(EmpKey empKey, SysUser user) {
        return emplSalChgMapper.deleteByPrimaryKey(empKey);
    }

    /**
     * 功能描述: 查询员工调职记录
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/5 19:28
     */
    @Override
    public List<EmplPosTf> selectEmplPosTf(EmpKey mode) {
        return emplPosTfMapper.selectList(mode);
    }

    @Override
    public void addEmplPosTf(List<EmplPosTf> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplPosTf e = list.get(0);
                e.setOprId(user.getUserId());
                emplPosTfMapper.insertSelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplPosTfMapper.insertSelective(s));
            }
        }
    }

    @Override
    public void updateEmplPosTf(List<EmplPosTf> list, SysUser user) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() <= 1) {
                EmplPosTf e = list.get(0);
                e.setOprId(user.getUserId());
                emplPosTfMapper.updateByPrimaryKeySelective(e);
            } else {
                list.parallelStream().forEach(s -> s.setOprId(user.getUserId()));
                list.parallelStream().forEach(s -> emplPosTfMapper.updateByPrimaryKeySelective(s));
            }
        }
    }

    @Override
    public int deleteEmplPosTf(EmpKey mode, SysUser user) {
        return emplPosTfMapper.deleteByPrimaryKey(mode);
    }
}
