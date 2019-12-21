package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsaMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SpnScpMapper;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaDrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaRtrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaTrService;
import com.boyu.erp.platform.usercenter.service.salesservice.SpnScpService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.price.SpnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PnScpVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname SpnServiceImpl
 * @Description TODO
 * @Date 2019/8/15 16:13
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class SpnScpServiceImpl implements SpnScpService {

    @Autowired
    private PsaMapper psaMapper;
    @Autowired
    private SpnScpMapper spnScpMapper;
    @Autowired
    private PsaDrService psaDrService;
    @Autowired
    private PsaTrService psaTrService;
    @Autowired
    private PsaRtrService psaRtrService;

    @Override
    public List<DtlProdVo> updateList(PscDtlModel vo) throws ServiceException {
        Psa psa = psaMapper.selectByVde(new Psa(vo.getVenderId(), vo.getVendeeId()));
        if (psa != null) {
            for (int i = 0; i < vo.getVo().size(); i++) {
                DtlProdVo ppv = vo.getVo().get(i);
                //查询是否改更改过价格信息
                PnScpVo ps = null;
                if (StringUtils.isNotEmpty(psa.getSlPrlRefId() + "")) {
                    ps = spnScpMapper.selectByPrice(vo.getVenderId(), psa.getSlPrlRefId(), ppv.getProdClsId());
                    if (ps == null) {
                        ps = spnScpMapper.selectByPrice(vo.getVenderId(), vo.getVendeeId(), ppv.getProdClsId());
                    }
                } else {
                    ps = spnScpMapper.selectByPrice(vo.getVenderId(), vo.getVendeeId(), ppv.getProdClsId());
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
                if(ppv.getDiscRate()==null){
                    ppv.setDiscRate(new BigDecimal(dr));
                    ppv.setFnlPrice(ppv.getUnitPrice().multiply(ppv.getDiscRate()));
                    if(price.compareTo(new BigDecimal("0")) > 0){
                        ppv.setUnitPrice(price);
                    }
                }
            }
            return vo.getVo();
        }
        return null;
    }

    @Override
    public List<SpnScpVo> findBySpn(SpnScpVo vo) {
        return spnScpMapper.selectBySpn(vo);
    }

    /**
     * 新增
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertSpnScp(SpnScpVo vo, SysUser user) {
        if (vo.getList() != null && vo.getList().size() > 0) {
            return spnScpMapper.insertByList(vo.getList());
        }
        return spnScpMapper.insertSelective(vo);
    }

    /**
     * 删除
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deleteSpnScp(SpnScpVo vo, SysUser user) {
        if (vo.getList() != null && vo.getList().size() > 0) {
            return spnScpMapper.deleteByList(vo.getList());
        }
        return spnScpMapper.deleteByPrimaryKey(vo);
    }

    /**
     * @param dtlVo
     * @param vendeeId
     * @param venderId
     * @return
     */
    @Override
    public PnScpVo updateRgoDtlVo(RgoDtlVo dtlVo, Long vendeeId, Long venderId, String pscType) {
        PnScpVo ps = null;
        String dr = "";//折率
        String tr = "";//税率
        String rtr = "";//可退率
        Psa psa = psaMapper.selectByVde(new Psa(venderId, vendeeId));
        if (psa != null) {
            if (StringUtils.isNotEmpty(psa.getSlPrlRefId() + "")) {
                ps = spnScpMapper.selectByPrice(venderId, psa.getSlPrlRefId(), dtlVo.getProdClsId());
                if (ps == null) {
                    ps = spnScpMapper.selectByPrice(venderId, vendeeId, dtlVo.getProdClsId());
                }
            } else {
                ps = spnScpMapper.selectByPrice(venderId, vendeeId, dtlVo.getProdClsId());
            }
        }
        if (ps != null) {
            if (StringUtils.NullEmpty(ps.getTaxRate() + "")) {
                ps.setTaxRate(new BigDecimal(psaTrService.selectByTr(pscType, dtlVo.getProdCatId(), psa)));
            }
        } else {
            ps = new PnScpVo();
            ps.setDiscRate(new BigDecimal(psaDrService.selectByDr(pscType, dtlVo.getProdCatId(), psa)));
            ps.setTaxRate(new BigDecimal(psaTrService.selectByTr(pscType, dtlVo.getProdCatId(), psa)));
        }
        //查可退率
        ps.setRtnaRate(new BigDecimal(psaRtrService.selectByRtr(pscType, dtlVo.getProdCatId(), psa)));
        return ps;
    }
}
