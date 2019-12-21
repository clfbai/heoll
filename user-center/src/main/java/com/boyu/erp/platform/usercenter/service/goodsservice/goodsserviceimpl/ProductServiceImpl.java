package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.model.goods.ProductModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumDtlSerivce;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.common.BaseScopeUtils;
import com.boyu.erp.platform.usercenter.utils.refttable.ReftClass;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsGoodsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdStkAdopte;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductWindowVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform
 * @description: 商品业务层
 * @author: clf
 * @create: 2019-06-15 11:40
 */
@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ReftClass reftClass;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SysRefNumDtlSerivce refNumDtlSerivce;
    @Autowired
    private UsersService usersService;
    @Autowired
    private BaseScopeUtils baseScopeUtils;

    @Override
    public PageInfo<ProdClsGoodsVo> getProdcutAll(Integer page, Integer size, ProductModel product, SysUser sessionSysUser) throws Exception {
        PageInfo<ProdClsGoodsVo> pageInfo;
        if (usersService.getIsAdmin(sessionSysUser)) {
            PageHelper.startPage(page, size);
            List<ProdClsGoodsVo> resultList = productMapper.selectAdminAll(product);
            pageInfo = new PageInfo<>(resultList);
            pageInfo.setList(setList(resultList));
            return pageInfo;
        }
        if (product.getUnitId() == null) {
            //默认查询当前组织
            product.setUnitId(sessionSysUser.getOwnerId());
        }
        PageHelper.startPage(page, size);
        List<ProdClsGoodsVo> resultList = productMapper.selectAll(product);
        pageInfo = new PageInfo<>(resultList);
        pageInfo.setList(setList(resultList));
        return pageInfo;
    }

    /**
     * 功能描述:  验证数据准确性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/16 16:06
     */
    @Override
    public Map<String, Object> verificationFile(ProductVo product) {
        Map<String, Object> map = new HashMap<>();
        map.put("bo", false);
        String m = "";

        if (StringUtils.isBlank(product.getColorCode())) {
            map.put("m", "颜色代码不能为空");
            return map;
        }
        if (StringUtils.isBlank(product.getColorName())) {
            map.put("m", "颜色名称不能为空");
            return map;
        }
        if (StringUtils.isBlank(product.getSpecGrpId())) {
            map.put("m", "规格号不能为空");
            return map;
        }
        if (StringUtils.isBlank(product.getSpecNum())) {
            map.put("m", "规格不能为空");
            return map;
        }
        if (StringUtils.isBlank(product.getInnerBc())) {
            map.put("m", "店内码能为空");
            return map;
        }
        if (StringUtils.isBlank(product.getIntlBc())) {
            map.put("m", "国际码已不能能为空");
            return map;
        }
        List<Product> list = productMapper.getProdcutClsCode();
        if (getLambda(product.getInnerBc(), list.stream().map(Product::getInnerBc).collect(Collectors.toList()))) {
            map.put("m", "店内码已存在");
            return map;
        }
        if (list.stream().map(Product::getIntlBc).collect(Collectors.toList()).stream().anyMatch(s -> s.equals(product.getIntlBc()))) {
            map.put("m", "国际码已存在");
            return map;
        }
        map.put("bo", true);
        return map;
    }

    /**
     * 根据出库任务中任务单据类别的明细商品id，查询对应的信息封装入StbDtl
     *
     * @author HHe
     */
    @Override
    public List<StbDtl> queryProdMess2StbDtl(List<String> dtlNum, Long warehId) {
        return productMapper.queryProdMess2StbDtl(dtlNum, warehId);
    }

    /**
     * 根据商品代码查询出库单页面商品相关信息
     *
     * @author HHe
     * @date 2019/8/27 11:53
     */
    @Override
    public Product queryProVoByProCode(String proCode) {
        return productMapper.queryProVoByProCode(proCode);

    }

    /**
     * 根据商品Id查询商品信息
     *
     * @author HHe
     * @date 2019/9/5 18:00
     */
    @Override
    public Product queryProductById(Long prodId) {
        return productMapper.queryProductById(prodId);
    }

    /**
     * 根据商品id集合查询商品信息集合
     *
     * @author HHe
     * @date 2019/9/5 19:44
     */
    @Override
    public List<Product> queryProductListByProdList(List<Long> prodIds) {
        return productMapper.queryProductListByProdList(prodIds);
    }

    /**
     * 根据商品品种Id集合查询商品Id集合
     *
     * @author HHe
     * @date 2019/9/19 11:43
     */
    @Override
    public List<Long> queryProdIdByProdClsIdList(List<Long> prodClsIdList) {
        return productMapper.queryProdIdByProdClsIdList(prodClsIdList);
    }

    /**
     * 查询商品信息集合
     *
     * @param prodIds 商品Id集合
     * @return 对应Id的信息
     * @author HHe
     * @date 2019/11/7 16:08
     */
    @Override
    public List<Product> queryProductsByProdIds(List<Long> prodIds) {
        return productMapper.queryProductsByProdIds(prodIds);
    }

    /**
     * 功能描述: 查询启用库存管理的商品
     *
     * @param prodIds (商品Id集合)
     * @param type    (T 启用库存管理 F 禁用库存管理)
     * @return listgoods  商品Id集合
     * @auther: CLF
     * @date: 2019/11/12 19:46
     */
    @Override
    public List<Long> selectStkAdopte(List<Long> prodIds, String type) {
        List<ProdStkAdopte> list = productMapper.selectProductStkAdopte(prodIds, type);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().map(a -> a.getProdId()).collect(Collectors.toList());
        }
        return null;
    }
    /**
     * 根据代码查询Id
     * @param prodCodes 商品代码集合
     * @return 商品集合
     * @author HHe
     * @date 2019/12/5 11:45
     */
    @Override
    public List<Product> queryProductListByCodes(Set<String> prodCodes) {
        return productMapper.queryProductListByCodes(prodCodes);
    }


    /**
     * 功能描述: 商品弹窗查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/19 17:23
     */
    @Override
    public PageInfo<ProductWindowVo> getProductWindow(Integer page, Integer size, ProductWindowVo product, SysUser user) {
        product.setUnitId(user.getOwnerId());
        if (usersService.getIsAdmin(user)) {
            product.setIsAdmin("");
        }
        PageHelper.startPage(page, size);
        List<ProductWindowVo> resultList = productMapper.getProductWindow(product);
        PageInfo<ProductWindowVo> pageInfo = new PageInfo<>(resultList);
        return pageInfo;
    }

    private List<ProdClsGoodsVo> setList(List<ProdClsGoodsVo> resultList) throws Exception {
        List<ProdClsGoodsVo> list = new ArrayList<>();
        for (ProdClsGoodsVo vo : resultList) {
            reftClass.setObject(vo);
            list.add(vo);
        }
        return list;
    }


    /**
     * 通过商品品种Id查询商品明细
     *
     * @param product
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductVo> getProductDetail(Product product) {
        return productMapper.selectProdClsId(product);
    }

    /**
     * 商品品种下修改商品明细
     *
     * @param product
     * @return
     */
    @Override
    public int udapteProdClsUpdateProduct(Product product) throws ServiceException {
        /**
         * UNIQUE KEY `idx_product_1` (`prod_code`),
         *   UNIQUE KEY `idx_product_2` (`inner_bc`),
         *   UNIQUE KEY `idx_product_3` (`intl_bc`),
         *   UNIQUE KEY `idx_product_4` (`prod_cls_id`,`color_id`,`edition`,`spec_id`)
         * */
        verificationKey(product);
        return productMapper.updateByProduct(product);
    }

    /**
     * 增加商品
     * 1. 增加商品
     * 2. 修改编号表
     *
     * @param product
     * @return a
     */
    @Override
    public int instrProduct(Product product, SysUser user) {
        Long max = refNumDtlSerivce.creatId("prod_id");
        product.setProdId(max);
        verificationKey(product);
        product.setIsDel(CommonFainl.BTYPESTATUS);
        product.setCreateBy(user.getUserId());
        product.setCreateTime(new Date());
        if (product.getInnerBc() == null && product.getIntlBc() == null) {
            product.setIntlBc("@" + max);
            product.setInnerBc("@" + max);
        }
        product.setDeleted(CommonFainl.FALSE);
        //设置Sku状态
        product.setSkuStatus(CommonFainl.USER_STATUS);
        int a = productMapper.insertProduct(product);
        /**
         * 2. 修改编号表
         * */
        SysRefNumDtl sysRefNum = new SysRefNumDtl();
        sysRefNum.setRefNumId("prod_id");
        sysRefNum.setLastNum(max);
        refNumDtlSerivce.updateRefNumDtl(sysRefNum);
        return a;
    }

    /**
     * 查询商品Code是否存在
     */
    @Override
    @Transactional(readOnly = true)
    public Product findByCode(Product product) {
        return productMapper.findByProdcutClsCode(product);
    }

    /**
     * 功能描述:
     * 根据商品Code查询是否存在商品集合
     * (生成商品代码使用)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/16 11:20
     */
    @Override
    public List<Product> getProduct(Product product) {
        return productMapper.getProdcutCode(product);
    }

    /**
     * @param product
     * @description: 物理删除删除商品
     * @author: CLF
     * 1. 删除商品表
     * 2. 查看商品Id是否最大，如果最大修改编号表
     */
    @Override
    public int deleteProduct(Product product, SysUser user) {
        SysRefNumDtl dtl = new SysRefNumDtl();
        //主键
        dtl.setRefNumId("prod_id");
        dtl = refNumDtlSerivce.getLastStep(dtl);
        /**
         * 2  查看商品Id是否最大，如果最大修改编号表
         * */
        if (product.getProdId().equals(dtl.getLastNum())) {
            dtl.setLastNum(dtl.getLastNum() - dtl.getStepSize());
            dtl.setRefNumId("prod_id");
            dtl.setUpdateBy(user.getUserId());
            dtl.setUpdateTime(new Date());
            refNumDtlSerivce.updateRefNumDtl(dtl);
        }
        return productMapper.deleteByProduct(product.getProdId());
    }

    /**
     * 功能描述: 打标删除
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 9:28
     */
    @Override
    public int updateDeleteProduct(Product product, SysUser user) {

        return productMapper.updateByProduct(product);
    }


    /**
     * 验证是否会主键、外键冲突
     */
    public void verificationKey(Product product) {
        List<Product> list = productMapper.getProdcutClsCode();
        if (StringUtils.isNotBlank(product.getInnerBc())
                && getLambda(product.getInnerBc(), list.stream().map(Product::getInnerBc).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "店内码已存在");
        }
        if (StringUtils.isNotBlank(product.getIntlBc())
                && list.stream().map(Product::getIntlBc).collect(Collectors.toList()).stream().anyMatch(s -> s.equals(product.getIntlBc()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "国际码已存在");
        }


    }

    /**
     * Lambda 过滤集合判断 字符是否存在  需要不停进行上下文切换 适合并发
     */
    public boolean getLambda(String s, List<Object> list) {
        boolean b = list.stream().anyMatch(p -> p.equals(s));
        return b;
    }

    /**
     * 普通过滤 效率暂时没有区别   单管道时更快
     */
    public boolean getFore(Product product, List<Product> list) {
        boolean b = false;
        for (Product p : list) {
            if (product.getInnerBc().equals(p.getInnerBc())) {
                b = true;
            }
        }
        return b;
    }

}
