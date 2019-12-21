package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUg;
import com.boyu.erp.platform.usercenter.entity.system.SysUgDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.system.UgAndUgDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.UgServer;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.UgVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @param: 组织分组控制层
 * @return:
 * @auther: CLF
 * @date: 2019/8/8 11:03
 */
@Slf4j
@RestController
@RequestMapping("/unit/ug")
public class UnitUgController extends BaseController {
    @Autowired
    private UgServer ugServer;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService codeDtlService;

    /**
     * 组织分组下拉
     *
     * @return
     */
    @PostMapping(value = "/optins")
    public JsonResult ugOptins() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            //组织类别
            map.put("unitType", codeDtlService.optionGet("UNIT_TYPE"));
            //分组类别
            map.put("ugType", codeDtlService.optionGet("UG_TYPE"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[CodeDtlService][optionGet] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][ugOptins] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组下拉" + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询组织分组列表
     *
     * @param ug
     * @return
     */
    @GetMapping(value = "/list")
    public JsonResult ugAll(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                            @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                            SysUg ug, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            if (StringUtils.NullEmpty(ug.getUgType())) {
                ug.setUgType(null);
            }
            PageInfo<UgVo> pageInfo = ugServer.getUg(page, size, ug, user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[UgServer][getUg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][ugAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组" + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 组织分组信息(单条信息)查询
     *
     * @param ug
     * @return
     */
    @PostMapping(value = "/getUgId")
    public JsonResult getUgId(@RequestBody SysUg ug, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, ugServer.getUgId(ug));
        } catch (ServiceException ex) {
            log.error("[UgServer][getUg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][getUgId] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组明细" + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询组织分组明细列表
     *
     * @param ugDtl
     * @return
     */
    @PostMapping(value = "/dtl/list")
    public JsonResult dtlList(@RequestBody SysUgDtl ugDtl, HttpServletRequest request) {
        try {
            SysUser user =this.isNullUser(request);
            List<SysUgDtl> list = ugServer.getUgDtl(ugDtl, user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(list));
        } catch (ServiceException ex) {
            log.error("[UgServer][getUg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][dtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组明细" + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加组织分组
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/add")
    public JsonResult addUg(@RequestBody UgAndUgDtlModel model, HttpServletRequest request) {
        try {
            SysUg ug = model.getUg();
            if (ug.getUgNum() != null && ug.getUgNum().length() > 4) {
                return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "分组编号长度不能超过4位", "");
            }
            SysUser user =  this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, ugServer.addUg(ug, model, user));
        } catch (ServiceException ex) {
            log.error("[UgServer][addUg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][addUg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组" + CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 修改组织分组
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/update")
    public JsonResult updateUg(@RequestBody UgAndUgDtlModel model, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, ugServer.upadteUg(model, user));
        } catch (ServiceException ex) {
            log.error("[UgServer][addUg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][updateUg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组" + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 修改组织分组明细
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/update/ugdtl")
    public JsonResult updateUgDtl(@RequestBody UgAndUgDtlModel model, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, ugServer.upadteUgDtl(model, user));
        } catch (ServiceException ex) {
            log.error("[UgServer][addUg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][updateUgDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组" + CommonFainl.UPDATEFANS, "");
        }
    }


    /**
     * 删除组织分组明细
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/delete")
    public JsonResult deleteUg(@RequestBody SysUg model, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, ugServer.deleteUg(model, user));
        } catch (ServiceException ex) {
            log.error("[UgServer][deleteUg] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitUgController][deleteUg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_UG, "组织分组" + CommonFainl.DELETEFANS, "");
        }
    }

}
