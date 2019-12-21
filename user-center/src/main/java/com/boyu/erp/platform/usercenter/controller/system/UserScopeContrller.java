package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.system.UserScopeServer;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * 类描述:  用户范围权限查询
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/8 19:51
 */
@Slf4j
@RestController
@RequestMapping("/user/scope")
public class UserScopeContrller extends BaseController {
    @Autowired
    private UserScopeServer uerScopeServer;
    @Autowired
    private SysUnitService unitService;
    @Autowired
    private ProdClsUtils prodClsUtils;

    /**
     * 查询用户查看组织列表
     */
    @PostMapping(value = "/list")
    public JsonResult list(@RequestBody UnitDomainVo vo, HttpServletRequest re) {
        try {
            SysUser user =  this.isNullUser(re);
            /**
             * 目前遇到BUG  再别地方修改信息后 回来查询此接口，用户user.getUnit().getUnitHierarchy() 为空
             * 因此手动查询赋值
             * */
            if (user.getUnit() == null || user.getUnit().getUnitHierarchy() == null) {
                user.setUnit(unitService.selectByPrimaryKey(user.getOwnerId()));
            }
            Set<UnitDomainVo> set=uerScopeServer.getUnitScope(user, vo);
            if (CollectionUtils.isNotEmpty(set)) {
                m:
                for (UnitDomainVo v : set) {
                    if (v.getUnitId().equals(user.getOwnerId())) {
                        set.remove(v);
                        break m;
                    }
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", RestulMap.getResut(set));
        }catch (ServiceException ex) {
            log.error("[UserPrivService][updateUserPriv] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UserRoleController][listgoods] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 功能描述:  查询用户在选中组织的权限信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/10 11:00
     */
    @PostMapping(value = "/unitscope/priv")
    public JsonResult getUnitPriv(@RequestBody UnitDomainVo vo, HttpServletRequest re) {
        try {
            SysUser user =  this.isNullUser(re);
            Map<String, Object> map = prodClsUtils.getMap();
            map.put("priv", uerScopeServer.getUnitScopePriv(vo, user, ""));
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", map);
        } catch (ServiceException ex) {
            log.error("[UserPrivService][updateUserPriv] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UserRoleController][getUnitPriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

}
