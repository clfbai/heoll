package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.FunRule;
import com.boyu.erp.platform.usercenter.mapper.system.FunRuleMapper;
import com.boyu.erp.platform.usercenter.service.system.FunRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 功能规则服务
 * @author HHe
 * @date 2019/10/9 12:08
 */
@Service
public class FunRuleServiceImpl implements FunRuleService {
    @Autowired
    private FunRuleMapper funRuleMapper;
    /**
     * 查询功能规则集合
     * @author HHe
     * @date 2019/10/9 12:08
     */
    @Override
    public List<FunRule> queryFunctionList(FunRule funRule) {
        return funRuleMapper.queryFunctionList(funRule);
    }
}
