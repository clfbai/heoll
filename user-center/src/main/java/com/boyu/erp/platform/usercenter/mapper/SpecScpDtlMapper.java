package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.SpecScpDtlKey;
import com.boyu.erp.platform.usercenter.vo.SpecScpDtlVo;
import java.util.List;
/**
 * 规格范围明细
 */
public interface SpecScpDtlMapper {

    public List<SpecScpDtlVo> selectAll(SpecScpDtlKey specScpDtlKey);

    public int insertSpecScpDtlKey(SpecScpDtlKey specScpDtlKey);

    public int updateSpecScpDtlKey(SpecScpDtlKey specScpDtlKey);

    public int deleteSpecScpDtlKey(SpecScpDtlKey specScpDtlKey);
}