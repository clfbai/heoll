package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.GoodsBrandPrivAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.brand.UnitBg;
import com.boyu.erp.platform.usercenter.entity.brand.UserBg;
import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.brand.UnitBgMapper;
import com.boyu.erp.platform.usercenter.model.UserBgModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.UserBgService;
import com.boyu.erp.platform.usercenter.service.system.BgService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.BgVo;
import com.boyu.erp.platform.usercenter.vo.system.UserBgVo;
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
 * 品牌分组
 */
@Slf4j
@RestController
@RequestMapping(value = "/product/brandgroup")
public class ProductBgController extends BaseController {

    @Autowired
    private BgService bgService;

    @Autowired
    private UserBgService userBgService;

    @Autowired
    private UnitBgMapper unitBgMapper;

    @Autowired
    private UsersService usersService;

    /**
     * 查询品牌分组
     *
     * @param page
     * @param size
     * @param bg
     * @return
     */

    @RequestMapping(value = "/selectAll", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getbrandgroup(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "15") Integer size,
                                    Bg bg) {
        try {
            PageInfo<BgVo> bgVoPageInfo = bgService.selectAll(page, size, bg);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", bgVoPageInfo);
        } catch (Exception ex) {
            log.error("[getbrandgroup][getbrandgroup] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 添加品牌分组
     *
     * @param bg
     * @param request
     * @return
     */
    @SystemLog(name = "添加品牌分组")
    @GoodsBrandPrivAuthority(name = "Bg")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addbrandgroup(@RequestBody Bg bg,
                                    HttpServletRequest request) {
        try {
            int insert = bgService.insert(returnBg(request, bg));
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", insert);
        } catch (Exception ex) {
            log.error("[getbrandgroup][addbrandgroup] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 修改品牌分组
     *
     * @param bg
     * @param request
     * @return
     */
    @SystemLog(name = "修改品牌分组")
    @GoodsBrandPrivAuthority(name = "Bg")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatebrandgroup(@RequestBody Bg bg,
                                       HttpServletRequest request) {
        try {
            int insert = bgService.updateByPrimaryKeySelective(returnBg(request, bg));
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", insert);
        } catch (Exception ex) {
            log.error("[getbrandgroup][updatebrandgroup] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除品牌分组
     * 1.  删除品牌分组表数据
     * 2.  删除品牌分组明细
     * 4. 删除用户品牌分组(打标)
     * 5. 删除组织品牌分组(打标)
     *
     * @param bg
     * @param
     * @return
     */
    @SystemLog(name = "删除品牌分组")
    @GoodsBrandPrivAuthority(name = "Bg")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletebrandgroup(@RequestBody Bg bg, HttpServletRequest request) {
        try {
            int insert = bgService.deleteBg(bg, this.getSessionSysUser(request));
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", insert);
        } catch (Exception ex) {
            log.error("[getbrandgroup][updatebrandgroup] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 用户拥有品牌分组
     *
     * @param bg
     * @param
     * @return
     */
    @RequestMapping(value = "/user/bg", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUserBg(UserBgModel bg) {
        try {
            List<UserBgVo> list = userBgService.getUserBg(bg);
            Map<String, Object> map = new HashMap<>();
            map.put("size", list == null ? 0 : list.size());
            map.put("list", list);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", map);
        } catch (Exception ex) {
            log.error("[getbrandgroup][updatebrandgroup] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 修改用户拥有品牌分组
     *
     * @param bg
     * @param
     * @return
     */
    @SystemLog(name = "修改用户拥有品牌分组")
    @RequestMapping(value = "/user/update/bg", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUserBg(@RequestBody UserBgModel bg, HttpServletRequest request) {
        try {
            SysUser user = new SysUser();
            user.setUserId(bg.getUserId());
            Long unitId = bg.getUnitId() == null ? bg.getOwnerId() : bg.getUnitId();
            user.setOwnerId(unitId);

            //普通用户
            Boolean b = false;
            if (usersService.getAdmin(user) == null) {
                UnitBg unitBg = new UnitBg();
                unitBg.setUnitId(unitId);
                List<UnitBg> list = unitBgMapper.unitBgList(unitBg);
                int a = 0;
                if (list.size() <= 0) {
                    return new JsonResult(JsonResultCode.FAILURE, "请先将品牌分组授予组织", "");
                }
                if (bg.getAddBg().size() > 0) {
                    /**
                     *普通用户先判断组织是否具有品牌分组
                     * */
                    for (UserBg bs : bg.getAddBg()) {
                        for (UnitBg un : list) {
                            if (un.getBgId().equalsIgnoreCase(bs.getBgId())) {
                                a++;
                            }
                        }
                    }
                    if (a < bg.getAddBg().size()) {
                        return new JsonResult(JsonResultCode.FAILURE, "请先将品牌分组授予组织", "");
                    }
                }
            }
            userBgService.updateUserbg(this.getSessionSysUser(request), bg);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
        } catch (Exception ex) {
            log.error("[getbrandgroup][updatebrandgroup] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    private Bg returnBg(HttpServletRequest request, Bg bg) {
        bg.setUpdTime(new Date());
        bg.setOprId(this.getSessionSysUser(request).getUserId());

        return bg;
    }

}
