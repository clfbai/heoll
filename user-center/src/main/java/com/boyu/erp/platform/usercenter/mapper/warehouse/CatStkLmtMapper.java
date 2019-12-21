package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.CatStkLmt;
import com.boyu.erp.platform.usercenter.model.wareh.CatStkLmtModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.CatStkLmtVo;

import java.util.List;

public interface CatStkLmtMapper {
    int deleteByPrimaryKey(CatStkLmt key);

    /**
     * 功能描述: 不保留组织设置库存策略时删除
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/20 20:39
     */
    int deleteProdKey(CatStkLmt key);

    int insert(CatStkLmt record);

    int insertSelective(CatStkLmt record);

    CatStkLmt selectByPrimaryKey(CatStkLmt key);

    int updateByPrimaryKeySelective(CatStkLmt record);

    int updateByPrimaryKey(CatStkLmt record);

    /**
     * 功能描述: 查询分类库存预警
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/11 17:42
     */
    List<CatStkLmtVo> selectList(CatStkLmtModel catStkLmtVo);
}