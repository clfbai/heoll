package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.Vender;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.sales.VendeeVo;

import java.util.List;

public interface VenderMapper {
    int deleteByPrimaryKey(Vender key);

    int insert(Vender record);

    int insertSelective(Vender record);

    Vender selectByPrimaryKey(Vender key);

    int updateByPrimaryKeySelective(Vender record);

    int updateByPrimaryKey(Vender record);

    /**
     * 系统管理员查询
     * @param vo
     * @return
     */
    List<VenderVo> selectALL(VenderVo vo);

    /**
     * 普通组织查询
     * @param vo
     * @return
     */
    List<VenderVo> selectByUnit(VenderVo vo);

    //添加供应商信息
    int insertVender(VenderVo vo);

    //修改供应商信息
    int updateByVender(VenderVo vo);

    /**
     * 供应商中通过组织代码或者组织id查询供应商页面数据
     */
    VenderVo selectByVender(VenderVo vo);

    /**
     * 供应商供货系统管理员查看所有
     * @param vo
     * @return
     */
    List<VdrSupplyVo> selectVdrALL(VdrSupplyVo vo);
    /**
     * 供应商供货个人查看
     * @param vo
     * @return
     */
    List<VdrSupplyVo> selectByVdrUnit(VdrSupplyVo vo);

    /**
     * 添加供应商时判断是否已有数据
     * @param vo
    * @return
     */
    List<Vender> findByVendeeVo(VendeeVo vo);

    /**
     * 采购商中添加供应商
     * @param vo
     * @return
     */
    int insertByVendee(VendeeVo vo);
}