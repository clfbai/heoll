package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.CatStkLmt;
import com.boyu.erp.platform.usercenter.model.wareh.CatStkLmtModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.CatStkLmtVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CatStkLmtSerivce {
    PageInfo<CatStkLmtVo> selectPage(Integer page, Integer size, CatStkLmtModel model, SysUser user);

    int addCatStkLmt(CatStkLmt catStkLmt);

    int addCatStkLmtList(List<CatStkLmt> list);

    int updateCatStkLmt(CatStkLmt catStkLmt);

    int updateCatStkLmtList(List<CatStkLmt> list);

    int deleteCatStkLmt(CatStkLmt catStkLmt);

    int deleteCatStkLmtList(List<CatStkLmt> list);

    /**
     * 不保留组织设置库存策略时删除
     */
    void deleteCatStkLmtAll(CatStkLmt catStkLmt);

}
