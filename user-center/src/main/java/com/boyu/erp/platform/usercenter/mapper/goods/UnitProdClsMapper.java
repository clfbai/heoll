package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.UnitProdCls;
import com.boyu.erp.platform.usercenter.vo.purchase.BillDtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;

import java.util.List;
import java.util.Map;

public interface UnitProdClsMapper {

    int deleteUnitProdCls(UnitProdCls key);

    int insertUnitProdCls(UnitProdCls record);

    int insertSelectiveUnitProdCls(UnitProdCls record);

    UnitProdCls selectUnitProdCls(UnitProdCls key);

    int updateUnitProdClsSelective(UnitProdCls record);

    int updateUnitProdCls(UnitProdCls record);

    //系统管理员查询所有商品
    List<DtlProdVo> selectALL(DtlProdVo p);

    //普通组织查询商品
    List<DtlProdVo> selectUserList(DtlProdVo p);

    //输入商品代码查询商品
    DtlProdVo selectByProdCode(DtlProdVo p);

    /**
     * 订单中查询商品
     * @param vo
     * @return
     */
    List<DtlProdVo> selectBillALL(BillDtlProdVo vo);

    /**
     * 订单中查询输入商品代码商品是否存在
     * @param vo
     * @return
     */
    DtlProdVo findByBillProdCode(BillDtlProdVo vo);

    /**
     * 用户拥有的品牌权限与用户在当前组织拥有的品牌权限（交集）
     * @param vo
     * @return
     */
    List<DtlProdVo> getByPower(BillDtlProdVo vo);

    /**
     * 存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与供货信息（交集）
     * @param vo
     * @return
     */
    List<DtlProdVo> getByVdrSupply(BillDtlProdVo vo);

    /**
     * 总部
     * 不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与当前组织拥有的商品（交集）
     * @param vo
     * @return
     */
    List<DtlProdVo> getByNoVdrSupply(BillDtlProdVo vo);

    /**
     * 非总部
     * 不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与选择的供应商共享商品（交集）
     * @param vo
     * @return
     */
    List<DtlProdVo> findByNoVdrSupply(BillDtlProdVo vo);
}