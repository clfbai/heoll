package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.UserBgModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.UserBgService;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.system.UserBgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @program: boyu-platform
 * @description: 用户品牌分组控制层
 * @author: clf
 * @create: 2019-05-24 14:14
 */
@Slf4j
@RestController
@RequestMapping("/user/bg")
public class UserBgController extends BaseController {
    @Autowired
    private UserBgService userBgService;


    /**
     * 查询用户品牌分组
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/TableList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getList(UserBgModel model, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            List<UserBgVo> list = userBgService.getUserBg(model);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", RestulMap.getResut(list));
        } catch (ServiceException ex) {
            log.error("[UserBgService][getUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserBgController][getList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * 查询用户品牌分组
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUserBg(HttpServletRequest request, UserBgModel model) {
        try {
            SysUser user = this.getSessionSysUser(request);
            userBgService.updateUserbg((SysUser) this.isNullUser(request), model);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "");
        } catch (ServiceException ex) {
            log.error("[UserBgService][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserController][updateUserBg] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }
}
