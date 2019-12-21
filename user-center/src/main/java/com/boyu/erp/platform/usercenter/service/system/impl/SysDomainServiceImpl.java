package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.model.system.OrdinaryDomainModel;
import com.boyu.erp.platform.usercenter.service.system.SysDomainService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.DomainUtils;
import com.boyu.erp.platform.usercenter.utils.PasswordUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.LoginModel;
import com.boyu.erp.platform.usercenter.vo.OriginDomainModel;
import com.boyu.erp.platform.usercenter.vo.system.DomainVo;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class SysDomainServiceImpl implements SysDomainService {

    @Autowired
    private SysDomainMapper domainMapper;
    @Autowired
    private SysDomainSwitchMapper domainSwitchMapper;
    @Autowired
    private SysUnitMapper sysUnitMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysPrsnlMapper prsnlMapper;

    @Autowired
    private SysUnitMapper unitMapper;

    @Autowired
    private UsersService usersService;

    @Autowired
    private WarehService warehService;


    /**
     * 主键查询
     */
    @Override
    @Transactional(readOnly = true)
    public SysDomain selectByPrimaryKey(Long unitId) {
        return domainMapper.selectByPrimaryKey(unitId);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginModel sysCutLog(Long unitId) {
        return domainMapper.cutDomain(unitId);
    }

    @Override
    public LoginModel originDomain(OriginDomainModel model) {
        return domainMapper.originDomain(model);
    }

    /**
     * 新增领域
     *
     * @return 说明:
     * 1. 新增 sys_unit 表
     * 2. 新增 sys_psnl
     * 3. 新增 sys_user
     * 4. 新增 sys_domain 表
     * 5. 新增 wareh_a (为当前领域初始化一个虚拟仓库 属主是当前领域)
     * 6. sys_unit_owner(初始化领域属主关系)
     * 7. sys_unit_clsf(初始化组织类型)
     */
    @Override
    public void insertDomain(SysUnit unit, SysUser user) {
        //新增组织时 暂定为不能指定管理员 只能是系统默认管理员  后期可以更换管理员
        if (StringUtils.NullEmpty(String.valueOf(unit.getUnitId())) || String.valueOf(unit.getUnitId()).equalsIgnoreCase("null")) {
            /**
             * 1.添加组织 sys_unit
             *   添加组织后组织Id返回字段已在Mapper.xml 定义
             * */
            unit.setUnitStatus("A");
            unit.setCtrlUnitId(user.getOwnerId());
            unit.setOprId(user.getUserId());
            //设置组织层级
            unit.setUnitHierarchy(user.getUnit().getUnitHierarchy() + "|" + unit.getUnitCode());
            sysUnitMapper.insertSelective(unit);
            //增加仓库相关(这里是新增组织为领域需要初始化仓库信息)
            warehService.addDomainWareh(unit, user);
            this.insertDomainUp(unit, user);


        }

    }

    public void insertDomainUp(SysUnit unit, SysUser user) {
        SysPrsnl prs = new SysPrsnl();
        //管理员人员代码 默认为组织代码+SA
        prs.setPrsnlCode(unit.getUnitCode() + "SA");
        // 管理组织Id 是用户对应(管理或被管理)的组织Id
        prs.setCtrlUnitId(unit.getUnitId());
        prs.setUnitHierarchy(unit.getUnitHierarchy());
        prs.setShared(unit.getShared());

        /**
         * 2.添加sys_prsnl
         * 3.添加 sys_user
         * */
        Long userId = insertPrsnlAndUser(prs, user, unit.getUnitName());

        /**
         * 4.添加领域  sys_domain
         * */
        SysDomain domain = new SysDomain();
        //组织Id
        domain.setUnitId(unit.getUnitId());
        System.out.println("" + unit.getUnitCode());
        //领域Id 默认为组织Code
        domain.setDomainId(unit.getUnitCode());
        //操作员Id
        domain.setOprId(user.getUserId());
        //组织状态A代表激活
        domain.setDomainStatus("A");
        //管理员Id
        domain.setSaId(userId);
        domain.setUpdTime(new Date());

        domainMapper.insertSelective(domain);
    }


    @Override
    public List<DomainAndUnitVo> findDomianAll(SysUser user) {
        List<DomainAndUnitVo> resultList = new ArrayList<>();
        if (usersService.getAdmin(user) != null) {
            if (usersService.getAdmin(user).getOprId() == -1) {
                //系统管理员领域
                return domainMapper.findAll(user);
            }
            //管理员领域(需要递归)
            //if(user.getUnit()){}
            List<DomainAndUnitVo> digui = new ArrayList<>();
            if (StringUtils.NullEmpty(user.getDomain().getDomainId()) && StringUtils.NullEmpty(user.getUnit().getUnitCode()) &&
                    StringUtils.NullEmpty(user.getUnit().getInputCode()) && StringUtils.NullEmpty(user.getUnit().getUnitStatus())) {
                resultList = domainMapper.findAll(user);
                return getvo(DomainUtils.getDomain(resultList, user));
            } else {
                resultList = domainMapper.findAll(user);
                if (resultList.size() > 0) {
                    user.getDomain().setDomainId("");
                    user.getUnit().setUnitCode("");
                    user.getUnit().setInputCode(null);
                    user.getUnit().setUnitStatus(null);
                    List<DomainAndUnitVo> max = domainMapper.findAll(user);
                    // 模糊查询的
                    return getvo(DomainUtils.likeDomain(max, resultList, user));
                }
                return null;
            }
        } else {
            //普通用户
            return domainMapper.domiangetAll(user);
        }

    }

    public List<DomainAndUnitVo> getvo(List<Object> ob) {
        List<DomainAndUnitVo> ds = new ArrayList<>();
        if (ob == null || ob.size() == 0) {
            return null;
        }
        for (Object vo : ob) {
            if (vo != null) {
                DomainAndUnitVo v = (DomainAndUnitVo) vo;
                ds.add(v);
            }
        }
        return ds;
    }

    /**
     * 修改领域
     *
     * @return
     */
    @Override
    public int updateDomain(DomainAndUnitVo domainAndUnitVo) {
        return updateUnitDmain(domainAndUnitVo);
    }

    /**
     * 删除 打标
     * 1. 删除 领域下组织和所有子组织
     * 2. 删除组织和子组织下所有用户
     * 3.删除组织和子组织下所有人员
     *
     * @param domainAndUnitVo
     * @return
     */
    @Override
    public int deleteDomain(DomainAndUnitVo domainAndUnitVo, SysUser users) {


        SysUser user = new SysUser();
        SysUnit units = new SysUnit();
        SysDomain domain = new SysDomain();
        user.setUnit(units);
        user.setDomain(domain);
        //这个空对象 用来查询 组织递归
        List<DomainAndUnitVo> resultList = domainMapper.findAll(user);
        user.setOwnerId(domainAndUnitVo.getUnitId());
        List<DomainAndUnitVo> rest = getvo(DomainUtils.getDomain(resultList, user));
        log.info("长度:   " + rest.size());
        int a = 0;
        for (DomainAndUnitVo deleteVo : rest) {
            SysUnit unit = new SysUnit();
            BeanUtils.copyProperties(deleteVo, unit);
            unit.setUnitStatus("D");
            /**
             * 1. 删除组织
             * */
            int s = unitMapper.updateByPrimaryKeySelective(unit);
            /**
             * 2. 删除人员
             * */
            SysPrsnlVo vo = new SysPrsnlVo();
            vo.setCtrlUnitId(deleteVo.getUnitId());
            vo.setOprId(users.getUserId());
            vo.setPrsnlStatus("D");
            vo.setUpdTime(new Date());
            int st = prsnlMapper.deletePrsnlAndUser(vo);
            /**
             * 3. 删除组织下所有用户
             * */
            user.setOwnerId(deleteVo.getUnitId());
            user.setUpdTime(new Date());
            user.setOprId(users.getUserId());
            user.setUserStatus("D");
            int skt = userMapper.deleteUserStatus(user);
            if (a == 0) {
                a = s + st + skt;
            } else {
                a = a + s + st + skt;
            }
        }
        return a;
    }

    @Override
    public List<SysPrsnl> queryUnitSa(SysPrsnlVo vo) {
        return domainMapper.queryUnitSa(vo);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<DomainAndUnitVo> selectdomain(Integer page, Integer size, DomainAndUnitVo domainAndUnitVo) {
        PageHelper.startPage(page, size);
        List<DomainAndUnitVo> selectdomain = domainMapper.selectdomain(domainAndUnitVo);
        PageInfo<DomainAndUnitVo> pageInfo = new PageInfo<>(selectdomain);
        return pageInfo;
    }

    /**
     * 当前领域
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DomainAndUnitVo currentdomain(DomainAndUnitVo domainAndUnitVo) {
        List<DomainAndUnitVo> selectdomain = domainMapper.selectdomain(domainAndUnitVo);
        return selectdomain != null && selectdomain.size() > 0 ? selectdomain.get(0) : null;
    }

    /**
     * 查询用户能授权切换的领域(为切换领域准备)
     *
     * @return
     */
    @Override
    public List<SysDomain> getSysDomain(SysDomain domain, SysUser user) {
        SysDomainSwitch sysDomainSwitch = new SysDomainSwitch();
        sysDomainSwitch.setDomainAccreditId(user.getOwnerId());
        return (List<SysDomain>) CollectionUtils.subtract(domainMapper.statusDomain(domain), domainSwitchMapper.statusDomain(sysDomainSwitch));
    }

    /**
     * 领域的修改 包含组织的修改
     *
     * @param domainAndUnitVo
     * @return
     */
    private int updateUnitDmain(DomainAndUnitVo domainAndUnitVo) {
        SysUnit unit = new SysUnit();
        SysDomain domain = new SysDomain();

        BeanUtils.copyProperties(domainAndUnitVo, unit);
        BeanUtils.copyProperties(domainAndUnitVo, domain);

        domainMapper.updateByPrimaryKeySelective(domain);
        return unitMapper.updateByPrimaryKeySelective(unit);
    }


    private Long insertPrsnlAndUser(SysPrsnl prsnl, SysUser sessionuser, String unitname) {

        //操作时间
        prsnl.setUpdTime(new Date());
        //操作员id
        prsnl.setOprId(sessionuser.getUserId());
        prsnl.setFaxNum(unitname + "管理员");
        //人员状态
        prsnl.setPrsnlStatus(CommonFainl.USER_STATUS);
        //插入prsnl表
        prsnlMapper.insertPrsnl(prsnl);
        SysUser user = new SysUser();
        //添加用户的id
        user.setUserId(prsnl.getPrsnlId());
        //属主id
        user.setOwnerId(prsnl.getCtrlUnitId());
        //操作员id
        user.setOprId(sessionuser.getUserId());
        //默认密码
        user.setUserPswd(PasswordUtils.encryptionPassword("123456"));
        //操作时间
        user.setUpdTime(new Date());
        //机器控制  含义待定
        user.setMachCtrl("F");
        user.setUserStatus("A");
        if (StringUtils.NullEmpty(user.getMenuId())) {
            user.setMenuId("ENTIRE");
        }

        //插入user表
        userMapper.insertSelective(user);

        return prsnl.getPrsnlId();


    }


    /**
     * 功能描述: 查询(普通)用户能切换的领域
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/14 11:17
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<OrdinaryDomainModel> getPage(Integer page, Integer size, OrdinaryDomainModel model) {
        PageHelper.startPage(page, size);
        List<OrdinaryDomainModel> selectdomain = domainMapper.selectOrdinaryDomain(model);
        PageInfo<OrdinaryDomainModel> pageInfo = new PageInfo<>(selectdomain);
        return pageInfo;
    }

    /**
     * 功能描述: 管理员查看普通用户切换领域列表
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 14:09
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrdinaryDomainModel> getAdminPage(OrdinaryDomainModel model) {
        //管理员能看到的组织层级
        List<SysUnit> list = unitMapper.getUnitId(model.getUnitId());
        model.setUnitId(null);
        List<OrdinaryDomainModel> res = new ArrayList<>();
        //用户拥有的领域
        List<OrdinaryDomainModel> selectdomain = domainMapper.selectOrdinaryDomain(model);
        //过滤(用户可能包含超出当前组织的层级组织)
        for (OrdinaryDomainModel o : selectdomain) {
            for (SysUnit u : list) {
                if (o.getUnitId().equals(u.getUnitId())) {
                    res.add(o);
                }
            }
        }
        return res;
    }

    /**
     * 功能描述: 修改普通用户领域信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 14:55
     */
    @Override
    public int updateOrdinary(OrdinaryDomainModel model, SysUser user) {
        int a = 0;
        if (CollectionUtils.isNotEmpty(model.getAddList())) {
            for (OrdinaryDomainModel m : model.getAddList()) {
                //判断用户是否存在，存在激活用户，不存在增加
                selectUserExit(m, CommonFainl.ADD, user);
                a++;
            }
        }
        if (CollectionUtils.isNotEmpty(model.getDeleteList())) {
            for (OrdinaryDomainModel m : model.getDeleteList()) {
                selectUserExit(m, CommonFainl.DELETE, user);
                a++;
            }
        }
        return a;
    }

    /**
     * 功能描述: 管理员或系统管理员查询可以授予某个用户的领域信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 16:15
     */
    @Override
    @Transactional(readOnly = true)
    public List<DomainVo> getDomain(DomainVo vo) {

        OrdinaryDomainModel model = new OrdinaryDomainModel();
        model.setUserId(vo.getUserId());
        model.setUnitId(null);
        //用户拥有领域
        List<OrdinaryDomainModel> userList = domainMapper.selectOrdinaryDomain(model);
        //管理员能授权所有领域
        List<DomainVo> selectdomain = domainMapper.getDomain(vo);
        List<DomainVo> rest = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userList) && CollectionUtils.isNotEmpty(selectdomain)) {
            //过滤出用户没有的领域
            for (DomainVo vos : selectdomain) {
                for (OrdinaryDomainModel o : userList) {
                    if (o.getOwnerId().equals(vos.getUnitId())) {
                        rest.add(vos);
                    }
                }
            }
            selectdomain.removeAll(rest);
        }
        return selectdomain;
    }
    /**
     * 功能描述: 查询已激活领域
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/28 11:46
     */
    @Override
    public List<SysDomain> getActivityDomain(SysDomain domain) {
        return domainMapper.statusDomain(domain);
    }

    public void selectUserExit(OrdinaryDomainModel m, String str, SysUser users) {
        SysUser user = userMapper.getUserIdAndOwnerId(m.getUserId(), m.getOwnerId());
        if (str.equalsIgnoreCase(CommonFainl.ADD)) {
            //用户不存在新增
            if (user == null || user.getUserId() == null) {
                user = new SysUser();
                //设置密码
                user.setUserPswd(PasswordUtils.encryptionPassword("123456"));
                user.setUserId(m.getUserId());
                user.setOwnerId(m.getOwnerId());
                user.setUpdTime(new Date());
                user.setOprId(users.getUserId());
                user.setUserStatus("A");
                userMapper.insertSelective(user);
            } else {
                //改为给活动
                user.setUserStatus("A");
                user.setUpdTime(new Date());
                user.setOprId(users.getUserId());
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
        if (str.equalsIgnoreCase(CommonFainl.DELETE)) {
            user.setUserStatus("D");
            user.setUpdTime(new Date());
            user.setOprId(users.getUserId());
            user.setOwnerId(m.getOwnerId());
            userMapper.updateByPrimaryKeySelective(user);
        }

    }


}
