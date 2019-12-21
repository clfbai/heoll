package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysRefNumDtlMapper;
import com.boyu.erp.platform.usercenter.redis.redisutils.RedisUtils;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumDtlSerivce;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 编号业务层
 * @author: clf
 * @create: 2019-05-15 09:59
 */
@Slf4j
@Service
@Transactional
public class SysRefNumDtlSerivceImpl implements SysRefNumDtlSerivce {
    @Autowired
    private SysRefNumDtlMapper refNumDtlMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Transactional(readOnly = true)
    public SysRefNumDtl findById(SysRefNumDtl sysRefNumDtl) {
        return refNumDtlMapper.finbyId(sysRefNumDtl);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysRefNumDtl> getPage(Integer page, Integer size, SysRefNumDtl sysRefNumDtl) {
        PageHelper.startPage(page, size);
        List<SysRefNumDtl> list = refNumDtlMapper.selectByPrimary(sysRefNumDtl);
        PageInfo<SysRefNumDtl> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }

    @Override
    @Transactional(readOnly = true)
    public List<SysRefNumDtl> getAll(SysRefNumDtl refNumdtl, SysUser user) {
        SysUser u = usersService.getAdmin(user);
        if (u != null) {
            if (u.getOprId() == CommonFainl.ADMIN) {
                refNumdtl.setUnitId(null);
                refNumdtl.setAdminId(CommonFainl.ADMIN);
                return refNumDtlMapper.selectByPrimary(refNumdtl);
            }
            refNumdtl.setAdminId(CommonFainl.LOWSER);
            return refNumDtlMapper.selectByPrimary(refNumdtl);
        }
        return refNumDtlMapper.selectByPrimary(refNumdtl);
    }

    /**
     * 获得最大编号 和增量
     */
    @Override
    @Transactional(readOnly = true)
    public SysRefNumDtl getLastStep(SysRefNumDtl sysRefNumDtl) {
        return refNumDtlMapper.getLastStep(sysRefNumDtl);
    }

    /**
     * 根据 主键字段、增量、和最大编号生成当前唯一Id (目前仅支持生成number)
     *
     * @param flied 主键
     * @return max
     */
    @Override
    public Long creatId(String flied) {
        SysRefNumDtl sysRefNum = new SysRefNumDtl();
        sysRefNum.setRefNumId(flied);
        sysRefNum = refNumDtlMapper.getLastStep(sysRefNum);
        /**
         * LastNum(最大值)
         * StepSize(增量)
         * */
        Long max = sysRefNum.getLastNum() + sysRefNum.getStepSize();
        sysRefNum.setLastNum(max);
        return max;
    }

    @Override
    @Transactional(readOnly = true)
    public Long selectLum(SysRefNumDtl flied) {
        return refNumDtlMapper.getLastNum(flied);
    }
    /**
     * 根据编号Id和组织Id查询编号Dtl
     * @author HHe
     * @date 2019/9/19 19:55
     */
    @Override
    public SysRefNumDtl querySysRefDtlByNumIdAndUnitId(SysRefNumDtl sysRefNumDtl) {
        SysRefNumDtl refNumDtl =  refNumDtlMapper.querySysRefDtlByNumIdAndUnitId(sysRefNumDtl);
        if(refNumDtl==null){
            SysRefNumDtl defaultNum = new SysRefNumDtl();
            defaultNum.setRefNumId("DEFAULT_NUM");
            defaultNum.setUnitId(0L);
            refNumDtl = refNumDtlMapper.querySysRefDtlByNumIdAndUnitId(defaultNum);
        }
        return refNumDtl;
    }
    /**
     * 自增最大编号并且返回
     * @author HHe
     * @date 2019/9/20 10:58
     */
    @Override
    public Long updateAutoIncrementLastNum(SysRefNumDtl sysRefNumDtl, Long stepSize) {
        return refNumDtlMapper.updateAutoIncrementLastNum(sysRefNumDtl, stepSize);
    }

    @Override
    public int updateRefNumDtl(SysRefNumDtl sysRefNumDtl) {
        return refNumDtlMapper.updateByPrimaryKeySelective(sysRefNumDtl);
    }

    @Override
    public int addRefNumDtl(SysRefNumDtl sysRefNumDtl) {
        return refNumDtlMapper.insertSelective(sysRefNumDtl);
    }


}
