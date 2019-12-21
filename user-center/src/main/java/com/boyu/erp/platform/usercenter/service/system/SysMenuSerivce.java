package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysMenu;
import com.boyu.erp.platform.usercenter.entity.system.SysMenuDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.TreeModel;
import com.boyu.erp.platform.usercenter.vo.system.TreeVo;

import java.util.List;

public interface SysMenuSerivce {
    /**
     * 根据目录Id查询所有菜单
     *
     * @return
     */
    List<SysMenuDtl> getMenuTree(SysMenu menu);


    /**
     * 查询用户菜单
     */
    List<TreeVo> getUserTreeOne(SysUser user);

    /**
     * 添加目录菜单
     */
    int addTree(SysMenuDtl menuDtl);

    /**
     * 查询当前节点信息
     */
    SysMenuDtl findByMenudtl(SysMenuDtl menuDtl);

    /**
     * 查询目录详情表所有信息
     * 用途:添加目录时遍历判断有无重复
     */
    List<SysMenuDtl> getAll();

    /**
     * 通过目录 NodeId   查询下级目录
     */
    List<TreeModel> treeSube(TreeModel menuDtl, SysUser user);

    /**
     * 删除目录
     * 逻辑删除
     */
    void deletTree(TreeModel menuDtl);

    /**
     * 修改目录
     */
     int updateTree(TreeModel menuDtl);

    /**
     * 根据节点查询目录详细信息
     */
    SysMenuDtl findByNodeId(TreeModel menuDtl);
}
