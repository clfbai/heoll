package com.boyu.erp.platform.usercenter.mapper.brand;

import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.model.UnitBgModel;
import com.boyu.erp.platform.usercenter.vo.BgVo;

import java.util.List;

public interface BgMapper {

    public List<BgVo> selectAll(Bg bg);

    public List<Bg> getUnitBg(UnitBgModel mode);

    public Bg selectByPrimaryKey(String bgId);

    public int updateByPrimaryKeySelective(Bg record);

    public int insert(Bg record);

    public int deleteBg(Bg bg);

}