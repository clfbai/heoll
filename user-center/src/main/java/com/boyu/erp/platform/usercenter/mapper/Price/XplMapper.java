package com.boyu.erp.platform.usercenter.mapper.Price;

import com.boyu.erp.platform.usercenter.entity.Price.Xpl;
import com.boyu.erp.platform.usercenter.entity.purchase.PpnScp;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnScpVo;

import java.util.List;
import java.util.Map;

public interface XplMapper {
    int deleteByPrimaryKey(Xpl key);

    int insert(Xpl record);

    int insertSelective(Xpl record);

    Xpl selectByPrimaryKey(Xpl key);

    int updateByPrimaryKeySelective(Xpl record);

    int updateByPrimaryKey(Xpl record);
    //执行采购价格单时需要往历史价格中存入的数据
    List<Xpl> selectByRsvF(String venderId,String vendeeId,String prcCtlr);

    //删除相应的xpl
    int deleteByImplementF(Map<String,Object> map);

    //删除相应的xpl
    int deleteByHierImplementF(Map<String,Object> map);

    //定价范围时组织 删除
    int deleteByImplementT(Map<String,Object> map);

    //添加当前范围数据
    int insertByList(Map<String,Object> map);

    //添加范围下级采购商数据
    int insertByHierList(Map<String,Object> map);

    //查询出向下传递时符合要去的数据集合
    List<Xpl> selectByHierarchyList(List<SysUnit> list);
}