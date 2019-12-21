package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SysCodeController
 * @Description TODO
 * @Date 2019/5/7 20:39
 * @Created by js
 * 代码详情
 */

@Slf4j
@RestController
@RequestMapping(value = "/product/codeDtl")
public class ProductCodeDtlController extends BaseController {


    @Autowired
    private SysCodeDtlService codeDtlService;

    /**
     * 查询代码明细
     *
     * @param dtl
     * @return
     */
    @RequestMapping(value = "/querycodeDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getcodeDtlpage(SysCodeDtl dtl,
                                     HttpServletRequest request,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size) {
        try {
            PageInfo<SysCodeDtl> pageInfo = codeDtlService.selectAll(page, size, dtl);

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (Exception ex) {
            log.error("[SysCodeDtlController][getcodeDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 查询代码明细不分页
     *
     * @param dtl
     * @return
     */
    @RequestMapping(value = "/getDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getcodeDtl(@RequestBody SysCodeDtl dtl) {
        try {
            List<SysCodeDtl> pageInfo = codeDtlService.getAll(dtl);
            Map<String, Object> map = new HashMap<>();
            map.put("size", pageInfo == null ? 0 : pageInfo.size());
            map.put("list", pageInfo);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", map);
        } catch (Exception ex) {
            log.error("[SysCodeDtlController][getcodeDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 添加代码明细
     *
     * @param dtl
     * @return
     */
    @SystemLog(name = "添加代码明细")
    @RequestMapping(value = "/addcodeDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addcodeDtl(@RequestBody SysCodeDtl dtl, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            dtl.setCreateBy(user.getUserId());
            dtl.setCreateTime(new Date());
            dtl.setUpdateBy(user.getUserId());
            dtl.setUpdateTime(new Date());
            dtl.setIsDel(CommonFainl.BTYPESTATUS);
            if (codeDtlService.selectByPrimaryKey(dtl) != null) {
                //恢复状态
                int i = codeDtlService.updateByPrimaryKeySelective(dtl);
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", i);
            }
            int i = codeDtlService.insertSelective(dtl);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", i);
        } catch (Exception ex) {
            log.error("[SysCodeDtlController][addcodeDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改代码明细
     * 需要代码类别及代码及修改时间
     *
     * @param dtl
     * @return
     */
    @SystemLog(name = "修改代码明细")
    @RequestMapping(value = "/updatecodeDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatecodeDtl(@RequestBody SysCodeDtl dtl, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            SysCodeDtl dtls = new SysCodeDtl();
            if (StringUtils.isNotEmpty(dtl.getUpdateCode())) {
                dtls.setCodeType(dtl.getCodeType());
                dtls.setCode(dtl.getUpdateCode());

            } else {
                BeanUtils.copyProperties(dtl, dtls);
            }
            if (isExitCodeDtl(dtls, "update")) {
                dtl.setUpdateBy(user.getUserId());
                dtl.setUpdateTime(new Date());
                int i = codeDtlService.updateByPrimaryKeySelective(dtl);
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", i);
            }
            return new JsonResult(JsonResultCode.FAILURE, "已存在该代码明细", "");
        } catch (Exception ex) {
            log.error("[SysCodeDtlController][updatecodeDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除代码明细
     * 需要代码类别及代码
     * (打标)
     *
     * @param dtl
     * @return
     */
    @SystemLog(name = "删除代码明细")
    @RequestMapping(value = "/deletecodeDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletecodeDtl(@RequestBody SysCodeDtl dtl, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            dtl.setUpdateBy(user.getUserId());
            dtl.setUpdateTime(new Date());
            dtl.setIsDel(CommonFainl.FAILSTATUS);
            int i = codeDtlService.deleteByPrimaryKey(dtl);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", i);
        } catch (Exception ex) {
            log.error("[SysCodeDtlController][deletecodeDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 是否已存在代码明细信息
     *
     * @param dtl
     * @return
     */
    private Boolean isExitCodeDtl(SysCodeDtl dtl, String s) {
        if (s.equalsIgnoreCase("update")) {
            //如果不修改Code值
            if (dtl.getUpdateCode() == null) {
                dtl.setCode(null);
            }

        }
        SysCodeDtl sysCodeDtl = codeDtlService.selectByPrimaryKey(dtl);
        if (sysCodeDtl == null) {
            return true;
        } else {
            return false;
        }

    }
}
