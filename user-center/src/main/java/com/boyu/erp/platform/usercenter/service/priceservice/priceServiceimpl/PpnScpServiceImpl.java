package com.boyu.erp.platform.usercenter.service.priceservice.priceServiceimpl;

import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PpnScpMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsaMapper;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnScpService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaDrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaRtrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaTrService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: PpnScpServiceImpl
 * @description: 采购价格单范围服务层
 * @author: wz
 * @create: 2019-8-26 9:43
 */
@Slf4j
@Service
@Transactional
public class PpnScpServiceImpl implements PpnScpService {

    @Autowired
    private PpnScpMapper ppnScpMapper;
    @Autowired
    private PsaDrService psaDrService;
    @Autowired
    private PsaTrService psaTrService;
    @Autowired
    private PsaRtrService psaRtrService;
    @Autowired
    private PsaMapper psaMapper;


    @Override
    public List<DtlProdVo> updateList(PscDtlModel vo) {
        Psa psa = psaMapper.selectByVdr(new Psa(vo.getVenderId(), vo.getVendeeId()));
        if (psa != null) {
            for (int i = 0; i < vo.getVo().size(); i++) {
                DtlProdVo ppv = vo.getVo().get(i);
                //查询是否改更改过价格信息
                PnScpVo ps = null;
                if (StringUtils.isNotEmpty(psa.getPuPrlRefId() + "")) {
                    ps = ppnScpMapper.selectByPrice(vo.getVendeeId(), psa.getPuPrlRefId(), ppv.getProdClsId());
                    if (ps == null) {
                        ps = ppnScpMapper.selectByPrice(vo.getVendeeId(), vo.getVenderId(), ppv.getProdClsId());
                    }
                } else {
                    ps = ppnScpMapper.selectByPrice(vo.getVendeeId(), vo.getVenderId(), ppv.getProdClsId());
                }
                String dr = "";//折率
                String tr = "";//税率
                String rtr = "";//可退率
                BigDecimal price = new BigDecimal("0");
                if (ps != null) {
                    price = ps.getUnitPrice();
                    dr = ppv.getDiscRate() + "";
                    if (StringUtils.isNotEmpty(ppv.getRtnaRate() + "")) {
                        tr = ppv.getRtnaRate() + "";
                    } else {
                        tr = psaTrService.selectByTr(vo.getPscType(), ppv.getProdCatId(), psa);
                    }
                } else {
                    dr = psaDrService.selectByDr(vo.getPscType(), ppv.getProdCatId(), psa);
                    tr = psaTrService.selectByTr(vo.getPscType(), ppv.getProdCatId(), psa);
                }
                //将查出来的折率，税率填充进数据
                ppv.setTaxRate(new BigDecimal(tr));
                //查可退率
                rtr = psaRtrService.selectByRtr(vo.getPscType(), ppv.getProdCatId(), psa);
                ppv.setRtnaRate(new BigDecimal(rtr));
                if (ppv.getDiscRate() == null) {
                    ppv.setDiscRate(new BigDecimal(dr));
                    ppv.setFnlPrice(ppv.getUnitPrice().multiply(ppv.getDiscRate()));
                    if (price.compareTo(new BigDecimal("0")) > 0) {
                        ppv.setUnitPrice(price);
                    }
                }
            }
            return vo.getVo();
        }else{
            throw new ServiceException(JsonResultCode.FAILURE, "当前供应商与采购商不存在购销协议！");
        }
    }

    @Override
    public List<PpnScpVo> findByPpn(PpnScpVo vo) {
        return ppnScpMapper.selectByPpn(vo);
    }

    /**
     * 新增
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertPpnScp(PpnScpVo vo, SysUser user) {
        if (vo.getList() != null && vo.getList().size() > 0) {
            return ppnScpMapper.insertByList(vo.getList());
        }
        return ppnScpMapper.insertSelective(vo);
    }

    /**
     * 删除
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deletePpnScp(PpnScpVo vo, SysUser user) {
        if (vo.getList() != null && vo.getList().size() > 0) {
            return ppnScpMapper.deleteByList(vo.getList());
        }
        return ppnScpMapper.deleteByPrimaryKey(vo);
    }
}
