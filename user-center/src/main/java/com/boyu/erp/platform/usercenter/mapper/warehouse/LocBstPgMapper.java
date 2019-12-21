package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.LocBstPg;

public interface LocBstPgMapper {
    int deleteByPrimaryKey(LocBstPg key);

    /**
     * 删除仓库时删除货位装箱未决库存
     */
    int deleteLocBstPgWareh(Long key);

    int insertSelective(LocBstPg record);

    LocBstPg selectByPrimaryKey(LocBstPg key);

    int updateByPrimaryKeySelective(LocBstPg record);

}