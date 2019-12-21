package com.boyu.erp.platform.usercenter.service.caservice;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.github.pagehelper.PageInfo;

/**
 * 类描述:
 *
 * @Description: 往来账户
 * @auther: wz
 * @date: 2019/9/10 11:41
 */
public interface CaService{

    /**
     * 往来账户分页查询
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<CaVo> selectAll(CaVo vo, Integer page, Integer size, SysUser sysUser) throws Exception;

    /**
     * 往来账户新增
     * @param vo
     * @param sysUser
     * @return
     */
    int insertCa(CaVo vo, SysUser sysUser);

    /**
     * 往来账户修改
     * @param vo
     * @param sysUser
     * @return
     */
    int updateCa(CaVo vo, SysUser sysUser);

    /**
     * 往来账户删除
     * @param vo
     * @param sysUser
     * @return
     */
    int deleteCa(CaVo vo, SysUser sysUser);
    /**
     * 查询往来账户
     * @author HHe
     * @date 2019/10/21 11:53
     */
    Ca queryCaByRcvAndUnit(Long fsclUnitId, Long rcvFsclUnitId,String caType);
}
