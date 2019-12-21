package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.StlFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.StlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.StlService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/warehouse/stl")
public class StlController extends BaseController {
    @Autowired
    private StlService stlService;
    /**
     * 盘点清单下拉
     * @author HHe
     * @date 2019/10/8 16:44
     */
    @RequestMapping("/stlpulldown")
    public JsonResult stlPullDown(){
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,stlService.loadStlPullDown());
        } catch (ServiceException e) {
            log.error("[StlController][stlPullDown] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[StlController][stlPullDown] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }
    /**
     * 根据筛选条件分页查询列表
     * @author HHe
     * @date 2019/9/23 10:51
     */
    @RequestMapping(value = "/queylist", method = RequestMethod.POST)
    public JsonResult queryList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size, @RequestBody StlFilterModel stlFilterModel, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, stlService.queryList(page,size,stlFilterModel,sysUser));
        } catch (ServiceException e) {
            log.error("[StlController][queryList] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[StlController][queryList] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询盘点清单详情
     * @author HHe
     * @date 2019/9/25 9:45
     */
    @RequestMapping(value = "/querydetails",method = RequestMethod.POST)
    public JsonResult queryDetails(@RequestBody Stl stl,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,stlService.queryDetails(stl,sysUser));
        } catch (ServiceException e) {
            log.error("[StlController][queryDetails] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[StlController][queryDetails] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }

    /**
     * 添加盘点清单
     * @author HHe
     * @date 2019/9/23 14:18
     */
    @RequestMapping(value = "/addstl",method = RequestMethod.POST)
    public JsonResult addStl(@RequestBody StlModel stlModel, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,stlService.addStl(stlModel,sysUser));
        } catch (ServiceException e) {
            log.error("[StlController][addStl] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[StlController][addStl] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.ADDFIANL,"");
        }
    }

    /**
     * 确认盘点清单
     * @author HHe
     * @date 2019/9/25 11:37
     */
    @RequestMapping(value = "/confirmstl",method =RequestMethod.POST)
    public JsonResult confirmStl(Stl stl, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,stlService.atterStl(stl,sysUser,"confirm"));
        } catch (ServiceException e) {
            log.error("[StlController][confirmStl] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[StlController][confirmStl] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.UPDATEFANS,"");
        }
    }

    /**
     * 重做盘点清单
     * @author HHe
     * @date 2019/9/25 12:21
     */
    @RequestMapping(value = "/redostl",method = RequestMethod.POST)
    public JsonResult redoStl(Stl stl,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,stlService.atterStl(stl,sysUser,"redo"));
        } catch (ServiceException e) {
            log.error("[StlController][redoStl] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[StlController][redoStl] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.UPDATEFANS,"");
        }
    }

    /**
     * 修改盘点清单
     * @author HHe
     * @date 2019/9/23 15:18
     */
    @RequestMapping(value = "/updatestl",method = RequestMethod.POST)
    public JsonResult updateStl(@RequestBody StlModel stlModel,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,stlService.updateStl(stlModel,sysUser));
        } catch (ServiceException e) {
            log.error("[StlController][updateStl] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[StlController][updateStl] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.UPDATEFANS,"");
        }
    }

    /**
     * 删除盘点清单
     * @author HHe
     * @date 2019/9/23 16:24
     */
    @RequestMapping(value = "/delstl",method = RequestMethod.GET)
    public JsonResult delStl(StlModel stlModel,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,stlService.delStl(stlModel,sysUser));
        } catch (ServiceException e) {
            log.error("[StlController][delStl] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[StlController][delStl] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.DELETEFANS,"");
        }
    }
}
