package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Puc;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PucMapper {

    int insert(Puc record);

    int insertSelective(Puc record);

    Puc selectByPrimaryKey(Puc key);

    int updateByPrimaryKeySelective(Puc record);

    int updateByPrimaryKey(Puc record);

    //系统管理员查询
    List<PscVo> selectALL(PscVo vo);

    //组织查询
    List<PscVo> selectByUnit(PscVo vo);

    //添加
    int insertByPscVo(PscVo vo);

    //修改
    int updateByPscVo(PscVo vo);

    //删除
    int deletePscVo(String unitId, String pucNum);

    Puc selectByPscNum(String pscNum);

    //更新审核信息
    int updateByChkr(String chkrId, String pscNum);

    int deleteByPscNum(String pscNum);

    //查询单条
    PscVo selectByOnly(PscVo vo);

    /**
     * 出入库明细查询
     * @param vo
     * @return
     */
    PscVo selectObject(PscVo vo);

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
     * 收货/取消收货时同步出库数据
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    PscVo getPucByStb(@Param("srcDocUnitId") Long srcDocUnitId, @Param("srcDocNum") String srcDocNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag);

}