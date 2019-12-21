package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.DocBox;

import java.util.List;

public interface DocBoxMapper {
    int deleteByPrimaryKey(DocBox key);

    int insert(DocBox record);

    int insertSelective(DocBox record);

    DocBox selectByPrimaryKey(DocBox key);

    int updateByPrimaryKeySelective(DocBox record);

    int updateByPrimaryKey(DocBox record);

    /**
     * 查询相关数据集合
     * @param record
     * @return
     */
    List<DocBox> selectByList(DocBox record);
}