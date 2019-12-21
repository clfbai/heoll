package com.boyu.erp.platform.usercenter.controller.ca;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.caservice.CaService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname CaController
 *  往来账户授信控制层
 * @Description TODO
 * @Date 2019/10/6 11:24
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/ca/caAcc")
public class CaAccController extends BaseController {

    private static final String caAccCon = "往来账户授信明细";

    @Autowired
    private CaAccService caAccService;

    /**
     * 往来账户查询往来账户授信明细
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult caAccList(CaVo vo, @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "15") Integer size,
                             HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            PageInfo<CaAcc> resultList = caAccService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[CaAccController][caAccList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CaAccController][caAccList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, caAccCon+CommonFainl.SELECTFIANL, "");
        }
    }

}
