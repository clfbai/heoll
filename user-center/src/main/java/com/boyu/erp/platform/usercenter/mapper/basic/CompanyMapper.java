package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.Company;

import java.util.Map;

public interface CompanyMapper {
    int deleteByPrimaryKey(Long companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Long companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
    //供应商/采购商中新增公司
    int insertByCom(Map<String,Object> map);
    //供应商/采购商中修改公司
    int updateByCom(Map<String,Object> map);


}