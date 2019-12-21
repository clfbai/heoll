package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysMenu;
import com.boyu.erp.platform.usercenter.entity.system.SysMenuDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysMenuSerivce;
import com.boyu.erp.platform.usercenter.vo.system.TreeKeyValue;
import com.boyu.erp.platform.usercenter.vo.system.TreeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单Tree
 */
@Slf4j
@RestController
@RequestMapping("/user/menu")
public class SystemTreeController extends BaseController {

    @Autowired
    private SysMenuSerivce menuSerivceService;


    /**
     * 数据库所有菜单目录
     */
    @RequestMapping(value = "/tree", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getAllTree(HttpServletRequest request) {
        try {
            this.isNullUser(request);
            SysMenu menu = new SysMenu();
            menu.setMenuId(this.getSessionSysUser(request).getMenuId());
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", menuSerivceService.getMenuTree(menu));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SytemTree][getAllTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 用户菜单目录
     */
    @RequestMapping(value = "/treepriv", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUserTreePriv(HttpServletRequest request) {
        try {
            this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", menuSerivceService.getUserTreeOne(this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SytemTree][getUserTreePriv] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 添加菜单路径或项目
     */
    @SystemLog(name = "添加菜单路径或项目")
    @RequestMapping(value = "/addtree", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addTree(HttpServletRequest request, @RequestBody TreeModel menuDtl) {
        try {
            this.getSessionSysUser(request);
            SysMenuDtl dtl = new SysMenuDtl();
            if (menuDtl.getFatherNodeId() != 0) {
                dtl.setNodeId(menuDtl.getFatherNodeId());
                //通过父级Id获取父级节点信息
                SysMenuDtl dtrs = menuSerivceService.findByMenudtl(dtl);
                BeanUtils.copyProperties(menuDtl, dtl);
                dtl.setParnNodeId(dtrs.getNodeId());
                //设置隶属关系
                String host = dtrs.getHierarchy() + dtrs.getNodeId() + "|";
                dtl.setHierarchy(host);
            } else {
                //最大节点
                BeanUtils.copyProperties(menuDtl, dtl);
                dtl.setParnNodeId(0L);
                dtl.setHierarchy("|0|");
            }
            dtl.setNodeId(null);
            dtl.setMenuId(this.getSessionSysUser(request).getMenuId());
            /*for (SysMenuDtl ms : menuSerivceService.getAll()) {
                if (ms.getDescription().equalsIgnoreCase(dtl.getDescription())) {
                    return new JsonResult(JsonResultCode.FAILURE, "The menu already exists", "");
                }
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, "添加成功", menuSerivceService.addTree(dtl));
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SytemTree][addTree] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加失败", "");
        }
    }


    /**
     * 通过目录Id查询下级
     */
    @RequestMapping(value = "/treesube", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult treeSube(HttpServletRequest request, TreeModel menuDtl) {
        try {
            this.getSessionSysUser(request);
            List<TreeModel> treeModels = menuSerivceService.treeSube(menuDtl, this.getSessionSysUser(request));
            List<TreeKeyValue> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            if (treeModels.size() > 0) {
                for (TreeModel m : treeModels) {
                    TreeKeyValue ms = new TreeKeyValue(m.getDescription(), m.getNodeId());
                    list.add(ms);
                }
            }
            map.put("list", list);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SytemTree][treeSube] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 删除目录或路径
     */
    @SystemLog(name = "删除目录或路径")
    @RequestMapping(value = "/deletetree", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteTree(HttpServletRequest request, @RequestBody TreeModel menuDtl) {
        try {
            if (this.getSessionSysUser(request) == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登陆后再操作", "");
            }

            menuSerivceService.deletTree(menuDtl);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", "");
        } catch (Exception ex) {
            log.error("[SytemTree][treeSube] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除失败", "");
        }
    }

    /**
     * 修改目录或路径
     */
    @SystemLog(name = "修改目录或路径")
    @RequestMapping(value = "/update/tree", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateTree(HttpServletRequest request, @RequestBody TreeModel menuDtl) {
        try {
            if (this.getSessionSysUser(request) == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登陆后再操作", "");
            }

            int a = menuSerivceService.updateTree(menuDtl);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", a);
        } catch (Exception ex) {
            log.error("[SytemTree][treeSube] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * 查询当前节点信息
     */
    @RequestMapping(value = "/tree/nodeid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult treeNodeId(HttpServletRequest request, @RequestBody TreeModel menuDtl) {
        try {
            if (this.getSessionSysUser(request) == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登陆后再操作", "");
            }
            SysMenuDtl dtl = menuSerivceService.findByNodeId(menuDtl);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", dtl);
        } catch (Exception ex) {
            log.error("[SytemTree][treeSube] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }
}


