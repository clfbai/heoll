package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.vo.price.SpnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PnScpVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoDtlVo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 销售价格接口
 * @author: wz
 * @create: 2019-8-15 16:12
 */
public interface SpnScpService {
    /**
     * 销售中查询销售价格单
     * @param vo
     * @return
     * @throws ServiceException
     */

    List<DtlProdVo> updateList(PscDtlModel vo) throws ServiceException;

    /**
     * 查询销售价格单范围
     * @param vo
     * @return
     */
    List<SpnScpVo> findBySpn(SpnScpVo vo);

    /**
     * 新增销售价格单范围
     * @param vo
     * @param user
     * @return
     */
    int insertSpnScp(SpnScpVo vo, SysUser user);

    /**
     * 删除销售价格单范围
     * @param vo
     * @param user
     * @return
     */
    int deleteSpnScp(SpnScpVo vo, SysUser user);

    /**
     * 调配单查询折率/税率
     */
    PnScpVo updateRgoDtlVo(RgoDtlVo dtlVo, Long vendeeId, Long venderId, String pscType);
}
