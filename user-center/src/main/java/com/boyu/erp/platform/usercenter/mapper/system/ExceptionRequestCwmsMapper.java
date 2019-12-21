package com.boyu.erp.platform.usercenter.mapper.system;

import com.boyu.erp.platform.usercenter.entity.system.ExceptionRequestCwms;

public interface ExceptionRequestCwmsMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ExceptionRequestCwms record);

    /**
     * 根据UUID删除消息
     */
    int deleteByMessage(String UUID);

    /**
     * 根据UUID 查询信息
     */
    ExceptionRequestCwms selectExceptionRequestCwms(String UUID);

    ExceptionRequestCwms selectByPrimaryKey(Integer id);

    int updateByPrimary(ExceptionRequestCwms record);

    /**
     * 根据UUID修改消息
     */
    int updateMessage(ExceptionRequestCwms record);

}