package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdUidCnfl;

public interface ProdUidCnflMaaper {
    int deleteByPrimaryKey(ProdUidCnfl key);
    /**
     * 删除仓库时删除冲突码
     * */
    int deleteProdUidCnfl(Long warehId);

    int insertSelective(ProdUidCnfl record);

    ProdUidCnfl selectByPrimaryKey(ProdUidCnfl key);

    int updateByPrimaryKeySelective(ProdUidCnfl record);


}