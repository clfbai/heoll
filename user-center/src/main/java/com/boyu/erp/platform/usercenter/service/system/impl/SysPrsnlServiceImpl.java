package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysPrsnlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserMapper;
import com.boyu.erp.platform.usercenter.model.option.PrsnlOptionModel;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.model.system.SysPrsnlModel;
import com.boyu.erp.platform.usercenter.service.system.SysPrsnlService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.PasswordUtils;
import com.boyu.erp.platform.usercenter.utils.PrsnlUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlPgVO;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class SysPrsnlServiceImpl implements SysPrsnlService {

    @Autowired
    private SysPrsnlMapper prsnlMapper;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private UsersService usersService;


    /**
     * 功能描述:  查询组织下用户(组织筛选用户)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 14:51
     */
    @Override
    public PageInfo<SysPrsnlVo> selectPrsnlAndUint(Integer page, Integer size, SysPrsnlVo prsnl) {
        PageHelper.startPage(page, size);
        List<SysPrsnlVo> likeMin = prsnlMapper.selectPrsnlAndUser(prsnl);
        PageInfo<SysPrsnlVo> pageInfo = new PageInfo<>(likeMin);
        return pageInfo;
    }

    /**
     * 查询人员弹窗（根据属主Id 通用）
     */
    @Override
    public PageInfo<PrsnlOptionModel> getOwnerPrsnl(Integer page, Integer size, SysPrsnlModel prsnl, SysUser sessionSysUser) {
        prsnl.setUnitHierarchy(unitMapper.selectByPrimaryKey(prsnl.getUnitId()).getUnitHierarchy());
        PageHelper.startPage(page, size);
        List<PrsnlOptionModel> sysPrsnls = prsnlMapper.getOwnerPrsnl(prsnl);
        PageInfo<PrsnlOptionModel> pageInfo = new PageInfo<>(sysPrsnls);
        return pageInfo;
    }

    /**
     * 用户装入人员查询(可组织筛选)
     */
    @Override
    public PageInfo<SysPrsnlVo> selectPrsnlUserList(Integer page, Integer size, SysPrsnlVo record) {
        PageHelper.startPage(page, size);
        List<SysPrsnlVo> likeMin = prsnlMapper.selectPrsnlUserList(record);
        PageInfo<SysPrsnlVo> pageInfo = new PageInfo<>(likeMin);
        return pageInfo;
    }

    /**
     * 根据Id查询用户信息
     *
     * @author HHe
     * @date 2019/9/12 10:39
     */
    @Override
    public List<SysPrsnl> queryPrsnlByIds(Set<Long> sysPrsnls) {
        return prsnlMapper.queryPrsnlByIds(sysPrsnls);
    }

    @Override
    @Transactional(readOnly = true)
    public SysPrsnl selectByPrsnlCode(String prsnlCode) {

        return prsnlMapper.selectByPrsnlCode(prsnlCode);
    }

    /**
     * 分页查询（查询人员表所有)
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysPrsnl> selectAll(Integer page, Integer size, SysPrsnl prsnl) {
        PageHelper.startPage(page, size);
        List<SysPrsnl> sysPrsnls = prsnlMapper.selectAll(prsnl);
        PageInfo<SysPrsnl> pageInfo = new PageInfo<>(sysPrsnls);
        return pageInfo;
    }

    /**
     * 修改人员信息 用户信息
     */
    @Override
    public int changePrsnl(SysPrsnlVo prsnl, SysUser user) {
        return updatePrsnlAndUser(prsnl, user);
    }

    /**
     * 添加人员信息
     */
    @Override
    public Long insertPrsnl(SysPrsnlVo prsnl, SysUser user) {

        return insertPrsnlAndUser(prsnl, user);
    }


    /**
     * 用户查看当前组织下人员信息（管理员，系统管理员，普通用户)
     */
    @Override
    @Transactional(readOnly = true)

    public List<SysPrsnlVo> getPrsnlAndUser(SysUser user, SysPrsnlVo prsnl) {


        if (usersService.getAdmin(user) != null) {
            if (usersService.getAdmin(user).getOprId() == -1) {
                return prsnlMapper.selectPrsnlAndUser(prsnl);
            }
            if (StringUtils.isNotEmpty(prsnl.getFullName()) || StringUtils.isNotEmpty(prsnl.getPrsnlCode())
                    || StringUtils.isNotEmpty(prsnl.getGender()) || StringUtils.isNotEmpty(prsnl.getUserStatus())) {
                List<SysPrsnlVo> likeMin = prsnlMapper.selectPrsnlAndUser(prsnl);
                System.out.println("组织筛选： " + likeMin.size());
                prsnl.setPrsnlCode(null);
                prsnl.setFullName(null);
                List<SysPrsnlVo> max = prsnlMapper.selectPrsnlAndUser(prsnl);

                return PrsnlUtils.getPrsnlisNull(max, likeMin, user, prsnl);
            }
            return PrsnlUtils.getPrsnl(prsnlMapper.selectPrsnlAndUser(prsnl), user);
        }
        return null;
    }

    /**
     * 查询设计人员
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<PrsnlOptionModel> findByDesign(Integer page, Integer size, SysPrsnl record) {
        PageHelper.startPage(page, size);
        List<PrsnlOptionModel> sysPrsnls = prsnlMapper.findByDesign(record);
        PageInfo<PrsnlOptionModel> pageInfo = new PageInfo<>(sysPrsnls);
        return pageInfo;
    }

    /**
     * 查询人员弹窗（因为设计人员要修改所以用上面内容)
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<PrsnlOptionModel> getOpetionPrsin(Integer page, Integer size, SysPrsnl record, SysUser user) {
        //查询用户的层级 判断用户能看到多少人
        record.setUnitHierarchy(prsnlMapper.selectById(user.getUserId()).getUnitHierarchy());
        PageHelper.startPage(page, size);
        List<PrsnlOptionModel> sysPrsnls = prsnlMapper.findByDesign(record);
        PageInfo<PrsnlOptionModel> pageInfo = new PageInfo<>(sysPrsnls);
        return pageInfo;
    }

    @Override
    public List<SysPrsnlPgVO> selectAllICN() {
        return prsnlMapper.selectAllICN();
    }


    @Override
    @Transactional(readOnly = true)
    public SysPrsnl selectById(long id) {
        return prsnlMapper.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SysPrsnlVo selectPrsnlAndUserByid(SysPrsnlVo record) {
        return prsnlMapper.selectPrsnlAndUserByid(record);
    }


    /**
     * 修改人员及用户
     *
     * @param prsnl
     * @return
     */
    private int updatePrsnlAndUser(SysPrsnlVo prsnl, SysUser sessionuser) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(prsnl, user);//对象拷贝
        user.setUpdTime(new Date());//操作时间
        user.setOprId(sessionuser.getUserId());//操作员id
        user.setUserId(prsnl.getUserId());//修改的id

        SysPrsnl prsnl1 = new SysPrsnl();
        BeanUtils.copyProperties(prsnl, prsnl1);//对象拷贝
        prsnl1.setUpdTime(new Date());//操作时间
        prsnl1.setOprId(sessionuser.getUserId());//操作员id
        prsnl1.setPrsnlId(prsnl.getUserId());

        System.out.println(user.getUserId() + "    " + user.getOwnerId() + "    " + user.getUserStatus());
        userMapper.updateByPrimaryKeySelective(user);
        return prsnlMapper.updateByPrimaryKeySelective(prsnl1);
    }

    /**
     * 添加人员及用户
     *
     * @param prsnl
     * @return
     */
    private Long insertPrsnlAndUser(SysPrsnlVo prsnl, SysUser sessionuser) {
        SysPrsnl prsnl1 = new SysPrsnl();
        BeanUtils.copyProperties(prsnl, prsnl1);//对象拷贝
        prsnl1.setUpdTime(new Date());//操作时间
        prsnl1.setOprId(sessionuser.getUserId());//操作员id
        prsnl1.setCtrlUnitId(prsnl.getOwnerId());//管理组织id
        prsnl1.setUnitHierarchy(unitMapper.selectByPrimaryKey(prsnl.getOwnerId()).getUnitHierarchy());
        prsnl1.setPrsnlStatus(CommonFainl.USER_STATUS);
        //插入prsnl表
        prsnlMapper.insertPrsnl(prsnl1);
        SysUser user = new SysUser();
        BeanUtils.copyProperties(prsnl, user);//对象拷贝
        user.setOwnerId(prsnl.getOwnerId());//属主id
        user.setOprId(sessionuser.getUserId());//操作员id
        user.setUserPswd(PasswordUtils.encryptionPassword("123456"));//默认密码
        user.setUpdTime(new Date());//操作时间
        user.setUserId(prsnl1.getPrsnlId());//修改的id
        user.setMachCtrl("F");//机器控制
        user.setUserStatus(CommonFainl.USER_STATUS);//用户状态
        //插入user表
        userMapper.insertSelective(user);
        return prsnl.getPrsnlId();
    }

    /**
     * 用户查看当前组织下人员信息
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysPrsnlVo> selectPrsnlAndUser(Integer page, Integer size, SysPrsnlVo record) {
        PageHelper.startPage(page, size);
        List<SysPrsnlVo> sysPrsnlVos = prsnlMapper.selectPrsnlAndUser(record);
        PageInfo<SysPrsnlVo> pageInfo = new PageInfo<>(sysPrsnlVos);
        return pageInfo;
    }

    /**
     * 功能描述: 通用弹窗方法
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 19:42
     */
    @Override
    public Object selectObject(CommonWindowModel model, SysUser user) {
        if("sys_prsnl".equalsIgnoreCase(model.getSelectType())){
          return   prsnlMapper.selectObject(model);
        }
        return null;
    }
}