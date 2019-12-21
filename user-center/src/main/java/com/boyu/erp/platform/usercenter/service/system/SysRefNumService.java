package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

/**
 * @program: boyu-platform
 * @description: 编号接口
 * @author: clf
 * @create: 2019-05-14 19:25
 */
public interface SysRefNumService {
    PageInfo<SysRefNum> getAll(Integer page, Integer size, SysRefNum refNum);

    int updateRefNum(SysRefNum refNum);

    int addRefNum(SysRefNum refNum);

    SysRefNum findByRef(SysRefNum refNum);

    /**
     * 主表编号
     * @param use
     * @param type
     * @return
     */
    String mainNum(SysUser use,String type);

    /**
     * 副表编号
     * @param use
     * @param type
     * @return
     */
    String viceNum(SysUser use,String type);
    /**
     * 根据编号Id查询信息
     * @author HHe
     * @date 2019/9/19 20:12
     */
    SysRefNum querySysRefNumByNumId(String refNumId);



}
