package com.boyu.erp.platform.usercenter.service.purchaseservice;


import com.boyu.erp.platform.usercenter.entity.goods.UnitProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.BillDtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.github.pagehelper.PageInfo;

/**
 * @Classname UnitProdClsService
 * @Description TODO
 * @Date 2019/7/10 14:44
 * @Created wz
 */

public interface UnitProdClsService {

    //商品弹窗
    PageInfo<DtlProdVo> selectByDtl(DtlProdVo p, Integer page, Integer size, SysUser sysUser) throws ServiceException;

    /**
     * 输入商品代码查询商品
     *
     * @param vo
     * @return
     */
    DtlProdVo selectByProdCode(DtlProdVo vo);

    //采购销售订单中商品弹窗
    PageInfo<DtlProdVo> selectByBillDtl(BillDtlProdVo p, Integer page, Integer size, SysUser sysUser) throws ServiceException;

    /**
     * 订单中输入商品代码查询商品
     *
     * @param p
     * @param sysUser
     * @return
     */
    DtlProdVo findByBillProdCode(BillDtlProdVo p, SysUser sysUser);

    /**
     * 功能描述: 查询组织商品价格等
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 21:08
     */
    UnitProdCls selectUnitProdCls(Long unitId, Long prodClsId);
}
