package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCat;
import com.boyu.erp.platform.usercenter.exception.ServiceException;

import java.util.List;

public interface ProdCatService {

    /**
     * 通过上级id找下级元素
     *
     * @param prodCat
     * @return
     */
    public List<ProdCat> selectByParentId(ProdCat prodCat) throws ServiceException;

    /**
     * 功能描述: 查询所有
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/17 20:24
     */
    List<ProdCat> selectTree(ProdCat cat) throws ServiceException;


    /**
     * 根据主键获取商品分类对象
     *
     * @param prodCat
     * @return
     * @throws ServiceException
     */
    public ProdCat getProdCatById(ProdCat prodCat) throws ServiceException;

    /**
     * 增加分类
     *
     * @param prodCat
     * @return
     * @throws ServiceException
     */
    public int insertProdCat(ProdCat prodCat) throws ServiceException;

    /**
     * 修改分类
     *
     * @param prodCat
     * @return
     * @throws ServiceException
     */
    public int updateProdCat(ProdCat prodCat) throws ServiceException;

    /**
     * 删除分类
     *
     * @param prodCat
     * @return
     * @throws ServiceException
     */
    public int deleteProdCat(ProdCat prodCat) throws ServiceException;

    /**
     * 查询商品分类下拉树形结构
     */
    List<ProdCat> getOpetion(ProdCat prodCat);
}