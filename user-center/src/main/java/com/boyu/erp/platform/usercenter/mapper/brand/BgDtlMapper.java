package com.boyu.erp.platform.usercenter.mapper.brand;

import com.boyu.erp.platform.usercenter.entity.brand.BgDtl;
import com.boyu.erp.platform.usercenter.model.BgDtlModel;

import java.util.List;

public interface BgDtlMapper {

    List<BgDtl> selectAll(BgDtl dtl);

    int insert(BgDtl record);

    int insertSelective(BgDtl record);

    int deleteBgDtl(BgDtl dtl);

    int deleteBgList(BgDtl dtl);


    int updateBgDtl(BgDtlModel dtl);
}