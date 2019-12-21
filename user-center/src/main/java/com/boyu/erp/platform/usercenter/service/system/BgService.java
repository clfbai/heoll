package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.BgVo;
import com.github.pagehelper.PageInfo;

/**
 * @Classname BgService
 * @Description TODO
 * @Date 2019/5/7 10:58
 * @Created by js
 */
public interface BgService {

    public int insert(Bg record);

    public Bg selectByPrimaryKey(String bgId);

    public int updateByPrimaryKeySelective(Bg record);

    public PageInfo<BgVo> selectAll(Integer page, Integer size,Bg bg);

    public int deleteBg(Bg record, SysUser user);
}
