package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.UserModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerUnitModel;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.system.SysUnitPgVO;
import com.boyu.erp.platform.usercenter.vo.system.UnitTreeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

public interface SysUnitService extends BaseService {


    /**
     * 通过层级关系查询下属组织
     */
    List<SysUnit> selectHierarchy(SysUnit unit);

    /**
     * 通过主键查询
     */
    SysUnit selectByPrimaryKey(long unitId);


    /**
     * 查询组织代码 和组织名称 (不能有重复添加)
     */
    String selectCodeAndName(SysUnit unit);

    /**
     * 查询组织代码 和组织名称 (不能有重复添加)
     */
    boolean getCodeAndName(SysUnit unit,String type);

    /**
     * 查询所有
     */
    PageInfo<DomainAndUnitVo> selectAll(Integer page, Integer size, SysUnit unit);

    /**
     * 新增组织
     *
     * @return 说明: 1. 新增 sys_domain 表
     * 2. 新增 sys_unit 表
     * 3. 新增 sys_psnl
     * 4. 新增 sys_user
     * 5. 新增 sys_user_qs
     */
    int insertUnit(SysUnit unit, SysUser user);


    /**
     * 修改
     */
    int updateUnit(SysUnit unit);


    /**
     * 删除
     */
    int deleteUnit(SysUnit unit);

    /**
     * 查询组织下用户
     */
    List<UserModel> getUnitUser(Long unitId);


    /**
     * 分页查询所有供应商
     */
    PageInfo<SysUnit> findAll(Integer page, Integer size, SysUnit unit);

    /**
     * 采购协议弹框
     *
     * @return
     */
    PageInfo<UnitOptionVo> selectByOption(UnitOptionVo vo, Integer page, Integer size, SysUser sysUser);

    /**
     * 采购商/供应商代码弹窗
     *
     * @return
     */
    PageInfo<UnitOptionVo> selectByOptionCode(UnitOptionVo vo, Integer page, Integer size, SysUser sysUser);

    /**
     * 购销协议中输入编号 查询相应数据
     * @param vo
     * @return
     */
    UnitOptionVo findOptionByNum(UnitOptionVo vo);
    /**
     * 采购合同弹框（根据所选的值  不用加上范围权限）
     *
     * @return
     */
    PageInfo<UnitOptionVo> selectByOption(UnitOptionVo vo, Integer page, Integer size);

    /**
     * 供应商中所有弹窗
     *
     * @return
     */
    PageInfo<UnitOptionVo> selectByOptionAll(UnitOptionVo vo, Integer page, Integer size);

    /**
     * 查询厂商、设计方
     */
    PageInfo<SysUnit> getAll(Integer page, Integer size, SysUnit unit);

    /**
     * 功能描述: 查询用户能看到的树形结构
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 14:33
     */
    UnitTreeVo getUnitTree(SysUnit unit);

    /**
     * 功能描述: 查询用户能看到的树形结构（通过组织类型查询）
     *
     * @param:
     * @return:
     * @auther: wz
     * @date: 2019/8/30 16:38
     */
    UnitTreeVo getUnitTreeByType(SysUnit unit);


    /**
     * 查询用户所有组织的Id、code、name
     * @return
     */
    /**
     * 通过层级关系查询下属组织分页
     */
    PageInfo<SysUnit> selectPageInfoHierarchy(Integer page, Integer size, SysUnit unit);

    List<SysUnitPgVO> selectAllICN();

    /**
     * 判断仓库编号是否可用
     *
     * @author HHe
     * @date 2019/8/24 10:04
     */
    UnitOptionVo judgewarehavailable(String warehCode, SysUser sysUser);


    /**
     * 功能描述:  查询组织是否是系统组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 16:50
     */
    boolean getIsAdminUnit(Long unitId);

    /**
     * 根据code判断组织是否存在
     *
     * @author HHe
     * @date 2019/8/26 11:02
     */
    SysUnit queryUnitByCode(String warehCode);

    /**
     * 功能描述:  查询组织弹窗(根据用户属主Id)通用
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/27 10:17
     */
    PageInfo<UnitOptionVo> selectOptionUnit(Integer page, Integer size, SysUnit unit, SysUser suser);
    /**
     * 根据code模糊查询出对象集合
     * @author HHe
     * @date 2019/9/11 10:23
     */
    List<SysUnit> queryUnitByConCatCode(String warehCode);
    /**
     * 根据id查询集合
     * @author HHe
     * @date 2019/9/11 14:52
     */
    List<SysUnit> queryUnitByIds(Set<Long> sysUnitIds);
    /**
     * 模糊查询组织
     * @author HHe
     * @date 2019/9/15 15:37
     */
    List<SysUnit> queryUnitLikeProp(SysUnit sysUnit);

    /**
     * 往来账户组织弹窗
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<UnitOptionVo>  selectByCaOption(UnitOptionVo vo, Integer page, Integer size, SysUser sysUser);
    /**
     * 查询组织封装到model中
     * @param ownerUnitModelList 封装组织id信息
     * @return 封装好的组织信息
     * @author HHe
     * @date 2019/11/18 12:01
     */
    List<OwnerUnitModel> queryUnit2Model(List<OwnerUnitModel> ownerUnitModelList);
}
