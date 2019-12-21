package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.OrdinaryDomainModel;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.LoginModel;
import com.boyu.erp.platform.usercenter.vo.OriginDomainModel;
import com.boyu.erp.platform.usercenter.vo.system.DomainVo;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface SysDomainService {
    /**
     * 组织id查询领域
     */
    SysDomain selectByPrimaryKey(Long unitId);


    /**
     * 切换领域登陆
     */
    LoginModel sysCutLog(Long unitId);

    /**
     * 回到原领域
     */
    LoginModel originDomain(OriginDomainModel model);

    /**
     * 新增领域(组织)
     *
     * @return 说明: 1. 新增 sys_domain 表
     * 2. 新增 sys_unit 表
     * 3. 新增 sys_psnl(初始化一个管理员)
     * 4. 新增 sys_user
     * 5. 新增 wareh_a (为当前领域初始化一个虚拟仓库 属主是当前领域)
     * 6. sys_unit_owner(初始化领域属主关系)
     * 7. sys_unit_clsf(初始化组织类型)
     */
    void insertDomain(SysUnit unit, SysUser user);


    /**
     * 查询领域(修改）
     */
    List<DomainAndUnitVo> findDomianAll(SysUser user);

    int updateDomain(DomainAndUnitVo domainAndUnitVo);

    int deleteDomain(DomainAndUnitVo domainAndUnitVo, SysUser users);

    /**
     * 查询组织的管理员
     *
     * @param vo
     * @return
     */
    List<SysPrsnl> queryUnitSa(SysPrsnlVo vo);

    PageInfo<DomainAndUnitVo> selectdomain(Integer page, Integer size, DomainAndUnitVo domainAndUnitVo);

    /**
     * 当前领域
     *
     * @return
     */
    DomainAndUnitVo currentdomain(DomainAndUnitVo domainAndUnitVo);

    /**
     * 查询用户能授权切换的领域
     *
     * @return
     */
    List<SysDomain> getSysDomain(SysDomain domain, SysUser user);


    /**
     * 功能描述: 查询普通用户能切换的领域
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/14 11:17
     */
    PageInfo<OrdinaryDomainModel> getPage(Integer page, Integer size, OrdinaryDomainModel model);

    /**
     * 功能描述: 管理员查看普通用户切换领域列表
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 14:09
     */
    List<OrdinaryDomainModel> getAdminPage(OrdinaryDomainModel model);

    /**
     * 功能描述: 修改普通用户领域信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 14:55
     */
    int updateOrdinary(OrdinaryDomainModel model, SysUser user);

    /**
     * 功能描述: 管理员或系统管理员查看领域信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 16:15
     */
    List<DomainVo> getDomain(DomainVo vo);

    /**
     * 功能描述: 查询已激活领域
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/28 11:46
     */
    List<SysDomain> getActivityDomain(SysDomain domain);
}
