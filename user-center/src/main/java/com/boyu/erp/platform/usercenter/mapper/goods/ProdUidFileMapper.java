package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdUidFile;

public interface ProdUidFileMapper {
    int deleteByPrimaryKey(String prodUid);

    int deleteProdUidFile(Long warehid);

    int insertSelective(ProdUidFile record);

    ProdUidFile selectByPrimaryKey(String prodUid);

    int updateByPrimaryKeySelective(ProdUidFile record);


}