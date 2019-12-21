package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.Vendee;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.sales.VendeeVo;

import java.util.List;

public interface VendeeMapper {
    int deleteByPrimaryKey(Vendee key);

    int insert(Vendee record);

    int insertSelective(Vendee record);

    Vendee selectByPrimaryKey(Vendee key);

    int updateByPrimaryKeySelective(Vendee record);

    int updateByPrimaryKey(Vendee record);

    /**
     * 添加供应商时判断是否已有数据
     * @param vo
     * @return
     */
    List<Vendee> findByVenderVo(VenderVo vo);

    //供应商中添加采购商
    int insertByVender(VenderVo vo);

    /**
     * 系统管理员查询
     * @param vo
     * @return
     */
    List<VendeeVo> selectALL(VendeeVo vo);

    /**
     * 普通组织查询
     * @param vo
     * @return
     */
    List<VendeeVo> selectByUnit(VendeeVo vo);

    //添加采购商信息
    int insertVendee(VendeeVo vo);

    //修改采购商信息
    int updateVendee(VendeeVo vo);

    /**
     * 采购商中通过组织代码或者组织id查询采购商页面数据
     */
    VendeeVo selectByVendee(VendeeVo vo);
    /**
     * 查询vendee是否是属主的采购商
     * @author HHe
     * @date 2019/10/19 11:31
     */
    Vendee queryVendeeByIdAndOwner(Vendee pVendee);

    /**
     * 采购商购货查询
     * @param vo
     * @return
     */
    List<VdrSupplyVo> selectByVdeUnit(VdrSupplyVo vo);
}