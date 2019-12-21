package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivDepKey;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.PrivdelModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.PrivDelSerivce;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 权限依赖控制层
 * @author: clf
 * @create: 2019-05-27 09:28
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class PrivDelController extends BaseController {

    @Autowired
    private PrivDelSerivce privDelSerivce;

    /**
     * 权限依赖查询
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/privdel/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult privDelList(@RequestBody PrivdelModel key) {
        try {
            SysPrivDepKey privDepKey = new SysPrivDepKey();
            BeanUtils.copyProperties(key, privDepKey);
            List<SysPrivDepKey> list = privDelSerivce.selectPrivDelKey(privDepKey);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", RestulMap.getResut(list));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][selectPrivDelKey] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivDelController][privDelList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 权限依赖添加
     *
     * @param key
     * @return
     */
    @SystemLog(name = "权限依赖添加")
    @RequestMapping(value = "/privdel/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPrivDel(@RequestBody PrivdelModel key, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            SysPrivDepKey privDepKey = new SysPrivDepKey();
            BeanUtils.copyProperties(key, privDepKey);
            if (!veriFication(privDepKey)) {
                return new JsonResult(JsonResultCode.FAILURE, "Already Exist,Please do not add", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "添加成功", privDelSerivce.addPrivDel(privDepKey));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][addPrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivDelController][addprivdel] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 修改依赖添加
     *
     * @param key
     * @return
     */
    @SystemLog(name = "修改权限依赖")
    @RequestMapping(value = "/privdel/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePrivDel(@RequestBody PrivdelModel key, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            SysPrivDepKey privDepKey = new SysPrivDepKey();
            privDepKey.setDepPrivId(key.getUpdateDepPrivId());
            privDepKey.setPrivId(key.getPrivId());
            if (!veriFication(privDepKey)) {
                return new JsonResult(JsonResultCode.FAILURE, "Already Exist,Please do not add", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", privDelSerivce.updatePrivDel(key));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][updatePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivDelController][updatePrivDel] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改异常,请联系管理员", "");
        }
    }

    /**
     * 删除依赖添加
     *
     * @param key
     * @return
     */
    @SystemLog(name = "删除权限依赖")
    @RequestMapping(value = "/privdel/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePrivDel(@RequestBody PrivdelModel key, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            SysPrivDepKey privDepKey = new SysPrivDepKey();
            BeanUtils.copyProperties(key, privDepKey);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", privDelSerivce.deletePrivDel(privDepKey));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivDelController][deletePrivDel] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除异常,请联系管理员", "");
        }
    }

    /**
     * 添加查询全年依赖是否存在
     *
     * @param key
     * @return
     */
    public Boolean veriFication(SysPrivDepKey key) {
        boolean bo = false;
        if (privDelSerivce.findByPrivDel(key) == null) {
            bo = true;
        }
        return bo;
    }
}
