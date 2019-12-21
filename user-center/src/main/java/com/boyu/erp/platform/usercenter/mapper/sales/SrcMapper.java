package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.Src;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SrcMapper {
    int deleteByPrimaryKey(Src key);

    int insert(Src record);

    int insertSelective(Src record);

    Src selectByPrimaryKey(Src key);

    //根据退货合同查询退销合同
    Src selectByRtcNum(String rtcNum);

    int updateByPrimaryKeySelective(Src record);

    int updateByPrimaryKey(Src record);

    int deleteByPrc(String unitId,String rtcNum);

    //更新审核信息
    int updateByChkr(String chkrId,String rtcNum);

    //系统管理员查询
    List<PrcVo> selectALL(PrcVo vo);
    //组织查询
    List<PrcVo> selectByUnit(PrcVo vo);

    //新增
    int insertByRtcVo(PrcVo vo);

    //更新
    int updateByRtcVo(PrcVo vo);

    //根据组织/退销合同号删除
    int deleteRtcVo(String unitId,String srcNum);

    /**
     * 出入库明细查询
     * @param vo
     * @return
     */
    PrcVo selectObject(PrcVo vo);

    //查询单挑数据
    PrcVo selectByOnly(PrcVo vo);

    /**
     * 出入库明细查询2
     * @param vo
     * @return
     */
    WarehSrcInfoVo selectByWareh(PrcVo vo);

    /**
     * 出入库明细查询3
     * @param vo
     * @return
     */
    WarehSrcInfoVo findByWareh(PrcVo vo);

    /**
     * 收货/取消收货时同步出库数据
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    PrcVo getSrcByStb(@Param("srcDocUnitId") Long srcDocUnitId, @Param("srcDocNum") String srcDocNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag);
}