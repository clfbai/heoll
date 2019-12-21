package com.boyu.erp.platform.usercenter.controller.option;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.SpecGrp;
import com.boyu.erp.platform.usercenter.entity.SpecScp;
import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.goods.ProductModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.*;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehOptionSerivce;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.system.TreeKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商品品种下拉接口
 * @auther: CLF
 * @date: 2019/7/17 18:29
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProdClsOptinController extends BaseController {
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private SpecGrpService specGrpService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SpecScpService specScpService;
    @Autowired
    private WarehOptionSerivce warehOptionSerivce;
    @Autowired
    private ColorService colorService;
    @Autowired
    private ProdClsUtils prodClsUtils;


    @Autowired
    private SpecService specService;

    /**
     * 商品品种 基本下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/basic", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionBasic() {
        try {

            List<String> list = prodClsUtils.getListString();
            //分颜色
            list.add("multiColor");
            //分规格
            list.add("multiSpec");
            //分罩杯
            list.add("multiEdition");

            Map<String, Object> map = prodClsUtils.getMapOffOn(list);


            //初始化所有规格
            //List<Spec> singleSpec = this.specService.getAll();
            //初始化所有颜色下拉选择

            /*List<OptionVo> colors = new ArrayList<>();
            if (colorService.getOpentin().size() > 0) {
                for (Color m : colorService.getOpentin()) {
                 colors.add(new OptionVo(m.getColorName(), m.getColorCode()));
                }
            }*/
            //规格范围下拉
            List<OptionVo> optionVos2 = new ArrayList<OptionVo>();
            List<SpecScp> resultList2 = specScpService.getSelectOption();
            for (SpecScp sg : resultList2) {
                optionVos2.add(new OptionVo(sg.getSpecScpName(), sg.getSpecScpId()));
            }
            //规格组下拉
            List<OptionVo> specGrpsOption = new ArrayList<>();
            for (SpecGrp sg : specGrpService.getSelectAll()) {

                specGrpsOption.add(new OptionVo(sg.getSpecGrpName(), sg.getSpecGrpId()));
            }
            //品牌Id对应品牌名称下拉
            List<TreeKeyValue> keys = new ArrayList<>();
            if (brandService.optionGet() != null && brandService.optionGet().size() > 0) {
                for (Brand m : brandService.optionGet()) {
                    TreeKeyValue ms = new TreeKeyValue(m.getBrandName(), m.getBrandId());
                    keys.add(ms);
                }
            }
            /**
             * 获取商品状态option
             */
            map.put("goodsStatus", codeDtlService.optionGet("PROD_STATUS"));
            /**审核状态*/
            map.put("auditType", codeDtlService.optionGet("auditType"));

            //性别
            map.put("gender", codeDtlService.optionGet("GENDER"));
            //套件属性
            map.put("suiteProp", codeDtlService.optionGet("SUITE_PROP"));
            //形状
            map.put("mainStyle", codeDtlService.optionGet("MAIN_STYLE"));
            //面料属性
            map.put("assisStyle", codeDtlService.optionGet("ASSIS_STYLE"));
            //排扣(子款号)
            map.put("subModel", codeDtlService.optionGet("SUB_STYLE"));
            //计量单位
            map.put("uom", codeDtlService.optionGet("UOM"));
            //版型(罩杯)
            map.put("edition", codeDtlService.optionGet("EDITION"));
            //是否共享
            map.put("shared", codeDtlService.optionGet("shared"));

            /**
             * 颜色
             */
            // map.put("color", colors);
            /**
             * 规格
             */
            //map.put("singleSpec", singleSpec);
            //品牌
            map.put("brand", keys);
            //规格范围下拉
            map.put("specGrp", optionVos2);

            //规格组下拉
            map.put("specGrps", specGrpsOption);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 商品品种 市场下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/Bazaar", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionBazaar() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            //适销季节
            map.put("eason", codeDtlService.optionGet("SEASON"));
            //市场级别
            map.put("mktGrd", codeDtlService.optionGet("MKT_GRD"));
            //营销系列
            map.put("mktGrdCp", codeDtlService.optionGet("MKT_SORT"));
            //特价方式
            map.put("mktType", codeDtlService.optionGet("MKT_TYPE"));
            //销售渠道
            map.put("salesChnl", codeDtlService.optionGet("SALES_CHNL"));
            //价格档
            map.put("prcLvl", codeDtlService.optionGet("PRC_LVL"));
            //销售方式
            map.put("salesMode", codeDtlService.optionGet("SALES_MODE"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 商品品种 生产下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/production", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionProduction(@RequestBody ProductModel model, HttpServletRequest request) {
        try {


            SysUser user =  this.isNullUser(request);
            List<String> list = prodClsUtils.getListString();
            //是否淘汰
            list.add("Td");
            //是否样品
            list.add("isSample");
            //停产
            list.add("endOfPdn");
            Map<String, Object> map = prodClsUtils.getMapOffOn(list);
            //面料
            map.put("outMtrl", codeDtlService.optionGet("outMtrl"));
            //里料
            map.put("inMtrl", codeDtlService.optionGet("CUP_IN_MTRL"));
            //厂商品牌
            map.put("MFR_BRAND_ID", codeDtlService.optionGet("MFR_BRAND_ID "));
            //执行标准
            map.put("PROD_STD", codeDtlService.optionGet("PROD_STD"));
            //商品级别
            map.put("PROD_GRD", codeDtlService.optionGet("PROD_GRD"));
            //产品风格
            map.put("PROD_STYLE", codeDtlService.optionGet("PROD_STYLE"));
            //存储仓库
            map.put("wearhId", warehOptionSerivce.getOption(model.getUnitId()));
            // 故事线(楼层)
            map.put("STORY_LINE", codeDtlService.optionGet("STORY_LINE"));

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 商品品种 物流下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/logistics", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionLogistics(HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);

            List<String> list = prodClsUtils.getListString();
            /**
             * 库存
             */
            list.add("stk_adopted");
            /**
             * 唯一码管理
             */
            list.add("uid_adopted");
            /**
             * 是否销带品
             */
            list.add("is_pgb");

            Map<String, Object> map = prodClsUtils.getMapOffOn(list);


            /**
             * 内包装
             */
            map.put("INNER_PCK", codeDtlService.optionGet("INNER_PCK"));
            /**
             * 打包类型
             */
            map.put("PCK_TYPE", codeDtlService.optionGet("PCK_TYPE"));
            /**
             * 库存级别
             */
            map.put("STK_GRD", codeDtlService.optionGet("STK_GRD"));


            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 商品下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/goods", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionGoods() {
        try {
            List<String> list = prodClsUtils.getListString();
            /**
             * 库存
             */
            list.add("stk_adopted");
            /**
             * 唯一码管理
             */
            list.add("uid_adopted");
            /**
             * 分颜色
             */
            list.add("multiColor");
            /**
             * 分规格
             */
            list.add("multiSpec");
            /**
             * 分版型
             */
            list.add("multiEdition");

            Map<String, Object> map = prodClsUtils.getMapOffOn(list);

            //适销季节
            map.put("eason", codeDtlService.optionGet("SEASON"));
            //性别
            map.put("gender", codeDtlService.optionGet("GENDER"));
            //版型(罩杯)
            map.put("edition", codeDtlService.optionGet("EDITION"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 颜色所有下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/getColor", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getColor() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            //初始化所有规格
            List<Spec> singleSpec = this.specService.getAll();
            //初始化所有颜色下拉选择
            List<OptionVo> colors = new ArrayList<>();
            if (colorService.getOpentin().size() > 0) {
                for (Color m : colorService.getOpentin()) {
                    colors.add(new OptionVo(m.getColorName(), m.getColorCode()));
                }
            }
            /**
             * 颜色
             */
            map.put("color", colors);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }
    /**
     * 所有规格下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/getSpec", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSpec() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            //初始化所有规格
            List<Spec> singleSpec = this.specService.getAll();
            /**
             * 规格
             */
            map.put("singleSpec", singleSpec);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getSpec] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 商品品种 存储仓库下拉
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getOption/warehCls", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionWarehCls(@RequestBody ProductModel model, HttpServletRequest request) {
        try {

            SysUser user = (SysUser) this.isNullUser(request);
            Map<String, Object> map = prodClsUtils.getMap();
            //存储仓库
            map.put("wearhId", warehOptionSerivce.getOption(model.getUnitId()));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


}
