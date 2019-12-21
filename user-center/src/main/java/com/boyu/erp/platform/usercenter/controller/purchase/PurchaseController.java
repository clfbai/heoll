package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import com.boyu.erp.platform.usercenter.TPOS.mapper.WmsErpNumMapper;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.mq.order.OrderAdd;
import com.boyu.erp.platform.usercenter.entity.mq.order.SrcOrderAdd;
import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PrcMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PucMapper;
import com.boyu.erp.platform.usercenter.model.system.CommonPopupModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.salesservice.SalesService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.AttriButeUtils;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.StbProdDtlVo;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.OptionByPsaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: PurchaseController
 * @description: 采购公共控制层
 * @author: wz
 * @create: 2019-06-20 09:57
 */
@Slf4j
@RestController
@RequestMapping("/purchase")
public class PurchaseController extends BaseController {
    private static final String purchase = "公用方法";

    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private PsaService psaService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private SalesService salesService;

    /**
     * Lambda 过滤集合判断 字符是否存在  需要不停进行上下文切换 适合并发
     */
    public static boolean getLambda(String s, List<Object> list) {
        boolean b = list.stream().anyMatch(p -> p.equals(s));
        return b;
    }

    /**
     * 出入库查询明细接口
     * @param vo
     * @param re
     * @return
     */
    @RequestMapping(value = "/dtl/CommonDtl", method = {RequestMethod.POST})
    public JsonResult CommonDtl(@RequestBody StbProdDtlVo vo, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            List<CommonDtl> list = purchaseService.selectBillInfo2(vo.getType(),vo.getCntrNum(),vo.getProdId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException ex) {
            log.error("[PurchaseController][CommonDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_COMMONDTL_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseController][CommonDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_COMMONDTL_R, purchase+CommonFainl.SELECTPOPUPFIANL, "");
        }
    }

    /**
     * @return 采购合同中除了供应商弹窗的弹窗
     * 除了供应商编号弹窗不走  其他都走这里
     * 必传字段为:unitType  ownerId
     */
    @RequestMapping(value = "/common/popup", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult popup(@RequestBody CommonPopupModel model, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            Object obj = purchaseService.getObject(model,user);
            if ( obj== null) {
                return new JsonResult(JsonResultCode.FAILURE, "没有搜索到您要的信息", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, obj);
        } catch (ServiceException ex) {
            log.error("[PurchaseController][popup] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_OPTION_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseController][popup] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_OPTION_R, purchase+CommonFainl.SELECTPOPUPFIANL, "");
        }
    }

    /**
     * @return 采购合同中除了供应商弹窗的弹窗
     * 除了供应商编号弹窗不走  其他都走这里
     * 必传字段为:unitType  ownerId
     */
    @RequestMapping(value = "/unitOption/unitOptionByNew", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitOptionByNew(UnitOptionVo vo, @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "15") Integer size) {
        try {
            PageInfo<UnitOptionVo> list = null;
            if (StringUtils.isNotEmpty(vo.getsUnitId() + "")) {
                list = sysUnitService.selectByOption(vo, page, size);
            } else {
                list = new PageInfo<>();
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException ex) {
            log.error("[PurchaseController][unitOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_OPTION_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseController][unitOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_OPTION_R, purchase+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 当传过来的是供应商时  采购中判断
     * 当传过来的时采购商时  销售中判断
     * @return 选择供应商后，拿到供应商与当前组织，去判断中间是否有购销协议的存在
     */
    @RequestMapping(value = "/option/optionByPur", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult optionByPsa(@RequestBody OptionByPsaVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            OptionByPsaVo psa = null;
            if(vo.getPsaCtlr().equals("E")){
                psa = psaService.selectByPsaByVde(vo);
            }else{
                psa = psaService.selectByPsaByVdr(vo);
            }

            if(psa!=null){
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, psa);
            }
            return new JsonResult(JsonResultCode.FAILURE_PUC_OPTION_R, CommonFainl.OPTIONfAIL, "");

        } catch (ServiceException ex) {
            log.error("[PurchaseController][unitOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUR_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseController][unitOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUR_R, purchase+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * @return 输入编号查询组织相关数据
     */
    @RequestMapping(value = "/unitOption/unitOptionByNum", method = {RequestMethod.POST})
    public JsonResult unitOptionByNum(@RequestBody UnitOptionVo vo,
                                      HttpServletRequest re) {
        try {
            UnitOptionVo option  = sysUnitService.findOptionByNum(vo);
            if ( option== null) {
                return new JsonResult(JsonResultCode.FAILURE, "没有搜索到您要的信息", new UnitOptionVo());
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, option);
        } catch (Exception ex) {
            log.error("[PurchaseController][unitOptionByNum] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUR_UNIT_R, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * @return 原始单据查询
     */
    @RequestMapping(value = "/srcDocNum/getSrcDocNum", method = {RequestMethod.POST})
    public JsonResult getSrcDocNum(@RequestBody Psc srcDoc,
                                   HttpServletRequest re) {
        try {
            int sum = purchaseService.findByNum(srcDoc);
            if ( sum== 0) {
                return new JsonResult(JsonResultCode.FAILURE, "没有搜索到您要的信息", "");
            }
            Map<String,Object> map = new HashMap<>();
            map.put("srcDocUnitId",srcDoc.getSrcDocUnitId());
            map.put("srcDocNum",srcDoc.getSrcDocNum());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PurchaseController][getSrcDocNum] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUR_SRC_R, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * @return 原始单据查询
     */
    @RequestMapping(value = "/pscReqd/getPscNum", method = {RequestMethod.POST})
    public JsonResult getPscNum(@RequestBody Psc srcDoc,
                                   HttpServletRequest re) {
        try {
            Psc psc = purchaseService.findByPscNum(srcDoc);
            if ( psc== null) {
                return new JsonResult(JsonResultCode.FAILURE, "没有搜索到您要的信息", "");
            }
            Map<String,Object> map = new HashMap<>();
            map.put("pscNum",psc.getPscNum());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PurchaseController][getPscNum] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUR_SRC_R, CommonFainl.SELECTFIANL, "");
        }
    }


    @RequestMapping(value = "/aa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult aa(HttpServletRequest re,@RequestBody SrcOrderAdd add) throws Exception {
//        PrcVo a = new PrcVo();
//        AttriButeUtils.Attr(a);
        SysUser user = (SysUser) this.isNullUser(re);
        salesService.creatSrc(add,user);
        return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, "");
    }

   // @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(value="exh.order.bc.de",durable = "true",type = "direct"),value = @Queue(value = "order.bc.de",durable = "true"),key = "order.bc.de"))
    public void process(Message message) {
        String a = new String(message.getBody());
        System.err.println(a);
    }
}



