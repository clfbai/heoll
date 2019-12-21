package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CaMapper {
    int deleteByPrimaryKey(Ca key);

    int insert(Ca record);

    int insertSelective(Ca record);

    Ca selectByPrimaryKey(Ca key);

    int updateByPrimaryKeySelective(Ca record);

    int updateByPrimaryKey(Ca record);

    //查询是否存在记录
    Ca selectByCaUnitId(Ca key);

    Ca selectByCaUnitIdDesc(Ca key);

    /**
     * 查询不符合删除条件的数据
     * @param key
     * @return
     */
    List<Ca> selectByDelete(Ca key);

    /**
     * 取消授信时修改ca表字段
     * @param list
     * @return
     */
    int updateByRevoke(List<CaAcc> list);

    //超级管理员查询
    List<CaVo> selectALL(CaVo vo);

    //组织/用户查询
    List<CaVo> selectByUnit(CaVo vo);
    /**
     * 查询往来账户
     * @author HHe
     * @date 2019/10/21 11:54
     */
    Ca queryCaByRcvAndUnit(@Param("fsclUnitId") Long fsclUnitId, @Param("rcvFsclUnitId") Long rcvFsclUnitId, @Param("caType") String caType);
}