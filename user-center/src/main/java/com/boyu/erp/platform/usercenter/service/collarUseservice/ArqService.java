package com.boyu.erp.platform.usercenter.service.collarUseservice;

import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProductModel;
import com.boyu.erp.platform.usercenter.vo.collarUse.ArqVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsGoodsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: ArqService
 * @description: 领用单
 * @author: wz
 * @create: 2019-8-23 12:19
 */
public interface ArqService {

    PageInfo<ArqVo> selectAll(ArqVo vo, Integer page, Integer size, SysUser sysUser);

    int insertArq(ArqVo vo,SysUser user);

    int updateArq(ArqVo vo,SysUser user);

    int deleteArq(ArqVo vo);

}
