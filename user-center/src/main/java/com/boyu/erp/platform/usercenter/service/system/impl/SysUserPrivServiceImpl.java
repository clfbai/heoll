package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SysUserPa;
import com.boyu.erp.platform.usercenter.entity.system.SysUserPrivKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysPrivilegeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserPaMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserPrivMapper;
import com.boyu.erp.platform.usercenter.model.system.UserPrivModel;
import com.boyu.erp.platform.usercenter.service.system.SysUserPrivService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysUserPrivServiceImpl implements SysUserPrivService {

    @Autowired
    private SysUserPrivMapper userPrivMapper;

    @Autowired
    private SysUserPaMapper userPaMapper;
    @Autowired
    private SysPrivilegeMapper privilegeMapper;

    /**
     * 分页查询
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<SysPrivilege> selectAll(Integer page, Integer size, SysUserPrivKey userPrivKey) {
        PageHelper.startPage(page, size);
        SysUser user = new SysUser();
        user.setUserId(userPrivKey.getUserId());
        user.setOwnerId(userPrivKey.getOwnerId());
        List<SysPrivilege> sysUserPrivKeys = privilegeMapper.getuserPriv(user);
        PageInfo<SysPrivilege> pageInfo = new PageInfo<>(sysUserPrivKeys);
        return pageInfo;
    }

    /**
     * 添加用户权限
     */
    @Override
    public int insertSelective(List<SysUserPrivKey> record, SysUser user) {
        int count = 0;


        for (SysUserPrivKey k : record) {
            if (k.getUnitId() != null && k.getUnitId() != 0L) {
                k.setUgId(-1L);
            } else {
                k.setUnitId(0L);
            }
            SysUserPa pa = new SysUserPa();
            pa.setUserId(k.getUserId());//用户id
            pa.setPrivId(k.getPrivId());//权限id
            List<SysUserPa> sysUserPas = userPaMapper.selectAll(pa);
            pa.setOwnerId(k.getOwnerId());//属主id
            pa.setUnlimited(k.getUnlimited());//全局
            pa.setUgId(k.getUgId());//分组id
            pa.setUnitId(k.getUnitId());//组织
            pa.setOpTime(new Date());//操作时间
            pa.setOprId(user.getUserId());//操作员id
            pa.setGrRv("G");
            if (sysUserPas == null || sysUserPas.size() == 0) {
                userPaMapper.insertSelective(pa);
            } else {
                userPaMapper.accreditRecycle(pa);
            }
            count++;
        }
        userPrivMapper.insertList(record);
        return count;
    }

    /**
     * 删除用户权限
     */
    @Override
    public int deleteUserPriv(List<SysUserPrivKey> record, SysUser user) {
        int count = 0;
        for (SysUserPrivKey k : record) {
            SysUserPa pa = new SysUserPa();
            pa.setUserId(k.getUserId());//用户id
            pa.setPrivId(k.getPrivId());//权限id
            pa.setGrRv("R");//授权回收
            pa.setOprId(user.getUserId());//操作员id
            pa.setOpTime(new Date());//操作时间
            userPaMapper.accreditRecycle(pa);

        }
        userPrivMapper.deleteList(record);
        return count;
    }

    /**
     * 功能描述: 批量修改用户权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 19:56
     */
    @Override
    public void updateUserPriv(UserPrivModel model, SysUser user) throws ServiceException {
        //增加
        if (model.getDeleteUserPriv() != null && model.getDeleteUserPriv().size() > 0) {
            this.deleteUserPriv(model.getDeleteUserPriv(), user);
        }
        //删除
        if (model.getAddUserPriv() != null && model.getAddUserPriv().size() > 0) {
            this.insertSelective(model.getAddUserPriv(), user);
        }
    }


}
