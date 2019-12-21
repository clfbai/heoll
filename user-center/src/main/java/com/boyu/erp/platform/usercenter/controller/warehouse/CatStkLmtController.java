package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.CatStkLmt;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.UserBrandModel;
import com.boyu.erp.platform.usercenter.model.wareh.CatStkLmtModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.BrandService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.CatStkLmtSerivce;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.system.UserBrandVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.CatStkLmtVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/warehouse/catstk")
public class CatStkLmtController extends BaseController {

    private static final String table = "cat_stk_lmt|";
    private static final String Operations = "CatStkLmtController|";
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private CatStkLmtSerivce catStkLmtSerivce;
    @Autowired
    private SysParameterService prameterService;
    @Autowired
    private FiledUtils filedUtils;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private UsersService usersService;
    final String privId = "ClassifyCatStk";

    /**
     * 查询分类库存预警列表
     *
     * @author
     */
    @GetMapping(value = "/list")
    public Object catStkLmtList(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                CatStkLmtModel model, HttpServletRequest request) {
        try {
            //查看是否登录
            SysUser sysUser =  this.isNullUser(request);

            //仓库库存预警控制采用的分类字段 对应参数
            String prameter = "CATEGORY_FIELD_IN_WAREHOUSE_STOCK_LIMIT_CONTROL";
            //默认分类code
            String codeType = "SALES_LVL";
            SysParameter parameters = prameterService.findByParameter(prameter);
            if (parameters != null && StringUtils.isNotEmpty(parameters.getParmVal())) {
                codeType = parameters.getParmVal();
            }
            model.setCatCode(codeType);
            PageInfo<CatStkLmtVo> pageInfo = catStkLmtSerivce.selectPage(page, size, model, sysUser);

            /**
             * 系统参数EFFICIENT_STOCK_LIMIT_FIELDS控制目前有效的库存控制字段，缺省为：MAX_STK;MIN_STK;BEST_STK。多个字段之间用半角“;”分隔。没有生效的字段不显示，包括商品范围和结果两个网格。
             */
            String prameter2 = "EFFICIENT_STOCK_LIMIT_FIELDS";
            parameters = prameterService.findByParameter(prameter2);
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            JsonResult o = new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS);
            Set<String> set = new HashSet<>();
            if (parameters != null && StringUtils.isNotEmpty(parameters.getParmVal())) {
                if (parameters.getParmVal().indexOf(";") > 0) {
                    String[] strs = parameters.getParmVal().split(";");
                    List<String> strmax = Arrays.asList(strs);
                    List<String> ss = new ArrayList<>();
                    ss.add("MAX_STK");
                    ss.add("MIN_STK");
                    ss.add("BEST_STK");
                    ss.removeAll(strmax);
                    //有需要过滤的字段
                    if (ss.size() > 0) {
                        for (String s : ss) {
                            filter.getExcludes().add(filedUtils.getConvert(s));
                            set.add(filedUtils.getConvert(s));
                        }
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("file", set);
            map.put("original", pageInfo);
            o.setObject(map);
            //注意转Json 时对应的属性需要存在(属性要有值，比如 属性 A:null 那么A 在序列化时会被去掉)
            String result = JSONObject.toJSONString(o, filter);
            return JSONObject.parseObject(result);
        } catch (ServiceException e) {
            log.error("[CatStkLmtSerivce][selectPage] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_PENDING_Q, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[CatStkLmtController][catStkLmtList] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "仓库分类库存预警" + CommonFainl.SELECTFIANL + " CatStkLmtController.catStkLmtList(),Exception Null", null);
        }
    }


    /**
     * 增加分类库存预警
     *
     * @author
     */
    @SystemLog(name = "增加分类库存预警")
    @PostMapping(value = "/add")
    public JsonResult addcatStkLmt(@RequestBody @Validated CatStkLmtModel model, HttpServletRequest request) {
        try {
            //查看是否登录
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if ("GL".equalsIgnoreCase(model.getScopeUnit())) {
                //全局
                model.setUnitId(0L);
                if (CollectionUtils.isNotEmpty(model.getCatStkLmtList()))
                    model.getCatStkLmtList().forEach(s -> s.setUnitId(0L));
            }
            if ("CP".equalsIgnoreCase(model.getScopeUnit())) {
                //公司
                if (CollectionUtils.isNotEmpty(model.getCatStkLmtList()))
                    model.getCatStkLmtList().forEach(s -> s.setUnitId(sysUser.getOwnerId()));
            }
            if (CollectionUtils.isNotEmpty(model.getCatStkLmtList())) {
                for (CatStkLmt c : model.getCatStkLmtList()) {
                    if (c.getUnitId() != 0L) {
                        PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.ADD, c.getUnitId(), sysUser);
                        if (!examine.getPrivBoolean()) {
                            return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                        }
                    } else {
                        if (!privUtils.isUserPrivId(sysUser, privId, "分类库存预警全局权限")) {
                            return new JsonResult(JsonResultCode.FAILURE, "您还尚未拥有:" + privId + "分类库存预警全局权限", "");
                        }
                    }

                }
                catStkLmtSerivce.addCatStkLmtList(model.getCatStkLmtList());
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, "");
        } catch (ServiceException e) {
            log.error("[CatStkLmtSerivce][warehcodepopup] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[CatStkLmtController][addcatStkLmt] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "仓库分类库存预警" + CommonFainl.SELECTFIANL + " CatStkLmtController.addcatStkLmt(),Exception Null", null);
        }
    }


    /**
     * 修改分类库存预警
     *
     * @author
     */
    @SystemLog(name = "修改分类库存预警")
    @PostMapping(value = "/update")
    public JsonResult updateCatStkLmt(@RequestBody CatStkLmtModel model, HttpServletRequest request) {
        try {
            //查看是否登录
            SysUser user = (SysUser) this.isNullUser(request);
            if (CollectionUtils.isNotEmpty(model.getCatStkLmtList())) {
                for (CatStkLmt c : model.getCatStkLmtList()) {
                    PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, c.getUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                    }
                }
                catStkLmtSerivce.updateCatStkLmtList(model.getCatStkLmtList());
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, "");
        } catch (ServiceException e) {
            log.error("[CatStkLmtSerivce][deleteCatStkLmtList] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[CatStkLmtController][addcatStkLmt] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "仓库分类库存预警" + CommonFainl.USER_STATUS + " CatStkLmtController.updateCatStkLmt(),Exception Null", null);
        }
    }

    /**
     * 删除分类库存预警
     *
     * @author
     */
    @SystemLog(name = "删除分类库存预警")
    @PostMapping(value = "/delete")
    public JsonResult deleteCatStkLmt(@RequestBody CatStkLmtModel model, HttpServletRequest request) {
        try {
            //查看是否登录
            SysUser user = (SysUser) this.isNullUser(request);
            if (CollectionUtils.isNotEmpty(model.getCatStkLmtList())) {
                for (CatStkLmt c : model.getCatStkLmtList()) {
                    PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.DELETE, c.getUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                    }
                }
                catStkLmtSerivce.deleteCatStkLmtList(model.getCatStkLmtList());
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, "");
        } catch (ServiceException e) {
            log.error("[CatStkLmtSerivce][deleteCatStkLmtList] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[CatStkLmtController][addcatStkLmt] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "仓库分类库存预警" + CommonFainl.DELETEFANS + " CatStkLmtController.deleteCatStkLmt(),Exception Null", null);
        }
    }


    @GetMapping("/option")
    public JsonResult catStkLmtOption( String isType, HttpServletRequest request) {
        try {

            //仓库库存预警控制采用的分类字段 对应参数
            String prameter = "CATEGORY_FIELD_IN_WAREHOUSE_STOCK_LIMIT_CONTROL";
            //默认分类code
            SysParameter parameters = prameterService.findByParameter(prameter);
            String codeType = parameters == null ? "SALES_LVL" : parameters.getParmVal();
            SysUser user = (SysUser) this.isNullUser(request);
            Map<String, Object> map = prodClsUtils.getMap();
            //分类Id下拉
            map.put("catId", codeDtlService.optionGet(codeType));
            List<PurKeyValue> list = codeDtlService.optionGet("STK_LMT_SCP");
            if (!privUtils.isUserPrivId(user, privId)) {
                m:
                for (PurKeyValue keyValue : list) {
                    if (keyValue.getOptionValue().equalsIgnoreCase("GL")) {
                        list.remove(keyValue);
                        break m;
                    }
                }
            }

            if("wareh".equalsIgnoreCase(isType)){
               m: for (PurKeyValue keyValue : list) {
                    if (keyValue.getOptionValue().equalsIgnoreCase("GL")) {
                        list.remove(keyValue);
                        break m;
                    }
                }
            }
            //范围下拉
            map.put("scopeUnit", list);
            List<PurKeyValue> keyValues = new ArrayList<>();
            List<Brand> brands = brandService.userOperationBrand(user);
            if (CollectionUtils.isNotEmpty(brands)) {
                for (Brand b : brands) {
                    PurKeyValue purKeyValue = new PurKeyValue(b.getBrandName(), b.getBrandId() + "");
                    keyValues.add(purKeyValue);
                }
            } else {
                UserBrandModel model = new UserBrandModel();
                model.setUnitId(user.getOwnerId());
                model.setUserId(user.getUserId());
                List<UserBrandVo> userBrand = brandService.getUserBrand(model);
                for (UserBrandVo b : userBrand) {
                    PurKeyValue purKeyValue = new PurKeyValue(b.getBrandName(), b.getBrandId() + "");
                    keyValues.add(purKeyValue);
                }
            }
            map.put("brandId", keyValues);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException e) {
            log.error("[CatStkLmtSerivce][deleteCatStkLmtList] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[CatStkLmtController][addcatStkLmt] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "仓库分类库存预警下拉" + CommonFainl.SELECTFIANL + " CatStkLmtController.catStkLmtOption(),Exception Null", null);
        }
    }
}
