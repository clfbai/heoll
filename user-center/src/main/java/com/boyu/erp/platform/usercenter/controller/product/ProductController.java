package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.FieldAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProductModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.ColorService;
import com.boyu.erp.platform.usercenter.service.system.SpecService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProductCodeUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsGoodsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductWindowVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 商品控制层
 * @author: clf
 * @create: 2019-06-15 11:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/product/goods")
public class ProductController extends BaseController {
    private static final String psn = "商品";
    private static final String table = "product|";
    private static final String Operations = "ProductController|";

    @Autowired
    private ProductService productService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SpecService specService;
    @Autowired
    private ProductCodeUtils productCodeUtils;
    @Autowired
    private OperationAuthorityUtils operationAuthorityUtils;

    /**
     * 查询商品列表
     *
     * @param product
     * @return
     */
    @FieldAuthority(name = "Prodcut")
    @RequestMapping(value = "/prodcut/all", method = {RequestMethod.POST, RequestMethod.GET})
    public Object prodcutAll(ProductModel product,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "15") Integer size,
                             HttpServletRequest request) {
        try {
            /**
             *必填查询字段(任选其一 )
             * prodCode
             * prodCatId
             * prodName
             * brandId
             * season
             */
            List<Object> list = new ArrayList<>();
            list.add(product.getProdCode());
            list.add(product.getProdCatId());
            list.add(product.getProdName());
            list.add(product.getSeason());
            list.add(product.getBrandId());
            if (StringUtils.isParamNotEmpty(list)) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.PARAMRESTU, "");
            }
            if (StringUtils.NullEmpty(product.getBrandId())) {
                product.setBrandId(null);
            }
            PageInfo<ProdClsGoodsVo> pageInfo = productService.getProdcutAll(page, size, product, this.getSessionSysUser(request));


            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[ProductService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductController][prodcutList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }


    /**
     * 通过商品品种Id查询商品明细
     *
     * @param product
     * @return
     */
    @RequestMapping(value = "/ProdClsId/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult goodsList(@RequestBody Product product) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", RestulMap.getResut(productService.getProductDetail(product)));
        } catch (ServiceException ex) {
            log.error("[ProductService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductController][goodsList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS, psn + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 增加商品
     *
     * @param vo
     * @return
     */

    @SystemLog(name = "增加商品")
    @RequestMapping(value = "/Product/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult productAdd(@RequestBody ProductVo vo, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (vo.getAddList().size() <= 0) {
                PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.ADD, vo.getUnitId(), user);
                if (!examine.getPrivBoolean()) {
                    return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                }
                Product product = new Product();
                BeanUtils.copyProperties(vo, product);
                String code = productCodeUtils.creatProductCode(vo, user);
                Color color = new Color();
                BeanUtils.copyProperties(vo, color);
                color = colorService.getColor(color);
                product.setColorId(color.getColorId());
                Spec spec = new Spec();
                spec.setSpecGrpId(vo.getSpecGrpId());
                spec.setSpecCode(vo.getSpecCode());
                log.info("{规格： }"+specService.getSpecById(spec));
                product.setSpecId(specService.getSpecById(spec).getSpecId());
                product.setProdCode(code);
                int a = productService.instrProduct(product, user);
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, a);
            } else {
                for (ProductVo vs : vo.getAddList()) {
                    //权限判断
                    PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.ADD, vo.getUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                    }
                    //不为空项
                    if (!((boolean) productService.verificationFile(vs).get("bo"))) {
                        return new JsonResult(JsonResultCode.FAILURE, (String) productService.verificationFile(vs).get("m"), vs);
                    }
                }
                List<Product> list = productCodeUtils.setProduct(vo.getAddList(), user);
                list.forEach(g -> productService.instrProduct(g, user));
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
            }
        } catch (ServiceException ex) {
            log.error("[ProductService][instrProduct] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductController][productAdd] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_1, psn + CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 商品品种Id查询下修改商品明细
     *
     * @param productVo
     * @return
     */
    @SystemLog(name = "修改商品明细")
    @RequestMapping(value = "/ProdCls/updateProduct", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateProduct(@RequestBody ProductVo productVo, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);

            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, productVo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            if (!CommonFainl.USER_STATUS.equalsIgnoreCase(productVo.getSkuStatus())) {
                examine = operationAuthorityUtils.isUnitPriv("disabledProduct", "禁用商品明细权限", productVo.getUnitId(), user);
                if (!examine.getPrivBoolean()) {
                    return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), productVo);
                }
            }

            Color color = new Color();
            color.setColorCode(productVo.getColorCode());
            color.setColorName(productVo.getColorName());
            Spec spec = new Spec();
            spec.setSpecGrpId(productVo.getSpecGrpId());
            spec.setSpecCode(productVo.getSpecCode());
            spec.setSpecNum(productVo.getSpecNum());
            Product product = new Product();
            BeanUtils.copyProperties(productVo, product);
            product.setColorId(colorService.getColor(color).getColorId());
            product.setSpecId(specService.getSpecById(spec).getSpecId());
            product.setInnerBc(productVo.getUpdateinnerBc());
            product.setIntlBc(productVo.getUpdateintlBc());
            product.setProdCode(productVo.getUpdateProdCode());
            product.setProdClsId(null);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, productService.udapteProdClsUpdateProduct(product));
        } catch (ServiceException ex) {
            log.error("[ProdClsAttrService][updateProduct] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_2, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductController][updateProduct] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_2, psn + CommonFainl.UPDATEFANS, "");
        }
    }


    /**
     * 删除商品明细
     *
     * @param productVo
     * @return
     */
    @SystemLog(name = "打标删除商品明细")
    @RequestMapping(value = "/ProdCls/deleteProduct", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteProduct(@RequestBody ProductVo productVo, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            String s = operationAuthorityUtils.isAuthority(table, Operations + CommonFainl.DELETE);
            if (!(operationAuthorityUtils.isEmptAuthority(table, Operations + CommonFainl.DELETE, user))) {
                return new JsonResult(JsonResultCode.FAILURE, CommonFainl.TEXT + s, "");
            }
            Product product = new Product();
            product.setProdCode(productVo.getProdCode());
            product.setProdId(productVo.getProdId());
            product.setSkuStatus(CommonFainl.FILAN_STATUS);
            product.setDeleted(CommonFainl.TRUE);
            product.setIsDel(CommonFainl.FAILSTATUS);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, productService.updateDeleteProduct(product, user));
        } catch (ServiceException ex) {
            log.error("[ProdClsAttrService][deleteProduct] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_3, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductController][deleteProduct] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_3, psn + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 生成 商品code
     */
    @RequestMapping(value = "/ProdCls/creatProductCode", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult creatProductCodeId(@RequestBody ProductVo product, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            String code = productCodeUtils.creatProductCode(product, user);
            return new JsonResult(JsonResultCode.SUCCESS, "生成成功", code);
        } catch (Exception ex) {
            log.error("[ProductController][updateProduct] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "商品代码已存在", "");
        }
    }


    /**
     * 查询商品弹窗（通用）
     */
    @RequestMapping(value = "/goods/getProductWindow", method = {RequestMethod.GET})
    public JsonResult getProductWindow(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                       @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                       ProductWindowVo product,
                                       HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, productService.getProductWindow(page, size, product, user));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getShopAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductController][getProductWindow] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商品弹窗失败:ProductController.getProductWindow(),Exception Null", "");
        }
    }

}
