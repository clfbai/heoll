package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.goods.ProdAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProdAttrDefModel;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProdAttrDefService {

    /**
     * 查询全部
     */
    PageInfo<ProdAttrDef> selectAll(Integer page, Integer size, ProdAttrDef prodAttrDef) throws ServiceException;

    /**
     * 主键查询
     */
    ProdAttrDef selectByPrimaryKey(ProdAttrDef prodAttrDef) throws ServiceException;

    /**
     * 添加
     */
    int insertProdAttrDef(ProdAttrDefModel prodAttrDef, SysUser user) throws Exception;

    /**
     * 修改
     */
    int updateProdAttrDef(ProdAttrDefModel prodAttrDefModel, SysUser user) throws Exception;


    /**
     * 删除
     */
    int deleteProdAttrDef(ProdAttrDefModel prodAttrDef,SysUser user) throws Exception;

    /**
     * 查询所有自定义属性 属性类别对应属性名称
     */
    List<ProdAttrDefVo> getAttrAndName();
}