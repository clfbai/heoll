package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import org.apache.ibatis.annotations.Param;

public interface PscMapper {
    int deleteByPrimaryKey(String pscNum);

    int insert(Psc record);

    int insertSelective(Psc record);

    //通过购销编号取状态
    Psc selectByPrimaryKey(String pscNum);

    int updateByPrimaryKeySelective(Psc record);

    //采购/销售合同添加购销合同
    int insertByPscVo(PscVo v);

    //采购/销售合同更新购销合同
    int updateByPscVo(PscVo v);

    //采购/销售中更改状态
    int updeteByGen(String pucGen,String slcGen,String pscNum);

    //开始分配
    int updateTaskAdd(String pscNum);

    //终止分配
    int updateTaskReduce(String pscNum);

    //功能方法中更改状态
    int updateByState(PscVo v);

    Psc selectBySrcDoc(Psc psc);

    /**
     * 判断输入原始合同是否正确
     * @return
     */
    int findBySrcDoc(@Param("srcType") String srcType,@Param("srcUnitId") Long srcUnitId,@Param("srcNum") String srcNum);

    /**
     * 判断输入购销合同是否正确
     * @return
     */
    Psc findByPscNum(@Param("srcType") String srcType,@Param("srcUnitId") Long srcUnitId,@Param("srcNum") String srcNum);

    /**
     * 更新退购信息
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    int updateByStbToRp(@Param("pscNum") String pscNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("flag") boolean flag);

    /**
     * 更新退销信息
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    int updateByStbToRs(@Param("pscNum") String pscNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("flag") boolean flag);

}