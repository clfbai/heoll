package com.boyu.erp.platform.usercenter.controller.system.table;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: boyu-platform_text
 * @description: 数据库表字段表名控制层
 * @author: clf
 * @create: 2019-06-27 15:05
 */
@Slf4j
@RestController
@RequestMapping("/table")
public class TableController extends BaseController {


    @Autowired
    private TableService tableService;

    /**
     * 查询表字段和注释
     *
     * @param tableFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTableFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getTableFile(@RequestBody TableFile tableFile, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, tableService.getTable(tableFile));
        } catch (Exception ex) {
            log.error("[TableController][getTableFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询数据库表名
     *
     * @param tableFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTableName", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult findByTableName(@RequestBody TableFile tableFile, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, tableService.findBydatabaseName(tableFile));
        } catch (Exception ex) {
            log.error("[TableController][getTableName] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }
}
