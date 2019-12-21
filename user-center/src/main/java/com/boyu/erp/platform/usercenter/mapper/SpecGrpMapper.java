package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.SpecGrp;

import java.util.List;

/**
 * 规格组
 */
public interface SpecGrpMapper {

    public List<SpecGrp> selectAll(SpecGrp specGrp);

    public SpecGrp selectByPrimaryKey(SpecGrp specGrp);

    public int insertSpecGrp(SpecGrp specGrp);

    public int updateSpecGrp(SpecGrp specGrp);

    public int deleteSpecGrp(SpecGrp specGrp);
    /**
     * 通过规格差规格组名称
     */
    SpecGrp getSpecAndSpecGrp(Spec spec);
}