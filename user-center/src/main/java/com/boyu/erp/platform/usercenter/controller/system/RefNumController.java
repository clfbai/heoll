package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumDtlSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.common.ParamLength;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 编号控制层
 * @author: clf
 * @create: 2019-05-15 09:14
 */
@Slf4j
@RestController
@RequestMapping("/user/refnum")
public class RefNumController extends BaseController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysRefNumService refNumService;

    @Autowired
    private SysRefNumDtlSerivce refNumDtlSerivce;

    /**
     * @program: re ,page ,size,refNum
     * @description: 查询编号
     */
    @RequestMapping(value = "/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult all(@RequestParam(defaultValue = "0") Integer page,
                          @RequestParam(defaultValue = "15") Integer size,
                          HttpServletRequest re,
                          SysRefNum refNum) {
        try {
            SysUser user =  this.isNullUser(re);
            PageInfo<SysRefNum> pageInfo = refNumService.getAll(page, size, refNum);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RefNumController][all] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * @program: re, refNum
     * @description: 新建编号
     */
    @SystemLog(name = "新建编号")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult add(HttpServletRequest re,
                          @RequestBody SysRefNum refNum) {
        try {
            if (ParamLength.isParamLength("编号Id", refNum.getRefNumId(), 32) != null) {
                return ParamLength.isParamLength("编号Id", refNum.getRefNumId(), 32);
            }

            SysUser user =  this.isNullUser(re);
            refNum.setCreateBy(user.getUserId());
            refNum.setIsDel(CommonFainl.BTYPESTATUS);
            refNum.setCreateTime(new Date());
            if (findId(refNum)) {
                return new JsonResult(JsonResultCode.SUCCESS, "添加成功", refNumService.addRefNum(refNum));
            }
            return new JsonResult(JsonResultCode.FAILURE, "请勿重复添加", "");
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RefNumController][add] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加失败", "");
        }
    }

    /**
     * @program: refNum
     * @description: 修改编号
     */
    @SystemLog(name = "修改编号")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult update(HttpServletRequest re,
                             @RequestBody SysRefNum refNum) {
        try {
            SysUser user =  this.isNullUser(re);
            refNum.setUpdateBy(user.getUserId());
            refNum.setUpdateTime(new Date());

            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", refNumService.updateRefNum(refNum));

        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RefNumController][update] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * @program: re，refNum
     * @description: 删除编号
     */
    @SystemLog(name = "删除编号")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult delete(HttpServletRequest re, @RequestBody SysRefNum refNum) {
        try {
            SysUser user =  this.isNullUser(re);
            if (!usersService.getIsAdmin(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "请联线系统管理员删除编号", "");
            }
            refNum.setUpdateBy(user.getUserId());
            refNum.setUpdateTime(new Date());
            refNum.setIsDel(CommonFainl.FAILSTATUS);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", refNumService.updateRefNum(refNum));

        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[RefNumController][delete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除失败", "");
        }
    }

    /**
     * @program: re ,page ,size,refNum
     * @description: 查询编号明细
     */
    @RequestMapping(value = "/dtlAll", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult dtlAll(HttpServletRequest re,
                             @RequestBody SysRefNumDtl refNumdtl) {
        try {
            SysUser user =  this.isNullUser(re);
            refNumdtl.setUnitId(user.getOwnerId());
            List<SysRefNumDtl> pageInfo = refNumDtlSerivce.getAll(refNumdtl, user);
            Map<String, Object> map = new HashMap<>();
            map.put("size", pageInfo == null ? 0 : pageInfo.size());
            map.put("list", pageInfo);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RefNumController][dtlAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * @program: re, refNumDtl
     * @description: 新建编号明细
     */
    @SystemLog(name = "新建编号明细")
    @RequestMapping(value = "/dtlAdd", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult dtlAdd(HttpServletRequest re,
                             @RequestBody SysRefNumDtl refDtlNum) {
        try {

            SysUser user = this.isNullUser(re);
            refDtlNum.setCreateBy(user.getUserId());
            refDtlNum.setCreateTime(new Date());
            refDtlNum.setUpdateTime(new Date());
            refDtlNum.setUpdateBy(user.getUserId());
            refDtlNum.setIsDel(CommonFainl.BTYPESTATUS);
            if (findDtlId(refDtlNum)) {
                return new JsonResult(JsonResultCode.SUCCESS, "添加成功", refNumDtlSerivce.addRefNumDtl(refDtlNum));
            }
            return new JsonResult(JsonResultCode.FAILURE, "请勿重复添加", "");
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RefNumController][dtlAdd] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加失败", "");
        }
    }


    /**
     * @program: refDtlNum
     * @description: 修改编号明细
     */
    @SystemLog(name = "修改编号明细")
    @RequestMapping(value = "/dtlUpdate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult dtlUpdate(HttpServletRequest re,
                                @RequestBody SysRefNumDtl refDtlNum) {
        try {
            SysUser user = this.isNullUser(re);
            refDtlNum.setUpdateBy(user.getUserId());
            refDtlNum.setUpdateTime(new Date());
            SysRefNumDtl refNumDtl = new SysRefNumDtl();
            refNumDtl.setRefNumId(refDtlNum.getRefNumId());
            refNumDtl.setUnitId(refDtlNum.getUpdateUnitId());
            if (!findDtlId(refNumDtl)) {
                return new JsonResult(JsonResultCode.FAILURE, "编号已存在请勿重复修改", 0);
            }
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", refNumDtlSerivce.updateRefNumDtl(refDtlNum));

        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RefNumController][dtlUpdate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }


    /**
     * @program: re，refNum
     * @description: 删除编号明细
     */
    @SystemLog(name = " 删除编号明细")
    @RequestMapping(value = "/dtlDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult dtlDelete(HttpServletRequest re, @RequestBody SysRefNumDtl refDtlNum) {
        try {
            SysUser user =  this.isNullUser(re);
            if (!usersService.getIsAdmin(user)) {
                return new JsonResult(JsonResultCode.FAILURE, "请联系系统管理员删除编号明细", "");
            }
            refDtlNum.setUpdateBy(user.getUserId());
            refDtlNum.setUpdateTime(new Date());
            refDtlNum.setIsDel(CommonFainl.FAILSTATUS);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", refNumDtlSerivce.updateRefNumDtl(refDtlNum));

        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[RefNumController][dtlDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除失败", "");
        }
    }


    /**
     * 获取最新编号
     * */


    /**
     * @program: re ,page ,size,refNum
     * @description: 查询编号
     */
    public boolean findId(SysRefNum refNum) {
        if (refNumService.findByRef(refNum) == null) {
            return true;
        }
        return false;
    }


    /**
     * @program: re ,page ,size,refNum
     * @description: 查询编号明细
     */
    public boolean findDtlId(SysRefNumDtl refDtlNum) {
        if (refNumDtlSerivce.findById(refDtlNum) == null) {
            return true;
        }
        return false;
    }
}
