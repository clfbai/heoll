package com.boyu.erp.platform.usercenter.service.caservice.caserviceimpl;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.basic.CaFrz;
import com.boyu.erp.platform.usercenter.entity.basic.CaTx;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.basic.CaAccMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CaFrzMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.caservice.CaTxService;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: CaAccServerImpl
 * @description: 往来账户记账
 * @author: wz
 * @create: 2019-9-10 10:19
 */
@Service
@Transactional
public class CaAccServerImpl implements CaAccService {

    @Autowired
    private CaAccMapper caAccMapper;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private CaFrzMapper caFrzMapper;
    @Autowired
    private CaTxService caTxService;

    //单据方面往来账户得解冻
    @Override
    public void defreeze(CaAcc caAcc, SysUser user) throws Exception {
        List<CaAcc> list = caAccMapper.selectByDoc(caAcc);
        if (list.isEmpty()) {
            list = new ArrayList<>();
            list.add(caAcc);
        }
        if (list != null && list.size() > 0) {
            for (CaAcc vo : list) {
                //查询ca表
                Ca ca = caMapper.selectByPrimaryKey(new Ca(caAcc.getUnitId(), vo.getCaId(), 0L));
                if (ca != null) {
                    //查询ca_frz表
                    CaFrz caFrz = caFrzMapper.selectByPrimaryKey(new CaFrz(ca.getUnitId(), vo.getCaId(), vo.getDocType(), vo.getDocUnitId(), vo.getDocNum()));
                    if (caFrz != null) {
                        //修改ca表
                        ca.setDbFrzBal(ca.getDbFrzBal().subtract(caFrz.getDbFrzVal()));
                        ca.setCrFrzBal(ca.getCrFrzBal().subtract(caFrz.getCrFrzVal()));
                        caMapper.updateByPrimaryKeySelective(ca);
                        //删除ca_frz表
                        caFrzMapper.deleteByPrimaryKey(caFrz);
                        //登记ca_tx表
                        caTxService.insertByBill(new CaTx(user.getDomain().getUnitId(), vo.getCaId(), vo.getDocType(), vo.getDocUnitId(), vo.getDocNum(), "FZ", "F", new BigDecimal("0").subtract(caFrz.getDbFrzVal()), new BigDecimal("0").subtract(caFrz.getCrFrzVal())), user);
                    }
                }
            }
        }
    }

    //单据方面往来账户得冻结
    @Override
    public void freeze(CaAcc caAcc, SysUser user, Ca ca, BigDecimal debitValue, BigDecimal creditValue,boolean flag) throws Exception {
        //交易金额预处理
        if (ca.getBalDir().equals("D")) {
            debitValue = debitValue.subtract(creditValue);
            creditValue = new BigDecimal("0");
        } else if (ca.getBalDir().equals("C")) {
            creditValue = creditValue.subtract(debitValue);
            debitValue = new BigDecimal("0");
        }
        //更新ca_frz表  无记录则新增 如果表中字段都等于0则删除
        if (debitValue.compareTo(new BigDecimal("0")) != 0 || creditValue.compareTo(new BigDecimal("0")) != 0) {
            CaFrz caFrz = new CaFrz(ca.getUnitId(), ca.getCaId(), caAcc.getDocType(), caAcc.getDocUnitId(), caAcc.getDocNum());
            CaFrz frz = caFrzMapper.selectByPrimaryKey(caFrz);
            //存在就更新数据   不存在就新增
            if(!flag){
                creditValue = new BigDecimal("0").subtract(creditValue);
                debitValue = new BigDecimal("0").subtract(debitValue);
            }
            if (frz != null) {
                frz.setDbFrzVal(frz.getDbFrzVal().add(debitValue));
                frz.setCrFrzVal(frz.getCrFrzVal().add(creditValue));
                caFrzMapper.updateByPrimaryKeySelective(frz);
            } else {
                caFrz.setDbFrzVal(debitValue);
                caFrz.setCrFrzVal(creditValue);
                caFrzMapper.insertSelective(caFrz);
            }
            //更新ca表中字段
            ca.setDbFrzBal(ca.getDbFrzBal().add(debitValue));
            ca.setCrFrzBal(ca.getCrFrzBal().add(creditValue));
            caMapper.updateByPrimaryKeySelective(ca);
        }
        //调用doCheckBalance（预留）

        //登记ca_tx表
        caTxService.insertByBill(new CaTx(user.getDomain().getUnitId(), ca.getCaId(), caAcc.getDocType(), caAcc.getDocUnitId(), caAcc.getDocNum(), "FZ", "F", debitValue, creditValue), user);
    }

    //单据方面往来账户的授信
    @Override
    public void accredit(CaAcc caAcc, SysUser user, Ca ca, BigDecimal debitValue, BigDecimal creditValue,boolean flag) throws Exception {
        //交易金额预处理
        if (ca.getBalDir().equals("D")) {
            debitValue = debitValue.subtract(creditValue);
            creditValue = new BigDecimal("0");
        } else if (ca.getBalDir().equals("C")) {
            creditValue = creditValue.subtract(debitValue);
            debitValue = new BigDecimal("0");
        }

        //如果这两个值都为0  不需要修改数据也不需要创建数据
        if (debitValue.compareTo(new BigDecimal("0")) != 0 || creditValue.compareTo(new BigDecimal("0")) != 0) {
            if(!flag){
                creditValue = new BigDecimal("0").subtract(creditValue);
                debitValue = new BigDecimal("0").subtract(debitValue);
            }
            //更新ca_acc表字段
            List<CaAcc> accList = caAccMapper.selectByDoc(caAcc);
            if (accList != null && accList.size() > 0) {
                CaAcc acc = accList.get(0);
                acc.setDbAccVal(acc.getDbAccVal().add(debitValue));
                acc.setCrAccVal(acc.getCrAccVal().add(creditValue));
                caAccMapper.updateByPrimaryKeySelective(acc);
            } else {
                caAcc.setUnitId(user.getDomain().getUnitId());
                caAcc.setDbAccVal(debitValue);
                caAcc.setCrAccVal(creditValue);
                caAccMapper.insertSelective(caAcc);
            }
            ca.setDbAccBal(ca.getDbAccBal().add(debitValue));
            ca.setCrAccBal(ca.getCrAccBal().add(creditValue));
            caMapper.updateByPrimaryKeySelective(ca);
        }
        //登记ca_tx表
        caTxService.insertByBill(new CaTx(user.getDomain().getUnitId(), ca.getCaId(), caAcc.getDocType(), caAcc.getDocUnitId(), caAcc.getDocNum(), "AC", "F", debitValue, creditValue), user);
    }

    /**
     * 单据方面取消授信接口
     *
     * @param caAcc
     * @param user
     */
    @Override
    public void revoke(CaAcc caAcc, SysUser user) throws Exception {
        //查询出caAcc集合
        List<CaAcc> accList = caAccMapper.selectByDoc(caAcc);
        if (accList != null && accList.size() > 0) {
            caMapper.updateByRevoke(accList);
            for (CaAcc acc : accList) {
                //登记ca_tx表
                caTxService.insertByBill(new CaTx(user.getDomain().getUnitId(), acc.getCaId(), acc.getDocType(), acc.getDocUnitId(), acc.getDocNum(), "AC", "F", acc.getDbAccVal(), acc.getCrAccVal()), user);
            }
            //删除
            caAccMapper.deleteByRevoke(accList);
        }
    }

    /**
     * 往来账户中查询授信明细
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<CaAcc> selectAll(CaVo vo, Integer page, Integer size, SysUser sysUser) throws Exception {
        //查询所有授信明细
        PageHelper.startPage(page, size);
        List<CaAcc> list = caAccMapper.selectALL(vo);
        PageInfo<CaAcc> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
