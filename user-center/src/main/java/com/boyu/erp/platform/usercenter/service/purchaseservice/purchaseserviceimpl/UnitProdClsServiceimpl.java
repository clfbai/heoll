package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.goods.UnitProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.UnitProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.VdrSupplyProdMapper;
import com.boyu.erp.platform.usercenter.service.goodsservice.GoodsPopupService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.UnitProdClsService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.BillDtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyProdVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UnitProdClsServiceimpl
 * @Description TODO
 * @Date 2019/7/10 14:44
 * @Created wz
 */
@Service
@Transactional
public class UnitProdClsServiceimpl implements UnitProdClsService {

    @Autowired
    private UnitProdClsMapper unitProdClsMapper;
    @Autowired
    private GoodsPopupService goodsPopupService;
    @Autowired
    private VdrSupplyProdMapper vdrSupplyProdMapper;

    /**
     * 商品弹窗
     *
     * @param p
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    public PageInfo<DtlProdVo> selectByDtl(DtlProdVo p, Integer page, Integer size, SysUser user) {

        List<DtlProdVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = unitProdClsMapper.selectALL(p);
        } else {
            PageHelper.startPage(page, size);
            list = unitProdClsMapper.selectUserList(p);
        }
        PageInfo<DtlProdVo> pageInfo = new PageInfo<DtlProdVo>(list);
        return pageInfo;
    }

    /**
     * 输入商品代码查询商品
     *
     * @param vo
     * @return
     */
    @Override
    public DtlProdVo selectByProdCode(DtlProdVo vo) {
        return unitProdClsMapper.selectByProdCode(vo);
    }

    /**
     * 订单中商品弹窗
     *
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<DtlProdVo> selectByBillDtl(BillDtlProdVo vo, Integer page, Integer size, SysUser sysUser) throws ServiceException {
        List<DtlProdVo> list = null;
        vo.setUserId(sysUser.getPrsnl().getPrsnlId());
        PageHelper.startPage(page, size);
        if (!StringUtils.NullEmpty(vo.getVendeeId() + "") || !StringUtils.NullEmpty(vo.getVenderId() + "")) {
            //采购中
            if (StringUtils.NullEmpty(vo.getVendeeId() + "")) {
                vo.setVendeeId(vo.getsUnitId());
                //判断当前选择供应商是否与当前组织存在供应商供货关系
                List<VdrSupplyProdVo> vdrList = vdrSupplyProdMapper.selectAll(new VdrSupplyProdVo(vo.getVenderId(), vo.getVendeeId()));
                if (!vdrList.isEmpty()) {
                    //不用判断是否总部
                    //存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与供货信息（交集）
                    list = goodsPopupService.getByVdrSupply(vo);
                } else {
                    //判断当前组织是否是总部
                    if (sysUser.getUnit().getUnitType().equals("1")) {
                        //不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与当前组织拥有的商品（交集） 总部
                        list = goodsPopupService.getByNoVdrSupply(vo);
                    } else {
                        //不存在供货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与选择的供应商共享商品（交集） 非总部
                        list = goodsPopupService.findByNoVdrSupply(vo);
                    }
                }
            }
            //销售中
            if (StringUtils.NullEmpty(vo.getVenderId() + "")) {
                vo.setVenderId(vo.getsUnitId());
                //判断当前组织是否是总部
                if (sysUser.getUnit().getUnitType().equals("1")) {
                    //总部
                    //判断当前选择采购商是否与当前组织存在采购商购货关系
                    List<VdrSupplyProdVo> vdrList = vdrSupplyProdMapper.selectAll(new VdrSupplyProdVo(vo.getVenderId(), vo.getVendeeId()));
                    if (!vdrList.isEmpty()) {
                        //存在购货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与购货信息（交集）
                        list = goodsPopupService.getByVdrSupply(vo);
                    } else {
                        //不存在购货关系：用户拥有品牌权限与用户在当前组织拥有品牌权限与当前组织拥有的商品（交集）
                        list = goodsPopupService.getByNoVdrSupply(vo);
                    }
                } else {
                    //非总部  返回空集合
                    list = new ArrayList<>();
                }
            }
        } else {
            list = goodsPopupService.getByPower(vo);
        }
        PageInfo<DtlProdVo> pageInfo = new PageInfo<DtlProdVo>(list);
        return pageInfo;
    }

    @Override
    public DtlProdVo findByBillProdCode(BillDtlProdVo vo, SysUser sysUser) {
        vo.setUserId(sysUser.getPrsnl().getPrsnlId());
        return unitProdClsMapper.findByBillProdCode(vo);
    }

    /**
     * 功能描述: 查询组织商品价格等
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 21:08
     */
    @Override
    public UnitProdCls selectUnitProdCls(Long unitId, Long prodClsId) {

        return unitProdClsMapper.selectUnitProdCls(new UnitProdCls(unitId, prodClsId));
    }
}
