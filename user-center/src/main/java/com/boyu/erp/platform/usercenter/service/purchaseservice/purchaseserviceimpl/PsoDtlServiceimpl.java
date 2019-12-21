package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.Pso;
import com.boyu.erp.platform.usercenter.entity.purchase.PsoDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsoDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsoMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsoDtlService;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname PsoDtlServiceimpl
 * @Description TODO
 * @Date 2019/7/18 11:33
 * @Created wz
 */
@Service
@Transactional
public class PsoDtlServiceimpl implements PsoDtlService {

    @Autowired
    private PsoDtlMapper psoDtlMapper;
    @Autowired
    private PsoMapper psoMapper;

    @Override
    public List<PsoDtlVo> findByPuoNum(PsoDtlVo vo) {
        return psoDtlMapper.findByPuoNum(vo);
    }

    /**
     * 主表已建后，新增明细
     *
     * @param p
     * @return
     */
    @Override
    public int insertPsoDtl(PsoDtlVo p) {
        String psoNum = p.getPsoNum();
        //先查出pso购销单数据
        Pso pso = psoMapper.selectByPrimaryKey(psoNum);
        //设置序号 默认为0  插到数据更改
        Long num = 0L;
        //查询它得最后一条记录
        PsoDtl pd = psoDtlMapper.selectByDesc(pso.getPsoNum());
        if (pd != null) {
            if (pd.getLineNum() != null) {
                num = pd.getLineNum();
            }
        }
        //计算购销协议需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlVal = new BigDecimal("0");
        BigDecimal ttlTax = new BigDecimal("0");
        BigDecimal ttlMkv = new BigDecimal("0");
        //判断是批量新增还是单独新增
        if (p.getList() != null && p.getList().size() > 0) {
            //循环计算主表需要更新的值
            for (int i = 0; i < p.getList().size(); i++) {
                PsoDtlVo pDtl = p.getList().get(i);
                pDtl.setPsoNum(psoNum);
                //设置行号 排号
                pDtl.setLineNum(num + i + 1);
                pDtl.setRowNum(num + i + 1);

                ttlQty = ttlQty.add(pDtl.getQty());
                ttlVal = ttlVal.add(pDtl.getVal());
                ttlTax = ttlTax.add(pDtl.getTax());
                ttlMkv = ttlMkv.add(pDtl.getMkv());
            }
            //批量增加
            try {
                psoDtlMapper.insertList(p.getList());
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }
        } else {

            //设置行号 排号
            p.setLineNum(num + 1);
            p.setRowNum(num + 1);

            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlTax = ttlTax.add(p.getTax());
            ttlMkv = ttlMkv.add(p.getMkv());
            try {
                psoDtlMapper.insertByPsoNum(p);
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }

        }
        return this.updatePso(pso, ttlQty, ttlVal, ttlTax, ttlMkv);
    }

    //修改
    @Override
    public int updatePsoDtl(PsoDtlVo p) {
        String psoNum = this.psoNum(p);
        //先查出pso购销单数据
        Pso pso = psoMapper.selectByPrimaryKey(psoNum);

        if (p.getList() != null && p.getList().size() > 0) {
            for (PsoDtlVo pDtl : p.getList()) {
                //查询之前的记录
                PsoDtl pd = new PsoDtl();
                pd.setPsoNum(pDtl.getPsoNum());
                pd.setProdId(pDtl.getProdId());
                pd = psoDtlMapper.selectByPrimaryKey(pd);

                //计算当前
                pso.setTtlQty(pso.getTtlQty().subtract(pd.getQty()).add(pDtl.getQty()));
                pso.setTtlTax(pso.getTtlTax().subtract(pd.getTax()).add(pDtl.getTax()));
                pso.setTtlVal(pso.getTtlVal().subtract(pd.getVal()).add(pDtl.getVal()));
                pso.setTtlMkv(pso.getTtlMkv().subtract(pd.getMkv()).add(pDtl.getMkv()));
            }
            psoDtlMapper.updateList(p.getList());
        } else {
            //查询之前的记录
            PsoDtl pd = new PsoDtl();
            pd.setPsoNum(p.getPsoNum());
            pd.setProdId(p.getProdId());
            pd = psoDtlMapper.selectByPrimaryKey(pd);

            //计算当前
            pso.setTtlQty(pso.getTtlQty().subtract(pd.getQty()).add(p.getQty()));
            pso.setTtlTax(pso.getTtlTax().subtract(pd.getTax()).add(p.getTax()));
            pso.setTtlVal(pso.getTtlVal().subtract(pd.getVal()).add(p.getVal()));
            pso.setTtlMkv(pso.getTtlMkv().subtract(pd.getMkv()).add(p.getMkv()));

            psoDtlMapper.updateByPsoNum(p);
        }

        return psoMapper.updateByPrimaryKeySelective(pso);
    }

    //删除
    @Override
    public int deletePsoDtl(PsoDtlVo p) {
        String psoNum = this.psoNum(p);
        //删除购销协议明细的数据PSO_DTL
        //最后修改购销单PSO
        //先查出pso购销单数据
        Pso pso = psoMapper.selectByPrimaryKey(psoNum);

        //判断是批量删除还是只删除一条数据
        if (p.getList() != null && p.getList().size() > 0) {
            for (PsoDtlVo pDtl : p.getList()) {
                pso.setTtlQty(pso.getTtlQty().subtract(pDtl.getQty()));
                pso.setTtlTax(pso.getTtlTax().subtract(pDtl.getTax()));
                pso.setTtlVal(pso.getTtlVal().subtract(pDtl.getVal()));
                pso.setTtlMkv(pso.getTtlMkv().subtract(pDtl.getMkv()));
            }
            //批量删除
            psoDtlMapper.deleteList(p.getList());
        } else {
            pso.setTtlQty(pso.getTtlQty().subtract(p.getQty()));
            pso.setTtlTax(pso.getTtlTax().subtract(p.getTax()));
            pso.setTtlVal(pso.getTtlVal().subtract(p.getVal()));
            pso.setTtlMkv(pso.getTtlMkv().subtract(p.getMkv()));


            PsoDtl pd = new PsoDtl();
            pd.setPsoNum(p.getPsoNum());
            pd.setProdId(p.getProdId());
            psoDtlMapper.deleteByPrimaryKey(pd);
        }

        return psoMapper.updateByPrimaryKeySelective(pso);
    }

    /**
     * 批量添加明细  并更新购销单信息
     * 这里是新建主表时批量新增明细
     *
     * @param vo
     * @return
     */
    @Override
    public PsoVo insertByPso(PsoVo vo) {

        //计算购销协议需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlVal = new BigDecimal("0");
        BigDecimal ttlTax = new BigDecimal("0");
        BigDecimal ttlMkv = new BigDecimal("0");

        //循环计算主表需要更新的值
        for (int i = 0; i < vo.getPsoDtlList().size(); i++) {
            PsoDtlVo p = vo.getPsoDtlList().get(i);
            p.setPsoNum(vo.getPsoNum());
            //设置序号 默认为0  插到数据更改
            Long num = 0L;
            //设置行号 排号
            p.setLineNum(num + i + 1);
            p.setRowNum(num + i + 1);

            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlTax = ttlTax.add(p.getTax());
            ttlMkv = ttlMkv.add(p.getMkv());

        }
        //批量增加
        psoDtlMapper.insertList(vo.getPsoDtlList());

        vo.setTtlQty(ttlQty);
        vo.setTtlVal(ttlVal);
        vo.setTtlTax(ttlTax);
        vo.setTtlMkv(ttlMkv);
        return vo;
    }

    private int updatePso(Pso pso, BigDecimal ttlQty, BigDecimal ttlVal, BigDecimal ttlTax, BigDecimal ttlMkv) {
        //修改购销协议的数据PSC
        if (pso.getTtlQty() != null) {
            pso.setTtlQty(pso.getTtlQty().add(ttlQty));
        } else {
            pso.setTtlQty(ttlQty);
        }
        if (pso.getTtlVal() != null) {
            pso.setTtlVal(pso.getTtlVal().add(ttlVal));
        } else {
            pso.setTtlVal(ttlVal);
        }
        if (pso.getTtlTax() != null) {
            pso.setTtlTax(pso.getTtlTax().add(ttlTax));
        } else {
            pso.setTtlTax(ttlTax);
        }
        if (pso.getTtlMkv() != null) {
            pso.setTtlMkv(pso.getTtlMkv().add(ttlMkv));
        } else {
            pso.setTtlMkv(ttlMkv);
        }
        return psoMapper.updateByPrimaryKeySelective(pso);
    }

    /**
     * 获取编号
     *
     * @param p
     * @return
     */
    public String psoNum(PsoDtlVo p) {
        String psoNum = "";
        if (p.getList() != null && p.getList().size() > 0) {
            psoNum = p.getList().get(0).getPsoNum();
        } else {
            psoNum = p.getPsoNum();
        }
        return psoNum;
    }

}
