package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.option.PrsnlOptionModel;
import com.boyu.erp.platform.usercenter.model.system.SysPrsnlModel;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlPgVO;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

public interface SysPrsnlService  extends BaseService {
    /**
     * 根据prsnlCode获取prsnl
     */
    SysPrsnl selectByPrsnlCode(String prsnlCode);

    /**
     * 查询所有
     */
    PageInfo<SysPrsnl> selectAll(Integer page, Integer size, SysPrsnl prsnl);

    /**
     * 修改人员信息
     */
    int changePrsnl(SysPrsnlVo prsnl, SysUser user);

    /**
     * 新增人员
     */
    Long insertPrsnl(SysPrsnlVo prsnl, SysUser user);


    SysPrsnl selectById(long id);

    /**
     * 根据id 查询prsnl和user数据
     */
    SysPrsnlVo selectPrsnlAndUserByid(SysPrsnlVo record);

    /**
     * 查询组织下所有用户（分为系统管理员，管理员，和普通用户）
     */
    PageInfo<SysPrsnlVo> selectPrsnlAndUser(Integer page, Integer size, SysPrsnlVo record);


    /**
     * 查询用户能看到的人员
     */
    List<SysPrsnlVo> getPrsnlAndUser(SysUser sessionSysUser, SysPrsnlVo prsnl);

    /**
     * 查询设计人员
     */
    PageInfo<PrsnlOptionModel> findByDesign(Integer page, Integer size, SysPrsnl record);


    /**
     * 查询人员弹窗
     */
    PageInfo<PrsnlOptionModel> getOpetionPrsin(Integer page, Integer size, SysPrsnl record, SysUser user);


    List<SysPrsnlPgVO> selectAllICN();

    /**
     * 功能描述:  查询组织下用户(组织筛选用户)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 14:51
     */
    PageInfo<SysPrsnlVo> selectPrsnlAndUint(Integer page, Integer size, SysPrsnlVo prsnl);

    /**
     * 查询人员弹窗（根据属主Id）
     */
    PageInfo<PrsnlOptionModel> getOwnerPrsnl(Integer page, Integer size, SysPrsnlModel prsnl, SysUser sessionSysUser);


    /**
     * 用户装入人员查询(可组织筛选)
     */
    PageInfo<SysPrsnlVo> selectPrsnlUserList(Integer page, Integer size, SysPrsnlVo record);

    /**
     * 根据Id查询用户信息
     * @author HHe
     * @date 2019/9/12 10:39
     */
    List<SysPrsnl> queryPrsnlByIds(Set<Long> sysPrsnl);

}
