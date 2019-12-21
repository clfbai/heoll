package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.UnitProdClsMapper;
import com.boyu.erp.platform.usercenter.model.goods.GoodsPopupFilterModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.GoodsPopupService;
import com.boyu.erp.platform.usercenter.vo.purchase.BillDtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 商品弹窗服务
 * @author HHe
 * @date 2019/12/5 20:01
 */
@Service
@Transactional
@Slf4j
public class GoodsPopupServiceImpl implements GoodsPopupService {

    @Autowired
    private ProdClsMapper prodClsMapper;
    @Autowired
    private UnitProdClsMapper unitProdClsMapper;

    /**
     * 查询总部供应商品弹窗
     * @param goodsPopupFilterModel 商品弹窗筛选信息
     * @return
     * @author HHe
     * @date 2019/12/5 20:02
     */
    @Override
    public List<DtlProdVo> queryHQSupplyProdPopUp(GoodsPopupFilterModel goodsPopupFilterModel) {
        //分类筛选内容

        return null;
    }

    /**
     * 供应商供货信息弹窗
     * 用户拥有的品牌权限与用户在当前组织拥有的品牌权限与当前组织拥有的商品（交集）
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    @Override
    public PageInfo<DtlProdVo> selectByVdrSu(DtlProdVo vo, Integer page, Integer size, SysUser sysUser) {
        List<DtlProdVo> list = null;
        vo.setUserId(sysUser.getPrsnl().getPrsnlId());
        PageHelper.startPage(page, size);
        list = prodClsMapper.selectByVdrSu(vo);
        PageInfo<DtlProdVo> pageInfo = new PageInfo<DtlProdVo>(list);
        return pageInfo;
    }

    /**
     * 供应商供货输入商品代码判断商品是否存在
     * @param vo
     * @return
     */
    @Override
    public DtlProdVo getProdCode(DtlProdVo vo) {
        return prodClsMapper.getProdCode(vo);
    }

    /**
     * 用户拥有的品牌权限与用户在当前组织拥有的品牌权限（交集）
     * @param vo
     * @return
     */
    @Override
    public List<DtlProdVo> getByPower(BillDtlProdVo vo) {
        return unitProdClsMapper.getByPower(vo);
    }

    /**
     * 存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与供货信息（交集）
     * @param vo
     * @return
     */
    @Override
    public List<DtlProdVo> getByVdrSupply(BillDtlProdVo vo) {
        return unitProdClsMapper.getByVdrSupply(vo);
    }

    /**
     *  总部
     * 不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与当前组织拥有的商品（交集）
     * @param vo
     * @return
     */
    @Override
    public List<DtlProdVo> getByNoVdrSupply(BillDtlProdVo vo) {
        return unitProdClsMapper.getByNoVdrSupply(vo);
    }

    /**
     *  非总部
     * 不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与选择的供应商共享商品（交集）
     * @param vo
     * @return
     */
    @Override
    public List<DtlProdVo> findByNoVdrSupply(BillDtlProdVo vo) {
        return unitProdClsMapper.findByNoVdrSupply(vo);
    }
}
