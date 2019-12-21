package com.boyu.erp.platform.usercenter.controller.option;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehPopupSerivce;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehPopupController extends BaseController {

    @Autowired
    private WarehPopupSerivce warehPopupSerivce;

    /**
     * 发货方查询(按组织筛选)
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/wareh/warehInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getWarehInfo(@RequestBody WarehInfoVo vo) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehPopupSerivce.getWarehInfo(vo));
        } catch (ServiceException ex) {
            log.error("[WarehOptionSerivce][getCompanyinfo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_COMPANY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehOptionController][getDelivOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_COMPANY, "原始单据明细查询异常: WarehOptionController.getWarehInfo()", "");
        }
    }


    /**
     <select id="selectByOption" parameterType="com.boyu.erp.platform.usercenter.vo.UnitOptionVo"
     resultMap="BaseResultUnit">
     SELECT
     su.unit_id AS unitId,
     any_value(suo.unit_num) AS unitCode ,
     any_value(su.unit_name) AS unitName,
     any_value(su.input_code) AS inputCode,
     any_value(su.address) AS address,
     any_value(su.tel_num) AS telNum,
     any_value(su.unit_status) AS unitStatus,
     any_value(p.full_name) AS fullName
     FROM
     (SELECT a.* FROM SYS_UNIT_CLSF a WHERE 1=1 AND a.frz_type='0'
     <if test="unitType != null and unitType !=''">
     AND a.UNIT_TYPE = #{unitType}
     </if>
     <if test="sUnitId != null and sUnitId !=''">
     AND a.OWNER_ID = #{sUnitId}
     </if>
     ) suc
     LEFT JOIN SYS_UNIT su ON su.UNIT_ID = suc.UNIT_ID
     LEFT JOIN sys_prsnl p ON su.lm_id =p.prsnl_id
     LEFT JOIN SYS_UNIT_OWNER suo ON suo.UNIT_ID = su.UNIT_ID AND suo.OWNER_ID = suc.OWNER_ID
     WHERE 1=1 and su.unit_status!='D'
     <if test="unitCode != null and unitCode !=''">
     and suo.unit_num like CONCAT(#{unitCode}, '%')
     </if>
     <if test="unitName != null and unitName !=''">
     and su.unit_name like CONCAT('%', #{unitName}, '%')
     </if>
     <if test="inputCode != null and inputCode !=''">
     and su.input_code like CONCAT(#{inputCode}, '%')
     </if>
     <if test="unitStatus != null and unitStatus !=''">
     and su.unit_status = #{unitStatus}
     </if>
     GROUP BY su.unit_id
     </select>
     */
}
