package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PscType;

import java.util.List;

import com.boyu.erp.platform.usercenter.entity.purchase.PsiType;
import com.boyu.erp.platform.usercenter.vo.partner.PscTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import org.apache.ibatis.annotations.Param;

public interface PscTypeMapper {
    int deleteByPrimaryKey(String pscType);

    int insert(PscType record);

    int insertSelective(PscType record);

    PscType selectByPrimaryKey(String pscType);

    int updateByPrimaryKeySelective(PscType record);

    int updateByPrimaryKey(PscType record);

    List<PurKeyValue> optionGet();

    /**
     * 购销合同查询
     * @param pscType
     * @return
     */
    List<PscTypeVo> selectALL(PscType pscType);
}