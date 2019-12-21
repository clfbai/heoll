package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.SanDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.SanDtlVO;

import java.util.List;

/**
 * 库存调整表明细接口
 * @author HHe
 * @date 2019/10/7 11:00
 */
public interface SanDtlService {
    /**
     * 根据对象中的值查询集合
     * @author HHe
     * @date 2019/10/7 11:25
     */
    List<SanDtlVO> queryListBySanDtl(SanDtl sanDtl, SysUser sysUser);
    /**
     * 添加库存调整表明细集合
     * @author HHe
     * @date 2019/10/7 12:02
     */
    int addSanDtlByList(List<SanDtl> sanDtlList,Long unitId);
    /**
     * 删除明细
     * @author HHe
     * @date 2019/10/7 14:28
     */
    int delSanDtlBySanDtl(SanDtl sanDtl, SysUser sysUser);
}
