package com.boyu.erp.platform.usercenter.service.purchaseservice;


import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.OptionByPsaVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Classname PsaService
 * @Description TODO
 * @Date 2019/6/21 10:47
 * @Created wz
 */

public interface PsaService {

    /**
     * 分页显示采购协议
     *
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<PsaVo> selectVdeAll(PsaVo psa, Integer page, Integer size, SysUser sysUser) throws ServiceException;

    /**
     * 分页查询销售协议
     *
     * @param psa
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws ServiceException
     */
    PageInfo<PsaVo> selectVdrAll(PsaVo psa, Integer page, Integer size, SysUser sysUser) throws ServiceException;

    /**
     * 添加采购协议
     *
     * @param psaVo
     * @return
     * @throws ServiceException
     */
    int insertPsa(PsaVo psaVo, SysUser user) throws ServiceException;

    /**
     * 删除采购协议
     *
     * @param psaVo
     * @return
     * @throws ServiceException
     */
    int deletePsa(PsaVo psaVo) throws ServiceException;

    /**
     * 修改采购协议
     *
     * @param psaVo
     * @return
     * @throws ServiceException
     */
    int updatePsa(PsaVo psaVo, SysUser user) throws ServiceException;

    /**
     * 采购价格单中的查询判断
     *
     * @param venderId
     * @param user
     * @return
     */
    int judge(String venderId, String vendeeId, SysUser user);

    /**
     * 采购中 选择供应商后去判断是否存在协议  存在 查询出协议控制方
     *
     * @param vo
     * @return
     */
    OptionByPsaVo selectByPsaByVde(OptionByPsaVo vo);

    /**
     * 销售中 选择采购商后去判断是否存在协议  存在 查询出协议控制方
     *
     * @param vo
     * @return
     */
    OptionByPsaVo selectByPsaByVdr(OptionByPsaVo vo);

}
