package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsxDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsxDtlRqdMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsxMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsxDtlService;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname PscDtlServiceimpl
 * @Description TODO
 * @Date 2019/7/8 12:18
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class PsxDtlServiceimpl implements PsxDtlService {

    @Autowired
    private PsxDtlMapper psxDtlMapper;
    @Autowired
    private PsxMapper psxMapper;
    @Autowired
    private PsxDtlRqdMapper psxDtlRqdMapper;

    @Override
    public List<PsxDtlVo> findByPsxNum(PsxDtlVo vo) {
        return psxDtlMapper.findByPsxNum(vo);
    }

    @Override
    public int insertPsxDtl(PsxDtlVo p) throws Exception {
        String psxNum = p.getPsxNum();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //先查出psx购销单数据
        Psx psx = psxMapper.selectByPrimaryKey(psxNum);
        //设置序号 默认为0  插到数据更改
        Long num = 0L;
        //查询它得最后一条记录
        PsxDtl pd = psxDtlMapper.selectByDesc(psx.getPsxNum());
        if (pd != null) {
            if (pd.getLineNum() != null) {
                num = pd.getLineNum();
            }
        }
        //计算购销协议需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlVal = new BigDecimal("0");
        BigDecimal ttlMkv = new BigDecimal("0");

        if (p.getList() != null && p.getList().size() > 0) {

            List<PsxDtlRqd> rqdList = new ArrayList<>();

            for (int i = 0; i < p.getList().size(); i++) {
                PsxDtlVo pDtl = p.getList().get(i);
                pDtl.setPsxNum(psxNum);
                //设置行号 排号
                pDtl.setLineNum(num + i + 1);
                pDtl.setRowNum(num + i + 1);

                ttlQty = ttlQty.add(pDtl.getQty());
                ttlVal = ttlVal.add(pDtl.getVal());
                ttlMkv = ttlMkv.add(pDtl.getMkv());
                //判断货期的添加方式  SG不做添加  PD在PSC_DTL中做保存 FR在PSC_DTL_RQD中保存
                if (psx.getRqdCtrl().equals("PD")) {
                    if (psx.getReqdDate() != null) {
                        pDtl.setReqdDate(sdf.parse(psx.getReqdDate() + ""));
                    }
                } else if (psx.getRqdCtrl().equals("FR")) {
                    if (psx.getReqdDate() != null) {
                        PsxDtlRqd pdr = new PsxDtlRqd();
                        pdr.setPsxNum(pDtl.getPsxNum());
                        pdr.setProdId(pDtl.getProdId());
                        pdr.setQty(pDtl.getQty());
                        pdr.setReqdDate(sdf.parse(psx.getReqdDate() + ""));
                        rqdList.add(pdr);
                    }
                }
            }
            try {
                psxDtlMapper.insertList(p.getList());
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }
            if (rqdList != null && rqdList.size() > 0) {
                psxDtlRqdMapper.insertList(rqdList);
            }
        } else {
            //设置行号 排号
            p.setLineNum(num + 1);
            p.setRowNum(num + 1);

            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlMkv = ttlMkv.add(p.getMkv());
            //判断货期的添加方式  SG不做添加  PD在PSC_DTL中做保存 FR在PSC_DTL_RQD中保存
            if (psx.getRqdCtrl().equals("PD")) {
                if (psx.getReqdDate() != null) {
                    p.setReqdDate(psx.getReqdDate());
                }
            } else if (psx.getRqdCtrl().equals("FR")) {
                if (psx.getReqdDate() != null) {
                    PsxDtlRqd pdr = new PsxDtlRqd();
                    pdr.setPsxNum(p.getPsxNum());
                    pdr.setProdId(p.getProdId());
                    pdr.setQty(p.getQty());
                    pdr.setReqdDate(psx.getReqdDate());
                    psxDtlRqdMapper.insertSelective(pdr);
                }
            }
            try {
                psxDtlMapper.insertByPsxNum(p);
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }

        }

        return this.updatePsx(psx, ttlQty, ttlVal, ttlMkv);
    }

    @Override
    public int updatePsxDtl(PsxDtlVo p) {
        String psxNum = this.psxNum(p);
        //先查出pso购销单数据
        Psx psx = psxMapper.selectByPrimaryKey(psxNum);
        BigDecimal qty = new BigDecimal("0");//数量
        BigDecimal val = new BigDecimal("0");//金额
        BigDecimal mkv = new BigDecimal("0");//市值

        if (p.getList() != null && p.getList().size() > 0) {
            for (PsxDtlVo pDtl : p.getList()) {
                //查询之前的记录
                PsxDtl pd = new PsxDtl();
                pd.setPsxNum(pDtl.getPsxNum());
                pd.setProdId(pDtl.getProdId());
                pd = psxDtlMapper.selectByPrimaryKey(pd);

                //计算当前
                psx.setTtlQty(psx.getTtlQty().subtract(pd.getQty()).add(pDtl.getQty()));
                psx.setTtlVal(psx.getTtlVal().subtract(pd.getVal()).add(pDtl.getVal()));
                psx.setTtlMkv(psx.getTtlMkv().subtract(pd.getMkv()).add(pDtl.getMkv()));

                if (psx.getRqdCtrl().equals("FR")) {
                    psxDtlRqdMapper.updateList(p.getList());
                }
            }
            psxDtlMapper.updateList(p.getList());
        } else {
            //查询之前的记录
            PsxDtl pd = new PsxDtl();
            pd.setPsxNum(p.getPsxNum());
            pd.setProdId(p.getProdId());
            pd = psxDtlMapper.selectByPrimaryKey(pd);

            //计算当前
            psx.setTtlQty(psx.getTtlQty().subtract(pd.getQty()).add(p.getQty()));
            psx.setTtlVal(psx.getTtlVal().subtract(pd.getVal()).add(p.getVal()));
            psx.setTtlMkv(psx.getTtlMkv().subtract(pd.getMkv()).add(p.getMkv()));

            if (psx.getRqdCtrl().equals("FR")) {
                PsxDtlRqd pdr = new PsxDtlRqd();
                pdr.setProdId(p.getProdId());
                pdr.setPsxNum(p.getPsxNum());
                pdr.setQty(p.getQty());
                psxDtlRqdMapper.updateByPrimaryKey(pdr);
            }
            psxDtlMapper.updateByPsxNum(p);
        }
        return psxMapper.updateByPrimaryKeySelective(psx);
    }

    @Override
    public int deletePsxDtl(PsxDtlVo p) {
        String psxNum = this.psxNum(p);
        //先查出psx购销单数据
        Psx psx = psxMapper.selectByPrimaryKey(psxNum);
        if (p.getList() != null && p.getList().size() > 0) {
            for (PsxDtlVo pDtl : p.getList()) {
                psx.setTtlQty(psx.getTtlQty().subtract(pDtl.getQty()));
                psx.setTtlVal(psx.getTtlVal().subtract(pDtl.getVal()));
                psx.setTtlMkv(psx.getTtlMkv().subtract(pDtl.getMkv()));
            }
            if (psx.getRqdCtrl().equals("FR")) {
                psxDtlRqdMapper.deleteList(p.getList());
            }
            psxDtlMapper.deleteList(p.getList());
        } else {
            psx.setTtlQty(psx.getTtlQty().subtract(p.getQty()));
            psx.setTtlVal(psx.getTtlVal().subtract(p.getVal()));
            psx.setTtlMkv(psx.getTtlMkv().subtract(p.getMkv()));
            if (psx.getRqdCtrl().equals("FR")) {
                PsxDtlRqd pdr = new PsxDtlRqd();
                pdr.setProdId(p.getProdId());
                pdr.setPsxNum(p.getPsxNum());
                psxDtlRqdMapper.deleteByPrimaryKey(pdr);
            }
            PsxDtl pd = new PsxDtl();
            pd.setPsxNum(p.getPsxNum());
            pd.setProdId(p.getProdId());
            psxDtlMapper.deleteByPrimaryKey(pd);
        }

        return psxMapper.updateByPrimaryKeySelective(psx);
    }

    @Override
    public PsxVo insertByPua(PsxVo vo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //计算购销协议需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlVal = new BigDecimal("0");
        BigDecimal ttlMkv = new BigDecimal("0");

        List<PsxDtlRqd> rqdList = new ArrayList<>();

        for (int i = 0; i < vo.getPsxDtlList().size(); i++) {
            PsxDtlVo p = vo.getPsxDtlList().get(i);
            p.setPsxNum(vo.getPsxNum());
            //设置序号 默认为0  插到数据更改
            Long num = 0L;
            //设置行号 排号
            p.setLineNum(num + i + 1);
            p.setRowNum(num + i + 1);

            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlMkv = ttlMkv.add(p.getMkv());
            //判断货期的添加方式  SG不做添加  PD在PSC_DTL中做保存 FR在PSC_DTL_RQD中保存
            if (vo.getRqdCtrl().equals("PD")) {
                if (vo.getReqdDate() != null) {
                    p.setReqdDate(sdf.parse(vo.getReqdDate()));
                }
            } else if (vo.getRqdCtrl().equals("FR")) {
                if (vo.getReqdDate() != null) {
                    PsxDtlRqd pdr = new PsxDtlRqd();
                    pdr.setPsxNum(p.getPsxNum());
                    pdr.setProdId(p.getProdId());
                    pdr.setQty(p.getQty());
                    pdr.setReqdDate(sdf.parse(vo.getReqdDate()));
                    rqdList.add(pdr);
                }
//                psxDtlRqdMapper.insertSelective(pdr);
            }
//            psxDtlMapper.insertByPsxNum(p);
        }
        psxDtlMapper.insertList(vo.getPsxDtlList());
        if (rqdList != null && rqdList.size() > 0) {
            psxDtlRqdMapper.insertList(rqdList);
        }
        vo.setTtlQty(ttlQty);
        vo.setTtlVal(ttlVal);
        vo.setTtlMkv(ttlMkv);
        return vo;
    }


    private int updatePsx(Psx psx, BigDecimal ttlQty, BigDecimal ttlVal, BigDecimal ttlMkv) {
        //修改购销协议的数据PSC
        if (psx.getTtlQty() != null) {
            psx.setTtlQty(psx.getTtlQty().add(ttlQty));
        } else {
            psx.setTtlQty(ttlQty);
        }
        if (psx.getTtlVal() != null) {
            psx.setTtlVal(psx.getTtlVal().add(ttlVal));
        } else {
            psx.setTtlVal(ttlVal);
        }
        if (psx.getTtlMkv() != null) {
            psx.setTtlMkv(psx.getTtlMkv().add(ttlMkv));
        } else {
            psx.setTtlMkv(ttlMkv);
        }
        return psxMapper.updateByPrimaryKeySelective(psx);
    }

    public String psxNum(PsxDtlVo p) {
        String psxNum = "";
        if (p.getList() != null && p.getList().size() > 0) {
            psxNum = p.getList().get(0).getPsxNum();
        } else {
            psxNum = p.getPsxNum();
        }
        return psxNum;
    }

}
