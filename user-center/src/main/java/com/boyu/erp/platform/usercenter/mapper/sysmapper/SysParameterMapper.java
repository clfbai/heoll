package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysParameterMapper {


    /**
     * 增加参数
     * */
    int insertSelective(SysParameter record);
    /**
    * 查询参数
    * */
    List<SysParameter> selectByLikePrimary(SysParameter parmId);
    /**
     * 修改参数
     * */
    int updateByPrimaryKeySelective(SysParameter record);



    /**
    * 根据Id查询指定参数
    * */
    SysParameter findById(String parmId);

    /**
     * 删除类型时，同步更新不可修改/必填参数
     * @param creatTableFileds
     * @param tableNotUpdate
     * @return
     */
    int updateByType(String creatTableFileds,String tableNotUpdate,String updateBy);
}