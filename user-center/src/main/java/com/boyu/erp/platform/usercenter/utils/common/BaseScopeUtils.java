package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysRoleScope;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.service.system.RoleScopeSerivce;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @program: boyu-platform_text
 * @description: 用户范围查询赋值
 * @author: clf
 * @create: 2019-07-01 12:32
 */
@Slf4j
@Component
public class BaseScopeUtils {
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private RoleScopeSerivce roleScopeSerivce;
    @Autowired
    private UsersService usersService;

    /**
     * 查询用户可查看的组织范围
     * 1. 用户是否是系统管理员
     * 2. 用户是否是管理员
     * 3. 普通用户
     */
    public List<String> selectUnitScope(SysUser user) {
        List<String> list = new ArrayList<>();
        /**
         * 管理员
         * */
        if (usersService.getAdmin(user) != null) {
            /**
             * 系统管理员
             * */
            if (usersService.getAdmin(user).getOprId() == -1L) {
                list.add("" + usersService.getAdmin(user).getOprId());
            }
            List<SysUnit> units = unitMapper.findHierarchy(user.getUnit());
            units.forEach(u -> list.add(u.getUnitId() + ""));
            return list;
        }
        /**
         * 普通用户
         **/
        List<SysRoleScope> roleScopes = roleScopeSerivce.getUserRoleScope(user);
        SysUnit u = new SysUnit();
        for (SysRoleScope s : roleScopes) {
            if (CommonFainl.SCOPEALL.equalsIgnoreCase(s.getRoleScope())) {
                u.setUnitHierarchy(s.getRoleBelongUnit());
                //给了当前组织和当前组织下所有组织范围权限
                unitMapper.findHierarchy(u).stream().forEach(o -> list.add(o.getUnitId() + ""));
                return list;
            }

            //给了当前组织下多个组织范围权限
            if (CommonFainl.SCOPEOR.equalsIgnoreCase(s.getRoleScope())) {
                Arrays.stream(s.getRoleBelongUnit().split(",")).forEach(g -> list.add(g));
            }
            //给了当前组织范围权限
            if (CommonFainl.SCOPEOL.equalsIgnoreCase(s.getRoleScope())) {
                u.setUnitHierarchy(s.getRoleBelongUnit());
                unitMapper.findHierarchy(u).stream().forEach(us -> list.add(us.getUnitId() + ""));
            }
        }
        return list;
    }


    public Map<String, Object> setSqlMap(List<String> list, SysUser user, String tableAs, String tableFile) throws ServiceException {
        Map<String, Object> map = new HashMap<>();
        String sql = "";
        if (usersService.getIsAdmin(user)) {
            sql = null;
            map.put(sql, sql);
            return map;
        }
        //普通用户必须要有角色范围
        if (list == null || list.size() <= 0) {
            new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "您还没有角色范围");
        }
        for (int i = 0; i < list.size(); i++) {
            sql += "  or " + tableAs + "." + tableFile + "=" + list.get(i);
        }
        log.info("            "+sql);
        map.put("sql", sql);
        return map;
    }


}
