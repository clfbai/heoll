package com.boyu.erp.platform.usercenter.service.purchaseservice;


import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaRateVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;

import java.util.List;

/**
 * @Classname PsaRtrService
 * @Description TODO
 * @Date 2019/7/2 12:06
 * @Created wz
 */
public interface PsaRtrService {

    /**
     * 查询可退率
     * @return
     * @throws ServiceException
     */
    public List<PsaRateVo> selectAll(PsaRateVo vo) throws ServiceException;

    /**
     * 添加可退率
     * @param vo
     * @return
     */
    public int insertByPsa(PsaRateVo vo);

    /**
     * 修改可退率
     * @param vo
     * @return
     */
    public int updateByPsa(PsaRateVo vo);

    /**
     * 删除可退率
     * @param vo
     * @return
     */
    public int deleteByPsa(PsaRateVo vo);


    /**
     * 采购合同中添加商品时查出可退率
     */
    public String selectByRtr(String pscType, String prodCatId, Psa psa);

    /**
     * 批量添加可退率
     * @param vo
     * @return
     */
    public void insertByPsa(PsaVo vo);
}
