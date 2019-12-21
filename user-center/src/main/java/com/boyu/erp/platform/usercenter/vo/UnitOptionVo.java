package com.boyu.erp.platform.usercenter.vo;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import lombok.Data;

import java.util.Map;

/**
 * @Classname UnitOptionVo
 * @Description TODO
 * @Date 2019/6/21 19:43
 * @Created wz
 * 采购协议中弹窗公共类
 */
public class UnitOptionVo extends SysUnit {

    /**
     * 组织id
     */
    private Long sUnitId;

    /**
     * 组织id
     */
    private Long unitId;

    /**
     * 下拉选型
     */
    private String unitType;

    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    private String unitName;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 电话号码
     */
    private String telNum;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人姓名
     */
    private String fullName;

    /**
     * 组织状态  默认活动中
     */
    private String unitStatus;

    private String unitStatusCp;
    /**
     * 属主ID
     */
    private Long ownerId;
    /**
     * 组织编号
     */
    private String unitNum;

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }

    @Override
    public Long getUnitId() {
        return unitId;
    }

    @Override
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Override
    public String getUnitType() {
        return unitType;
    }

    @Override
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Override
    public String getUnitCode() {
        return unitCode;
    }

    @Override
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String getInputCode() {
        return inputCode;
    }

    @Override
    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Override
    public String getTelNum() {
        return telNum;
    }

    @Override
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getUnitStatus() {
        return unitStatus;
    }

    @Override
    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
    }

    public String getUnitStatusCp() {
        return unitStatusCp;
    }

    public void setUnitStatusCp(String unitStatusCp) {
        this.unitStatusCp = unitStatusCp;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }
}
