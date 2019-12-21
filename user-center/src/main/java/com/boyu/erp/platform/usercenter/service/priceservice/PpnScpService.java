package com.boyu.erp.platform.usercenter.service.priceservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;

import java.util.List;

/**
 * @program: PpnScpService
 * @description: 采购价格单范围接口
 * @author: wz
 * @create: 2019-8-26 9:43
 */
public interface PpnScpService {

    /**
     * 采购中查询采购价格单
     * @param vo
     * @return
     * @throws ServiceException
     */
    List<DtlProdVo> updateList(PscDtlModel vo) throws ServiceException;

    /**
     * 查询采购价格单范围
     * @param vo
     * @return
     */
    List<PpnScpVo> findByPpn(PpnScpVo vo);

    /**
     * 新增采购价格单范围
     * @param vo
     * @param user
     * @return
     */
    int insertPpnScp(PpnScpVo vo, SysUser user);

    /**
     * 删除采购价格单范围
     * @param vo
     * @param user
     * @return
     */
    int deletePpnScp(PpnScpVo vo, SysUser user);
}
