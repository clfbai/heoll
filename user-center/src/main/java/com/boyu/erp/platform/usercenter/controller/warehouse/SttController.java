package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.SttFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.SttModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.SttService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 盘点controller
 * @author HHe
 * @date 2019/9/10 19:50
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/stt")
public class SttController extends BaseController {
    @Autowired
    private SttService sttService;
    @Autowired
    private DelivUtil delivUtil;
    /**
     * 加载盘点表下拉字段
     * @author HHe
     * @date 2019/9/12 14:57
     */
    @RequestMapping("/loadsttpulldown")
    public JsonResult loadSttPullDown(){
        Map<String,Object> map = sttService.loadSttPullDown();
        return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
    }

    /**
     * 盘点表根据筛选条件查询列表
     * @author HHe
     * @date 2019/9/10 20:11
     */
    @RequestMapping("/querysttlistbyfiltermodel")
    public JsonResult querySttListByFilterModel(@RequestBody SttFilterModel sttFilterModel, @RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "15")Integer size, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sttService.querySttListByFilterModel(sttFilterModel,page,size,sysUser));
        } catch (ServiceException e) {
            log.error("[SttController][querySttListByFilterModel] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][querySttListByFilterModel] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }

    /**
     * 查询盘点表详情
     * @author HHe
     * @date 2019/9/20 17:18
     */
    @RequestMapping(value = "/querydetails",method = RequestMethod.POST)
    public JsonResult queryDetails(@RequestBody Stt stt, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sttService.queryDetails(stt,sysUser));
        } catch (ServiceException e) {
            log.error("[SttController][queryDetails] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][queryDetails] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }

    /**
     * 添加盘点表（盘点计划）
     * @author HHe
     * @date 2019/9/16 11:17
     */
    @RequestMapping(value = "/addstt",method = RequestMethod.POST)
    public JsonResult addStt(@RequestBody SttModel sttModel, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,sttService.addStt(sttModel,sysUser));
        } catch (ServiceException e) {
            log.error("[SttController][addStt] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][addStt] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.ADDFIANL,"");
        }
    }

    /**
     * 输入仓库编号判断合法，并加载信息
     * @author HHe
     * @date 2019/9/17 11:57
     */
    @RequestMapping("/loadwarehmessbycode")
    public JsonResult loadWarehMessByCode(String warehCode,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sttService.loadWarehMessByCode(warehCode,sysUser));
        } catch (ServiceException e) {
            log.error("[SttController][loadWarehMessByCode] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][loadWarehMessByCode] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }

    /**
     * 修改盘点表
     * @author HHe
     * @date 2019/9/16 11:36
     */
    @RequestMapping(value = "/updatestt",method = RequestMethod.POST)
    public JsonResult updateStt(@RequestBody SttModel sttModel, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,sttService.updateStt(sttModel,sysUser));
        } catch (ServiceException e) {
            log.error("[SttController][updateStt] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][updateStt] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.UPDATEFANS,"");
        }
    }

    /**
     * 删除盘点表
     * @author HHe
     * @date 2019/9/16 11:40
     */
    @RequestMapping(value = "/delstt",method = RequestMethod.POST)
    public JsonResult delStt(@RequestBody Stt stt, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,sttService.delStt(stt,sysUser));
        } catch (ServiceException e) {
            log.error("[SttController][delStt] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][delStt] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.DELETEFANS,"");
        }
    }

//    /**
//     * 确认盘点表
//     * @author HHe
//     * @date 2019/9/18 9:59
//     */
//    @RequestMapping(value = "/affirmstt",method = RequestMethod.GET)
//    public JsonResult affirmStt(String sttNum,HttpServletRequest request){
//        try {
//            SysUser sysUser = (SysUser) this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,sttService.affirmStt(sttNum,sysUser));
//        } catch (ServiceException e) {
//            log.error("[SttController][affirmStt] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[SttController][affirmStt] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.UPDATEFANS,"");
//        }
//    }
//    /**
//     * 重做盘点表
//     * @author HHe
//     * @date 2019/9/19 16:37
//     */
//    @RequestMapping(value = "/reformstt",method = RequestMethod.GET)
//    public JsonResult reformStt(String sttNum,HttpServletRequest request){
//        try {
//            SysUser sysUser = (SysUser) this.isNullUser(request);
//            if(sysUser==null){
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,sttService.reformStt(sttNum,sysUser));
//        } catch (ServiceException e) {
//            log.error("[SttController][reformStt] ServiceException",e);
//            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
//        }catch (Exception e){
//            log.error("[SttController][reformStt] Exception",e);
//            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.UPDATEFANS,"");
//        }
//    }
    /**
     * 功能模块
     * @author HHe
     * @date 2019/9/26 12:32
     */
    @RequestMapping(value = "/checkStt",method = RequestMethod.GET)
    public JsonResult checkStt(String sttNum,String type,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,sttService.atterSttStatus( sttNum,type,sysUser));
        } catch (ServiceException e) {
            log.error("[SttController][checkStt] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][checkStt] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
        }
    }
    /**
     * 功能列表展示
     * @author HHe
     * @date 2019/10/14 20:03
     */
    @RequestMapping(value = "/funlist",method = RequestMethod.POST)
    public JsonResult funList(@RequestBody Stt stt, HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,sttService.queryFunList(stt));
        } catch (ServiceException e) {
            log.error("[SttController][funList] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][funList] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.SELECTFIANL,"");
        }
    }
    /**
     * 盘点表功能操作
     * @author HHe
     * @date 2019/10/15 10:19
     */
    @RequestMapping(value = "/sttfunction/{type}",method = RequestMethod.POST)
    public JsonResult sttFunction(@RequestBody SttModel sttModel,@PathVariable String type,HttpServletRequest request){
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if(sysUser==null){
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS,delivUtil.dynamicRequest("sttServiceImpl",type,sttModel));
        } catch (ServiceException e) {
            log.error("[SttController][sttFunction] ServiceException",e);
            return new JsonResult(JsonResultCode.FAILURE,e.getMessage(),"");
        }catch (Exception e){
            log.error("[SttController][sttFunction] Exception",e);
            return new JsonResult(JsonResultCode.FAILURE,CommonFainl.FUNCTIONOPFANS,"");
        }
    }
}

