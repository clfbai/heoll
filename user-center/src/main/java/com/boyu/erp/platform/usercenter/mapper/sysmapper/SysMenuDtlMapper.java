package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysMenu;
import com.boyu.erp.platform.usercenter.entity.system.SysMenuDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.TreeModel;
import com.boyu.erp.platform.usercenter.vo.system.TreeVo;

import java.util.List;

public interface SysMenuDtlMapper {
    /**
     * （慎用）
     * 根据功能Id删除目录
     */
    int deleteByPrimaryKey(SysMenuDtl nodeId);
    /**
     *
     * 根据目录Id删除目录
     */
    int deleteById(TreeModel menuDtl);

    int insertSelective(SysMenuDtl record);


    int updateByPrimaryKeySelective(SysMenuDtl record);

    /**
     * 查询指定目录Id下所有菜单
     */
    public List<SysMenuDtl> getMenuTree(SysMenu menu);

    /**
     * 查询用户所有菜单
     */
    public List<TreeVo> getUserTree(SysUser user);

    /**
     * 查询系统管理员菜单
     */
    public List<TreeVo> adminTree(SysUser user);

    /**
     * 查询组织管理员菜单
     */
    public List<TreeVo> unitTree(SysUser user);

    /**
     * 查询当前节点信息
     */
    public SysMenuDtl findByMenudtl(SysMenuDtl menuDtl);


    /**
     * 查询目录详情表所有信息
     */
    List<SysMenuDtl> getAll();

    /**
     * 通过目录Id查询下级节点
     */
    List<TreeModel> findById(TreeModel menuDtl);


}