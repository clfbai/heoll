package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.RtqQtaPg;

import java.util.List;
import java.util.Map;

public interface RtqQtaPgMapper {
    int deleteByPrimaryKey(RtqQtaPg key);

    int insert(RtqQtaPg record);

    int insertSelective(RtqQtaPg record);

    RtqQtaPg selectByPrimaryKey(RtqQtaPg key);

    int updateByPrimaryKeySelective(RtqQtaPg record);

    int updateByPrimaryKey(RtqQtaPg record);

    /**
     * 退购合同中添加可退未决额度
     * @param map
     * @return
     */
    int insertByBill(Map<String,Object> map);

    /**
     * 退购合同反审解冻可退数量
     * @param map
     * @return
     */
    List<RtqQtaPg> selectByUpdateRtqQta(Map<String,Object> map);

    /**
     * 删除
     * @param list
     * @return
     */
    int deleteByList(List<RtqQtaPg> list);
}