package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUg;
import com.boyu.erp.platform.usercenter.entity.system.SysUgDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUgDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUgMapper;
import com.boyu.erp.platform.usercenter.model.system.UgAndUgDtlModel;
import com.boyu.erp.platform.usercenter.service.system.UgServer;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.system.UgVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class UgServerImpl implements UgServer {
    @Autowired
    private SysUgMapper ugMapper;
    @Autowired
    private SysUgDtlMapper ugDtlMapper;
    @Autowired
    private UsersService usersService;


    /**
     * 功能描述:  组织分组分页查询
     *
     * @param ug (过滤对象)
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:07
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UgVo> getUg(Integer page, Integer size, SysUg ug, SysUser user) {
        if (usersService.getIsAdmin(user)) {
            ug.setOwnerId(null);
        } else {
            ug.setOwnerId(user.getOwnerId());
        }
        PageHelper.startPage(page, size);
        List<UgVo> ugList = ugMapper.getUg(ug);
        PageInfo<UgVo> pageInfo = new PageInfo<>(ugList);
        return pageInfo;
    }

    /**
     * 功能描述: 组织分组明细查询
     *
     * @param dtl (组织分组明细对象)
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:08
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysUgDtl> getUgDtl(SysUgDtl dtl, SysUser user) {
        return ugDtlMapper.getUgDtl(dtl);
    }

    /**
     * 功能描述: 增加织分组
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 14:20
     */
    @Override
    public int addUg(SysUg ug, UgAndUgDtlModel list, SysUser user) throws ServiceException {
        ug.setOwnerId(user.getOwnerId());
        ug.setOprId(user.getUserId());
        ug.setUpdTime(new Date());
        int a = ugMapper.insertSelective(ug);
        //存在明细数据增加
        if (list.getAddUgDtl() != null && list.getAddUgDtl().size() > 0) {
            list.getAddUgDtl().parallelStream().forEach(s -> s.setUgId(ug.getUgId()));
            list.setDeleteUgDtl(null);
            a += this.updateUgDtl(list);
        }
        return a;
    }

    @Override
    public int upadteUg(UgAndUgDtlModel model, SysUser user) throws ServiceException {
        SysUg ug = model.getUg();
        ug.setUpdTime(new Date());
        ug.setOprId(user.getUserId());
        //判断有无明细需要修改
        this.updateUgDtl(model);
        return ugMapper.updateByPrimaryKeySelective(ug);
    }

    @Override
    public int deleteUg(SysUg ug, SysUser user) throws ServiceException {
        int a = ugMapper.deleteByPrimaryKey(ug.getUgId());
        //删除对应的明细
        a += ugDtlMapper.deleteUg(ug.getUgId());
        return a;
    }

    @Override
    public int addUgDtl(SysUgDtl dtl, SysUser user) throws ServiceException {
        return 0;
    }

    @Override
    public int upadteUgDtl(UgAndUgDtlModel model, SysUser user) throws ServiceException {
        /**
         * 批量修改组织分组明细
         *
         */
        return this.updateUgDtl(model);

    }

    @Override
    public int deleteUgDtl(SysUgDtl dtl, SysUser user) throws ServiceException {
        return 0;
    }

    /**
     * 功能描述:  判断能否将分组授予与用户
     *
     * @param ugId (分组Id)
     * @param user (用户)
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 12:12
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> seletUgUser(SysUser user, Long ugId) {
        //判断用户所在组织是否包含分组所有的组织Id 不是组不能将该分组授予此用户
        SysUgDtl dtl = new SysUgDtl();
        dtl.setUgId(ugId);
        List<SysUgDtl> dtls = ugDtlMapper.getUgDtl(dtl);
        List<SysUnit> units = ugDtlMapper.getUgList(user.getOwnerId());
        List<SysUgDtl> save = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dtls)) {
            if (CollectionUtils.isNotEmpty(units)) {
                for (SysUgDtl d : dtls) {
                    for (SysUnit u : units) {
                        if (d.getMbrId().equals(u.getUnitId())) {
                            //相等添加到比较集合
                            save.add(d);
                        }
                    }
                }
                if (save.size() == dtls.size()) {
                    map.put("bo", true);
                    return map;
                } else {
                    map.put("bo", false);
                    dtls.removeAll(save);
                    String str = "";
                    for (SysUgDtl d : dtls) {
                        str += d.getMbrId() + "、";
                    }
                    map.put("list", "不能将" + str + "组织授予同集或下级用户");
                    return map;
                }
            } else
                map.put("bo", false);
            map.put("list", "该用户已禁用");
            return map;
        } else
            map.put("bo", true);
        return map;
    }

    /**
     * 功能描述: 查询组织分组单条信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 17:24
     */
    @Override
    public SysUg getUgId(SysUg ug) {
        return ugMapper.selectByPrimaryKey(ug.getUgId());
    }


    public int updateUgDtl(UgAndUgDtlModel model) {
        int a = 0;
        if (CollectionUtils.isNotEmpty(model.getAddUgDtl())) {
            //增加分组明细是 会删除所有拥有该分组权限(简单处理防止权限越界)
            SysUg ug = new SysUg();
            ug.setUgId(model.getAddUgDtl().get(0).getUgId());
            if (ugDtlMapper.getCountUg(ug) > 0) {
                ugDtlMapper.deleteUgPriv(ug);
            }
            a = ugDtlMapper.addUgDtlList(model.getAddUgDtl());
        }
        if (model.getDeleteUgDtl() != null && model.getDeleteUgDtl().size() > 0) {
            a += ugDtlMapper.deleteUgDtlList(model.getDeleteUgDtl());
        }
        return a;
    }
}
