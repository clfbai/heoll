package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.FsclPerd;
/**
 * 会计期间接口
 * @author HHe
 * @date 2019/10/21 17:20
 */
public interface FsclPerdService {
    /**
     * 查询期间
     * @author HHe
     * @date 2019/10/21 17:21
     */
    FsclPerd queryTimeQByPerd(Long curFsclYear, Long curFsclPerd);
}
