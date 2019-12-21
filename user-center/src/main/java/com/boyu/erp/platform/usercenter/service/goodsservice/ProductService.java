package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProductModel;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsGoodsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductWindowVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: boyu-platform
 * @description: 商品接口
 * @author: clf
 * @create: 2019-06-15 11:36
 */
public interface ProductService {
    /**
     * 功能描述: 通过商品品种Id 查询商品信息集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 17:16
     */
    List<ProductVo> getProductDetail(Product product);

    int udapteProdClsUpdateProduct(Product product) throws ServiceException;

    int instrProduct(Product product, SysUser user);

    Product findByCode(Product product);

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
    List<Product> getProduct(Product product);

    /**
     * 功能描述: 物理删除
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 9:26
     */
    int deleteProduct(Product product, SysUser user);

    /**
     * 功能描述: 打标删除
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 9:27
     */
    int updateDeleteProduct(Product product, SysUser user);

    PageInfo<ProdClsGoodsVo> getProdcutAll(Integer page, Integer size, ProductModel product, SysUser sessionSysUser) throws Exception;

    /**
     * 功能描述:  验证数据准确性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/16 16:06
     */
    Map<String, Object> verificationFile(ProductVo product);

    /**
     * 根据出库任务中任务单据类别的明细商品id，查询对应的信息封装入StbDtlVo
     *
     * @author HHe
     */
    List<StbDtl> queryProdMess2StbDtl(List<String> dtlNum, Long warehId);

    /**
     * 根据商品代码查询出库单页面商品相关信息
     *
     * @author HHe
     * @date 2019/8/27 11:53
     */
    Product queryProVoByProCode(String proCode);

    /**
     * 根据商品Id查询商品信息
     *
     * @author HHe
     * @date 2019/9/5 18:00
     */
    Product queryProductById(Long prodId);

    /**
     * 根据商品id集合查询商品信息集合
     *
     * @author HHe
     * @date 2019/9/5 19:44
     */
    List<Product> queryProductListByProdList(List<Long> prodIds);

    /**
     * 功能描述: 商品弹窗查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/19 17:23
     */
    PageInfo<ProductWindowVo> getProductWindow(Integer page, Integer size, ProductWindowVo product, SysUser user);

    /**
     * 根据商品品种Id集合查询商品Id集合
     *
     * @author HHe
     * @date 2019/9/19 11:40
     */
    List<Long> queryProdIdByProdClsIdList(List<Long> prodClsIdList);

    /**
     * 查询商品信息集合
     * @param prodIds 商品Id集合
     * @return 对应Id的信息
     * @author HHe
     * @date 2019/11/7 16:08
     */
    List<Product> queryProductsByProdIds(List<Long> prodIds);


    /**
     *
     * 功能描述: 查询启用库存管理的商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 19:46
     */
    List<Long> selectStkAdopte(List<Long> prodIds,String type);
    /**
     * 根据代码查询Id
     * @param prodCodes 商品代码集合
     * @return 商品集合
     * @author HHe
     * @date 2019/12/5 11:45
     */
    List<Product> queryProductListByCodes(Set<String> prodCodes);
}
