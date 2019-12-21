package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.basic.Vendee;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitClsf;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.sales.VendeeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Classname VendeeService
 * @Description TODO
 * @Date 2019/7/1 11:11
 * @Created wz
 */
public interface VendeeService {

    /**
     * 添加采应商（供应商通过参数自动创建采购商）
     * @param vo
     * @return
     * @throws ServiceException
     */
    public void insertVender(VenderVo vo, SysUser sysUser) throws ServiceException;

    /**
     * 分页显示采购商
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<VendeeVo> selectAll(Integer page, Integer size, VendeeVo vo, SysUser sysUser) throws Exception;

    /**
     * 添加采购商
     * @param vo
     * @return
     * @throws ServiceException
     */
    int insertVendee(VendeeVo vo, SysUser user) throws Exception;

    /**
     * 修改采购商
     * @param vo
     * @param user
     * @return
     */
    int updateVendee(VendeeVo vo, SysUser user);

    /**
     * 删除采购商
     * @param vo
     * @return
     * @throws ServiceException
     */
    int deleteVendee(VendeeVo vo, SysUser user);

    /**
     * 选择或者输入采购商代码后查询数据
     * @param vo
     * @return
     */
    VendeeVo selectByCode(VendeeVo vo);

    /**
     * 冻结采购商
     * @param vo
     * @return
     */
    int freeze(VendeeVo vo);

    /**
     * 解冻采购商
     * @param vo
     * @return
     */
    int defreeze(VendeeVo vo);

    /**
     * 采购商可操作状态
     * @param sys
     * @return
     */
    List<OperationVo> initButtons(SysUnitClsf sys);
    /**
     * 查询vendee是否是属主的采购商
     * @author HHe
     * @date 2019/10/19 11:29
     */
    Vendee queryVendeeByIdAndOwner(Vendee pVendee);

    /**
     * 采购商购货信息中分页显示采购商
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<VdrSupplyVo> selectAll(Integer page, Integer size, VdrSupplyVo vo, SysUser sysUser) throws Exception;
}
