package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.*;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.UnitProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaDrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaRtrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaTrService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscDtlService;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class PscDtlServiceimpl implements PscDtlService {

    @Autowired
    private PscDtlMapper pscDtlMapper;

    @Autowired
    private PscMapper pscMapper;

    @Autowired
    private PscChgMapper pscChgMapper;

    @Autowired
    private PscChgDtlMapper pscChgDtlMapper;

    @Autowired
    private PscDtlRqdMapper pscDtlRqdMapper;

    @Autowired
    private PscChgDtlRqdMapper pscChgDtlRqdMapper;

    @Autowired
    private PscTypeMapper pscTypeMapper;


    @Override
    public List<PscDtlVo> findByPscNum(PscDtlVo vo) {
        return pscDtlMapper.findByPscNum(vo);
    }

    @Override
    public int insertPscDtl(PscDtlVo p, SysUser sysUser) throws Exception {

        String pscNum = p.getPscNum();
        //添加明细表 添加明细变更表 修改购销协议中的数据
        //先查出购销协议的数据
        Psc psc = pscMapper.selectByPrimaryKey(pscNum);
        Long rcdNum = this.insertPscChg(pscNum, sysUser);
        //设置序号 默认为0  插到数据更改
        Long num = 0L;
        PscDtl pd = pscDtlMapper.selectByPscDtl(pscNum);
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


        if (p.getList() != null && p.getList().size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<PscDtlRqd> pscDtlRqdList = new ArrayList<>();
            List<PscChgDtlRqd> pscChgDtlRqdList = new ArrayList<>();
            List<PscChgDtl> pscChgDtlList = new ArrayList<>();

            for (int i = 0; i < p.getList().size(); i++) {
                PscDtlVo pDtl = p.getList().get(i);

                pDtl.setPscNum(pscNum);

                //设置行号 排号
                pDtl.setLineNum(num + i + 1);
                pDtl.setRowNum(num + i + 1);

                pDtl.setTax(pDtl.getVal().divide(pDtl.getTaxRate().add(new BigDecimal(1))).multiply(pDtl.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                ttlQty = ttlQty.add(pDtl.getQty());
                ttlVal = ttlVal.add(pDtl.getVal());
                ttlTax = ttlTax.add(pDtl.getTax());
                ttlMkv = ttlMkv.add(pDtl.getMkv());

                //判断货期的添加方式  SG不做添加  PD在PSC_DTL中做保存 FR在PSC_DTL_RQD中保存
                if (psc.getRqdCtrl().equals("PD")) {
                    if (psc.getReqdDate() != null) {
                        p.setReqdDate(sdf.parse(psc.getReqdDate() + ""));
                    }
                } else if (psc.getRqdCtrl().equals("FR")) {
                    if (psc.getReqdDate() != null) {

                        PscDtlRqd pdr = new PscDtlRqd();
                        pdr.setPscNum(pDtl.getPscNum());
                        pdr.setProdId(pDtl.getProdId());
                        pdr.setQty(pDtl.getQty());
                        pdr.setReqdDate(sdf.parse(psc.getReqdDate() + ""));
                        pscDtlRqdList.add(pdr);

                        PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                        pscChgDtlRqd.setPscNum(pDtl.getPscNum());
                        pscChgDtlRqd.setProdId(pDtl.getProdId());
                        pscChgDtlRqd.setAdjQty(pDtl.getQty());
                        pscChgDtlRqd.setReqdDate(sdf.parse(psc.getReqdDate() + ""));
                        pscChgDtlRqd.setRcdNum(rcdNum);
                        pscChgDtlRqdList.add(pscChgDtlRqd);
                    }
                }
                //添加购销合同变更明细PSC_CHG_DTL
                PscChgDtl pscChgDtl = new PscChgDtl();
                pscChgDtl.setPscNum(pDtl.getPscNum());
                pscChgDtl.setRcdNum(rcdNum);
                pscChgDtl.setProdId(pDtl.getProdId());
                pscChgDtl.setAdjQty(pDtl.getQty());
                pscChgDtlList.add(pscChgDtl);
            }
            try {
                pscDtlMapper.insertList(p.getList());
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }
            if (pscDtlRqdList != null && pscDtlRqdList.size() > 0) {
                pscDtlRqdMapper.insertList(pscDtlRqdList);
            }
            if (pscChgDtlRqdList != null && pscChgDtlRqdList.size() > 0) {
                pscChgDtlRqdMapper.insertList(pscChgDtlRqdList);
            }
            if (pscChgDtlList != null && pscChgDtlList.size() > 0) {
                pscChgDtlMapper.insertList(pscChgDtlList);
            }
        } else {
            //设置行号 排号
            p.setLineNum(num + 1);
            p.setRowNum(num + 1);

            p.setTax(p.getVal().divide(p.getTaxRate().add(new BigDecimal(1))).multiply(p.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlTax = ttlTax.add(p.getTax());
            ttlMkv = ttlMkv.add(p.getMkv());

            //判断货期的添加方式  SG不做添加  PD在PSC_DTL中做保存 FR在PSC_DTL_RQD中保存
            if (psc.getRqdCtrl().equals("PD")) {
                if (psc.getReqdDate() != null) {
                    p.setReqdDate(psc.getReqdDate());
                }
            } else if (psc.getRqdCtrl().equals("FR")) {
                if (psc.getReqdDate() != null) {
                    PscDtlRqd pdr = new PscDtlRqd();
                    pdr.setPscNum(p.getPscNum());
                    pdr.setProdId(p.getProdId());
                    pdr.setQty(p.getQty());
                    pdr.setReqdDate(psc.getReqdDate());
                    pscDtlRqdMapper.insertSelective(pdr);

                    PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                    pscChgDtlRqd.setPscNum(p.getPscNum());
                    pscChgDtlRqd.setProdId(p.getProdId());
                    pscChgDtlRqd.setAdjQty(p.getQty());
                    pscChgDtlRqd.setReqdDate(psc.getReqdDate());
                    pscChgDtlRqd.setRcdNum(rcdNum);
                    pscChgDtlRqdMapper.insertSelective(pscChgDtlRqd);
                }
            }
            try {
                //添加表明细PSC_DTL
                pscDtlMapper.insertByPscNum(p);
            } catch (Exception e) {
                throw new ServiceException(ResultCode.DATA_REPEAT, "存在重复记录！");
            }

            //添加购销合同变更明细PSC_CHG_DTL
            PscChgDtl pscChgDtl = new PscChgDtl();
            pscChgDtl.setPscNum(p.getPscNum());
            pscChgDtl.setRcdNum(rcdNum);
            pscChgDtl.setProdId(p.getProdId());
            pscChgDtl.setAdjQty(p.getQty());
            pscChgDtlMapper.insertSelective(pscChgDtl);
        }

        return this.updatePsc(psc, ttlQty, ttlVal, ttlTax, ttlMkv);
    }


    /**
     * 修改合同明细
     *
     * @param p
     * @param sysUser
     * @return
     */
    @Override
    public int updatePscDtl(PscDtlVo p, SysUser sysUser) {
        String pscNum = this.pscNum(p);
        //修改明细表 添加或修改明细变更表 修改购销协议中的数据
        //先查出购销协议的数据
        Psc psc = pscMapper.selectByPrimaryKey(pscNum);
        Long rcdNum = this.insertPscChg(pscNum, sysUser);
        //计算购销协议需要修改的数据

        BigDecimal qty = new BigDecimal("0");//数量
        BigDecimal tax = new BigDecimal("0");//税款
        BigDecimal val = new BigDecimal("0");//金额
        BigDecimal mkv = new BigDecimal("0");//市值

        //添加或修改购销合同变更PSC_CHG
        //添加购销合同变更明细PSC_CHG_DTL

        if (p.getList() != null && p.getList().size() > 0) {
            List<PscDtlRqd> pscDtlRqdList = new ArrayList<>();
            List<PscChgDtlRqd> pscChgDtlRqdList = new ArrayList<>();
            List<PscChgDtl> pscChgDtlList = new ArrayList<>();
            for (PscDtlVo pDtl : p.getList()) {
                //查询之前的记录
                PscDtl pd = pscDtlMapper.selectByPrimaryKey(new PscDtl(pDtl.getPscNum(), pDtl.getProdId()));

                pDtl.setTax(pDtl.getVal().divide(pDtl.getTaxRate().add(new BigDecimal(1))).multiply(pDtl.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
                //判断是否修改了数量
                BigDecimal num = new BigDecimal("0");

                if (pDtl.getQty().compareTo(pd.getQty()) != 0) {
                    num = pDtl.getQty().subtract(pd.getQty());
                }


                //计算当前
                psc.setTtlQty(psc.getTtlQty().subtract(pd.getQty()).add(pDtl.getQty()));
                psc.setTtlTax(psc.getTtlTax().subtract(pd.getTax()).add(pDtl.getTax()));
                psc.setTtlVal(psc.getTtlVal().subtract(pd.getVal()).add(pDtl.getVal()));
                psc.setTtlMkv(psc.getTtlMkv().subtract(pd.getMkv()).add(pDtl.getMkv()));

                PscType pscType = pscTypeMapper.selectByPrimaryKey(psc.getPscType());
                //计算保证金以及定金
                psc.setDeposit(psc.getDeposit().add((pDtl.getVal().subtract(pd.getVal())).multiply(pscType.getDpstRate())));
                if (psc.getGmEnabled().equals("T")) {
                    psc.setGuaMon(psc.getGuaMon().add((pDtl.getVal().subtract(pd.getVal())).multiply(pscType.getGmRate())));
                }
                if (num.compareTo(new BigDecimal("0")) != 0) {
                    //添加购销合同变更明细PSC_CHG_DTL
                    PscChgDtl pscChgDtl = new PscChgDtl();
                    pscChgDtl.setPscNum(pDtl.getPscNum());
                    pscChgDtl.setRcdNum(rcdNum);
                    pscChgDtl.setProdId(pDtl.getProdId());
                    pscChgDtl.setAdjQty(num);
                    pscChgDtlList.add(pscChgDtl);
//                    pscChgDtlMapper.insertSelective(pscChgDtl);
                    if (psc.getRqdCtrl().equals("FR")) {
                        if (psc.getReqdDate() != null) {
                            PscDtlRqd pscDtlRqd = new PscDtlRqd();
                            pscDtlRqd.setPscNum(pDtl.getPscNum());
                            pscDtlRqd.setReqdDate(psc.getReqdDate());
                            pscDtlRqd.setProdId(pDtl.getProdId());
                            pscDtlRqd.setQty(pDtl.getQty());
                            pscDtlRqdList.add(pscDtlRqd);
//                    pscDtlRqdMapper.updateByPrimaryKeySelective(pscDtlRqd);
                            PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                            pscChgDtlRqd.setPscNum(pDtl.getPscNum());
                            pscChgDtlRqd.setReqdDate(psc.getReqdDate());
                            pscChgDtlRqd.setProdId(pDtl.getProdId());
                            pscChgDtlRqd.setAdjQty(num);
                            pscChgDtlRqd.setRcdNum(rcdNum);
                            pscChgDtlRqdList.add(pscChgDtlRqd);
                        }
                    }
                }
            }
            pscDtlMapper.updateList(p.getList());
            if (pscDtlRqdList != null && pscDtlRqdList.size() > 0) {
                pscDtlRqdMapper.updateList(pscDtlRqdList);
            }
            if (pscChgDtlRqdList != null && pscChgDtlRqdList.size() > 0) {
                pscChgDtlRqdMapper.insertList(pscChgDtlRqdList);
            }
            if (pscChgDtlList != null && pscChgDtlList.size() > 0) {
                pscChgDtlMapper.insertList(pscChgDtlList);
            }

        } else {
            //查询之前的记录
            PscDtl pd = pscDtlMapper.selectByPrimaryKey(new PscDtl(p.getPscNum(), p.getProdId()));

//            p.setFnlPrice(p.getDiscRate().multiply(p.getDiscRate()));
            p.setTax(p.getVal().divide(p.getTaxRate().add(new BigDecimal(1))).multiply(p.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            //判断是否修改了数量
            BigDecimal num = new BigDecimal("0");

            if (p.getQty().compareTo(pd.getQty()) != 0) {
                num = p.getQty().subtract(pd.getQty());
            }
            //计算当前
            psc.setTtlQty(psc.getTtlQty().subtract(pd.getQty()).add(p.getQty()));
            psc.setTtlTax(psc.getTtlTax().subtract(pd.getTax()).add(p.getTax()));
            psc.setTtlVal(psc.getTtlVal().subtract(pd.getVal()).add(p.getVal()));
            psc.setTtlMkv(psc.getTtlMkv().subtract(pd.getMkv()).add(p.getMkv()));

            PscType pscType = pscTypeMapper.selectByPrimaryKey(psc.getPscType());
            //计算保证金以及定金
            psc.setDeposit(psc.getDeposit().add((p.getVal().subtract(pd.getVal())).multiply(pscType.getDpstRate())));
            if (psc.getGmEnabled().equals("T")) {
                psc.setGuaMon(psc.getGuaMon().add((p.getVal().subtract(pd.getVal())).multiply(pscType.getGmRate())));
            }
            if (num.compareTo(new BigDecimal("0")) != 0) {
                //添加购销合同变更明细PSC_CHG_DTL
                PscChgDtl pscChgDtl = new PscChgDtl();
                pscChgDtl.setPscNum(p.getPscNum());
                pscChgDtl.setRcdNum(rcdNum);
                pscChgDtl.setProdId(p.getProdId());
                pscChgDtl.setAdjQty(num);
                pscChgDtlMapper.insertSelective(pscChgDtl);
                if (psc.getRqdCtrl().equals("FR")) {
                    if (psc.getReqdDate() != null) {
                        PscDtlRqd pscDtlRqd = new PscDtlRqd();
                        pscDtlRqd.setPscNum(p.getPscNum());
                        pscDtlRqd.setReqdDate(psc.getReqdDate());
                        pscDtlRqd.setProdId(p.getProdId());
                        pscDtlRqd.setQty(num);
                        pscDtlRqdMapper.updateByPrimaryKeySelective(pscDtlRqd);
                        PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                        pscChgDtlRqd.setPscNum(p.getPscNum());
                        pscChgDtlRqd.setReqdDate(psc.getReqdDate());
                        pscChgDtlRqd.setProdId(p.getProdId());
                        pscChgDtlRqd.setAdjQty(num);
                        pscChgDtlRqd.setRcdNum(rcdNum);
                        pscChgDtlRqdMapper.insertSelective(pscChgDtlRqd);
                    }
                }
            }
            pscDtlMapper.updateByPsc(p);
        }

        //修改购销协议的数据PSC
        return pscMapper.updateByPrimaryKeySelective(psc);
    }

    /**
     * 删除合同明细
     *
     * @param p
     * @param sysUser
     * @return
     */
    @Override
    public int deletePscDtl(PscDtlVo p, SysUser sysUser) {

        String pscNum = this.pscNum(p);
        //循环通过购销合同与商品id查出需要删除的记录
        //添加或修改购销合同变更PSC_CHG
        //添加购销合同变更明细PSC_CHG_DTL
        //删除购销协议明细的数据PSC_DTL
        //最后修改购销合同PSC
        Psc psc = pscMapper.selectByPrimaryKey(pscNum);
        Long rcdNum = this.insertPscChg(pscNum, sysUser);

        //添加或修改购销合同变更PSC_CHG
        //添加购销合同变更明细PSC_CHG_DTL

        if (p.getList() != null && p.getList().size() > 0) {
            List<PscDtlRqd> pscDtlRqdList = new ArrayList<>();
            List<PscChgDtlRqd> pscChgDtlRqdList = new ArrayList<>();
            List<PscChgDtl> pscChgDtlList = new ArrayList<>();
            for (PscDtlVo pDtl : p.getList()) {
                //添加购销合同变更明细PSC_CHG_DTL
                PscChgDtl pscChgDtl = new PscChgDtl();
                pscChgDtl.setPscNum(pDtl.getPscNum());
                pscChgDtl.setRcdNum(rcdNum);
                pscChgDtl.setProdId(pDtl.getProdId());
                pscChgDtl.setAdjQty(new BigDecimal("0").subtract(pDtl.getQty()));
                pscChgDtlList.add(pscChgDtl);

                if (psc.getRqdCtrl().equals("FR")) {
                    PscDtlRqd pscDtlRqd = new PscDtlRqd();
                    pscDtlRqd.setPscNum(pDtl.getPscNum());
                    pscDtlRqd.setProdId(pDtl.getProdId());
                    pscDtlRqd.setReqdDate(psc.getReqdDate());
                    pscDtlRqdList.add(pscDtlRqd);
                    PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                    pscChgDtlRqd.setPscNum(pDtl.getPscNum());
                    pscChgDtlRqd.setReqdDate(psc.getReqdDate());
                    pscChgDtlRqd.setProdId(pDtl.getProdId());
                    pscChgDtlRqd.setAdjQty(new BigDecimal("0").subtract(pDtl.getQty()));
                    pscChgDtlRqd.setRcdNum(rcdNum);
                    pscChgDtlRqdList.add(pscChgDtlRqd);
                }
                psc.setTtlQty(psc.getTtlQty().subtract(pDtl.getQty()));
                psc.setTtlTax(psc.getTtlTax().subtract(pDtl.getTax()));
                psc.setTtlVal(psc.getTtlVal().subtract(pDtl.getVal()));
                psc.setTtlMkv(psc.getTtlMkv().subtract(pDtl.getMkv()));

                PscType pscType = pscTypeMapper.selectByPrimaryKey(psc.getPscType());
                //计算保证金以及定金
                psc.setDeposit(psc.getDeposit().subtract(pDtl.getVal().multiply(pscType.getDpstRate())));
                if (psc.getGmEnabled().equals("T")) {
                    psc.setGuaMon(psc.getGuaMon().subtract(pDtl.getVal().multiply(pscType.getGmRate())));
                }

            }
            pscDtlMapper.deleteList(p.getList());
            if (pscDtlRqdList != null && pscDtlRqdList.size() > 0) {
                pscDtlRqdMapper.deleteList(pscDtlRqdList);
            }
            if (pscChgDtlRqdList != null && pscChgDtlRqdList.size() > 0) {
                pscChgDtlRqdMapper.insertList(pscChgDtlRqdList);
            }
            if (pscChgDtlList != null && pscChgDtlList.size() > 0) {
                pscChgDtlMapper.insertList(pscChgDtlList);
            }

        } else {
            //添加购销合同变更明细PSC_CHG_DTL
            PscChgDtl pscChgDtl = new PscChgDtl();
            pscChgDtl.setPscNum(p.getPscNum());
            pscChgDtl.setRcdNum(rcdNum);
            pscChgDtl.setProdId(p.getProdId());
            pscChgDtl.setAdjQty(new BigDecimal("0").subtract(p.getQty()));
            pscChgDtlMapper.insertSelective(pscChgDtl);

            if (psc.getRqdCtrl().equals("FR")) {
                PscDtlRqd pscDtlRqd = new PscDtlRqd();
                pscDtlRqd.setPscNum(p.getPscNum());
                pscDtlRqd.setProdId(p.getProdId());
                pscDtlRqd.setReqdDate(psc.getReqdDate());
                pscDtlRqdMapper.deleteByPrimaryKey(pscDtlRqd);
                PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                pscChgDtlRqd.setPscNum(p.getPscNum());
                pscChgDtlRqd.setReqdDate(psc.getReqdDate());
                pscChgDtlRqd.setProdId(p.getProdId());
                pscChgDtlRqd.setAdjQty(new BigDecimal("0").subtract(p.getQty()));
                pscChgDtlRqd.setRcdNum(rcdNum);
                pscChgDtlRqdMapper.insertSelective(pscChgDtlRqd);
            }

            psc.setTtlQty(psc.getTtlQty().subtract(p.getQty()));
            psc.setTtlTax(psc.getTtlTax().subtract(p.getTax()));
            psc.setTtlVal(psc.getTtlVal().subtract(p.getVal()));
            psc.setTtlMkv(psc.getTtlMkv().subtract(p.getMkv()));

            PscType pscType = pscTypeMapper.selectByPrimaryKey(psc.getPscType());
            //计算保证金以及定金
            psc.setDeposit(psc.getDeposit().subtract(p.getVal().multiply(pscType.getDpstRate())));
            if (psc.getGmEnabled().equals("T")) {
                psc.setGuaMon(psc.getGuaMon().subtract(p.getVal().multiply(pscType.getGmRate())));
            }

            pscDtlMapper.deleteByPrimaryKey(new PscDtl(p.getPscNum(), p.getProdId()));
        }

        return pscMapper.updateByPrimaryKeySelective(psc);
    }

    /**
     * 更换类别以及供应商时删除明细
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int deletePscDtl(List<PscDtlVo> vo, SysUser sysUser) {
        //循环通过购销合同与商品id查出需要删除的记录
        //添加或修改购销合同变更PSC_CHG
        //添加购销合同变更明细PSC_CHG_DTL
        //删除购销协议明细的数据PSC_DTL
        //最后修改购销合同PSC
        Psc psc = pscMapper.selectByPrimaryKey(vo.get(0).getPscNum());
        Long rcdNum = this.insertPscChg(psc.getPscNum(), sysUser);
        //添加或修改购销合同变更PSC_CHG
        //添加购销合同变更明细PSC_CHG_DTL
        for (int i = 0; i < vo.size(); i++) {
            PscDtlVo p = vo.get(i);

            //添加购销合同变更明细PSC_CHG_DTL
            PscChgDtl pscChgDtl = new PscChgDtl();
            pscChgDtl.setPscNum(p.getPscNum());
            pscChgDtl.setRcdNum(rcdNum);
            pscChgDtl.setProdId(p.getProdId());
            pscChgDtl.setAdjQty(new BigDecimal("0").subtract(p.getQty()));
            pscChgDtlMapper.insertSelective(pscChgDtl);
            //先判断货期控制是否是FR
            if (psc.getRqdCtrl().equals("FR")) {
                PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                pscChgDtlRqd.setPscNum(p.getPscNum());
                pscChgDtlRqd.setRcdNum(rcdNum);
                pscChgDtlRqd.setProdId(p.getProdId());
                pscChgDtlRqd.setAdjQty(new BigDecimal("0").subtract(p.getQty()));
                pscChgDtlRqd.setReqdDate(psc.getReqdDate());
                pscChgDtlRqdMapper.insertSelective(pscChgDtlRqd);
            }
        }
        pscDtlMapper.deleteByPscNum(psc.getPscNum());
        if (psc.getRqdCtrl().equals("FR")) {
            pscDtlRqdMapper.deleteByRqdCtrl(psc.getPscNum());
        }
        psc.setTtlQty(new BigDecimal("0"));
        psc.setTtlTax(new BigDecimal("0"));
        psc.setTtlVal(new BigDecimal("0"));
        psc.setTtlMkv(new BigDecimal("0"));
        psc.setDeposit(new BigDecimal("0"));
        psc.setGuaMon(new BigDecimal("0"));
        return pscMapper.updateByPrimaryKeySelective(psc);
    }

    /**
     * 新增采购合同时，增加明细
     *
     * @param v
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public PscVo insertByPsc(PscVo v, SysUser user) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //计算购销协议需要修改的数据
        BigDecimal ttlQty = new BigDecimal("0");
        BigDecimal ttlVal = new BigDecimal("0");
        BigDecimal ttlTax = new BigDecimal("0");
        BigDecimal ttlMkv = new BigDecimal("0");

        //添加或修改购销合同变更PSC_CHG

        PscChg pscChg = new PscChg();
        pscChg.setPscNum(v.getPscNum());
        pscChg.setRcdNum(1L);
        pscChg.setOprId(user.getPrsnl().getPrsnlId());
        pscChgMapper.insertSelective(pscChg);

        List<PscDtlRqd> pscDtlRqdList = new ArrayList<>();
        List<PscChgDtlRqd> pscChgDtlRqdList = new ArrayList<>();
        List<PscChgDtl> pscChgDtlList = new ArrayList<>();

        for (int i = 0; i < v.getPscDtlList().size(); i++) {
            PscDtlVo p = v.getPscDtlList().get(i);
            p.setPscNum(v.getPscNum());
            //设置序号 默认为0  插到数据更改
            Long num = 0L;
            //设置行号 排号
            p.setLineNum(num + i + 1);
            p.setRowNum(num + i + 1);

            p.setTax(p.getVal().divide(p.getTaxRate().add(new BigDecimal(1))).multiply(p.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

            ttlQty = ttlQty.add(p.getQty());
            ttlVal = ttlVal.add(p.getVal());
            ttlTax = ttlTax.add(p.getTax());
            ttlMkv = ttlMkv.add(p.getMkv());

            //判断货期的添加方式  SG不做添加  PD在PSC_DTL中做保存 FR在PSC_DTL_RQD中保存
            if (v.getRqdCtrl().equals("PD")) {
                if (v.getReqdDate() != null) {
                    p.setReqdDate(sdf.parse(v.getReqdDate()));
                }
            } else if (v.getRqdCtrl().equals("FR")) {
                if (v.getReqdDate() != null) {

                    PscDtlRqd pdr = new PscDtlRqd();
                    pdr.setPscNum(p.getPscNum());
                    pdr.setProdId(p.getProdId());
                    pdr.setQty(p.getQty());
                    pdr.setReqdDate(sdf.parse(v.getReqdDate()));
                    pscDtlRqdList.add(pdr);

                    PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                    pscChgDtlRqd.setPscNum(p.getPscNum());
                    pscChgDtlRqd.setProdId(p.getProdId());
                    pscChgDtlRqd.setAdjQty(p.getQty());
                    pscChgDtlRqd.setReqdDate(sdf.parse(v.getReqdDate()));
                    pscChgDtlRqd.setRcdNum(pscChg.getRcdNum());
                    pscChgDtlRqdList.add(pscChgDtlRqd);
                }
            }
            //添加购销合同变更明细PSC_CHG_DTL
            PscChgDtl pscChgDtl = new PscChgDtl();
            pscChgDtl.setPscNum(p.getPscNum());
            pscChgDtl.setRcdNum(pscChg.getRcdNum());
            pscChgDtl.setProdId(p.getProdId());
            pscChgDtl.setAdjQty(p.getQty());
            pscChgDtlList.add(pscChgDtl);
        }

        pscDtlMapper.insertList(v.getPscDtlList());
        if (pscDtlRqdList != null && pscDtlRqdList.size() > 0) {
            pscDtlRqdMapper.insertList(pscDtlRqdList);
        }
        if (pscChgDtlRqdList != null && pscChgDtlRqdList.size() > 0) {
            pscChgDtlRqdMapper.insertList(pscChgDtlRqdList);
        }
        if (pscChgDtlList != null && pscChgDtlList.size() > 0) {
            pscChgDtlMapper.insertList(pscChgDtlList);
        }
        v.setTtlQty(ttlQty);
        v.setTtlVal(ttlVal);
        v.setTtlTax(ttlTax);
        v.setTtlMkv(ttlMkv);
        return v;
    }

    /**
     * 删除采购合同时，删除明细操作
     *
     * @param pscNum
     * @return
     */
    @Override
    public void deleteByPuc(String pscNum, SysUser sysUser) {
        List<PscDtlVo> list = pscDtlMapper.findByPscNum(new PscDtlVo(pscNum));
        if (list != null && list.size() > 0) {
            this.deletePscDtl(list, sysUser);
        }
    }


    /**
     * 采购单中选择商品后判断返回
     *
     * @param vo
     * @return
     */
    @Override
    public List<PscDtlVo> getJudgeDtlByPuo(PscDtlVo vo) {
        return pscDtlMapper.getJudgeDtlByPuo(vo);
    }

    /**
     * 销售单中选择商品后判断返回
     *
     * @param vo
     * @return
     */
    @Override
    public List<PscDtlVo> getJudgeDtlBySlo(PscDtlVo vo) {
        return pscDtlMapper.getJudgeDtlBySlo(vo);
    }

    /**
     * 采购合同中判断是否继续收货
     *
     * @param pscNum
     * @return
     */
    @Override
    public List<PscDtlVo> judge(String pscNum) {
        return pscDtlMapper.judge(pscNum);
    }


    public int updatePsc(Psc psc, BigDecimal ttlQty, BigDecimal ttlVal, BigDecimal ttlTax, BigDecimal ttlMkv) {
        PscType pscType = pscTypeMapper.selectByPrimaryKey(psc.getPscType());

        //修改购销协议的数据PSC
        if (psc.getTtlQty() != null) {
            psc.setTtlQty(psc.getTtlQty().add(ttlQty));
        } else {
            psc.setTtlQty(ttlQty);
        }
        if (psc.getTtlVal() != null) {
            psc.setTtlVal(psc.getTtlVal().add(ttlVal));
        } else {
            psc.setTtlVal(ttlVal);
        }
        //计算保证金
        if (psc.getGmEnabled().equals("T")) {
            if (psc.getGuaMon() != null) {
                psc.setDeposit(psc.getGuaMon().add(ttlVal.multiply(pscType.getGmRate())));
            } else {
                psc.setDeposit(ttlVal.multiply(pscType.getGmRate()));
            }
        }
        //计算定金
        if (psc.getDeposit() != null) {
            psc.setDeposit(psc.getDeposit().add(ttlVal.multiply(pscType.getDpstRate())));
        } else {
            psc.setDeposit(ttlVal.multiply(pscType.getDpstRate()));
        }
        if (psc.getTtlTax() != null) {
            psc.setTtlTax(psc.getTtlTax().add(ttlTax));
        } else {
            psc.setTtlTax(ttlTax);
        }
        if (psc.getTtlMkv() != null) {
            psc.setTtlMkv(psc.getTtlMkv().add(ttlMkv));
        } else {
            psc.setTtlMkv(ttlMkv);
        }
        return pscMapper.updateByPrimaryKeySelective(psc);
    }

    public String pscNum(PscDtlVo p) {
        String pscNum = "";
        if (p.getList() != null && p.getList().size() > 0) {
            pscNum = p.getList().get(0).getPscNum();
        } else {
            pscNum = p.getPscNum();
        }
        return pscNum;
    }

    //生成pscChg
    private Long insertPscChg(String pscNum, SysUser sysUser) {
        PscChg pscChg = pscChgMapper.selectByPscDtl(pscNum);
        //添加或修改购销合同变更PSC_CHG
        if (pscChg != null) {
            pscChg.setRcdNum(pscChg.getRcdNum() + 1);
        } else {
            pscChg = new PscChg();
            pscChg.setPscNum(pscNum);
            pscChg.setRcdNum(1L);
        }
        pscChg.setOprId(sysUser.getPrsnl().getPrsnlId());
        pscChg.setOpTime(new Date());
        pscChgMapper.insertSelective(pscChg);
        return pscChg.getRcdNum();
    }
}
