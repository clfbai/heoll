package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.goods.GoodsPopupFilterModel;
import com.boyu.erp.platform.usercenter.vo.purchase.BillDtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 商品弹窗接口
 * @author HHe
 * @date 2019/12/5 19:36
 */
public interface GoodsPopupService {
    /**
     * 查询总部供应商品弹窗 s
     * @param goodsPopupFilterModel 商品弹窗筛选信息
     * @return
     * @author HHe
     * @date 2019/12/5 19:59
     */
    List<DtlProdVo> queryHQSupplyProdPopUp(GoodsPopupFilterModel goodsPopupFilterModel);

    /**
     * 供应商供货信息弹窗
     *  用户拥有的品牌权限与用户在当前组织拥有的品牌权限与当前组织拥有的商品（交集）
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<DtlProdVo> selectByVdrSu(DtlProdVo vo, Integer page, Integer size, SysUser sysUser);

    /**
     * 供应商供货输入商品代码查询商品是否存在
     * @param vo
     * @return
     */
    DtlProdVo getProdCode(DtlProdVo vo);

    /**
     * 没有选择采购商或者供应商时的商品弹窗查询
     * 只查询登录用户拥有的品牌权限与登录用户在当前组织拥有的品牌权限（交集）
     * @param vo
     * @return
     */
    List<DtlProdVo> getByPower(BillDtlProdVo vo);

    /**
     * 采购中：存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与供货信息（交集）
     * 销售中：存在购货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与购货信息（交集）
     * @param vo
     * @return
     */
    List<DtlProdVo> getByVdrSupply(BillDtlProdVo vo);

    /**
     * 总部
     * 采购中：不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与当前组织拥有的商品（交集）
     * 销售中：不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与当前组织拥有的商品（交集）
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
