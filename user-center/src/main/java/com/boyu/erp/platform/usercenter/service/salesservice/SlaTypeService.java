package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
import com.boyu.erp.platform.usercenter.vo.sales.SlaTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 销售申请接口
 * @author: wz
 * @create: 2019-9-4 15:36
 */
public interface SlaTypeService {

    /**
     * 购销合同类别下拉
     */
    List<PurKeyValue> optionGet();

    /**
     * 通过选择销售申请类别获取销售申请类别信息
     * @pram type
     * @return
     */
    PsxType selectByPsxType(SlaType type);

    /**
     * 分页显示销售申请类别
     * @param page
     * @param size
     * @rturn
     * @throws ServiceException
     */
    PageInfo<SlaTypeVo> selectAll(Integer page, Integer size, SlaTypeVo slaType) throws ServiceException;

    /**
     * 添加销售申请
     * @param slaType
     * @param user
     * @return
     */
    int insertSlaType(SlaType slaType, SysUser user) throws Exception;

    /**
     * 修改销售申请类别
     * @param slaType
     * @return
     * @throws Exception
     */
    public int updateSlaType(SlaType slaType, SysUser user)throws Exception;

    /**
     * 删除销售申请类别
     * @param slaType
     * @return
     * @throws ServiceException
     */
    public int deleteSlaType(SlaType slaType, SysUser user) throws ServiceException;
}
