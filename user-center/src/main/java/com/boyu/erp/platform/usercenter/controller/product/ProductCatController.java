package com.boyu.erp.platform.usercenter.controller.product;

import com.alibaba.fastjson.JSON;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.constants.TreeData;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCat;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.ProdCatService;
import com.boyu.erp.platform.usercenter.utils.common.ProdKeyValue;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类
 */
@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductCatController extends BaseController {

    @Autowired
    private ProdCatService prodCatService;

    /**
     * 查询商品分类 树形结构
     *
     * @param prodCat
     * @return
     */
    @RequestMapping(value = "/cat/tree", method = {RequestMethod.POST, RequestMethod.GET})
    public Object catTree(ProdCat prodCat) {
        try {
            log.info("[ProductCatController][catTree] 请求参数为：{}", JSON.toJSONString(prodCat));

            List<ProdCat> prodCats = prodCatService.selectByParentId(prodCat);

            //组装前端需要的json格式
            TreeData treeData = new TreeData(200, "success");
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("code", 0);
            jsonMap.put("msg", "success");
            jsonMap.put("status", treeData);
            jsonMap.put("data", prodCats);
            return jsonMap;
        } catch (ServiceException ex) {
            log.error("[ProductCatController][catTree] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][catTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 查询商品分类 树形结构
     *
     * @param prodCat
     * @return
     */
    @RequestMapping(value = "/cat/treeAll", method = {RequestMethod.POST})
    public Object catTreeAll(@RequestBody ProdCat prodCat) {
        try {
            if (StringUtils.isBlank(prodCat.getProdCatId()) ) {
                prodCat = null;
            }
            List<ProdCat> prodCats = prodCatService.selectTree(prodCat);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prodCats);
        } catch (ServiceException ex) {
            log.error("[ProductCatController][catTree] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][catTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 查询商品分类
     *
     * @param prodCat
     * @return
     */
    @RequestMapping(value = "/select/prodCatId", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult selectProdCatId(@RequestBody ProdCat prodCat) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prodCatService.getProdCatById(prodCat));
        } catch (ServiceException ex) {
            log.error("[ProductCatController][selectProdCatId] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][selectProdCatId] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加商品一级分类
     *
     * @param prodCat
     * @return
     */
    @SystemLog(name = "添加商品一级分类")
    @RequestMapping(value = "/add/prodCatLvl", method = {RequestMethod.POST})
    public JsonResult addprodCatLvl(@RequestBody @Validated ProdCat prodCat) {
        try {
            prodCat.setProdCatLvl((short) 1);
            prodCat.setParnCatId(null);
            int result = prodCatService.insertProdCat(prodCat);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "增加一级分类失败", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductCatController][addProdCat] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][addProdCat] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 添加商品分类 树形结构
     *
     * @param prodCat
     * @return
     */
    @SystemLog(name = "添加商品分类树形结构")
    @RequestMapping(value = "/add/prodCat", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addProdCat(ProdCat prodCat) {
        try {
            int result = prodCatService.insertProdCat(prodCat);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductCatController][addProdCat] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][addProdCat] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改商品分类 树形结构
     *
     * @param prodCat
     * @return
     */
    @SystemLog(name = "修改商品分类树形结构")
    @RequestMapping(value = "/update/prodCat", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateProdCat(ProdCat prodCat) {
        try {
            int result = prodCatService.updateProdCat(prodCat);
            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductCatController][updateProdCat] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][updateProdCat] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除商品分类 树形结构
     *
     * @param prodCat
     * @return
     */
    @SystemLog(name = "删除商品分类树形结构")
    @RequestMapping(value = "/delete/prodCat", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteProdCat(ProdCat prodCat) {
        try {
            int result = prodCatService.deleteProdCat(prodCat);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductCatController][deleteProdCat] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][deleteProdCat] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 查询商品分类下拉树形结构
     *
     * @param prodCat
     * @return
     */
    @RequestMapping(value = "/prodCat/opetion", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult prodCatOpetion(ProdCat prodCat) {
        try {
            if (StringUtils.isBlank(prodCat.getProdCatId())) {
                prodCat.setProdCatLvl((short) 1);
                prodCat.setParnCatId(null);
            }
            List<ProdCat> list = prodCatService.getOpetion(prodCat);
            List<ProdKeyValue> keys = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            if (list.size() > 0) {
                for (ProdCat m : list) {
                    ProdKeyValue ms = new ProdKeyValue(m.getProdCatName(), m.getProdCatId());
                    keys.add(ms);
                }
            }
            map.put("list", keys);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", map);

        } catch (ServiceException ex) {
            log.error("[ProdCatService][getOpetion] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductCatController][deleteProdCat] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


}