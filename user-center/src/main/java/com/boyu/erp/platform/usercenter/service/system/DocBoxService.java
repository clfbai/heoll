package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.basic.DocBox;
import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.BgVo;
import com.github.pagehelper.PageInfo;

/**
 * @Classname DocBoxService
 * @Description TODO
 * @Date 2019/9/16 15:25
 * @Created by wz
 */
public interface DocBoxService {


    /**
     * 重启单据和实体单关联
     * @param box
     * @return
     */
    int reattach(DocBox box);
}
