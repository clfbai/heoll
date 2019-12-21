package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivDepKey;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.model.PrivdelModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysPrivDepMapper {
    /**
     * 通过权限Id和权限依赖Id删除
     */
    int deleteByPrimaryKey(SysPrivDepKey key);

    /**
     * 通过权限Id删除，删除权限时使用
     */
    int deletePrivDel(SysPrivDepKey key);


    int insert(SysPrivDepKey record);

    int insertSelective(SysPrivDepKey record);

    /**
     * 添加权限Id查询是否重复
     */
    SysPrivDepKey findByPrivDel(SysPrivDepKey key);

    /**
     * 通过权限Id查询权限依赖
     */

    List<SysPrivDepKey> findByPrivId(SysPrivDepKey key);

    /**
     * 修改权限依赖
     */
    int updatePrivDel(PrivdelModel depKey);

    /**
     * 功能描述: 批量查询权限依赖
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 15:46
     */
    List<SysPrivilege> getList(@Param("list") Set<SysPrivilege> list);


}