package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Grn;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.GrnService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: boyu-platform_text
 * @description: 调拨单明细控制层
 * @author: ck
 * @create: 2019-06-28 10:26
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/stb/stbDtl")
public class StbDtlController extends BaseController {
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private GrnService grnService;

    /**
     * 查询库存单明细列表
     *
     * @param stbDtl
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult stbDtlTypeAll(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                    @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                    @RequestBody StbDtl stbDtl, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (user != null) {
                stbDtl.setUnitId(user.getUnit().getUnitId());
            }
            PageInfo<StbDtlVo> pageInfo = stbDtlService.getStbDtlList(page, size, stbDtl);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[StbDtlTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbDtlTypeController][stbDtlTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }




    /**
     * 删除单条库存单明细
     *
     * @param stbDtl
     * @param re
     * @return
     */
    @SystemLog(name = "删除库存单明细")
    @RequestMapping(value = "/deleteStbDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteStbDtl(@RequestBody StbDtl stbDtl, HttpServletRequest re) {
        try {
            SysUser user =  this.isNullUser(re);
            if (StringUtils.NullEmpty(stbDtl.getProdId() + "")) {
                return new JsonResult(JsonResultCode.FAILURE, "入库单明细商品Id为空", "");
            }
            PrivIdExamine examine = privUtils.isUnitPriv("deleteStbDtl", "删除库存单明细", stbDtl.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            Grn grn = new Grn(stbDtl.getUnitId(), stbDtl.getStbNum());
            if (grnService.isUpdateGrn(grn)) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", stbDtlService.delete(stbDtl));
            }
            return new JsonResult(JsonResultCode.SUCCESS, "单据状态不能删除", 0);
        } catch (ServiceException ex) {
            log.error("[StbDtlService][deleteStdDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbDtlService][deleteStdDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }


}
