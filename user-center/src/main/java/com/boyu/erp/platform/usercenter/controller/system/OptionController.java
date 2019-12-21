package com.boyu.erp.platform.usercenter.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname OptionController
 * @Description TODO
 * @Date 2019/5/4 16:35
 * @Created by js
 */
@Slf4j
@RestController
@RequestMapping("/user/option")
public class OptionController extends BaseController {


    @Autowired
    private SysCodeDtlService codeDtlService;

    /**
     * 获取下拉框数据
     *
     * @param dtl
     * @return
     */
    @RequestMapping(value = "/getOptionData", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionData(@RequestBody SysCodeDtl[] dtl) {
        try {
            Map<String, List<OptionVo>> map = new HashMap<>();
            for (int i = 0; i < dtl.length; i++) {
                List<SysCodeDtl> sysCodeDtls = codeDtlService.selectAllNoPage(dtl[i]);
                List<OptionVo> menuId = new ArrayList<>();
                for (SysCodeDtl dtl1 : sysCodeDtls) {
                    menuId.add(new OptionVo(dtl1.getDescription(), dtl1.getCode()));
                }
                map.put(dtl[i].getCodeType(), menuId);
            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (Exception ex) {
            log.error("[SysCodeDtlController][getcodeDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 下拉框数据
     */
    @RequestMapping(value = "/getoption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUnitOption() {
        Map<String, List<OptionVo>> map = new HashMap<>();

        //执照类别
        List<OptionVo> licType = new ArrayList<>();
        licType.add(new OptionVo("实业有限公司", "0"));
        licType.add(new OptionVo("个体工商户", "1"));
        licType.add(new OptionVo("个体独资企业", "2"));
        licType.add(new OptionVo("合伙企业", "3"));
        licType.add(new OptionVo("有限责任公司", "4"));
        licType.add(new OptionVo("股份有限公司", "5"));
        licType.add(new OptionVo("农民专业合作社", "6"));

        //是否共享
        List<OptionVo> shared = new ArrayList<>();
        shared.add(new OptionVo("是", "T"));
        shared.add(new OptionVo("否", "F"));

        //可征募
        List<OptionVo> recruitable = shared;

        //组织状态
        List<OptionVo> unitStatus = new ArrayList<>();
        unitStatus.add(new OptionVo("活动", "A"));
        unitStatus.add(new OptionVo("非活动", "F"));

        //领域状态
        List<OptionVo> domainStatus = unitStatus;
        //用户状态
        List<OptionVo> userStatus = new ArrayList<>();
        userStatus.add(new OptionVo("活动", "A"));
        userStatus.add(new OptionVo("非活动", "F"));
        //人员状态
        List<OptionVo> prsnlStatus = new ArrayList<>();
        prsnlStatus.add(new OptionVo("活动", "A"));
        prsnlStatus.add(new OptionVo("非活动", "F"));
        //性别
        List<OptionVo> gender = new ArrayList<>();
        gender.add(new OptionVo("男", "M"));
        gender.add(new OptionVo("女", "F"));
        //证件类型
        List<OptionVo> idType = new ArrayList<>();
        idType.add(new OptionVo("身份证", "01"));
        idType.add(new OptionVo("护照", "02"));
        idType.add(new OptionVo("军人证", "03"));
        //机器控制
        List<OptionVo> machCtrl = new ArrayList<>();
        machCtrl.add(new OptionVo("是", "T"));
        machCtrl.add(new OptionVo("否", "F"));
        //菜单
        List<OptionVo> menuId = new ArrayList<>();
        menuId.add(new OptionVo("收银台工具", "0"));
        menuId.add(new OptionVo("加盟BOSS", "1"));
        menuId.add(new OptionVo("信息中心", "2"));
        menuId.add(new OptionVo("总部报表", "3"));
        //用户类型
        List<OptionVo> userType = new ArrayList<>();
        userType.add(new OptionVo("普通用户", "0"));
        userType.add(new OptionVo("其他用户", "CM"));




        map.put("prsnlStatus", prsnlStatus);
        map.put("userStatus", userStatus);
        map.put("gender", gender);
        map.put("idType", idType);
        map.put("machCtrl", machCtrl);
        map.put("menuId", menuId);
        map.put("userType", userType);
        map.put("licType", licType);
        map.put("shared", shared);
        map.put("recruitable", recruitable);
        map.put("unitStatus", unitStatus);
        map.put("domainStatus", domainStatus);

        return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
    }


    /**
     * 获取单据功能下拉框数据
     *
     * @return
     */
    @RequestMapping(value = "/getBillOptionData", method = {RequestMethod.POST})
    public JsonResult getBillOptionData(@RequestBody JSONObject jsonObject) {
        try {
            String codeType=jsonObject.get("codeType").toString();
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", codeDtlService.optionGet(codeType));
        } catch (Exception ex) {
            log.error("[OptionController][getBillOptionData] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


}
