package com.boyu.erp.platform.usercenter.service.priceservice.priceServiceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.Price.PpnDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SpnDtlMapper;
import com.boyu.erp.platform.usercenter.service.priceservice.SpnDtlService;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: SpnDtlServiceImpl
 * @description: 销售价格单明细服务层
 * @author: wz
 * @create: 2019-8-26 9:43
 */
@Slf4j
@Service
@Transactional
public class SpnDtlServiceImpl implements SpnDtlService {

    @Autowired
    private SpnDtlMapper spnDtlMapper;


    @Override
    public List<SpnDtlVo> findBySpn(SpnDtlVo vo) {
        return spnDtlMapper.selectBySpn(vo);
    }

    /**
     * 新增
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertSpnDtl(SpnDtlVo vo, SysUser user) {
        if(vo.getList()!=null && vo.getList().size()>0){
            return spnDtlMapper.insertByList(vo.getList());
        }
        try {
            return spnDtlMapper.insertSelective(vo);
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
    public int updateSpnDtl(SpnDtlVo vo, SysUser user) {
        if(vo.getList()!=null && vo.getList().size()>0){
            return spnDtlMapper.updateByList(vo.getList());
        }
        return spnDtlMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 删除
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deleteSpnDtl(SpnDtlVo vo, SysUser user) {
        if(vo.getList()!=null && vo.getList().size()>0){
            return spnDtlMapper.deleteByList(vo.getList());
        }
        return spnDtlMapper.deleteByPrimaryKey(vo);
    }
}
