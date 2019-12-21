package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.GdnFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.GdnModel;
import com.boyu.erp.platform.usercenter.model.wareh.GdnStbDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.GdnService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDmodeService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/warehouse/gdn")
public class GdnController extends BaseController {

    @Autowired
    private GdnService gdnService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private WarehDmodeService warehDmodeService;

    private static Pattern pattern = Pattern.compile("^(\\-)?\\d+(\\.\\d+)?$");

    /**
     * 根据仓库Id查询出库方式下拉以及必填等字段
     *
     * @return
     */
    @RequestMapping(value = "/gdnpulldown", method = RequestMethod.POST)
    public JsonResult gdnPullDown(@RequestBody GdnFilterModel gdnFilterModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            Map<String, Object> map = gdnService.gdnPullDown(gdnFilterModel.getsUnitId(), sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException e) {
            log.error("[GdnController][queryDelivModeByWarehId] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][queryDelivModeByWarehId] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 根据筛选条件查询列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonResult queryStbListByCon(GdnFilterModel gdnFilterModel, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.queryStbListByCon(gdnFilterModel, page, size, sysUser));
        } catch (ServiceException e) {
            log.error("[GdnController][queryStbListByCon] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][queryStbListByCon] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询列表中的总金额、总数量
     *
     * @author HHe
     * @date 2019/11/18 17:46
     */
    @RequestMapping("/getlisttotal")
    public JsonResult getListTotal(GdnFilterModel gdnFilterModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.getListTotal(gdnFilterModel));
        } catch (ServiceException e) {
            log.error("[GdnController][getListTotal] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][getListTotal] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 根据库存单编号查询库存明细集合
     *
     * @author HHe
     * @date 2019/8/24 10:27
     */
    @RequestMapping(value = "/querystbdtllist", method = RequestMethod.POST)
    public JsonResult queryStbDtlList(@RequestBody GdnFilterModel gdnFilterModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", gdnService.queryStbDtlList(gdnFilterModel, sysUser));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException e) {
            log.error("[GdnController][addGdn] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][addGdn] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加出库单;
     *
     * @author HHe
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResult addGdn(@RequestBody GdnModel gdnModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, gdnService.pageAddGdn(gdnModel, sysUser));
        } catch (ServiceException e) {
            log.error("[GdnController][addGdn] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][addGdn] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 根据仓库编号加载出库方式下拉列表、仓库名称、仓库编号、会计组织代码、会计组织名称
     *
     * @author HHe
     */
    @RequestMapping(value = "/loaddelivmodebywarehcode", method = RequestMethod.GET)
    public JsonResult loadDelivModeByWarehCode(String warehId) {
        try {
            if (warehId == null || "".equals(warehId)) {
                throw new ServiceException("201", "请选择仓库");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.loadDelivModeByWarehCode(Long.valueOf(warehId)));
        } catch (NumberFormatException e) {
            log.error("[GdnController][loadDelivModeByWarehCode] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.FORMAT_ERROR, "");
        } catch (ServiceException e) {
            log.error("[GdnController][loadDelivModeByWarehCode] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][loadDelivModeByWarehCode] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 根据仓库Id、出库方式初始化出库单页面信息
     *
     * @return
     */
    @RequestMapping(value = "/initgdnbydelivmode", method = {RequestMethod.GET})
    public JsonResult initGdnByDelivMode(Long warehId, String delivMode) {
        //根据出库类别的code，把查询到数据注入到gdnvo中，传输到页面；
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.initGdnByDelivMode(warehId, delivMode));
        } catch (ServiceException e) {
            log.error("[GdnController][initGdnByDelivMode] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][initGdnByDelivMode] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加出库单明细（单条）
     *
     * @author HHe
     * @date 2019/11/29 17:15
     */
    @RequestMapping(value = "/addgdnonestbdtl", method = RequestMethod.POST)
    public JsonResult addGdnOneStbDtl(@RequestBody GdnStbDtlModel gdnStbDtlModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            StbDtl stbDtl = judgePackDtl(gdnStbDtlModel);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, gdnService.addGdnOneStbDtl(stbDtl));
        } catch (ServiceException e) {
            log.error("[GdnController][addGdnStbDtl] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][addGdnStbDtl] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 判断输入格式
     *
     * @author HHe
     * @date 2019/12/3 9:45
     */
    private static void judgeNum(String num, String hint) {
        //数字正则表达式：整数、小数、负数
        if (!pattern.matcher(num).matches()) {
            throw new ServiceException("201", "请输入正确格式的" + hint);
        }
    }

    /**
     * 判断输入整数位
     *
     * @author HHe
     * @date 2019/12/5 10:54
     */
    private static void judgeMaxNum(BigDecimal num, int digit, String hint) {
        if (digit != 0) {
            StringBuffer maxNum = new StringBuffer("1");
            for (int i = 0; i < digit; i++) {
                maxNum.append("0");
            }
            if (num.compareTo(new BigDecimal(maxNum.toString())) > 0) {
                throw new ServiceException("201", hint + "整数位不可大于" + digit + "位");
            }
        }
    }

    /**
     * 修改出库单明细（单条）
     *
     * @author HHe
     * @date 2019/12/2 17:57
     */
    @RequestMapping(value = "/updategdnonestbdtl", method = RequestMethod.POST)
    public JsonResult updateGdnOneStbDtl(@RequestBody GdnStbDtlModel gdnStbDtlModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            StbDtl stbDtl = judgePackDtl(gdnStbDtlModel);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, gdnService.updateGdnOneStbDtl(stbDtl));
        } catch (ServiceException e) {
            log.error("[GdnController][updateGdnOneStbDtl] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][updateGdnOneStbDtl] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除出库单明细（单条）
     *
     * @author HHe
     * @date 2019/12/3 10:23
     */
    @RequestMapping(value = "/delgdnonestbdtl", method = RequestMethod.POST)
    public JsonResult delGdnOneStbDtl(@RequestBody GdnStbDtlModel gdnStbDtlModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, gdnService.delGdnOneStbDtl(gdnStbDtlModel));
        } catch (ServiceException e) {
            log.error("[GdnController][delGdnOneStbDtl] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][delGdnOneStbDtl] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * Assist
     * 判断封装出库单明细中的数字
     *
     * @author HHe
     * @date 2019/12/2 17:56
     */
    private StbDtl judgePackDtl(GdnStbDtlModel gdnStbDtlModel) {
        //判断数量
        judgeNum(gdnStbDtlModel.getQty(), "数量");
        //判断单价
        judgeNum(gdnStbDtlModel.getUnitPrice(), "单价");
        //判断折率
        judgeNum(gdnStbDtlModel.getDiscRate(), "折率");
        //判断税率
        judgeNum(gdnStbDtlModel.getTaxRate(), "税率");
        //转成BigDecimal判断负数
        //数量
        BigDecimal qty = new BigDecimal(gdnStbDtlModel.getQty());
        //单价
        BigDecimal unitPrice = new BigDecimal(gdnStbDtlModel.getUnitPrice());
        //折率
        BigDecimal discRate = new BigDecimal(gdnStbDtlModel.getDiscRate());
        //税率
        BigDecimal taxRate = new BigDecimal(gdnStbDtlModel.getTaxRate());
        //判断位数
        judgeMaxNum(qty, 8, "数量");
        judgeMaxNum(unitPrice, 8, "单价");
        judgeMaxNum(discRate, 1, "折率");
        judgeMaxNum(taxRate, 1, "税率");
        StbDtl stbDtl = new StbDtl();
        //数量不可负数
        if (qty.compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException("201", "数量不可小于0");
        }
        if (discRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException("201", "折率不可小于0");
        }
        //小数点四舍五入
        //数量：舍弃小数点四舍五入
        qty = qty.setScale(0, BigDecimal.ROUND_HALF_UP);
        //单价
        unitPrice = unitPrice.setScale(4, BigDecimal.ROUND_HALF_UP);
        //折率
        discRate = discRate.setScale(4, BigDecimal.ROUND_HALF_UP);
        //税率
        taxRate = taxRate.setScale(4, BigDecimal.ROUND_HALF_UP);
        stbDtl.setStbNum(gdnStbDtlModel.getStbNum());
        stbDtl.setUnitId(gdnStbDtlModel.getUnitId());
        stbDtl.setProdId(gdnStbDtlModel.getProdId());
        stbDtl.setQty(qty);
        stbDtl.setUnitPrice(unitPrice);
        stbDtl.setDiscRate(discRate);
        stbDtl.setTaxRate(taxRate);
        stbDtl.setRemarks(gdnStbDtlModel.getRemarks());
        return stbDtl;
    }


    /**
     * 根据商品代码判断能否添加并且显示信息
     *
     * @author HHe
     * @date 2019/8/27 11:53
     */
    @RequestMapping(value = "/queryprovobyprocode", method = RequestMethod.POST)
    public JsonResult queryProVoByProCode(@RequestBody StbDtlVo stbDtlVo, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.queryProVoByProCode(stbDtlVo, sysUser));
        } catch (ServiceException e) {
            log.error("[GdnController][queryProVoByProCode] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][queryProVoByProCode] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询明细中金额等字段是否可以修改（出库方式：固定单价）
     *
     * @author HHe
     * @date 2019/11/29 14:17
     */
    @RequestMapping(value = "/queryfixedunitprice", method = RequestMethod.POST)
    public JsonResult queryFixedUnitPrice(@RequestBody WarehDmode warehDmode, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehDmodeService.queryFixedUnitPriceByObj(warehDmode));
        } catch (ServiceException e) {
            log.error("[GdnController][queryFixedUnitPrice] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][queryFixedUnitPrice] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 根据用户输入数量计算库存明细数据
     *
     * @author HHe
     * @date 2019/8/28 20:22
     */
    @RequestMapping(value = "/getdtlmess", method = RequestMethod.POST)
    public JsonResult getDtlMess(@RequestBody StbDtlVo stbDtlVo, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, stbDtlService.getDtlMess(stbDtlVo, sysUser));
        } catch (ServiceException e) {
            log.error("[GdnController][getDtlMess] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][getDtlMess] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

//    /**
//     * 出库单明细商品弹窗
//     *
//     * @author HHe
//     * @date 2019/12/5 17:23
//     */
//    @RequestMapping(value = "/dtlPopup", method = RequestMethod.GET)
//    public JsonResult dtlPopup(GoodsPopupFilterModel goodsPopupFilterModel, @RequestParam(defaultValue = "0") Integer page,
//                               @RequestParam(defaultValue = "1000") Integer size, HttpServletRequest request) {
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if (sysUser == null) {
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.dtlPopup(page, size,goodsPopupFilterModel));
//        } catch (ServiceException e) {
//            log.error("[GdnController][dtlPopup] ServiceException", e);
//            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
//        } catch (Exception e) {
//            log.error("[GdnController][dtlPopup] Exception", e);
//            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
//        }
//    }


    /**
     * 修改出库单
     *
     * @param gdnModel
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult updateGdn(@RequestBody GdnModel gdnModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, gdnService.updateGdn(gdnModel, sysUser));
        } catch (ServiceException e) {
            log.error("[GdnController][updateGdn] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][updateGdn] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除出库单
     *
     * @param gdnModel
     * @param request
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public JsonResult delGdn(@RequestBody GdnModel gdnModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, gdnService.delGdn(gdnModel, sysUser));
        } catch (ServiceException e) {
            log.error("[GdnController][delGdn] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][delGdn] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 查询出库单能否删除、修改
     *
     * @author HHe
     * @date 2019/11/29 16:06
     */
    @RequestMapping(value = "/judgedelandupd", method = RequestMethod.POST)
    public JsonResult judgeDelAndUpd(@RequestBody GdnModel gdnModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.judgeDelAndUpd(gdnModel));
        } catch (ServiceException e) {
            log.error("[GdnController][judgeDelAndUpd] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][judgeDelAndUpd] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 出库单功能列表展示
     *
     * @author HHe
     * @date 2019/10/15 15:15
     */
    @RequestMapping(value = "/funlist", method = RequestMethod.POST)
    public JsonResult funList(@RequestBody GdnModel gdnModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, gdnService.queryFunList(gdnModel));
        } catch (ServiceException e) {
            log.error("[GdnController][funList] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][funList] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 出库单功能操作
     *
     * @author HHe
     * @date 2019/10/15 17:05
     */
    @RequestMapping(value = "/gdnfunction/{type}", method = RequestMethod.POST)
    public JsonResult gdnFunction(@RequestBody GdnModel gdnModel, @PathVariable String type, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS, delivUtil.dynamicRequest("gdnServiceImpl", type, gdnModel, sysUser));
        } catch (ServiceException e) {
            log.error("[GdnController][gdnFunction] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[GdnController][gdnFunction] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.FUNCTIONOPFANS, "");
        }
    }
//    /**
//     * 出库单功能操作：确认
//     * @author HHe
//     * @date 2019/10/15 17:05
//     */
//    @RequestMapping(value = "/gdnfunction/confirm",method = RequestMethod.POST)
//    public JsonResult confirm(@RequestBody GdnModel gdnModel,HttpServletRequest request){
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,gdnService.confirm(gdnModel,sysUser));
//        } catch (ServiceException e) {
//            log.error("[GdnController][confirm] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[GdnController][confirm] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
//        }
//    }
//    /**
//     * 出库单功能操作：重做
//     * @author HHe
//     * @date 2019/10/15 17:05
//     */
//    @RequestMapping(value = "/gdnfunction/redo",method = RequestMethod.POST)
//    public JsonResult redo(@RequestBody GdnModel gdnModel,HttpServletRequest request){
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,gdnService.redo(gdnModel,sysUser));
//        } catch (ServiceException e) {
//            log.error("[GdnController][redo] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[GdnController][redo] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
//        }
//    }
//    /**
//     * 出库单功能操作：挂起
//     * @author HHe
//     * @date 2019/10/15 17:05
//     */
//    @RequestMapping(value = "/gdnfunction/suspend",method = RequestMethod.POST)
//    public JsonResult suspend(@RequestBody GdnModel gdnModel,HttpServletRequest request){
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,gdnService.suspend(gdnModel,sysUser));
//        } catch (ServiceException e) {
//            log.error("[GdnController][suspend] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[GdnController][suspend] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
//        }
//    }
//    /**
//     * 出库单功能操作：恢复
//     * @author HHe
//     * @date 2019/10/15 17:05
//     */
//    @RequestMapping(value = "/gdnfunction/resume",method = RequestMethod.POST)
//    public JsonResult resume(@RequestBody GdnModel gdnModel,HttpServletRequest request){
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,gdnService.resume(gdnModel,sysUser));
//        } catch (ServiceException e) {
//            log.error("[GdnController][resume] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[GdnController][resume] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
//        }
//    }
//    /**
//     * 出库单功能操作：作废
//     * @author HHe
//     * @date 2019/10/15 17:05
//     */
//    @RequestMapping(value = "/gdnfunction/abolish",method = RequestMethod.POST)
//    public JsonResult abolish(@RequestBody GdnModel gdnModel,HttpServletRequest request){
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,gdnService.abolish(gdnModel,sysUser));
//        } catch (ServiceException e) {
//            log.error("[GdnController][abolish] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[GdnController][abolish] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
//        }
//    }
//    /**
//     * 出库单功能操作：出库
//     * @author HHe
//     * @date 2019/10/15 17:05
//     */
//    @RequestMapping(value = "/gdnfunction/deliver",method = RequestMethod.POST)
//    public JsonResult deliver(@RequestBody GdnModel gdnModel,HttpServletRequest request){
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,gdnService.deliver(gdnModel,sysUser));
//        } catch (ServiceException e) {
//            log.error("[GdnController][deliver] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[GdnController][deliver] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
//        }
//    }
//    /**
//     * 出库单功能操作：取消出库
//     * @author HHe
//     * @date 2019/10/15 17:05
//     */
//    @RequestMapping(value = "/gdnfunction/reverseDeliver",method = RequestMethod.POST)
//    public JsonResult reverseDeliver(@RequestBody GdnModel gdnModel,HttpServletRequest request){
//        try {
//            SysUser sysUser = this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,gdnService.reverseDeliver(gdnModel,sysUser));
//        } catch (ServiceException e) {
//            log.error("[GdnController][reverseDeliver] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[GdnController][reverseDeliver] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
//        }
//    }
}
