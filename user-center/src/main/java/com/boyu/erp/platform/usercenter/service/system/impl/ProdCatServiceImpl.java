package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCat;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdCatMapper;
import com.boyu.erp.platform.usercenter.service.system.ProdCatService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品分类服务实现
 */

@Service
@Transactional
public class ProdCatServiceImpl implements ProdCatService {

    @Autowired
    private ProdCatMapper prodCatMapper;

    /**
     * 上级id找子节点
     *
     * @param prodCat
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProdCat> selectByParentId(ProdCat prodCat) throws ServiceException {
        List<ProdCat> prodCats = prodCatMapper.selectByParentId(prodCat);
        return prodCats;
    }

    @Override
    public List<ProdCat> selectTree(ProdCat cat) throws ServiceException {
        List<ProdCat> prodCats = prodCatMapper.selectAll();
        return caterTreeCat(prodCats, cat);
    }

    private List<ProdCat> caterTreeCat(List<ProdCat> prodCats, ProdCat prodCat) {
        List<ProdCat> lv = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(prodCats)) {
            List<ProdCat> lvs = new ArrayList<>();
            if (prodCat == null || StringUtils.isBlank(prodCat.getProdCatId())) {
                lvs = prodCats.stream().filter(s -> "1".equals(String.valueOf(s.getProdCatLvl()))).collect(Collectors.toList());
            }
            if (prodCat != null && StringUtils.isNotBlank(prodCat.getProdCatId())) {
                lvs = prodCats.stream().filter(s -> s.getProdCatId().equals(prodCat.getProdCatId())).collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(lvs)) {
                for (ProdCat cat : lvs) {
                    /**
                     *步骤1.  递归给集合赋值
                     * */
                    cat.setProdCatList(getProdCatNodes(cat, prodCats));
                    lv.add(cat);
                }
            }
        }
        return lv;
    }

    private List<ProdCat> getProdCatNodes(ProdCat prodCat, List<ProdCat> prodCats) {
        List<ProdCat> chikCats = new ArrayList<>();
        for (ProdCat cat : prodCats) {
            if (prodCat.getProdCatId().equalsIgnoreCase(cat.getParnCatId())) {
                chikCats.add(cat);
                prodCat.setLast(false);
            }
        }
        if (chikCats.size() == 0) {
            prodCat.setLast(true);
            return new ArrayList<>();
        }
        for (ProdCat cat : chikCats) {
            /**
             *重复步骤1.
             * */
            cat.setProdCatList(getProdCatNodes(cat, prodCats));
        }
        return chikCats;
    }

    /**
     * 主键
     *
     * @param prodCat
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProdCat getProdCatById(ProdCat prodCat) {
        return prodCatMapper.selectByPrimaryKey(prodCat);
    }

    /**
     * 增加商品分类
     *
     * @param prodCat
     * @return
     * @throws ServiceException
     */
    @Override
    public int insertProdCat(ProdCat prodCat) throws ServiceException {
        ProdCat cat = this.prodCatMapper.selectByPrimaryKey(prodCat);

        if (cat != null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品分类编号已经存在,请重填");
        }
        //特殊情况处理前端树插件需要0的情况.
        if (prodCat.getParnCatId() != null && prodCat.getParnCatId().equalsIgnoreCase("0")) {
            prodCat.setParnCatId(null);
        }

        return prodCatMapper.insertProdCat(prodCat);
    }

    /**
     * 修改商品分类
     *
     * @param prodCat
     * @return
     */
    @Override
    public int updateProdCat(ProdCat prodCat) throws ServiceException {
        return prodCatMapper.updateProdCat(prodCat);
    }

    /**
     * 删除商品分类
     * 1.先判断商品品种中是否存在商品的分类
     * 2.如果存在则不能删除分类。
     * 3.如果不存在,则可以物理删除商品的分类
     *
     * @param prodCat
     * @return
     */
    @Override
    public int deleteProdCat(ProdCat prodCat) throws ServiceException {
        //TODO 商品品种的业务判断
        return prodCatMapper.deleteProdCat(prodCat);
    }

    @Override
    public List<ProdCat> getOpetion(ProdCat prodCat) {
        if (StringUtils.isNotBlank(prodCat.getProdCatId())) {
            prodCat.setParnCatId(prodCat.getProdCatId());
        }
        return prodCatMapper.getOption(prodCat);
    }


}