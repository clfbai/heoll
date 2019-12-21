package com.boyu.erp.platform.usercenter.service.warehouse;

import java.util.List;
import java.util.Map;
/**
 * 仓库分组服务
 * @author HHe
 * @date 2019/9/5 10:46
 */
public interface WarehUgService {
    /**
     * 根据组织分组多选加载仓库分组
     * @author HHe
     * @date 2019/9/5 10:46
     */
    Map<String, Object> loadWarehUg(List<Long> sysUgIds);
    /**
     * 根据组织分组Id查询仓库分组Id集合
     * @author HHe
     * @date 2019/10/13 11:30
     */
    List<Long> queryWarehUgIdsBySysUgId(List<Long> unitUgIds);
}
