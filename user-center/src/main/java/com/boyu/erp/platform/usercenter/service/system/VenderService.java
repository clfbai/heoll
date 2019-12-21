package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.sales.VendeeVo;
import com.github.pagehelper.PageInfo;

import java.util.*;

/**
 * @Classname VenderService
 * @Description TODO
 * @Date 2019/6/26 16:21
 * @Created wz
 */
public interface VenderService {

    /**
     * 分页显示供应商
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<VenderVo> selectAll(Integer page, Integer size, VenderVo vo, SysUser sysUser) throws Exception;

    /**
     * 添加供应商
     * @param vo
     * @return
     * @throws ServiceException
     */
    public int insertVender(VenderVo vo, SysUser user) throws Exception;

    /**
     * 修改供应商
     * @param vo
     * @return
     * @throws ServiceException
     */
    public int updateVender(VenderVo vo, SysUser user) throws ServiceException;

    /**
     * 删除供应商
     * @param vo
     * @return
     * @throws ServiceException
     */
    public int deleteVender(VenderVo vo, SysUser user) throws ServiceException;

    /**
     * 选择或者输入供应商代码后查询数据
     * @param vo
     * @return
     */
    VenderVo selectByCode(VenderVo vo);

    /**
     * 供应商供货信息中分页显示供应商
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<VdrSupplyVo> selectAll(Integer page, Integer size, VdrSupplyVo vo, SysUser sysUser) throws Exception;

    /**
     * 采购商中创建供应商
     */
    void insertVendee(VendeeVo vo, SysUser sysUser);
}
