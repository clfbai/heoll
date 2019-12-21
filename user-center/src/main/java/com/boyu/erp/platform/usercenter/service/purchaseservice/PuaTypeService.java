package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PsiType;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuiType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PuiTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * @Classname PsiTypeService
 * @Description TODO
 * @Date 2019/6/18 19:20
 * @Created wz
 */
public interface PuaTypeService {


    /**
     * 分页显示采购申请类别
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    PageInfo<PuaTypeVo> selectAll(Integer page, Integer size, PuaTypeVo vo) throws ServiceException;

    /**
     * 增加采购申请类别
     * @param vo
     * @return
     * @throws ServiceException
     */
    public int insertPuaType(PuaTypeVo vo, SysUser user) throws Exception;

    /**
     * 修改采购申请类别
     * @param vo,
     * @return
     * @throws ServiceException
     */
    public int updatePuaType(PuaTypeVo vo, SysUser user)throws Exception;

    /**
     * 删除采购申请类别
     * @param vo
     * @return
     * @throws ServiceException
     */
    public int deletePuaType(PuaTypeVo vo, SysUser user) throws ServiceException;

    /**
     * 购销合同类别下拉
     */
    List<PurKeyValue> optionGet();

    /**
     * 通过选择采购申请类别获取销售申请类别信息
     * @param type
     * @return
     */
    PsxType selectByPsxType(PuaType type);

}
