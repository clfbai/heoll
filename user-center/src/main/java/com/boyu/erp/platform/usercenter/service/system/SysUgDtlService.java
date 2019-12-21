package com.boyu.erp.platform.usercenter.service.system;

import java.util.List;

/**
 * 组织分组明细接口
 * @author HHe
 * @date 2019/11/26 11:16
 */
public interface SysUgDtlService {
    /**
     * 根据组织分组Id集合查询成员集合
     * @author HHe
     * @date 2019/11/26 11:18
     */
    List<Long> queryMkrIdsByUgIds(List<Long> unitUgIds);
}
