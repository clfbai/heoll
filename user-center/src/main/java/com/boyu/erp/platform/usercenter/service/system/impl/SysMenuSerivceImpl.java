package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysMenu;
import com.boyu.erp.platform.usercenter.entity.system.SysMenuDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysMenuDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysPrivilegeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserMapper;
import com.boyu.erp.platform.usercenter.service.system.SysMenuSerivce;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.TreeUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.TreeModel;
import com.boyu.erp.platform.usercenter.vo.system.TreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SysMenuSerivceImpl implements SysMenuSerivce {

    @Autowired
    private SysMenuDtlMapper menuDtlMapper;
    @Autowired
    private SysPrivilegeMapper privilegeMapper;

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private UsersService usersService;

    /**
     * 查询用户是否是管理员是管理返回组织权限
     * 不是返回用户权限
     */
    @Transactional(readOnly = true)
    public List<TreeVo> getAdminTree(SysUser user) {
        if (userMapper.getAdmin(user) != null && userMapper.getAdmin(user).getOprId() == -1) {
            //超级管理员菜单
            return menuDtlMapper.adminTree(user);
        }
        //组织管理员权限
        return menuDtlMapper.unitTree(user);
    }


    /**
     * 查询指定目录的菜单Tree
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysMenuDtl> getMenuTree(SysMenu menu) {
        return menuDtlMapper.getMenuTree(menu);
    }


    /***
     * 查询用户下属菜单（递归查询）
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TreeVo> getUserTreeOne(SysUser user) {
        List<TreeVo> list = new ArrayList<>();
        List<TreeVo> menuDtls;
        if (usersService.getIsAdmin(user) || usersService.getIsLeader(user)) {
            menuDtls = getAdminTree(user);
        } else {
            menuDtls = menuDtlMapper.getUserTree(user);
        }

        //菜单递归
        return TreeUtils.getTree(menuDtls);
    }


    /***
     * 添加目录菜单
     * @param menuDtl
     * @return
     */
    @Override
    public int addTree(SysMenuDtl menuDtl) {
        return menuDtlMapper.insertSelective(menuDtl);
    }


    /**
     * 查询当前节点信息
     */
    @Override
    @Transactional(readOnly = true)
    public SysMenuDtl findByMenudtl(SysMenuDtl menuDtl) {
        return menuDtlMapper.findByMenudtl(menuDtl);
    }

    /**
     * 查询目录详情表所有信息
     * 用途:添加目录时遍历判断有无重复
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysMenuDtl> getAll() {
        return menuDtlMapper.getAll();
    }

    /**
     * 通过目录 NodeId   查询下级目录
     */
    @Override
    @Transactional(readOnly = true)
    public List<TreeModel> treeSube(TreeModel menuDtl, SysUser user) {
        List<TreeVo> menuDtls;
        List<TreeModel> tr = new ArrayList<>();
        if (usersService.getIsLeader(user) || usersService.getIsAdmin(user)) {
            menuDtls = getAdminTree(user);
        } else {
            menuDtls = menuDtlMapper.getUserTree(user);
        }
        List<TreeModel> Tmode = menuDtlMapper.findById(menuDtl);
        for (TreeVo vo : menuDtls) {
            for (TreeModel m : Tmode) {
                if (vo.getNodeId().equals(m.getNodeId())) {
                    tr.add(m);
                }
            }
        }

        return tr;
    }

    /**
     * 修改目录
     */
    @Override
    public int updateTree(TreeModel menuDtl) {
        SysMenuDtl dtl = new SysMenuDtl();
        BeanUtils.copyProperties(menuDtl, dtl);

        return menuDtlMapper.updateByPrimaryKeySelective(dtl);
    }

    /**
     * 根据节点查询目录详细信息
     */
    @Override
    @Transactional(readOnly = true)
    public SysMenuDtl findByNodeId(TreeModel menuDtl) {
        SysMenuDtl dtls = new SysMenuDtl();
        BeanUtils.copyProperties(menuDtl, dtls);
        return menuDtlMapper.findByMenudtl(dtls);
    }

    /**
     * 删除目录
     * 1.是否是节点
     * 节点需删除节点下所有目录
     */
    @Override
    public void deletTree(TreeModel menuDtl) {

        Long oneNodeId = menuDtl.getNodeId();
        SysMenuDtl s = new SysMenuDtl();
        s.setNodeId(menuDtl.getNodeId());
        SysMenuDtl sysMenuDtl = menuDtlMapper.findByMenudtl(s);
        if (sysMenuDtl.getIsPath().equalsIgnoreCase(CommonFainl.TRUE)) {
            SysMenu menu = new SysMenu();
            menu.setMenuId(sysMenuDtl.getMenuId());
            /**
             * *递归获得节点下所有 目前不用(此方法通用)
             * List<SysMenuDtl> deleteMenu = TreeUtils.getByTreeNodeId(menuDtl, menuDtlMapper.getMenuTree(menu));
             * */
            //第二种方法 因为数据库的巧妙设计 (此方法不通用)
            //获得隶属关系
            String str = sysMenuDtl.getHierarchy();
            //进行拼接隶属关系
            String strsy = sysMenuDtl.getHierarchy() + sysMenuDtl.getNodeId() + "|";
            for (SysMenuDtl m : menuDtlMapper.getMenuTree(menu)) {
                if (m.getHierarchy().length() >= strsy.length()) {
                    //判断有哪些下级需要删除
                    if (m.getHierarchy().substring(0, strsy.length()).equalsIgnoreCase(strsy)) {
                        m.setIsDel(CommonFainl.FALSE);
                        //打标删除
                        menuDtlMapper.updateByPrimaryKeySelective(m);
                    }
                }
            }
        }
        SysMenuDtl ms = new SysMenuDtl();
        ms.setNodeId(oneNodeId);
        ms.setIsDel(CommonFainl.FALSE);
        //打标删除当前节点
        menuDtlMapper.updateByPrimaryKeySelective(ms);
    }


}
