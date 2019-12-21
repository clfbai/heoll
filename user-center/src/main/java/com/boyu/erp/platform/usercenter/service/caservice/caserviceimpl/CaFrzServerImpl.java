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
import com.boyu.erp.platform.usercenter.service.caservice.CaFrzService;
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
 * @program: CaFrzServerImpl
 * @description: 往来账户冻结明细
 * @author: wz
 * @create: 2019-10-6 11:39
 */
@Service
@Transactional
public class CaFrzServerImpl implements CaFrzService {

    @Autowired
    private CaFrzMapper caFrzMapper;

    /**
     * 查询往来账户中冻结明细
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<CaFrz> selectAll(CaVo vo, Integer page, Integer size, SysUser sysUser) throws Exception {
        //查询所有授信明细
        PageHelper.startPage(page, size);
        List<CaFrz> list = caFrzMapper.selectALL(vo);
        PageInfo<CaFrz> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
