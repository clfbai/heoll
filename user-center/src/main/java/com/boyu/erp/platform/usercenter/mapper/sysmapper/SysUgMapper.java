package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUg;
import com.boyu.erp.platform.usercenter.vo.system.UgVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysUgMapper {
    int deleteByPrimaryKey(Long ugId);

    int insert(SysUg record);

    int insertSelective(SysUg record);

    SysUg selectByPrimaryKey(Long ugId);

    int updateByPrimaryKeySelective(SysUg record);

    int updateByPrimaryKey(SysUg record);

    List<UgVo> getUg(SysUg ug);
    /**
     * 组织分组下拉Map
     * @author HHe
     * @date 2019/9/5 9:57
     */
    @Select("select ug_name AS optionName,ug_id AS optionValue from sys_ug where ug_type=#{ugType} and owner_id=#{unitId} order by ug_id")
    List<Map<String, String>> getSysUgMapByTypeOwner(@Param("ugType") String ugType, @Param("unitId") Long unitId);
}