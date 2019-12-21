package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserMapper;
import com.boyu.erp.platform.usercenter.model.UserModel;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerUnitModel;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.BaseScopeUtils;
import com.boyu.erp.platform.usercenter.utils.system.UnitTreeUtis;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.system.SysUnitPgVO;
import com.boyu.erp.platform.usercenter.vo.system.UnitTreeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SysUnitServiceImpl implements SysUnitService {

    @Autowired
    private SysUnitMapper sysUnitMapper;

    @Autowired
    private UnitTreeUtis unitTreeUtis;
    @Autowired
    private SysUserMapper userMapper;


    @Autowired
    private UsersService usersService;

    @Autowired
    private BaseScopeUtils baseScopeUtils;

    /**
     * 通过层级关系查询下属组织
     */
    @Override
    public List<SysUnit> selectHierarchy(SysUnit unit) {
        return sysUnitMapper.findHierarchy(unit);
    }

    /**
     * 主键查询
     */
    @Override
    @Transactional(readOnly = true)
    public SysUnit selectByPrimaryKey(long unitId) {
        return sysUnitMapper.selectByPrimaryKey(unitId);
    }


    /**
     * 查询组织代码 和组织名称 (不能有重复添加)
     */
    @Override
    @Transactional(readOnly = true)
    public String selectCodeAndName(SysUnit unit) {
        for (SysUnit u : sysUnitMapper.selectCodeAndName(unit)) {
            if (StringUtils.isNotEmpty(u.getUnitName()) || StringUtils.isNotEmpty(u.getUnitCode())) {
                return "组织名称或组织代码重复";
            }
        }
        return null;
    }

    /**
     * 查询组织代码 和组织名称 (不能有重复添加)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean getCodeAndName(SysUnit unit, String type) {
        List<SysUnit> list = sysUnitMapper.selectCodeAndName(unit);
        if (type.equals(CommonFainl.ADD)) {
            if (CollectionUtils.isNotEmpty(list)) {
                return true;
            }
            return false;
        }
        if (type.equals(CommonFainl.UPDATE)) {
            if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
                return true;
            }
            return false;
        }

        return false;
    }

    /**
     * 查询全部
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<DomainAndUnitVo> selectAll(Integer page, Integer size, SysUnit unit) {
        //分页查询
        PageHelper.startPage(page, size);
        List<DomainAndUnitVo> sysUnits = sysUnitMapper.selectAll(unit);
        PageInfo<DomainAndUnitVo> pageInfo = new PageInfo<>(sysUnits);
        return pageInfo;
    }

    /**
     * 新增组织
     * 2. 新增 sys_unit 表
     * 3. 新增 sys_psnl
     * 4. 新增 sys_user
     * 5. 新增 sys_user_qs
     */
    @Override
    public int insertUnit(SysUnit unit, SysUser user) {


        return sysUnitMapper.insertSelective(unit);
    }

    /**
     * 修改
     */
    @Override
    public int updateUnit(SysUnit unit) {
        return sysUnitMapper.updateByPrimaryKeySelective(unit);
    }

    /**
     * 删除
     * 需要删除组织下的用户
     */
    @Override
    public int deleteUnit(SysUnit unit) {
        return sysUnitMapper.updateByPrimaryKeySelective(unit);
    }

    @Override
    public List<UserModel> getUnitUser(Long unitId) {
        return userMapper.getUnitUser(unitId);
    }


    /**
     * 供应商查询
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysUnit> findAll(Integer page, Integer size, SysUnit unit) {
        PageHelper.startPage(page, size);
        List<SysUnit> sysUnits = sysUnitMapper.findAll(unit);
        PageInfo<SysUnit> pageInfo = new PageInfo<>(sysUnits);
        return pageInfo;
    }

    /**
     * 查询厂商、设计方查询
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysUnit> getAll(Integer page, Integer size, SysUnit unit) {
        PageHelper.startPage(page, size);
        List<SysUnit> sysUnits = sysUnitMapper.getAll(unit);
        PageInfo<SysUnit> pageInfo = new PageInfo<>(sysUnits);
        return pageInfo;
    }

    /**
     * 功能描述: 查询用户能看到的树形结构
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 14:33
     */
    @Override
    @Transactional(readOnly = true)
    public UnitTreeVo getUnitTree(SysUnit unit) {
        return unitTreeUtis.getUnitTree(unit.getUnitId());
    }

    /**
     * 功能描述: 查询用户能看到的树形结构（通过组织类型查询）
     *
     * @param:
     * @return:
     * @auther: wz
     * @date: 2019/8/30 16:38
     */
    @Override
    @Transactional(readOnly = true)
    public UnitTreeVo getUnitTreeByType(SysUnit unit) {
        return unitTreeUtis.getUnitTreeByType(unit);
    }

    /**
     * 功能描述:  通过属主Id 查询组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/21 11:57
     */
    @Override
    public PageInfo<SysUnit> selectPageInfoHierarchy(Integer page, Integer size, SysUnit unit) {
        PageHelper.startPage(page, size);
        List<SysUnit> sysUnits = sysUnitMapper.findHierarchyUnit(unit);
        PageInfo<SysUnit> pageInfo = new PageInfo<>(sysUnits);
        return pageInfo;
    }


    @Override
    @Transactional(readOnly = true)
    public List<SysUnitPgVO> selectAllICN() {
        return sysUnitMapper.selectAllICN();

    }

    /**
     * 判断仓库编号是否可用
     *
     * @author HHe
     * @date 2019/8/24 10:04
     */
    @Override
    public UnitOptionVo judgewarehavailable(String warehCode, SysUser sysUser) {
        return sysUnitMapper.judgewarehavailable(warehCode, sysUser.getOwnerId());
    }

    /**
     * 根据code判断组织是否存在
     *
     * @author HHe
     * @date 2019/8/26 11:02
     */
    @Override
    @Transactional(readOnly = true)
    public SysUnit queryUnitByCode(String warehCode) {
        return sysUnitMapper.queryUnitByCode(warehCode);
    }

    /**
     * 功能描述:  查询组织弹窗(根据用户属主Id)通用
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/27 10:17
     */
    @Override
    public PageInfo<UnitOptionVo> selectOptionUnit(Integer page, Integer size, SysUnit unit, SysUser suser) {
        PageHelper.startPage(page, size);
        List<UnitOptionVo> list = sysUnitMapper.selectUnitOption(unit);
        PageInfo<UnitOptionVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据code模糊查询出对象集合
     *
     * @author HHe
     * @date 2019/9/11 10:23
     */
    @Override
    public List<SysUnit> queryUnitByConCatCode(String warehCode) {
        return sysUnitMapper.queryUnitByConCatCode(warehCode);
    }

    /**
     * 根据id查询集合
     *
     * @author HHe
     * @date 2019/9/11 14:52
     */
    @Override
    public List<SysUnit> queryUnitByIds(Set<Long> sysUnitIds) {
        return sysUnitMapper.queryUnitByIds(sysUnitIds);
    }

    /**
     * 模糊查询组织
     *
     * @author HHe
     * @date 2019/9/15 15:37
     */
    @Override
    public List<SysUnit> queryUnitLikeProp(SysUnit sysUnit) {
        return sysUnitMapper.queryUnitLikeProp(sysUnit);
    }

    /**
     * 往来账户组织弹窗
     *
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    public PageInfo<UnitOptionVo> selectByCaOption(UnitOptionVo vo, Integer page, Integer size, SysUser user) {
        PageInfo<UnitOptionVo> pageInfo = null;
        List<UnitOptionVo> list = null;
        //系统管理员
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            PageHelper.startPage(page, size);
            list = sysUnitMapper.selectByCaAll(vo);
        } else {
            PageHelper.startPage(page, size);
            list = sysUnitMapper.selectByCaOption(vo);
        }
        pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    /**
     * 查询组织封装到model中
     * @param ownerUnitModelList 封装组织id信息
     * @return 封装好的组织信息
     * @author HHe
     * @date 2019/11/18 12:01
     */
    @Override
    public List<OwnerUnitModel> queryUnit2Model(List<OwnerUnitModel> ownerUnitModelList) {
        return null;
    }

    /**
     * 功能描述:  查询组织是否是系统组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/26 16:50
     */
    @Override
    public boolean getIsAdminUnit(Long unitId) {
        if (sysUnitMapper.getAdminUnit(unitId) != null && sysUnitMapper.getAdminUnit(unitId).getUnitId() != null) {
            return true;
        }
        return false;
    }

    /**
     * 购销协议弹窗
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UnitOptionVo> selectByOption(UnitOptionVo vo, Integer page, Integer size, SysUser user) {
        PageInfo<UnitOptionVo> pageInfo = null;
        //系统管理员
        if (false) {
            PageHelper.startPage(page, size);
            List<UnitOptionVo> list = sysUnitMapper.selectByAll(vo);
            pageInfo = new PageInfo<>(list);
            return pageInfo;
        }
        PageHelper.startPage(page, size);
        List<UnitOptionVo> list = sysUnitMapper.selectByOption(vo);
        pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 采购商/供应商代码弹窗
     * @param vo
     * @param page
     * @param size
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UnitOptionVo> selectByOptionCode(UnitOptionVo vo, Integer page, Integer size, SysUser sysUser) {
        PageInfo<UnitOptionVo> pageInfo = null;
        PageHelper.startPage(page, size);
        List<UnitOptionVo> list = sysUnitMapper.selectByOptionCode(vo);
        pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 购销协议中输入编号查询相关数据
     * @param vo
     * @return
     */
    @Override
    public UnitOptionVo findOptionByNum(UnitOptionVo vo) {
        return sysUnitMapper.findOptionByNum(vo);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<UnitOptionVo> selectByOptionAll(UnitOptionVo vo, Integer page, Integer size) {
        PageInfo<UnitOptionVo> pageInfo;
        PageHelper.startPage(page, size);
        List<UnitOptionVo> list = sysUnitMapper.selectByOptionAll(vo);
        pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 弹窗
     * @param vo
     * @param page
     * @param size
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UnitOptionVo> selectByOption(UnitOptionVo vo, Integer page, Integer size) {
        PageInfo<UnitOptionVo> pageInfo;
        PageHelper.startPage(page, size);
        List<UnitOptionVo> list = sysUnitMapper.selectByOption(vo);
        pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 功能描述: 组织通用弹窗(单条查询)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 17:19
     */
    @Override
    public Object selectObject(CommonWindowModel model, SysUser user) {
        if ("sys_unit".equalsIgnoreCase(model.getSelectType())) {
            return sysUnitMapper.selectObject(model);
        } else if ("ca_sys_unit".equalsIgnoreCase(model.getSelectType())) {
            //往来账户中往来组织/中转方弹窗
            return sysUnitMapper.selectCaObject(model);
        }
        return null;

    }


}
