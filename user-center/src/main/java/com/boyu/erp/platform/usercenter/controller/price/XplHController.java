package com.boyu.erp.platform.usercenter.controller.price;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.XplHService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.price.XplHVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname XplHController
 * 价格单历史
 * @Description TODO
 * @Date 2019/8/30 9:21
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/price/xplh")
public class XplHController extends BaseController {
    private static final String xplhCon = "价格单历史";

    @Autowired
    private XplHService xplHService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;


    /**
     * 价格单历史查询
     *
     * @return
     */
    @RequestMapping(value = "/xplHList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult xplHList(XplHVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            vo.setUnitId(sysUser.getDomain().getUnitId().toString());

            PageInfo<XplHVo> resultList = xplHService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[XplHController][xplHList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[XplHController][xplHList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_R, xplhCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult option() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();


            map.put("prcScp", sysCodeDtlService.optionGet("PRC_SCP"));//定价范围
            map.put("xpType", sysCodeDtlService.optionGet("XP_TYPE"));//价格类别
            map.put("specOfr", prodClsUtils.getList());//是否特价
            map.put("prcOpr", sysCodeDtlService.optionGet("PRC_OPR"));//定价操作
            map.put("prcPlcy", sysCodeDtlService.optionGet("PRC_PLCY"));//定价策略

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[XplHController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[XplHController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

}
