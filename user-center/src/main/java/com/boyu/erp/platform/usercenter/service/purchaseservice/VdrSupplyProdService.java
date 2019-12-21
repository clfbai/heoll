package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.VdrSupplyProd;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.purchase.VdrSupplyProdModel;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyProdVo;
import com.github.pagehelper.PageInfo;

/**
 * @Classname VdrSupplyProdService
 * @Description TODO
 * @Date 2019/8/5 17:31
 * @Created wz
 */
public interface VdrSupplyProdService {

    /**
     * 供应商供货信息明细查询
     * @param page
     * @param size
     * @param vo
     * @return
     */
    PageInfo<VdrSupplyProdVo> selectAllByVdr(Integer page, Integer size, VdrSupplyProdVo vo);

    int addOrDelete(VdrSupplyProdModel vo, SysUser sysUser);

    VdrSupplyProd selectByVer(String venderId,String vendeeId,String prodId);

    /**
     * 采购商购货信息明细查询
     * @param page
     * @param size
     * @param vo
     * @return
     */
    PageInfo<VdrSupplyProdVo> selectAllByVde(Integer page, Integer size, VdrSupplyProdVo vo);
}
