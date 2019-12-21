package com.boyu.erp.platform.usercenter.utils.goods;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProductCodeModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.ColorService;
import com.boyu.erp.platform.usercenter.service.system.SpecService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 商品代码生成工具类
 * @author: clf
 * @create: 2019-06-20 19:22
 */
@Component
public class ProductCodeUtils {
    @Autowired
    private SysParameterService parameterService;
    @Autowired
    private ProdClsService prodClsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SpecService specService;

    @Autowired
    private ColorService colorService;

    /**
     * 生成商品code
     */
    public String creatProductCode(ProductVo product, SysUser user) throws Exception {
        SysParameter parameter = parameterService.findByParameter(ParameterString.PRODUCT_CODE);
        if (parameter == null) {
            SysParameter p = new SysParameter();
            p.setParmId(ParameterString.PRODUCT_CODE);
            /**
             * 默认编号规则 商品品种属性代码+颜色代码+规格代码
             * */
            p.setParmVal("PROD_CLS_CODE+COLOR_CODE+SPEC_NUM");
            p.setDescription("商品编号规则");
            p.setIsDel(CommonFainl.BTYPESTATUS);
            p.setCreateBy(user.getUserId());
            p.setCreateTime(new Date());
            parameterService.insertParameter(p);
            parameter = parameterService.findByParameter(ParameterString.PRODUCT_CODE);
        }
        ProductCodeModel model = getMode(product);
        if (model == null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "参数有误");
        }
        String str = parameter.getParmVal();
        String[] list = str.split("\\+");
        List<String> file = new ArrayList<>();
        for (String s : list) {
            String[] gt = s.split("_");
            for (int i = 0; i < gt.length; i++) {
                gt[i] = getConvert(gt[i], i);
            }
            str = "";
            for (int i = 0; i < gt.length; i++) {
                str = str + gt[i];
            }
            //生成商品代码的字段
            file.add(str);
        }
        Field[] fields = model.getClass().getDeclaredFields();
        String retus = "";


        for (String s : file) {
            for (Field f : fields) {
                if (s.equals(f.getName())) {
                    f.setAccessible(true);
                    // System.out.println("取值字段:   " + f.getName());
                    if (f.get(model) == null) {
                        throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, f.getName() + ":  值为空");
                    }
                    // System.out.println("属性名:" + f.getName());
                    // System.out.println("属性值:" + f.get(model));
                    retus = retus + f.get(model);
                }
            }
        }
        System.out.println("拼接后" + retus);
        return isEmpProductId(retus, model.getProdClsCode());
    }

    /**
     * 生成商品代码模型 对象
     */
    public ProductCodeModel getMode(ProductVo p) throws Exception {

        ProductCodeModel model = new ProductCodeModel();
        ProdClsWithBLOBs prodCls = new ProdClsWithBLOBs();
        prodCls.setProdClsId(p.getProdClsId());
        ProdCls cls = prodClsService.findByProdCls(prodCls);
        //System.out.println("{商品品种对象}" + cls);
        BeanUtils.copyProperties(cls, model);
        model.setColorCode(p.getColorCode());
        model.setSpecNum(p.getSpecNum());
        String year = cls.getYearVal() + "";
        String year2 = year.substring(year.length() - 2, year.length());
        String year1 = year.substring(year.length() - 1, year.length());
        model.setYear1(year1);
        model.setYear2(year2);
        model.setYear4(year);
        model.setSpecNum(p.getSpecNum());
        //System.out.println("cop对象：  " + model);
        return model;
    }

    /**
     * 判断 商品code是否存在
     */
    public String isEmpProductId(String productCode, String prodClsCode) throws ServiceException {

        Product p = new Product();
        p.setProdCode(productCode);
        if (productService.findByCode(p) != null) {


            p.setProdCode(prodClsCode);
            List<Integer> list = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(productService.getProduct(p))) {
                for (Product ps : productService.getProduct(p)) {
                    int a = Integer.parseInt(ps.getProdCode().substring(prodClsCode.length(), ps.getProdCode().length()));
                    list.add(a);
                }
                //升序
                Collections.sort(list);
                int max = list.get(list.size() - 1) + 1;
                prodClsCode += max;
                return prodClsCode;
            }
        }
        return productCode;


    }

    /**
     * 生成驼峰命名字段
     */
    public String getConvert(String str, int i) {
        if (i == 0) {
            return str.toLowerCase();
        }
        String first = str.substring(0, 1);
        String after = str.substring(1);
        first = first.toUpperCase();
        after = after.toLowerCase();
        return first + after;
    }

    /**
     * 功能描述: 批量成商品Code 对商品信息赋值
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 20:10
     */
    public List<Product> setProduct(List<ProductVo> list, SysUser user) throws Exception {
        List<Spec> selectList = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Color> coloList = new ArrayList<>();
        for (ProductVo vo : list) {
            Spec spec = new Spec();
            spec.setSpecGrpId(vo.getSpecGrpId());
            spec.setSpecCode(vo.getSpecCode());
            selectList.add(spec);
            Color color = new Color();
            color.setColorCode(vo.getColorCode());
            color.setColorName(vo.getColorName());
            coloList.add(color);
        }
        List<Spec> spec = specService.getSpecList(list);
        List<Color> colors = colorService.getColorList(coloList);
        String listCode = "";
        Long sp = null;
        Long colorId = null;
        for (ProductVo vo : list) {
            if (CollectionUtils.isNotEmpty(spec)) {
                for (Spec s : spec) {
                    if (s.getSpecCode().equalsIgnoreCase(vo.getSpecCode()) &&
                            s.getSpecGrpId().equalsIgnoreCase(vo.getSpecGrpId())) {
                        sp = s.getSpecId();
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(colors)) {
                for (Color c : colors) {
                    if (vo.getColorCode().equalsIgnoreCase(c.getColorCode())
                            && vo.getColorName().equalsIgnoreCase(c.getColorName())) {
                        colorId = c.getColorId();
                    }
                }
            }
            if (sp == null) {
                //默认规格
                sp = -1L;
            }
            if (colorId == null) {
                //默认颜色
                colorId = -1L;
            }
            vo.setSpecId(sp);
            vo.setColorId(colorId);
            Product product = new Product();
            BeanUtils.copyProperties(vo, product);
            if (StringUtils.NullEmpty(listCode)) {
                listCode = this.creatProductCode(vo, user);
            } else {
                String maxs = "";
                String sss = "";
                if (listCode.indexOf(".") > 0) {
                    maxs = listCode.substring(0, listCode.lastIndexOf(".") + 1);
                    sss = listCode.substring(listCode.lastIndexOf(".") + 1, listCode.length());
                } else {
                    sss = listCode;
                }
                Long a = Long.parseLong(sss) + 1;
                listCode = "";
                listCode = maxs + a;

            }
            product.setProdCode(listCode);
            products.add(product);
        }
        if (products.size() != list.size()) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "批量赋值异常:ProductCodeUtils.setProduct()");
        }
        return products;
    }


}
