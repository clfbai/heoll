package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname SysUnitHierDtlController
 * 组织层级明细控制层
 * @Description TODO
 * @Date 2019/8/30 15:36
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/user/hierDtl")
public class SysUnitHierDtlController extends BaseController {

    private static final String hierDtlCon = "组织层级明细";

    /**
     * 通过组织层级id 与 组织状态去查询 当前组织下已征募的采购商
     * @param unitHierId 组织层级id
     * @param unitStatus 组织状态
     * @param unitType 类型
     * @param page
     * @param size
     * @param re
     * @return
     */
    @RequestMapping(value = "/hierDtlList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hierDtlList(String unitHierId,String unitStatus,String unitType
            , @RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }



            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, "");
        } catch (ServiceException ex) {
            log.error("[SysUnitHierDtlController][hierDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SysUnitHierDtlController][hierDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, hierDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

}
