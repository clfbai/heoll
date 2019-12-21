package com.boyu.erp.platform.usercenter.controller.shop;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.entity.shop.ShopGpFml;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.shop.ShopModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.shop.ShopGpFmlSeriver;
import com.boyu.erp.platform.usercenter.service.shop.ShopRentValSerivcer;
import com.boyu.erp.platform.usercenter.service.shop.ShopServicer;
import com.boyu.erp.platform.usercenter.service.shop.ShopSpFmlServicer;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.shop.ShopRentValVo;
import com.boyu.erp.platform.usercenter.vo.shop.ShopSpFmlVo;
import com.boyu.erp.platform.usercenter.vo.shop.ShopVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController extends BaseController {
    private static final String table = "shop|";
    private static final String Operations = "ShopController|";
    private static final String tableName = "shop";
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private ShopServicer shopServicer;
    @Autowired
    private ShopGpFmlSeriver shopGpFmlSeriver;
    @Autowired
    private ShopRentValSerivcer shopRentValSerivcer;
    @Autowired
    private ShopSpFmlServicer shopSpFmlServicer;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private TableService tableService;
    @Autowired
    private SysUnitService unitService;


    /**
     * 查询门店集合
     *
     * @param
     * @return
     */
    @GetMapping(value = "/list")
    public JsonResult shopList(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                               @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                               ShopModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (model.getUnitId() == null || model.getUnitId() == 0L) {
                //默认取当前登录组织下门店
                model.setUnitId(user.getOwnerId());
            }
            PageInfo<ShopVo> pageInfo = shopServicer.getShop(page, size, model, user);
            //非管理员需要查看权限
            if (!(user.getIsType().equalsIgnoreCase("A") || user.getIsType().equalsIgnoreCase("L"))) {
                PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.SELECT, model.getUnitId(), user);
                if (!examine.getPrivBoolean()) {
                    pageInfo.setList(null);
                }
                return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[ShopServicer][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shopList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店信息失败", "");
        }
    }

    /**
     * 添加门店
     *
     * @param
     * @return
     */
    @SystemLog(name = "增加门店")
    @PostMapping(value = "/add")
    public JsonResult addShop(@RequestBody @Validated ShopModel shop, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (shop.getList().size() > 0) {
                for (Shop s : shop.getList()) {
                    PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.ADD, s.getOwnerId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), s);
                    }
                }
                /**
                 * 批量添加保留待编写
                 * */
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, shopServicer.addList(shop));
            } else {
                PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.ADD, shop.getOwnerId(), user);
                if (!examine.getPrivBoolean()) {
                    return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                }
                if (unitService.getCodeAndName(shop.getShopUnit(),CommonFainl.ADD)) {
                    return new JsonResult(JsonResultCode.FAILURE, "门店代码或门店名称已存在", "门店代码或门店名称已存在");
                }
                /**
                 * 单条添加
                 * */
                shop.setOpenDate(new Date());
                shop.setOprId(user.getUserId());
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, shopServicer.add(shop, user));
            }
        } catch (ServiceException ex) {
            log.error("[ShopServicer][add] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception e) {
            log.error("[ShopController][addShop] exception", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "增加失败");
        }
    }

    /**
     * 修改门店信息
     *
     * @param
     * @return
     */
    @SystemLog(name = "修改门店")
    @PostMapping(value = "/update")
    public JsonResult updateShop(@RequestBody ShopModel shop, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            System.out.println(shop.getOwnerId());
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, shop.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "修改门店成功", shopServicer.updateShop(shop));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][add] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception e) {
            log.error("[ShopController][updateShop] exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "修改门店信息失败", "修改失败");
        }
    }

    /**
     * 修改门店信息
     *
     * @param
     * @return
     */
    @SystemLog(name = "删除门店")
    @PostMapping(value = "/delete")
    public JsonResult deleteShop(@RequestBody ShopModel shop, HttpServletRequest request) {
        try {
            //目前先做删除 2019-8-31
            // remove：删除门店。
            //1）参照modify逻辑，判断是否有未过账小票。如果有，则提示错误：MSG_SHOP_MUST_BE_SETTLED。
            //2）判断门店现金账户的余额是否为0，非0则提示错误：MSG_SHOP_CASH_BALANCE_MUST_BE_ZERO。
            //3）调用resolver删除SHOP信息。数据库中不物理删除，而是通过deletedMark设置为逻辑删除。
            //4）调用SysUnitTypeHome.remove删除组织类型。包括UnitType.SHOP，另一个类型有SHOP.SHOP_TYPE决定。
            //5）调用SysUnitTypeHome.list判断门店在当前组织是否还存在有效类型。如果不存在，则调用SysUnitOwnerHome.remove删除门店属主关系。
            //6）如果门店同时是仓库，则调用WarehouseHome.remove，删除仓库。
            //listSubordinate：获取下级门店列表。
            //不限定门店的OWNER_ID，而是根据组织层级限定。可参照SysUnitHierachy.isSubordinate判断是否下级的逻辑。
            //listCounter：获取卖场下的专柜列表。
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(table, Operations, CommonFainl.DELETE, shop.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "删除门店成功", shopServicer.deleteShop(shop));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][add] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception e) {
            log.error("[ShopController][updateShop] exception", e);
            return new JsonResult(JsonResultCode.FAILURE, "删除门店失败 ShopController.deleteShop()", "修改失败");
        }
    }

    /**
     * 查询门店必填字段
     *
     * @param
     * @return
     */
    @PostMapping(value = "/getShopFile")
    public JsonResult getShopFile(@RequestBody Shop shop, HttpServletRequest request) {
        try {
            List<String> tables = new ArrayList<>();
            tables.add(tableName);
            tables.add("sys_unit");
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, tableService.selectTableFile(tables));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][getShopAtrrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][getShopFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店属必填字段失败ShopController.getShopFile() exception Null", "");
        }
    }

    /**
     * 查询门店关键字段对应权限
     *
     * @param
     * @return
     */
    @PostMapping(value = "/getUpdateShopFile")
    public JsonResult getUpdateShopFile(@RequestBody Shop shop, HttpServletRequest request) {
        try {
            List<String> tables = new ArrayList<>();
            tables.add(tableName);
            tables.add("sys_unit");
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, tableService.selectTableFilePrivKey(tableName, tables));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][getShopAtrrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][getShopFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店属必填字段失败ShopController.getShopFile() exception Null", "");
        }
    }

    /**
     * 查询门店属性(行转列)
     *
     * @param
     * @return
     */
    @PostMapping(value = "/findByShopId")
    public JsonResult findByShopId(@RequestBody Shop shop, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, shopServicer.getShopAtrrDef(shop));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][getShopAtrrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][findByShopId] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店属性信息失败ShopController.findByShopId() exception Null", "");
        }
    }

    /**
     * 查询门店店员
     *
     * @param
     * @return
     */
    @PostMapping(value = "/shop/emp")
    public JsonResult shopEmp(@RequestBody Shop shop, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(shopServicer.getShopEmp(shop)));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shopEmp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店店员信息失败ShopController.shopEmp() exception Null", "");
        }
    }


    /**
     * 查询门店付款方式
     *
     * @param
     * @return
     */
    @PostMapping(value = "/payType")
    public JsonResult shoppayType(@RequestBody Shop shop, HttpServletRequest request) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(shopServicer.getShopPay(shop)));
        } catch (ServiceException ex) {
            log.error("[SysDomainService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shoppayType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店付款方式信息失败ShopController.shoppayType() exception Null", "");
        }
    }

    /**
     * 根据门店Id查询门店普通促销公式
     *
     * @param
     * @return
     */
    @PostMapping(value = "/shopGpFml")
    public JsonResult shopGpFml(@RequestBody ShopGpFml shopGpFml, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(shopGpFmlSeriver.getShopGpFml(shopGpFml)));
        } catch (ServiceException ex) {
            log.error("[ShopGpFmlSeriver][getShopGpFml] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shopGpFml] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店普通促销公式信息失败ShopController.shopGpFml() exception Null", "");
        }
    }

    /**
     * 根据门店Id查询门店租金
     *
     * @param
     * @return
     */
    @PostMapping(value = "/shopRentVal")
    public JsonResult shopRentVal(@RequestBody ShopRentValVo shopRentValVo, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(shopRentValSerivcer.getShopRentVal(shopRentValVo)));
        } catch (ServiceException ex) {
            log.error("[ShopGpFmlSeriver][getShopGpFml] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shopRentVal] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店租金信息失败ShopController.shopRentVal() exception Null", "");
        }
    }


    /**
     * 查询门店扣点公式
     *
     * @param
     * @return
     */
    @PostMapping(value = "/shopSpFml")
    public JsonResult shopSpFml(@RequestBody ShopSpFmlVo s, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(shopSpFmlServicer.getShopSpFml(s)));
        } catch (ServiceException ex) {
            log.error("[ShopGpFmlSeriver][getShopGpFml] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shopSpFml] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店扣点公式信息失败ShopController.shopSpFml() exception Null", "");
        }
    }

    /**
     * 查询门店扣点公式
     *
     * @param
     * @return
     */
    @PostMapping(value = "/shopOptions")
    public JsonResult shopOptions() {
        try {
            List<PurKeyValue> list = codeDtlService.optionGet("TandF");
            List<PurKeyValue> listStauts = codeDtlService.optionGet("SHOP_STATUS");
            Map<String, Object> map = prodClsUtils.getMap();
            //统一租金
            map.put("univRentVal", list);
            //接受VIP
            map.put("vipAcpt", list);
            //统一扣点
            map.put("univSpFml", list);
            //接受储值
            map.put("depAcpt", list);
            //收银暂存
            map.put("ctDep", list);
            //收银员账户管理
            map.put("ccEnabled", list);
            //营业员业绩管理
            map.put("svEnabled", list);
            //可征募
            map.put("recruitable", list);
            //是否共享
            map.put("shared", list);
            //是否代销
            map.put("isCms", list);
            //门店类别
            map.put("shopType", codeDtlService.optionGet("SHOP_TYPE"));
            //区域(大区)
            map.put("shopAreaCode", codeDtlService.optionGet("SHOP_PROP"));
            //分公司
            map.put("shopMode", codeDtlService.optionGet("SHOP_MODE"));
            //结算方式
            map.put("shopStlMode", codeDtlService.optionGet("SHOP_STL_MODE"));
            //物流方式(对应门店、面积级别字段)
            map.put("acrLvl", codeDtlService.optionGet("ACR_LVL"));
            //销售级别    SALES_LVL
            map.put("salesLvl", codeDtlService.optionGet("SALES_LVL"));
            //门店状态
            map.put("shopStatus", listStauts);
            //组织状态
            map.put("unitStatus", listStauts);
            //执照类别
            map.put("licType", codeDtlService.optionGet("LIC_TYPE"));
            //渠道
            // map.put("SHOP_TYPE1",codeDtlService.optionGet("SHOP_TYPE1"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[ShopGpFmlSeriver][] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shopOptions] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店下拉信息失败ShopController.shopOptions() exception Null", "");
        }
    }


}
