package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.brand.BgDtl;
import com.boyu.erp.platform.usercenter.model.BgDtlModel;
import com.boyu.erp.platform.usercenter.vo.BgVo;

import java.util.List;

/**
 * @Classname BgDtlService
 * @Description TODO
 * @Date 2019/5/7 11:23
 * @Created by js
 * 品牌分组明细
 */
public interface BgDtlService {

    public List<BgDtl> selectAll(BgDtl dtl);

    public int insert(BgDtl record);

    public int insertSelective(BgDtl record);

    public int deleteBgDtl(BgDtl dtl);

    public int updateBgDtl(BgVo vo);


    public int updateBgAndBgDtl(BgDtlModel model);

}
