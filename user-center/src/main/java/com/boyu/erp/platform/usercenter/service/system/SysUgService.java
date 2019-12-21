package com.boyu.erp.platform.usercenter.service.system;

import java.util.List;
import java.util.Map;
/**
 * 组织分组服务
 * @author HHe
 * @date 2019/9/5 9:57
 */
public interface SysUgService {
    /**
     * 组织分组下拉Map
     * @author HHe
     * @date 2019/9/5 9:57
     */
    List<Map<String, String>> getSysUgMapByTypeOwner(String wd, Long unitId);

}
