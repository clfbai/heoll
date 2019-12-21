package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.FsclPerd;
import com.boyu.erp.platform.usercenter.mapper.system.FsclPerdMapper;
import com.boyu.erp.platform.usercenter.service.system.FsclPerdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 会计期间服务
 * @author HHe
 * @date 2019/10/21 17:23
 */
@Service
public class FsclPerdServiceImpl implements FsclPerdService {
    @Autowired
    private FsclPerdMapper fsclPerdMapper;
    /**
     * 查询会计期间
     * @author HHe
     * @date 2019/10/21 17:23
     */
    @Override
    public FsclPerd queryTimeQByPerd(Long curFsclYear, Long curFsclPerd) {
        return fsclPerdMapper.queryTimeQByPerd(curFsclYear,curFsclPerd);
    }
}
