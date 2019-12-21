package com.boyu.erp.platform.usercenter.service.priceservice.priceServiceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.Price.PpnDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PpnScpMapper;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: PpnDtlServiceImpl
 * @description: 采购价格单明细服务层
 * @author: wz
 * @create: 2019-8-26 9:43
 */
@Slf4j
@Service
@Transactional
public class PpnDtlServiceImpl implements PpnDtlService {

    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private PpnDtlMapper ppnDtlMapper;
    @Autowired
    private PpnScpMapper ppnScpMapper;


    @Override
    public List<PpnDtlVo> findByPpn(PpnDtlVo vo) {
        return ppnDtlMapper.selectByPpn(vo);
    }

    /**
     * 新增
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertPpnDtl(PpnDtlVo vo, SysUser user) {
        if(vo.getList()!=null && vo.getList().size()>0){
            return ppnDtlMapper.insertByList(vo.getList());
        }
        try {
            return ppnDtlMapper.insertSelective(vo);
        }catch (Exception e){
            throw new ServiceException(ResultCode.DATA_REPEAT,"存在重复记录！");
        }

    }

    /**
     * 修改
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updatePpnDtl(PpnDtlVo vo, SysUser user) {
        if(vo.getList()!=null && vo.getList().size()>0){
            return ppnDtlMapper.updateByList(vo.getList());
        }
        return ppnDtlMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 删除
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deletePpnDtl(PpnDtlVo vo, SysUser user) {
        if(vo.getList()!=null && vo.getList().size()>0){
            return ppnDtlMapper.deleteByList(vo.getList());
        }
        return ppnDtlMapper.deleteByPrimaryKey(vo);
    }
}
