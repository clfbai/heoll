package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Prc;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrcMapper {
    int deleteByPrimaryKey(Prc key);

    int insert(Prc record);

    int insertSelective(Prc record);

    Prc selectByPrimaryKey(Prc key);

    int updateByPrimaryKeySelective(Prc record);

    int updateByPrimaryKey(Prc record);

    //系统管理员查询
    List<PrcVo> selectALL(PrcVo vo);
    //组织查询
    List<PrcVo> selectByUnit(PrcVo vo);
    //添加
    int insertByPrcVo(PrcVo vo);
    //跟新状态
    int updateByPrcVo(PrcVo vo);

    int deletePrcVo(String unitId,String prcNum);

    //更新审核信息
    int updateByChkr(String chkrId , String rtcNum);

    Prc selectByRtcNum(String rtcNum);

    int deleteByRtcNum(String rtcNum);

    //查询单条
    PrcVo selectByOnly(PrcVo vo);

    /**
     * 出入库明细查询
     * @param vo
     * @return
     */
    PrcVo selectObject(PrcVo vo);

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
     * 发货/取消发货时同步出库数据
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    PrcVo getPrcByStb(@Param("srcDocUnitId") Long srcDocUnitId, @Param("srcDocNum") String srcDocNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag);
}