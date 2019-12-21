package com.boyu.erp.platform.usercenter.service.caservice.caserviceimpl;

import com.boyu.erp.platform.usercenter.entity.basic.CaTx;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.basic.CaTxMapper;
import com.boyu.erp.platform.usercenter.service.caservice.CaTxService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: CaTxServerImpl
 * @description: 往来账户事务
 * @author: wz
 * @create: 2019-9-10 10:19 */
@Service
@Transactional
public class CaTxServerImpl implements CaTxService {

    @Autowired
    private CaTxMapper caTxMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    /**
     * 单据审核时添加数据
     * @param caTx
     * @return
     */
    @Override
    public int insertByBill(CaTx caTx, SysUser user) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //生成ca_tx_id
        String caTxId = sysRefNumService.viceNum(user, "CA_TX_ID");
        caTx.setCaTxId(Long.valueOf(caTxId));
        caTx.setFsclDate(sdf.parse(sdf.format(new Date())));
        caTx.setTxTime(new Date());
        caTx.setCaTxKind("DV");
        caTx.setCaTxType("");
        return caTxMapper.insertSelective(caTx);
    }
}
