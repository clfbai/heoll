package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.OrdinaryDomainModel;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.LoginModel;
import com.boyu.erp.platform.usercenter.vo.OriginDomainModel;
import com.boyu.erp.platform.usercenter.vo.system.DomainVo;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;

import java.util.List;


public interface SysDomainMapper {

    SysDomain selectByPrimaryKey(long unitId);

    int insertSelective(SysDomain record);

    int updateByPrimaryKeySelective(SysDomain record);

    int deleteByPrimaryKey(long unitId);

    List<SysPrsnl> queryUnitSa(SysPrsnlVo vo);

    List<DomainAndUnitVo> selectdomain(DomainAndUnitVo domainAndUnitVo);

    /**
     * 用户查看领域
     */
    List<DomainAndUnitVo> domiangetAll(SysUser user);

    /**
     * 系统管理员或管理员查看领域
     */
    List<DomainAndUnitVo> findAll(SysUser user);


    int countDomainCode(String domianCode);

    /**
     * 功能描述: 切换领域查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 16:14
     */
    LoginModel cutDomain(Long unitId);

    LoginModel originDomain(OriginDomainModel model);

    /**
     * 查询已激活领域(为切换领域准备)
     *
     * @return
     */
    List<SysDomain> statusDomain(SysDomain domain);

    /**
     * 功能描述:  查询普通用户能切换的领域
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/14 11:20
     */
    List<OrdinaryDomainModel> selectOrdinaryDomain(OrdinaryDomainModel model);


    /**
     * 功能描述: 管理员或系统管理员查询领域组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 16:10
     */
    List<DomainVo> getDomain(DomainVo domainVo);


}