package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysFunction;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysFunctionMapper;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysFunctionSerivce;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/function")
public class FunctionController extends BaseController {
    @Autowired
    private SysFunctionSerivce functionSerivce;

    @Resource
    private SysFunctionMapper functionMapper;

    /**
     * 查询功能分页
     */
    @RequestMapping(value = "/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult FunctionAll(HttpServletRequest request,
                                  SysFunction function,
                                  @RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size) {
        try {
            if (this.getSessionSysUser(request) == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登陆", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "请求成功", functionSerivce.findAll(page, size, function, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[SysFunctionSerivce][findAll] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][FunctionAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败,请联系管理员", "");
        }
    }

    /**
     * 新建功能
     */
    @SystemLog(name = "新建功能")
    @RequestMapping(value = "/addfunction", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addFunction(HttpServletRequest request, @RequestBody SysFunction function) {
        try {
            SysUser use = this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "添加成功", functionSerivce.addFunction(function));
        } catch (ServiceException ex) {
            log.error("[SysFunctionSerivce][addFunction] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][addFunctio] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加异常", "");
        }
    }


    /**
     * 功能修改
     */
    @SystemLog(name = "修改功能")
    @RequestMapping(value = "/updatefunction", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateFunction(HttpServletRequest request, @RequestBody SysFunction function) {
        try {
            SysUser use = this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", functionSerivce.updateFunction(function));
        }catch (ServiceException ex) {
            log.error("[SysFunctionSerivce][addFunction] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][updateFunction] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改异常", "");
        }
    }

    /**
     * （慎用）
     * 删除功能
     * 1.删除功能表
     * 2.删除目录详情表
     */
    @SystemLog(name = "删除功能")
    @RequestMapping(value = "/deletefunction", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteFunction(HttpServletRequest request, @RequestBody SysFunction function) {
        try {
            SysUser use = this.isNullUser(request);
            functionSerivce.deleteFunction(function);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", "");
        }catch (ServiceException ex) {
            log.error("[SysFunctionSerivce][addFunction] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][deleteFunction] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除异常", "");
        }
    }

    /**
     * 功能类别下拉
     */
    @RequestMapping(value = "/opetion", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult opetion() {
        try {
            List<SysFunction> f = functionMapper.getOption();
            List<OptionVo> vo = new ArrayList<>();
            for (SysFunction g : f) {
                OptionVo v = new OptionVo();
                if (StringUtils.NullEmpty(g.getFuncType())) {
                    v.setOptionName("");
                    v.setOptionValue("");
                    vo.add(v);
                } else {
                    v.setOptionName(g.getFuncType());
                    v.setOptionValue(g.getFuncType());
                    vo.add(v);
                }

            }
            Map<String, List<OptionVo>> map = new HashMap<>();
            map.put("FuncOpetion", vo);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        }catch (ServiceException ex) {
            log.error("[SysFunctionSerivce][addFunction] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PrivilegeController][opetion] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }


    /**
     *测试验证前台参数类
     */
  /*  @RequestMapping(value = "/text", method = {RequestMethod.POST, RequestMethod.GET})
    basic JsonResult text(@RequestBody TestModel mo) {
        try {
                   log.info("-==================-->"+mo);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", mo);
        } catch (Exception ex) {
            log.error("[PrivilegeController][opetion] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }*/

}
