package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.TPOS.service.ItemUploadingService;
import com.boyu.erp.platform.usercenter.components.FieldAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsAttr;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProdClsAttrModel;
import com.boyu.erp.platform.usercenter.model.goods.ProdClsDisable;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.base.RabbitTemplateSerivce;
import com.boyu.erp.platform.usercenter.service.base.SendSerivce;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsAttrService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductClsAuditSerivce;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.ColorService;
import com.boyu.erp.platform.usercenter.service.system.SpecService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.system.UserScopeServer;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.FileUpdateUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsAndProdcutUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @program: boyu-platform
 * @description: 商品品种控制层
 * @author: clf
 * @create: 2019-06-10 10:53
 */
@Slf4j
@Transactional
@RestController
@RequestMapping("/product/cls")
public class ProductClsController extends BaseController {
    private static final String pcn = "商品品种";
    private final String tableName = "prod_cls";
    private static final String Operations = "ProductClsController|";
    @Autowired
    private ProdClsAttrService prodClsAttrService;
    @Autowired
    private ProdClsService prodClsService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SpecService specService;
    @Autowired
    private ProdClsAndProdcutUtils prodClsAndProdcutUtils;
    @Autowired
    private FileUpdateUtils fileUpdateUtils;
    @Autowired
    private ProductService productService;
    @Autowired
    private TableService tableService;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private RabbitTemplateSerivce templateSerivce;
    @Autowired
    private SendSerivce sendSerivce;
    @Autowired
    private ItemUploadingService uploadingService;
    @Autowired
    private ProductClsAuditSerivce productClsAuditSerivce;
    @Autowired
    private ParameterString parameterString;
    @Autowired
    private UserScopeServer uerScopeServer;
    @Autowired
    private SysUnitService unitService;

    /**
     * 查询商当前商品品种或其他组织共享商品
     *
     * @param model
     * @return
     */
    @FieldAuthority(name = "ProdCls")
    @RequestMapping(value = "/listUnit", method = {RequestMethod.POST, RequestMethod.GET})
    public Object listMax(ProdClsModel model,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "15") Integer size,
                          HttpServletRequest request) {
        try {
            if (StringUtils.NullEmpty(model.getUnitType())) {
                //为空默认查当前组织商品
                model.setUnitType("this");
            }
            if (model.getProdClsId() == null
                    && com.boyu.erp.platform.usercenter.utils.StringUtils.NullEmpty(model.getProdName())
                    && StringUtils.NullEmpty(model.getProdClsCode())
                    && StringUtils.NullEmpty(model.getInputCode())
                    && StringUtils.NullEmpty(model.getBrandId())
                    && StringUtils.NullEmpty(model.getProdCatId())
            ) {
                return new JsonResult(JsonResultCode.SUCCESS, "请输入查询参数", "");
            }
            if (StringUtils.NullEmpty(model.getBrandId())) {
                model.setBrandId(null);
            }
            SysUser sysUser = this.getSessionSysUser(request);
            PageInfo<ProdClsVo> pageInof = null;
            //查询当前
            if ("this".equalsIgnoreCase(model.getUnitType())) {
                pageInof = prodClsService.getUnitProdClsList(page, size, model, sysUser);
            }
            //查询其他组织共享商品 需要判断拥有相应的品牌权限
            if ("other".equalsIgnoreCase(model.getUnitType())) {
                pageInof = prodClsService.getOtherUnitProdClsList(page, size, model, sysUser);
            }
            //没有结果手动封装前台不报异常
            if (pageInof == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("file", new HashSet<>());
                map.put("original", new PageInfo<>(new ArrayList<>()));
                return new JsonResult(JsonResultCode.SUCCESS, "查询成功,没有商品", map);
            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInof);
        } catch (ServiceException ex) {
            log.error("[ProdClsService][catTree] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_CLS, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductClsController][getListCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_CLS, pcn + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 商品品种自定义属性查询
     *
     * @param prodClsWithBLOBs
     * @return
     */
    @RequestMapping(value = "/findByProdClsId", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult findByProdClsId(@RequestBody ProdClsWithBLOBs prodClsWithBLOBs, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", prodClsAttrService.findProdAllAttr(prodClsWithBLOBs));
        } catch (ServiceException ex) {
            log.error("[ProdClsAttrService][FindByProdClsId] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_ATTR, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductClsController][findByProdClsId] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_ATTR, "商品属性查询失败", "");
        }
    }


    @SystemLog(name = "添加商品品种")
    @RequestMapping(value = "/addProdCls", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addProdCls(@RequestBody @Valid ProdClsVo prodClsVo, HttpServletRequest request) {
        try {
            SysUser us = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.ADD, prodClsVo.getUnitId(), us);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            //单条且不包含明细
            if (prodClsVo.getAddProdCls().size() <= 0) {
                if (prodClsVo.getUnitId() == null || prodClsVo.getUnitId() == 0L) {
                    return new JsonResult(JsonResultCode.FAILURE, "商品未添加对应组织", 0);
                }
                if (prodClsVo.getColorId() == null && StringUtils.isNotEmpty(prodClsVo.getColorCode()) && StringUtils.isNotEmpty(prodClsVo.getColorName())) {
                    Color color = new Color();
                    color.setColorCode(prodClsVo.getColorCode());
                    color.setColorName(prodClsVo.getColorName());
                    prodClsVo.setColorId(colorService.getColor(color).getColorId());
                } else {
                    prodClsVo.setColorId(6L);
                }
                if (prodClsVo.getSpecGrpId() != null && StringUtils.isNotEmpty(prodClsVo.getSpecCode())) {
                    Spec spec = new Spec();
                    spec.setSpecCode(prodClsVo.getSpecCode());
                    spec.setSpecGrpId(prodClsVo.getSpecGrpId());
                    prodClsVo.setSpecId(specService.getSpecById(spec).getSpecId());
                } else {
                    prodClsVo.setSpecId(-1L);
                }
                /**
                 * 新增 ： 增加商品明细
                 *
                 * */
                if (CollectionUtils.isNotEmpty(prodClsVo.getAddProduct())) {
                    for (ProductVo vs : prodClsVo.getAddProduct()) {
                        if (!((boolean) productService.verificationFile(vs).get("bo"))) {
                            return new JsonResult(JsonResultCode.FAILURE, (String) productService.verificationFile(vs).get("m"), vs);
                        }
                        //活动
                        vs.setSkuStatus(CommonFainl.USER_STATUS);
                    }
                }
                if (StringUtils.NullEmpty(prodClsVo.getStkAdopted())) {
                    //默认开启库存管理
                    prodClsVo.setStkAdopted("T");
                }
                int a = prodClsService.instrProdCls(prodClsVo, us);
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, a);
            }
            //预留多条(批量增加)
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, 0);
        } catch (ServiceException ex) {
            log.error("[ProdClsAttrService][FindByProdClsId] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductClsController][addProdCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_CLS_1, pcn + CommonFainl.ADDFIANL, "");
        }
    }


    @SystemLog(name = "修改商品品种")
    @RequestMapping(value = "/updateProdCls", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateProdCls(@RequestBody ProdClsVo prodClsVo, HttpServletRequest request) {
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(prodClsVo.getAuditType())) {
                return new JsonResult(JsonResultCode.FAILURE, "商品审核状态为空请检查参数: auditType", "");
            }
            if ("am".equalsIgnoreCase(prodClsVo.getAuditType())) {
                return new JsonResult(JsonResultCode.FAILURE, "商品审核状态为已审核不能修改", "");
            }
            SysUser us = this.getSessionSysUser(request);
            if (!"A".equalsIgnoreCase(us.getIsType()) && !"AS".equalsIgnoreCase(us.getIsType())) {
                if (!prodClsVo.getCtrlUnitId().equals(us.getOwnerId())) {
                    return new JsonResult(JsonResultCode.FAILURE, "不能修改其他组织管理商品", prodClsVo);
                }
            }
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, prodClsVo.getUnitId(), us);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            if (!CommonFainl.USER_STATUS.equalsIgnoreCase(prodClsVo.getProdStatus())) {

                examine = privUtils.isUnitPriv("disabledProdCls", "禁用商品品种权限", prodClsVo.getUnitId(), us);
                if (!examine.getPrivBoolean()) {
                    return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                }
            }
            fileUpdateUtils.isNotUpdate(prodClsVo, "prod_cls", us);
            Color color = new Color();
            color.setColorCode(prodClsVo.getColorCode());
            color.setColorName(prodClsVo.getColorName());
            prodClsVo.setColorId(colorService.getColor(color).getColorId());
            //不能直接更改商品库存管理
            prodClsVo.setStkAdopted(null);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, prodClsService.updateProdCls(prodClsVo, us));
        } catch (ServiceException ex) {
            log.error("[ProdClsAttrService][updateProdCls] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductClsController][updateProdCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_CLS_2, pcn + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * @description: 删除商品品种
     * 1.修改商品品种表（打标删除)
     * 2.删除组织商品品种表
     * 3.删除商品品种下 商品
     * @author: CLF
     */
    @SystemLog(name = "删除商品品种")
    @RequestMapping(value = "/deleteProdCls", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteProdCls(@RequestBody ProdClsVo prodClsVo, HttpServletRequest request) {
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(prodClsVo.getAuditType())) {
                return new JsonResult(JsonResultCode.FAILURE, "商品审核状态为空请检查参数: auditType", "");
            }
            if ("am".equalsIgnoreCase(prodClsVo.getAuditType())) {
                return new JsonResult(JsonResultCode.FAILURE, "商品审核状态为已审核不能删除", "");
            }
            SysUser us = this.getSessionSysUser(request);
            if (!"A".equalsIgnoreCase(us.getIsType()) && !"AS".equalsIgnoreCase(us.getIsType())) {
                if (!prodClsVo.getCtrlUnitId().equals(us.getOwnerId())) {
                    return new JsonResult(JsonResultCode.FAILURE, "不能删除他组织管理商品", prodClsVo);
                }
            }
            //这里应该取 prodClsVo.getCtrlUnitId()
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.DELETE, prodClsVo.getUnitId(), us);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            //先获取消息对象被修改状态后查询不到对象
            List<MessageObject> list = new ArrayList<>();
            if (prodClsVo.getIsAudit() == 1) {
                prodClsVo.setProdStatus(CommonFainl.FILAN_STATUS);
                ProdClsModel m = creatProdClsMode(prodClsVo.getProdClsId(), prodClsVo.getProdClsCode());
                //获取删除商品消息
                list = productClsAuditSerivce.productClsNotAudit(m, 4);
            }

            //逻辑删除删除
            int a = prodClsService.deleteprodCls(prodClsVo, us);
            if (a > 0) {
                //删除成功推送消息
                if (CollectionUtils.isNotEmpty(list)) {
                    log.info("[删除商品发送手动删除消息]:" + list.get(0));
                    list.stream().forEach(s -> templateSerivce.send(s));
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, a);
        } catch (ServiceException ex) {
            log.error("[ProdClsService][deleteprodCls] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_CLS_3, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductClsController][deleteProdCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GOODS_CLS_3, pcn + CommonFainl.DELETEFANS, "");
        }
    }


    /**
     * 商品品种 自定义商品属性修改
     *
     * @param prodClsAttrMode
     * @return
     */
    @SystemLog(name = "修改商品自定义属性")
    @RequestMapping(value = "/updateProdClsAttr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateProdClsAttr(@RequestBody ProdClsAttrModel prodClsAttrMode, HttpServletRequest request) {
        try {
            Long prodClsId = -1L;
            Long unitId = -1L;
            if (!prodClsAttrMode.getAddProdClsAttr().isEmpty()) {
                prodClsId = prodClsAttrMode.getAddProdClsAttr().get(0).getProdClsId();
                unitId = prodClsAttrMode.getAddProdClsAttr().get(0).getCtrlUnitId();
            }
            if (!prodClsAttrMode.getUpdateProdClsAttr().isEmpty()) {
                prodClsId = prodClsAttrMode.getUpdateProdClsAttr().get(0).getProdClsId();
                unitId = prodClsAttrMode.getUpdateProdClsAttr().get(0).getCtrlUnitId();
            }
            if (!prodClsAttrMode.getDeleteProdClsAttr().isEmpty()) {
                prodClsId = prodClsAttrMode.getDeleteProdClsAttr().get(0).getProdClsId();
                unitId = prodClsAttrMode.getDeleteProdClsAttr().get(0).getCtrlUnitId();
            }
            SysUser us = this.getSessionSysUser(request);
            if (!"A".equalsIgnoreCase(us.getIsType()) && !"AS".equalsIgnoreCase(us.getIsType())) {
                if (!unitId.equals(us.getOwnerId())) {
                    return new JsonResult(JsonResultCode.FAILURE, "不能修改其他组织管理商品", prodClsAttrMode);
                }
            }
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, unitId, us);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            if (prodClsId != -1L) {
                ProdCls cls = new ProdCls();
                cls.setProdClsId(prodClsId);
                cls = prodClsService.findByProdCls(cls);
                if ("se".equalsIgnoreCase(cls.getAuditType())) {
                    return new JsonResult(JsonResultCode.SUCCESS, "修改成功", prodClsAttrService.updateProdAllAttr(prodClsAttrMode));
                }
                return new JsonResult(JsonResultCode.FAILURE, "商品已审核不能修改属性", "商品已审核不能修改属性");
            }
            return new JsonResult(JsonResultCode.FAILURE, "请添加修改商品属性", "请添加修改商品属性");
        } catch (ServiceException ex) {
            log.error("[ProdClsAttrService][FindByProdClsId] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][updateProdClsAttr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * 商品品种 自定义商品属性修改
     *
     * @param prodClsAttr
     * @return
     */
    @SystemLog(name = "修改商品是否上传属性")
    @RequestMapping(value = "/updateProdClsAttrHtml", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateProdClsAttrHtml(@RequestBody ProdClsAttr prodClsAttr, HttpServletRequest request) {
        try {
            SysUser us = this.getSessionSysUser(request);
            if (!"A".equalsIgnoreCase(us.getIsType()) && !"AS".equalsIgnoreCase(us.getIsType())) {
                if (!prodClsAttr.getCtrlUnitId().equals(us.getOwnerId())) {
                    return new JsonResult(JsonResultCode.FAILURE, "不能修改其他组织管理商品", prodClsAttr);
                }
            }
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, prodClsAttr.getCtrlUnitId(), us);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            ProdCls cls = new ProdCls();
            cls.setProdClsId(prodClsAttr.getProdClsId());
            cls = prodClsService.findByProdCls(cls);
            int a = prodClsAttrService.updateProdClsAttr(prodClsAttr);
            if ("am".equalsIgnoreCase(cls.getAuditType()) && "UPLOAD_HM".equalsIgnoreCase(prodClsAttr.getAttrType())) {
                MessageObject msg = null;
                //上传商品且推送添加商品消息
                if (CommonFainl.FALSE.equalsIgnoreCase(prodClsAttr.getUpdateAttrVal()) && CommonFainl.TRUE.equalsIgnoreCase(prodClsAttr.getAttrVal())) {
                    msg = productClsAuditSerivce.productClsHtml(cls, prodClsAttr);
                    msg.setRequestMessage("添加商品");
                    log.info("添加商品消息");
                }
                //不上传商品且推送下架商品消息
                if (CommonFainl.TRUE.equalsIgnoreCase(prodClsAttr.getUpdateAttrVal()) && CommonFainl.FALSE.equalsIgnoreCase(prodClsAttr.getAttrVal())) {
                    msg = productClsAuditSerivce.productClsHtml(cls, prodClsAttr);
                    msg.setRequestMessage("商品下架");
                    log.info("下架消息");
                }
                if (msg != null) {
                    sendSerivce.send("exh.master.up", "master.up", msg);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "商品是否上传属性修改成功", a);
        } catch (ServiceException ex) {
            log.error("[ProdClsAttrService][FindByProdClsId] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][updateProdClsAttr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * 生成商品品种代码
     *
     * @param prodClsVo
     * @return
     */
    @RequestMapping(value = "/creatProdClsCode", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult creatProdClsCode(@RequestBody ProdClsVo prodClsVo, HttpServletRequest request) {
        try {
            ProdCls cls = new ProdCls();
            BeanUtils.copyProperties(prodClsVo, cls);
            String code = prodClsAndProdcutUtils.creatPrcoClsId(cls, this.getSessionSysUser(request));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.CODESTUS, code);
        } catch (Exception ex) {
            log.error("[ProductCatController][creatProdClsCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.CODEFIANL, "");
        }
    }


    /**
     * 查询商品品种必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getProdClsFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getProdClsFile() {
        try {
            List<String> tableNames = new ArrayList<>();
            tableNames.add(tableName);
            tableNames.add("unit_prod_cls");
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, tableService.selectTableFile(tableNames));
        } catch (ServiceException ex) {
            log.error("[TableService][selectTableFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][getProdClsFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询用户不能修改商品品种字段
     *
     * @return
     */
    @RequestMapping(value = "/getUpdateProdClsFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUpdateProdClsFile(HttpServletRequest request) {
        try {
            Map<String, Object> map = fileUpdateUtils.getTableFlie(new ProdCls(), "prod_cls");
            List<String> list = fileUpdateUtils.noUpdateFile(map, this.getSessionSysUser(request));
            List<TableFile> at = new ArrayList<>();
            for (String li : list) {
                TableFile t = new TableFile();
                t.setTableFlie(li);
                at.add(t);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (Exception ex) {
            log.error("[ProductCatController][getProdClsFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 功能描述: 审核商品品种(同时判断并发送消息)  支持批量审核
     * 1. 判断哪些组织拥审核商品权限
     * 2. 审核相应商品
     * 3. 生成消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 11:49
     */
    @SystemLog(name = "审核商品")
    @RequestMapping(value = "/auditTypeProdCls", method = {RequestMethod.POST})
    public JsonResult auditTypeProdCls(@RequestBody ProdClsModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            List<ProdClsModel> ms = new ArrayList<>();
            /**这里没必要考虑审核不同组织商品，应为搜索是按组织划分的*/
            if (CollectionUtils.isNotEmpty(model.getProdClsModels())) {
                for (ProdClsModel m : model.getProdClsModels()) {
                    //普通用户不能
                    if (!"A".equalsIgnoreCase(user.getIsType()) && !"AS".equalsIgnoreCase(user.getIsType())) {
                        if (!m.getCtrlUnitId().equals(user.getOwnerId())) {
                            return new JsonResult(JsonResultCode.FAILURE, "不能审核其他组织商品", m);
                        }
                    }
                    PrivIdExamine examine = privUtils.isUnitPriv("auditTypeProdCls", "商品审核权限", m.getCtrlUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), m);
                    }
                    if (!prodClsService.isAuditType(m)) {
                        return new JsonResult(JsonResultCode.SUCCESS, "商品状态不能被审核，或该商品没有商品明细", m);
                    }
                }
                //修改审核商品状态并返回增加商品消息对象
                List<MessageObject> list = productClsAuditSerivce.productClsAudit(model);
                if (CollectionUtils.isNotEmpty(list)) {
                    list.forEach(s -> s.setRequestMessage("ERP-2B增加商品"));
                    //发送消息
                    list.stream().forEach(s ->
                            sendSerivce.send("exh.master.up", "master.up", s)
                    );

                    if (parameterString.UploginCwms()) {
                        //CWMS-推送
                        for (ProdClsModel m : model.getProdClsModels()) {
                            ProdCls p = new ProdCls();
                            p.setProdClsId(m.getProdClsId());
                            uploadingService.sendCwmsItme(uploadingService.createItems(p, "ADD", user), user);
                        }
                    }

                    return new JsonResult(JsonResultCode.SUCCESS, "审核商品成功", 0);
                }
                return new JsonResult(JsonResultCode.SUCCESS, "审核商品成功无需上传商品", 0);
            }
            return new JsonResult(JsonResultCode.FAILURE, "请添加审核商品", 0);
        } catch (ServiceException ex) {
            log.error("[ProductClsAuditSerivce][productClsAuditSerivce] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][auditTypeProdCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "审核异常:ProductClsController.auditTypeProdCls()", "");
        }
    }


    /**
     * 功能描述: 反审商品品种(同时发送手动下架消息)  支持批量反审
     * 1. 判断组织用户在组织拥反审商品权限
     * 2. 审核相应商品
     * 3. 生成消息对象发送消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 11:49
     */
    @SystemLog(name = "反审商品")
    @RequestMapping(value = "/notAuditTypeProdCls", method = {RequestMethod.POST})
    public JsonResult notAuditTypeProdCls(@RequestBody ProdClsModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);

            if (CollectionUtils.isNotEmpty(model.getProdClsModels())) {
                for (ProdClsModel m : model.getProdClsModels()) {
                    //普通用户不能反审其他组织商品
                    if (!"A".equalsIgnoreCase(user.getIsType()) && !"AS".equalsIgnoreCase(user.getIsType())) {
                        if (!m.getCtrlUnitId().equals(user.getOwnerId())) {
                            return new JsonResult(JsonResultCode.FAILURE, "不能反审其他组织商品", m);
                        }
                    }
                    System.out.println(m.getIsAudit());
                    PrivIdExamine examine = privUtils.isUnitPriv("notAuditTypeProdCls", "商品反审权限", m.getCtrlUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), m);
                    }
                    if (!prodClsService.getNotAuditType(m)) {
                        return new JsonResult(JsonResultCode.FAILURE, "商品不能被反审", m);
                    }
                }
                //反审审核商品状态并返回审核对象
                List<MessageObject> list = productClsAuditSerivce.productClsNotAudit(model, 5);
                if (CollectionUtils.isNotEmpty(list)) {
                    list.forEach(s -> s.setRequestMessage("ERP-2B下架商品"));
                    //反审发送手动下架消息
                    list.stream().forEach(s -> sendSerivce.send("exh.master.up", "master.up", s));
                }
                return new JsonResult(JsonResultCode.SUCCESS, "商品反审成功", 0);
            }
            return new JsonResult(JsonResultCode.FAILURE, "请添加反审商品", 0);
        } catch (ServiceException ex) {
            log.error("[ProductClsAuditSerivce][productClsNotAudit] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][auditTypeProdCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "反审核异常:ProductClsController.auditTypeProdCls()", "");
        }
    }

    /**
     * 商品禁用
     */
    @RequestMapping(value = "/Disable", method = {RequestMethod.POST})
    public JsonResult prodclsDisableRecovery(@RequestBody ProdClsDisable model, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (model.getProdClsModels().size() > 0) {
                for (ProdClsDisable m : model.getProdClsModels()) {
                    if (!"A".equalsIgnoreCase(user.getIsType()) && !"AS".equalsIgnoreCase(user.getIsType())) {
                        if (!m.getCtrlUnitId().equals(user.getOwnerId())) {
                            return new JsonResult(JsonResultCode.FAILURE, "不能禁用其他组织商品", model);
                        }
                    }
                    PrivIdExamine examine;
                    //禁用
                    examine = privUtils.isUnitPriv("disableProdCls", "商品禁用权限", m.getCtrlUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), m);
                    }
                    //已经审核过的推送下架  其实这里 会造成 反审推送一条下架 反审后禁用又推送一次下架
                    //修改状态 获取消息对象
                    MessageObject msg = productClsAuditSerivce.productClsSellstate(m);
                    if (m.getIsAudit() == 1) {
                        if (msg != null) {
                            msg.setRequestMessage("ERP-2B禁用商品");
                            sendSerivce.send("exh.master.up", "master.up", msg);
                        }
                    }
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "商品禁用成功", 0);
        } catch (ServiceException ex) {
            log.error("[PodClsService][isAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][prodclsDisableRecovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "商品禁用异常:ProductClsController.prodclsDisableRecovery()", "");
        }
    }


    /**
     * 商品禁用恢复
     */
    @RequestMapping(value = "/Recovery", method = {RequestMethod.POST})
    public JsonResult prodclsRecovery(@RequestBody ProdClsDisable model, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (model.getProdClsModels().size() > 0) {
                for (ProdClsDisable m : model.getProdClsModels()) {
                    if (!"A".equalsIgnoreCase(user.getIsType()) && !"AS".equalsIgnoreCase(user.getIsType())) {
                        if (!m.getCtrlUnitId().equals(user.getOwnerId())) {
                            return new JsonResult(JsonResultCode.FAILURE, "不能禁用恢复其他组织商品", model);
                        }
                    }

                    //恢复
                    PrivIdExamine examine = privUtils.isUnitPriv("recoveryProdCls", "商品禁用恢复权限", m.getCtrlUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), m);
                    }
                    ProdClsWithBLOBs cls = new ProdClsWithBLOBs();
                    cls.setProdClsCode(m.getProdClsCode());
                    cls.setProdClsId(m.getProdClsId());
                    cls.setProdStatus(CommonFainl.USER_STATUS);
                    prodClsService.updateProductCls(cls, user);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "商品禁用恢复成功", 0);
        } catch (ServiceException ex) {
            log.error("[PodClsService][isAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][prodclsDisableRecovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "商品禁用恢复异常:ProductClsController.prodclsDisableRecovery()", "");
        }
    }


    private ProdClsModel creatProdClsMode(Long prodClsId, String prodClsCode) {
        ProdClsModel ms = new ProdClsModel();
        ms.setProdClsId(prodClsId);
        ms.setProdClsCode(prodClsCode);
        List<ProdClsModel> modes = new ArrayList<>();
        modes.add(ms);
        ms.setProdClsModels(modes);
        return ms;
    }

    /**
     * 查询商品能否审核
     */
    @RequestMapping(value = "/getAuditType", method = {RequestMethod.POST})
    public JsonResult getAuditType(@RequestBody ProdClsModel model, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            if (prodClsService.isAuditType(model)) {
                return new JsonResult(JsonResultCode.SUCCESS, "商品状态能被审核", 0);
            }
            return new JsonResult(JsonResultCode.FAILURE, "商品状态不能被审核，或该商品没有商品明细", model);
        } catch (ServiceException ex) {
            log.error("[PodClsService][isAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][auditTypeProdCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商品能否审核异常:ProductClsController.getAuditType()", "");
        }
    }

    /**
     * 查询商品按钮状态
     */
    @RequestMapping(value = "/prodClsAuditType", method = {RequestMethod.POST})
    public JsonResult prodClsAuditType(@RequestBody ProdCls model) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prodClsService.initButtons(model));
        } catch (ServiceException ex) {
            log.error("[PodClsService][isAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][auditTypeProdCls] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商品能否审核异常:ProductClsController.getAuditType()", "");
        }
    }


    /**
     * 商品库存管理禁用与启用
     */
    @RequestMapping(value = "/stkAdopte", method = {RequestMethod.POST})
    public JsonResult prodClsstkAdopte(@RequestBody ProdClsModel model, HttpServletRequest re) {
        try {
            SysUser user = this.getSessionSysUser(re);
            System.out.println(model.getProdClsModels());
            if (CollectionUtils.isNotEmpty(model.getProdClsModels())) {
                for (ProdClsModel m : model.getProdClsModels()) {
                    PrivIdExamine examine = privUtils.isUnitPriv("ProdClsStkAdopte", "商品库存管理设置权限", m.getCtrlUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), model);
                    }
                    if (CommonFainl.TRUE.equalsIgnoreCase(m.getStkAdopted())) {
                        if (prodClsService.selectProdClsStkAdopte(m.getProdClsId())) {
                            ProdCls cls = new ProdCls();
                            cls.setProdClsId(m.getProdClsId());
                            cls.setStkAdopted(CommonFainl.FALSE);
                            prodClsService.updateByProdCls(cls);
                            return new JsonResult(JsonResultCode.SUCCESS, "商品库存管理禁用成功", 0);
                        } else {
                            return new JsonResult(JsonResultCode.FAILURE, "仓库中已存在该商品不能禁用库存管理", 0);
                        }
                    }
                    if (CommonFainl.FALSE.equalsIgnoreCase(m.getStkAdopted())) {
                        ProdCls cls = new ProdCls();
                        cls.setProdClsId(m.getProdClsId());
                        cls = prodClsService.findByProdCls(cls);
                        if ("F".equalsIgnoreCase(cls.getStkAdopted())) {
                            cls.setStkAdopted(CommonFainl.TRUE);
                            cls.setProdClsId(m.getProdClsId());
                            prodClsService.updateByProdCls(cls);
                            return new JsonResult(JsonResultCode.SUCCESS, "商品库存管理启用成功", 0);
                        }
                    }
                }
            }
            return new JsonResult(JsonResultCode.FAILURE, "尚未选中商品", 0);
        } catch (ServiceException ex) {
            log.error("[PodClsService][isAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][prodClsstkAdopte] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "商品库存管理设置异常:ProductClsController.prodClsstkAdopte()", "");
        }
    }


    /**
     * 查询用户查看组织列表
     */
    @PostMapping(value = "/clsList")
    public JsonResult getlist(@RequestBody UnitDomainVo vo, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            /**
             * 目前遇到BUG  再别地方修改信息后 回来查询此接口，用户user.getUnit().getUnitHierarchy() 为空
             * 因此手动查询赋值
             * */
            if (user.getUnit() == null || user.getUnit().getUnitHierarchy() == null){
                user.setUnit(unitService.selectByPrimaryKey(user.getOwnerId()));
            }
            Set<UnitDomainVo> set = uerScopeServer.getUnitClsScope(user, vo);
            if (CollectionUtils.isNotEmpty(set)){
                m:
                for (UnitDomainVo v : set){
                    if (v.getUnitId().equals(user.getOwnerId())){
                        set.remove(v);
                        break m;
                    }
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", RestulMap.getResut(set));
        } catch (ServiceException ex) {
            log.error("[UserPrivService][getlist] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserRoleController][getlist] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


}
