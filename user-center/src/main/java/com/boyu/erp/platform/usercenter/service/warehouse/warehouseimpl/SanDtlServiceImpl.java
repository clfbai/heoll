package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.SanDtl;
import com.boyu.erp.platform.usercenter.mapper.warehouse.SanDtlMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.SanDtlService;
import com.boyu.erp.platform.usercenter.vo.warehouse.SanDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 库存调整表明细服务
 * @author HHe
 * @date 2019/10/7 11:01
 */
@Service
@Transactional
public class SanDtlServiceImpl implements SanDtlService {
    @Autowired
    private SanDtlMapper sanDtlMapper;
    /**
     * 根据对象中的值查询集合
     * @author HHe
     * @date 2019/10/7 11:25
     */
    @Override
    public List<SanDtlVO> queryListBySanDtl(SanDtl sanDtl, SysUser sysUser) {
        return sanDtlMapper.queryListBySanDtl(sanDtl,sysUser.getDomain().getUnitId());
    }
    /**
     * 添加库存调整表明细集合
     * @author HHe
     * @date 2019/10/7 12:02
     */
    @Override
    public int addSanDtlByList(List<SanDtl> sanDtlList,Long unitId) {
        return sanDtlMapper.addSanDtlByList(sanDtlList,unitId);
    }
    /**
     * 删除明细
     * @author HHe
     * @date 2019/10/7 14:29
     */
    @Override
    public int delSanDtlBySanDtl(SanDtl sanDtl, SysUser sysUser) {
        return sanDtlMapper.delSanDtlBySanDtl(sanDtl,sysUser.getDomain().getUnitId());
    }
}
