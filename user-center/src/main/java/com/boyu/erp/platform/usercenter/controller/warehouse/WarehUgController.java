package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehUgService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/warehouse/warehug")
public class WarehUgController extends BaseController {
    @Autowired
    private WarehUgService warehUgService;
    /**
     * 根据组织分组加载仓库分组
     *
     * @author HHe
     * @date 2019/9/5 10:32
     */
    @RequestMapping(value = "/loadwarehug", method = RequestMethod.GET)

    public JsonResult loadWarehUg(Long[] sysUgIds) {
        Map<String, Object> map = warehUgService.loadWarehUg(Arrays.asList(sysUgIds));
        return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
    }
}
