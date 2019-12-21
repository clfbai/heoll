package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.CatStkLmt;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkLmt;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.StockPolicyModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.service.warehouse.CatStkLmtSerivce;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehStkLmtService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 类描述:  库存策略控制层
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/20 19:42
 */
@Slf4j
@RestController
@Transactional
@RequestMapping("/warehouse/stock")
public class StockPolicyController extends BaseController {
    private static final String table = "cat_stk_lmt|";
    private static final String table2 = "wareh_stk_lmt|";
    private static final String Operations = "CatStkLmtController|";
    final String privId = "ClassifyCatStk";

    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private CatStkLmtSerivce catStkLmtSerivce;
    @Autowired
    private SysParameterService prameterService;
    @Autowired
    private FiledUtils filedUtils;
    @Autowired
    private ProductService productService;
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private WarehStkLmtService warehStkLmtService;

    /**
     * 增加分类库存预警
     *
     * @author
     */
    @SystemLog(name = "增加分类库存预警")
    @PostMapping(value = "/add")
    public JsonResult addCatStkLmt(@RequestBody StockPolicyModel model, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if ("GL".equalsIgnoreCase(model.getScopeUnit())) {
                //全局
                if (CollectionUtils.isNotEmpty(model.getList())) {
                    model.getList().forEach(s -> s.setUnitId(0L));
                }
                if (CommonFainl.FALSE.equalsIgnoreCase(model.getIsUnit())) {
                    if (!privUtils.isUserPrivId(user, privId, "分类库存预警全局权限")) {
                        return new JsonResult(JsonResultCode.FAILURE, "您还尚未拥有:" + privId + " 分类库存预警全局权限", "");
                    }
                    //不保留组织设定 删除所有商品品种分类库存策略
                    if (CollectionUtils.isNotEmpty(model.getList())) {
                        for (StockPolicyModel c : model.getList()) {
                            Product p = new Product();
                            p.setProdClsId(c.getProdClsId());
                            List<ProductVo> list = productService.getProductDetail(p);
                            if (CollectionUtils.isNotEmpty(list)) {
                                for (ProductVo vo : list) {
                                    CatStkLmt catStkLmt = new CatStkLmt();
                                    catStkLmt.setProdId(vo.getProdId());
                                    catStkLmtSerivce.deleteCatStkLmtAll(catStkLmt);
                                }
                            }
                        }
                    }
                }
            }
            if ("CP".equalsIgnoreCase(model.getScopeUnit())) {
                //公司
                if (CollectionUtils.isNotEmpty(model.getList()))
                    model.getList().forEach(s -> s.setUnitId(user.getOwnerId()));
            }
            if (CollectionUtils.isNotEmpty(model.getList())) {
                for (StockPolicyModel c : model.getList()) {
                    //判断是否拥有全局权限: ClassifyCatStk
                    if (c.getUnitId() != 0L) {
                        PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.ADD, c.getUnitId(), user);
                        if (!examine.getPrivBoolean()) {
                            return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                        }
                    }
                }
                for (StockPolicyModel c : model.getList()) {
                    Product p = new Product();
                    p.setProdClsId(c.getProdClsId());
                    List<ProductVo> list = productService.getProductDetail(p);
                    if (CollectionUtils.isNotEmpty(list)) {
                        for (ProductVo vo : list) {
                            CatStkLmt catStkLmt = new CatStkLmt();
                            catStkLmt.setProdId(vo.getProdId());
                            BeanUtils.copyProperties(c, catStkLmt);
                            catStkLmtSerivce.addCatStkLmt(catStkLmt);
                        }
                    }
                }

            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, "");
        } catch (ServiceException e) {
            log.error("[CatStkLmtSerivce][warehcodepopup] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[CatStkLmtController][addcatStkLmt] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "仓库分类库存预警" + CommonFainl.SELECTFIANL + " CatStkLmtController.addCatStkLmt(),Exception Null", null);
        }
    }

    /**
     * 增加仓库库存预警
     *
     * @author
     */
    @SystemLog(name = "增加仓库库存预警")
    @PostMapping(value = "/addWareh")
    public JsonResult addWarehouse(@RequestBody StockPolicyModel model, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);

            if ("CP".equalsIgnoreCase(model.getScopeUnit())) {
                //公司
                if (CollectionUtils.isNotEmpty(model.getList()))
                    model.getList().forEach(s -> s.setUnitId(user.getOwnerId()));
            }
            if (CollectionUtils.isNotEmpty(model.getList())) {
                for (StockPolicyModel c : model.getList()) {
                    PrivIdExamine examine = privUtils.isUnitPriv(table2, Operations, CommonFainl.ADD, c.getUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                    }
                }
                for (StockPolicyModel c : model.getList()) {
                    Product p = new Product();
                    p.setProdClsId(c.getProdClsId());
                    List<ProductVo> list = productService.getProductDetail(p);
                    if (CollectionUtils.isNotEmpty(list)) {
                        for (ProductVo vo : list) {
                            WarehStkLmt warehStkLmt = new WarehStkLmt();
                            warehStkLmt.setProdId(vo.getProdId());
                            BeanUtils.copyProperties(c, warehStkLmt);
                            warehStkLmt.setWarehId(c.getUnitId());
                            warehStkLmtService.addwarehStkLmt(warehStkLmt);
                        }
                    }
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, "");
        } catch (ServiceException e) {
            log.error("[CatStkLmtSerivce][warehcodepopup] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[CatStkLmtController][addWarehouse] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "仓库库存预警" + CommonFainl.SELECTFIANL + " CatStkLmtController.addWarehouse(),Exception Null", null);
        }
    }

    /**
     * 增加分类库存预警(库存策略)需要隐藏字段
     *
     * @author
     */

    @PostMapping(value = "/getFlie")
    public JsonResult getFlie() {
        try {
            String prameter = "EFFICIENT_STOCK_LIMIT_FIELDS";
            List<String> set = new ArrayList<>();
            SysParameter parameters = prameterService.findByParameter(prameter);
            if (parameters != null && StringUtils.isNotEmpty(parameters.getParmVal())) {
                if (parameters.getParmVal().indexOf(";") > 0) {
                    String[] str = parameters.getParmVal().split(";");
                    List<String> strmax = Arrays.asList(str);
                    List<String> ss = new ArrayList<>();
                    ss.add("MAX_STK");
                    ss.add("MIN_STK");
                    ss.add("BEST_STK");
                    ss.removeAll(strmax);
                    //有需要过滤的字段
                    if (ss.size() > 0) {
                        for (String s : ss) {
                            set.add(filedUtils.getConvert(s));
                        }
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("file", set);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception e) {
            log.error("[CatStkLmtController][getFlie] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL + " CatStkLmtController.getFlie(),Exception Null", null);
        }
    }
}
