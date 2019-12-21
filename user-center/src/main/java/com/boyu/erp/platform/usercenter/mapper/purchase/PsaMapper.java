package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.vo.purchase.OptionByPsaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;

import java.util.List;

public interface PsaMapper {
    int deleteByPrimaryKey(Psa key);

    int insert(Psa record);

    int insertSelective(Psa record);

    int updateByPrimaryKey(Psa record);

    /**
     * 采购协议系统管理员查询
     * @param psa
     * @return
     */
    List<PsaVo> selectALLByVde(PsaVo psa);

    /**
     * 销售协议系统管理员查询
     * @param psa
     * @return
     */
    List<PsaVo> selectALLByVdr(PsaVo psa);

    //查询
    List<Psa> selectByPrimaryKey(PsaVo psaVo);

    //查询是否有不同协议控制
    List<Psa> selectByDifference(PsaVo psaVo);

    int insertPsa(PsaVo pvd);

    int deletePsa(PsaVo psaVo);

    int updatePsa(PsaVo psaVo);

    /**
     * 采购协议组织用户查询
     * @param psa
     * @return
     */
    List<PsaVo> selectUserListByVde(PsaVo psa);

    /**
     * 销售协议组织用户查询
     * @param psa
     * @return
     */
    List<PsaVo> selectUserListByVdr(PsaVo psa);

    /**
     * 采购中查询
     * @param vo
     * @return
     */
    OptionByPsaVo selectByPsaByVde(OptionByPsaVo vo);

    /**
     * 销售中查询
     * @param vo
     * @return
     */
    OptionByPsaVo selectByPsaByVdr(OptionByPsaVo vo);

    //采购协议
    Psa selectByVdr(Psa psa);

    //销售协议
    Psa selectByVde(Psa psa);
}