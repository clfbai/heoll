package com.boyu.erp.platform.usercenter.mapper.Price;

import com.boyu.erp.platform.usercenter.entity.Price.XplH;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.price.XplHVo;

import java.util.List;
import java.util.Map;

public interface XplHMapper {
    int deleteByPrimaryKey(XplH key);

    int insert(XplH record);

    int insertSelective(XplH record);

    XplH selectByPrimaryKey(XplH key);

    int updateByPrimaryKeySelective(XplH record);

    int updateByPrimaryKey(XplH record);

    /**
     * 记录历史
     * @param map
     * @return
     */
    int insertByMap(Map<String,Object> map);

    /**
     * 范围新增
     * @param map
     * @return
     */
    int insertByList(Map<String,Object> map);

    /**
     * 向下投递新增
     * @param map
     * @return
     */
    int insertByHierList(Map<String,Object> map);

   //采购单历史查询 系统管理员
    List<XplHVo> selectALL(XplHVo vo);

    //采购单历史查询 普通组织
    List<XplHVo> selectByUnit(XplHVo vo);
}