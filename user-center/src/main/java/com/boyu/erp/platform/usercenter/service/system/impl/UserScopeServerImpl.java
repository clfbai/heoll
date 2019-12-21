package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.model.system.UnitScopeModel;
import com.boyu.erp.platform.usercenter.service.system.UserScopeServer;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;
import com.boyu.erp.platform.usercenter.vo.system.UnitPrivVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类描述:  用户范围权限服务
 *
 * @Description:
 * @author CLF
 * @date: 2019/8/8 19:54
 */
@Slf4j
@Service
@Transactional
public class UserScopeServerImpl implements UserScopeServer {
    @Autowired
    private SysUnitPrivMapper unitPrivMapper;
    @Autowired
    private SysUserPrivMapper userPrivMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysDomainMapper domainMapper;
    @Autowired
    private SysPrivDepMapper privDepMapper;


    /**
     * 功能描述:  查询用户在当前登录的领域是否拥有全局范围权限
     *
     * @param
     * @return false 没有全局范围权限
     * @auther: CLF
     * @date: 2019/8/8 19:56
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isAllScope(SysUser user) throws ServiceException {
        if (userPrivMapper.getScopeAll(user) != null &&
                userPrivMapper.getScopeAll(user).getUnlimited().equalsIgnoreCase(CommonFainl.TRUE)) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:  用户过滤勾选的组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 20:22
     */
    @Override
    @Transactional(readOnly = true)
    public Set<UnitDomainVo> getUnitScope(SysUser user, UnitDomainVo unitDomainVo) throws ServiceException {

        List<UnitDomainVo> list = unitMapper.getUnitScope(unitDomainVo);
        Set<UnitDomainVo> set = new HashSet<>();
        /**
         * 当前用户是系统管理员可以查看任何领域、组织
         * */
        if (usersService.getIsAdmin(user)) {
            set.addAll(list);
            return set;
        }
        /**
         * 当前用户是本组织管理员可以查看本组织及以下领域、组织
         * */
        else if (usersService.getIsLeader(user)) {
            /**
             * s.getUnitHierarchy().contains(user.getUnit().getUnitHierarchy()) 组织层级包含管理员组织为下级
             * */
            set.addAll(list.parallelStream().filter(s -> s.getUnitHierarchy().contains(user.getUnit().getUnitHierarchy())).collect(Collectors.toList()));
            return set;
        } else {
            /**
             * 当前用户是普通用户需要获取对应组织范围
             * */
            UnitScopeModel model = new UnitScopeModel();
            unitDomainVo.setOwnerId(user.getOwnerId());
            unitDomainVo.setUserId(user.getUserId());
            BeanUtils.copyProperties(unitDomainVo, model);
            set.addAll(unitMapper.getSaveScope(model));
            return set;
        }
    }

    @Override
    public Set<UnitDomainVo> getUnitClsScope(SysUser user, UnitDomainVo unitDomainVo) throws ServiceException {
        /**
         * 查询商品时筛选组织
         * */
        return unitMapper.getUnitScope(unitDomainVo).stream().filter(s -> !s.getUnitId().equals(1L)).collect(Collectors.toSet());
    }

    /**
     * 功能描述:  查询用户在需要过滤的组织拥有的权限
     *
     * @param vo
     * @param user
     * @return:
     * @auther: CLF
     * @date: 2019/8/10 11:03
     */
    @Override
    @Transactional(readOnly = true)
    public Set<SysPrivilege> getUnitScopePriv(UnitDomainVo vo, SysUser user, String type) throws ServiceException {
        //权限不能越界组织Id
        Long unitId = this.getDomain(vo.getUnitId());
        List<SysPrivilege> list = null;
        //组织直接权限
        List<SysPrivilege> listSave = null;
        if (unitMapper.getAdminUnit(vo.getUnitId()) != null && unitMapper.getAdminUnit(vo.getUnitId()).getOprId() == -1L) {
            list = unitPrivMapper.getByAll(new UnitPrivVo());
            listSave = list;
        } else {
            UnitPrivVo unitPrivVo = new UnitPrivVo();
            unitPrivVo.setUnitId(unitId);
            list = unitPrivMapper.selectAll(unitPrivVo);
            listSave = unitPrivMapper.getPriv(unitId);
        }
        Set<SysPrivilege> returnSet = new HashSet<>();

        //普通用户
        if (!usersService.getIsAdmin(user) && !usersService.getIsLeader(user)) {
            //登录领域组织层级
            String str = user.getUnit().getUnitHierarchy();
            //查询领域组织层级
            String save = unitMapper.selectByPrimaryKey(vo.getUnitId()).getUnitHierarchy();

            if (type.equalsIgnoreCase("login")) {
                /**
                 * @param: login(登录查询)
                 * @param ： 不传值或其他目前为查询下用户在下级的权限
                 * 登录时进行交换
                 * (此方法本来只用来查询用户在下级领域的权限，
                 * 但登录是需要查询用户在当前领域权限集合，传入可能是HQ 系统组织，
                 * 需要进行判断后交换组装组织层级)
                 * */
                String s = save;
                save = str;
                str = s;
                if (save.indexOf("|") < 0) {
                    save = save + "|";
                }
                //如果是登录 普通用户 需要查询在登录组织所有权限
                vo.setUnitId(user.getOwnerId());

            }
            Set<SysPrivilege> set = new HashSet<>();
            //所有需要查询全局权限的领域
            List<Long> restList = this.getUingCase(save, str, new ArrayList<>());
            //用户需要查询的组织分组权限
            List<SysPrivilege> ugList = this.getUgSave(restList, user);
            //加上分组权限
            if (CollectionUtils.isNotEmpty(ugList))
                set.addAll(ugList);
            for (Long L : restList) {
                //加上上级组织全局权限
                if (CollectionUtils.isNotEmpty(this.getUserScopePriv(user, L)))
                    set.addAll(this.getUserScopePriv(user, L));
            }
            //加上当前组织权限获得所有权限结果集:权限的集合
            if (CollectionUtils.isNotEmpty(this.getUserUnitPriv(user, vo.getUnitId())))
                set.addAll(this.getUserUnitPriv(user, vo.getUnitId()));
            if (CollectionUtils.isNotEmpty(set) && CollectionUtils.isNotEmpty(listSave))
                for (SysPrivilege p : listSave) {
                    for (SysPrivilege se : set) {
                        //过滤结果集不能超出组织范围
                        if (se.getPrivId().equalsIgnoreCase(p.getPrivId())) {
                            returnSet.add(se);
                        }
                    }
                }
            if (returnSet.size() > 0) {
                //加上权限依赖
                returnSet.addAll(privDepMapper.getList(returnSet));
            }
            //最后结果
            return returnSet;
        }

        if (type.equalsIgnoreCase("login")) {
            //登录时取用户是管理员则取属主组织权限
            if (usersService.getIsLeader(user)) {
                UnitPrivVo vos = new UnitPrivVo();
                vos.setUnitId(user.getOwnerId());
                list = unitPrivMapper.selectAll(vos);
            }
            if (usersService.getIsAdmin(user)) {
                list = unitPrivMapper.getByAll(new UnitPrivVo());
            }
        } else {
            /**
             * 存在问题 登录用户为管理员选中 系统组织查不到任何权限。
             * 解决办法： 管理员用户不能添加上级组织权限和上级组织范围，只有通过授权切换领域
             * 管理员用户不会有上级权限(可以更改)
             */
            UnitPrivVo vos = new UnitPrivVo();
            vos.setUnitId(unitId);
            list = unitPrivMapper.selectAll(vos);

        }

        //系统管理员或管理员直接获取当前组织所有权限
        returnSet.addAll(list);
        return returnSet;
    }

    /**
     * 判断勾选的组织是否是领域
     */
    public Long getDomain(Long unitId) {
        if (domainMapper.selectByPrimaryKey(unitId) == null) {
            /**
             * 不是领域找到上级领域
             * */
            return this.getUnitHier(unitId).getUnitId();
        }
        return unitId;
    }


    /**
     * 功能描述:  判断用户选着领域需要查询在那些组织拥有全局权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 14:57
     */
    public List<Long> getUingCase(String s, String s2, List<String> list) {
        //这里会吧当前组织去掉，如果当前组织是领域
        String rest = s.substring(0, s.lastIndexOf("|"));
        list.add(rest);
        if (!rest.equalsIgnoreCase(s2)) {
            this.getUingCase(rest, s2, list);
        }
        List<Long> restList = new ArrayList<>();
        for (String st : list) {
            restList.add(unitMapper.getHierarchy(st).getUnitId());
        }
        return restList;
    }


    /**
     * 查找非领域组织上级领域
     */
    public SysUnit getUnitHier(Long unitId) {
        SysUnit unit = unitMapper.selectByPrimaryKey(unitId);
        //'|'最后一次出现的位置 unit.getUnitHierarchy().lastIndexOf("|");
        String str = unit.getUnitHierarchy().substring(0, unit.getUnitHierarchy().lastIndexOf("|"));
        //通过层级查找组织
        unit = unitMapper.getHierarchy(str);
        if (domainMapper.selectByPrimaryKey(unit.getUnitId()) == null) {
            //递归查询上级知道找到领域为止
            getUnitHier(unit.getUnitId());
        }
        return unit;
    }

    /**
     * 查询用户在某个组织拥有的全局权限
     * 1.查询表： sys_user_priv ，sys_user_role
     */
    public List<SysPrivilege> getUserScopePriv(SysUser user, Long unitId) {
        SysUserPrivKey privKey = this.getUserPrivkey(user, unitId);
        privKey.setUnlimited("T");
        List<SysPrivilege> list = userPrivMapper.getScopeUnitPriv(this.getUserPrivkey(user, unitId));
        return list;
    }

    /**
     * 功能描述: 查询用户在指定组织拥有的所有权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 11:44
     */
    public List<SysPrivilege> getUserUnitPriv(SysUser user, Long unitId) {
        SysUserPrivKey privKey = this.getUserPrivkey(user, unitId);
        List<SysPrivilege> list = userPrivMapper.getScopeUnitPrivAll(privKey);
        return list;
    }

    /**
     * 功能描述: 查询用户拥有哪些分组权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 10:04
     */
    public List<SysUgDtl> getUserUg(SysUser user) {
        return userPrivMapper.getUserUg(user);
    }

    /**
     * 功能描述:  需要过滤的额分组权限
     *
     * @param unitList (用户需要查询的组织权限)
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 10:24
     */
    public List<SysPrivilege> getUgSave(List<Long> unitList, SysUser user) {
        Set<Long> set = new HashSet<>();

        List<SysUgDtl> userUg = this.getUserUg(user);
        //用户拥有组织分组才过滤
        if (CollectionUtils.isNotEmpty(userUg) && CollectionUtils.isNotEmpty(unitList)) {
            for (Long h : unitList) {
                for (SysUgDtl dtl : userUg) {
                    if (h.equals(dtl.getMbrId())) {
                        //添加需要增加的分组权限
                        set.add(dtl.getUgId());
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(set)) {
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("list", set);
            return userPrivMapper.getUgLitPriv(map);
        }
        return null;
    }


    public SysUserPrivKey getUserPrivkey(SysUser user, Long unitId) {
        SysUserPrivKey privKey = new SysUserPrivKey();
        privKey.setOwnerId(user.getOwnerId());
        privKey.setUserId(user.getUserId());
        privKey.setUnitId(unitId);
        return privKey;
    }


}
