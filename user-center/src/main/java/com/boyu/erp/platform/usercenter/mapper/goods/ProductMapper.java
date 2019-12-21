package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.mq.product.ProductSku;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.model.goods.ProductModel;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsGoodsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdStkAdopte;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductWindowVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByProduct(Long prodId);

    /**
     * 删除商品品种下商品
     */
    int deleteProductCls(Product prodId);

    int insertProduct(Product record);

    Product selectByProduct(Long prodId);


    int updateByProduct(Product record);

    /**
     * 查询商品详情(通过商品品种Id)
     */
    List<ProductVo> selectProdClsId(Product product);

    /**
     * 查询商品品种代码(通过商品品种Id)
     */
    ProdCls seletProdcut(ProductVo prodClsId);

    /**
     * 查询所有(某一商品品种代码)下商品
     */
    List<Product> getProdcutClsCode();

    /**
     * 查询商品Code是否存在
     */
    Product findByProdcutClsCode(Product product);

    /**
     * 根据商品Code查询是否存在商品集合
     * (生成商品代码使用)
     */
    List<Product> getProdcutCode(Product product);

    /**
     * 组织管理员和普通用户查看
     */
    List<ProdClsGoodsVo> selectAll(ProductModel product);

    /**
     * 系统管理员查看
     */
    List<ProdClsGoodsVo> selectAdminAll(ProductModel product);

    /**
     * 根据出库任务中任务单据类别的明细商品id，查询对应的信息封装入StbDtlVo
     *
     * @author HHe
     */
    List<StbDtl> queryProdMess2StbDtl(@Param("dtlNum") List<String> dtlNum, @Param("warehId") Long warehId);

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
     * 功能描述:  商品弹窗通用
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/20 11:54
     */
    List<ProductWindowVo> getProductWindow(ProductWindowVo product);

    /**
     * 根据商品品种Id集合查询商品Id集合
     *
     * @author HHe
     * @date 2019/9/19 11:44
     */
    List<Long> queryProdIdByProdClsIdList(@Param("prodClsIdList") List<Long> prodClsIdList);

    /**
     * 功能描述: 根据商品品种Id查询商品明细消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 19:15
     */
    List<ProductSku> selectProdClsIdMessge(ProdClsModel model);

    /**
     * 功能描述: 查询商品品种下是否存在活动商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 15:47
     */
    List<Product> selectAuditType(Long prodClsId);

    /**
     * 根据商品Id集合查询商品Id信息
     *
     * @author HHe
     * @date 2019/11/7 16:09
     */
    List<Product> queryProductsByProdIds(@Param("prodIds") List<Long> prodIds);


    /**
     * 根据商品Id集合查询商品库存管理信息
     *
     * @author HHe
     * @date 2019/11/7 16:09
     */
    List<ProdStkAdopte> selectProductStkAdopte(@Param("list") List<Long> list, @Param("stkAdopted") String stkAdopted);

    /**
     * 根据代码查询Id
     *
     * @param prodCodes 商品代码集合
     * @return 商品集合
     * @author HHe
     * @date 2019/12/5 11:45
     */
    List<Product> queryProductListByCodes(@Param("prodCodes") Set<String> prodCodes);

    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 18:04
     */
    List<Product> selectProdClsIdList(@Param("list") List<Long> prodClsId);
}