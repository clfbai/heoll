package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.San;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.SanFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.SanModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.SanService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/warehouse/san")
public class SanController extends BaseController {
    @Autowired
    private SanService sanService;
    @Autowired
    private DelivUtil delivUtil;
    /**
     * 加载库存调整单下拉
     * @author HHe
     * @date 2019/10/6 17:48
     */
    @RequestMapping("/loadsanpulldown")
    public JsonResult loadSanPullDown(HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sanService.loadSanPullDown(sysUser));
        } catch (ServiceException e) {
            log.error("[SanController][loadSanPullDown] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][loadSanPullDown] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }
    /**
     * 查询列表
     * @author HHe
     * @date 2019/10/6 16:51
     */
    @RequestMapping("/selectall")
    public JsonResult selectAll (@RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "15")Integer size, SanFilterModel sanFilterModel, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sanService.queryList(page,size,sanFilterModel,sysUser));
        } catch (ServiceException e) {
            log.error("[SanController][selectAll] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][selectAll] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }
    /**
     * 查询详情
     * @author HHe
     * @date 2019/10/7 10:37
     */
    @RequestMapping(value = "/selectdetails",method = RequestMethod.GET)
    public JsonResult selectDetails (String sanNum, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sanService.queryDetails(sanNum,sysUser));
        } catch (ServiceException e) {
            log.error("[SanController][selectDetails] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][selectDetails] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }
    /**
     * 添加库存调整表
     * @author HHe
     * @date 2019/10/7 10:52
     */
    @RequestMapping(value = "/addSan",method = RequestMethod.POST)
    public JsonResult addSan(@RequestBody SanModel sanModel, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,sanService.addSan(sanModel,sysUser));
        } catch (ServiceException e) {
            log.error("[SanController][addSan] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][addSan] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.ADDFIANL,"");
        }
    }

    /**
     * 修改库存调整表
     * @author HHe
     * @date 2019/10/7 14:23
     */
    @RequestMapping(value = "/updatesan",method = RequestMethod.POST)
    public JsonResult updateSan(SanModel sanModel,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,sanService.updateSan(sanModel,sysUser));
        } catch (ServiceException e) {
            log.error("[SanController][updateSan] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][updateSan] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.UPDATEFANS,"");
        }
    }
    /**
     * 删除库存调整表
     * @author HHe
     * @date 2019/10/7 14:23
     */
    @RequestMapping(value = "/delsan",method = RequestMethod.GET)
    public JsonResult delSan(String sanNum,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,sanService.delSan(sanNum,sysUser));
        } catch (ServiceException e) {
            log.error("[SanController][delSan] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][delSan] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.DELETEFANS,"");
        }
    }

    /**
     * 库存调整表功能列表查询
     * @author HHe
     * @date 2019/10/9 9:25
     */
    @RequestMapping(value = "/funlist",method = RequestMethod.POST)
    public JsonResult funList(@RequestBody San san, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sanService.queryFunList(san));
        } catch (ServiceException e) {
            log.error("[SanController][funList] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][funList] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }

    /**
     * 库存调整表功能操作
     * @author HHe
     * @date 2019/10/9 9:24
     */
    @RequestMapping(value = "/sanfunction/{type}",method = RequestMethod.POST)
    public JsonResult sanFunction(@RequestBody SanModel sanModel,@PathVariable String type,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,delivUtil.dynamicRequest("sanServiceImpl",type,sanModel));
        } catch (ServiceException e) {
            log.error("[SanController][sanFunction] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SanController][sanFunction] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
        }
    }
}
