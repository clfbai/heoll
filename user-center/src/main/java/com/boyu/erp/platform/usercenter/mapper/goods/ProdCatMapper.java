package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCat;

import java.util.List;

public interface ProdCatMapper {

    /**
     * 通过上级id找下级元素
     *
     * @param prodCat
     * @return
     */
    public List<ProdCat> selectByParentId(ProdCat prodCat);

    public ProdCat selectByPrimaryKey(ProdCat prodCat);

    public int insertProdCat(ProdCat prodCat);

    public int updateProdCat(ProdCat prodCat);

    public int deleteProdCat(ProdCat prodCat);

    /**
     * 通过id找下级元素
     *
     * @param prodCat
     * @return
     */
    List<ProdCat> getOption(ProdCat prodCat);

    /**
     * 通过id查找当前对象
     *
     * @param prodCat
     * @return
     */
    ProdCat findByProdCat(ProdCat prodCat);

    List<ProdCat> selectAll();
}