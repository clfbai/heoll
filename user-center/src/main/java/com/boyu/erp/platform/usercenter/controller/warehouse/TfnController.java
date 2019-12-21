package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitHier;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Tfn;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.TfnTypeMapper;
import com.boyu.erp.platform.usercenter.model.goods.ProductModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitHierService;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnService;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnTypeService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.TfnUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsGoodsVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform_text
 * @description: 调拨单控制层
 * @author: clf
 * @create: 2019-06-25 10:26
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/tfn")
public class TfnController extends BaseController {
    @Autowired
    private TfnService tfnService;

    @Autowired
    private SysCodeDtlService sysCodeDtlService;

    @Autowired
    private TfnTypeService tfnTypeService;

    @Autowired
    private SysUnitHierService sysUnitHierService;

    @Autowired
    private TfnUtil tfnUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehService warehService;
    @Autowired
    private TfnTypeMapper tfnTypeMapper;

    @Autowired
    private SysParameterService parameterService;
    /**
     * 查询调拨单列表
     *
     * @param tfn
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult tfnTypeAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 Tfn tfn, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (user  != null) {
                tfn.setUnitId(user.getUnit().getUnitId());
            }
            //tfn.setTfnNum(request.getParameter("tfnNum"));

            //单据日期范围查询
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String docDateFrom = request.getParameter("docDateFromStr");
            String docDateTo = request.getParameter("docDateToStr");
            if (StringUtils.isNotEmpty(docDateFrom)) {
                tfn.setDocDateFrom(dateFormat.parse(docDateFrom));
            }
            if (StringUtils.isNotEmpty(docDateTo)) {
                tfn.setDocDateTo(dateFormat.parse(docDateTo));
            }

            PageInfo<TfnVo> pageInfo = tfnService.getTfnList(page, size, tfn);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][tfnTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询当前选中记录
     */
    @RequestMapping(value = "/upSelectTfn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult tfnTypeAll(@RequestBody Tfn tfn, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (user  != null) {
                tfn.setUnitId(user.getUnit().getUnitId());
            }
            TfnVo vo = tfnService.getTfnVoByPk(tfn);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vo);
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][tfnTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 获取查询时需要的下拉框option
     * @return
     */
    @RequestMapping(value = "/getQueryOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getQueryOption() {
        try {
            Map<String, List<OptionVo>> map = new HashMap<>();
            /**
             * 获取调拨单进度option
             */
            List<OptionVo> tfnProg = new ArrayList<>();
            SysCodeDtl tfnProgDel = new SysCodeDtl();
            tfnProgDel.setCodeType("TFN_PROG");
            List<SysCodeDtl> tfnProgDelList = sysCodeDtlService.getAll(tfnProgDel);
            for (SysCodeDtl _sysCodeDtl: tfnProgDelList) {
                tfnProg.add(new OptionVo(_sysCodeDtl.getDescription(), _sysCodeDtl.getCode()));
            }

            map.put("tfnProg", tfnProg);

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }

    /**
     * 获取创建调拨单时所需下拉框option
     * @return
     */
    @RequestMapping(value = "/getCreateOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getCreateOption() {
        try {
            Map<String, List<OptionVo>> map = new HashMap<>();

            /**
             * 获取调拨单类别option
             */
            List<OptionVo> tfnType = new ArrayList<>();
            List<TfnType> tfnTypeList = tfnTypeMapper.getAll(new TfnType());
            for (TfnType _tfnType: tfnTypeList) {
                tfnType.add(new OptionVo(_tfnType.getDescription(), _tfnType.getTfnType()));
            }

            /**
             * 获取组织层次option
             */
            List<OptionVo> unitHier = new ArrayList<>();
            List<SysUnitHier> unitHierList = sysUnitHierService.getSysUnitHierList(new SysUnitHier());
            for (SysUnitHier _sysUnitHier: unitHierList) {
                unitHier.add(new OptionVo(_sysUnitHier.getUnitHierName(), _sysUnitHier.getUnitHierId()));
            }

            /**
             * 获取送货方式option
             */
            List<OptionVo> delivMthd = new ArrayList<>();
            SysCodeDtl delivMthdDel = new SysCodeDtl();
            delivMthdDel.setCodeType("DELIV_MTHD");
            List<SysCodeDtl> delivMthdDelList = sysCodeDtlService.getAll(delivMthdDel);
            for (SysCodeDtl _sysCodeDtl: delivMthdDelList) {
                delivMthd.add(new OptionVo(_sysCodeDtl.getDescription(), _sysCodeDtl.getCode()));
            }

            /**
             * 获取启用配码option
             */
            List<OptionVo> bxiEnabled = new ArrayList<>();
            bxiEnabled.add(new OptionVo("是", "T"));
            bxiEnabled.add(new OptionVo("否", "F"));

            /**
             * 获取差异裁定方option
             */
            List<OptionVo> drDiffJgd= new ArrayList<>();
            drDiffJgd.add(new OptionVo("发货方", "D"));
            drDiffJgd.add(new OptionVo("收货方", "F"));

            map.put("tfnType", tfnType);
            map.put("unitHier", unitHier);
            map.put("delivMthd", delivMthd);
            map.put("bxiEnabled", bxiEnabled);
            map.put("drDiffJgd", drDiffJgd);

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }

    /**
     * 删除调拨单
     * @param tfn
     * @param re
     * @return
     */
    @SystemLog(name = "删除调拨单")
    @RequestMapping(value = "/deleteTfn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteTfn(@RequestBody Tfn tfn, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfn.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnService.delete(tfn));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 创建调拨单
     * @param tfn
     * @param re
     * @return
     */
    @SystemLog(name = "创建调拨单")
    @RequestMapping(value = "/createTfn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult createTfn(@RequestBody Tfn tfn, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfn.setUnitId(user.getUnit().getUnitId());
                tfn.setOprId(user.getPrsnl().getPrsnlId());
            }
            tfn.setTfnNum(tfnUtil.generateTfnNum(tfn));
            tfn.setProgress("PG");
            //准备基础数据
            tfnUtil.prepareTfnInfo(tfn);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnService.insert(tfn));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }


    /**
     * 获取仓库时所需下拉框option
     * @return
     */
    @RequestMapping(value = "/getWarehOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getWarehOption() {
        try {
            Map<String, List<OptionVo>> map = new HashMap<>();


            /**
             * 获取组织状态option
             */
            List<OptionVo> unitStatus = new ArrayList<>();
            unitStatus.add(new OptionVo("活动", "A"));
            unitStatus.add(new OptionVo("非活动", "D"));


            map.put("unitStatus", unitStatus);

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }

    /**
     * 查询仓库
     * @param warehVo
     * @param re
     * @return
     */
    @SystemLog(name = "查询仓库")
    @RequestMapping(value = "/getWarehList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getWarehList(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size,
                                   WarehVo warehVo, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",
                    warehService.selectWareh(page,size,warehVo,user));
        } catch (ServiceException ex) {
            log.error("[TfnService][updateTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][updateTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 获取商品列表
     * @param page
     * @param size
     * @param productVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/product/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult tfnTypeAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 ProductModel productVo, HttpServletRequest request) {
        try {

            SysUser user = (SysUser) this.isNullUser(request);
            PageInfo<ProdClsGoodsVo> pageInfo = productService.getProdcutAll(page, size, productVo, user);
            List<ProdClsGoodsVo> list = pageInfo.getList();
            SysParameter sysParameter = parameterService.findByParameter("TRANSFER_PRICE_SOURCE");
            for (ProdClsGoodsVo _prodClsGoods : list) {
                if ("RP".equals(sysParameter.getParmVal())) {
                    _prodClsGoods.setRtUnitPrice(_prodClsGoods.getRtUnitPrice());
                } else if ("PP".equals(sysParameter.getParmVal())) {
                    _prodClsGoods.setRtUnitPrice(_prodClsGoods.getPuUnitPrice());
                } else if ("SP".equals(sysParameter.getParmVal())) {
                    _prodClsGoods.setRtUnitPrice(_prodClsGoods.getWsUnitPrice());
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][tfnTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    @RequestMapping(value = "/product/returnProductWithPrice", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult returnProductWithPrice(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 @RequestBody ProductModel model, HttpServletRequest request) {
        try {

            SysUser user = (SysUser) this.isNullUser(request);
            PageInfo<ProdClsGoodsVo> pageInfo = productService.getProdcutAll(page, size,  model, user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][tfnTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询所有仓库
     * @param tfn
     * @param re
     * @return
     */
    @SystemLog(name = "查询所有仓库")
    @RequestMapping(value = "/updateTfn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getWarehList(@RequestBody Tfn tfn, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfn.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnService.update(tfn));
        } catch (ServiceException ex) {
            log.error("[TfnService][updateTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][updateTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

}
