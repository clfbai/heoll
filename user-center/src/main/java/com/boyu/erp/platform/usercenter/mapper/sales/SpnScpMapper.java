package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.SpnScp;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PnScpVo;

import java.util.List;
import java.util.Map;

public interface SpnScpMapper {
    int deleteByPrimaryKey(SpnScp key);

    int insert(SpnScp record);

    int insertSelective(SpnScp record);

    //销售中走销售价格单改变数据
    PnScpVo selectByPrice(Long vendeeId, Long venderId, Long prodClsId);

    /**
     * 批量新增
     * @return
     */
    int insertByList(List<SpnScpVo> list);

    /**
     * 删除销售价格单时删除范围
     * @param unitId
     * @param spnNum
     * @return
     */
    int deleteBySpn(String unitId,String spnNum);

    /**
     * 查询
     * @param VO
     * @return
     */
    List<SpnScpVo> selectBySpn(SpnScpVo VO);

    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteByList(List<SpnScpVo> list);

    /**
     * 确认时查询范围
     * @param unitId
     * @return
     */
    SpnScp selectByConfirm(String unitId,String spnNum);

    /**
     * 查询不合法性的记录
     * @param spnNum
     * @return
     */
    List<SpnScpVo> selectByLeg(String spnNum);


    /**
     * 添加销售价格单时批量添加
     * @param map
     * @return
     */
    int insertByMap(Map<String,Object> map);

    /**
     * 查询范围的组织集合
     * @param map
     * @return
     */
    List<SysUnit> selectBySysUnit(Map<String,Object> map);
}