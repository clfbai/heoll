package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.Slc;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlcMapper {
    int deleteByPrimaryKey(Slc key);

    int insert(Slc record);

    int insertSelective(Slc record);

    Slc selectByPrimaryKey(Slc key);

    int updateByPrimaryKeySelective(Slc record);

    int updateByPrimaryKey(Slc record);

    //系统管理员查询
    List<PscVo> selectALL(PscVo vo);
    //组织查询
    List<PscVo> selectByUnit(PscVo vo);

    Slc selectByPscNum(String pscNum);

    //更新审核信息
    int updateByChkr(String chkrId,String pscNum);

    //新增销售合同记录
    int insertByPscVo(PscVo vo);

    //更新表信息
    int updateByPscVo(PscVo vo);

    //根据组织与销售合同号删除
    int deletePscVo(String unitId,String slcNum);

    //通过购销合同号删除
    int deleteByPscNum(String pscNum);

    /**
     * 出入库明细查询
     * @param vo
     * @return
     */
    PscVo selectObject(PscVo vo);

    //查询单条记录
    PscVo selectByOnly(PscVo vo);

    /**
     * 出入库明细查询2
     * @param vo
     * @return
     */
    WarehSrcInfoVo selectByWareh(PscVo vo);

    /**
     * 出入库明细查询3
     * @param vo
     * @return
     */
    WarehSrcInfoVo findByWareh(PscVo vo);

    /**
     * 发货/取消发货时同步出库数据
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    PscVo getSlcByStb(@Param("srcDocUnitId") Long srcDocUnitId,@Param("srcDocNum") String srcDocNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("flag") boolean flag);

    /**
     * 调配单中查询唯一条销售合同记录
     * @param unitId
     * @param vendeeId
     * @param rgoNum
     * @return
     */
    PscVo selectByRgo(@Param("unitId") Long unitId,@Param("vendeeId") Long vendeeId,@Param("rgoNum") String rgoNum);
   }