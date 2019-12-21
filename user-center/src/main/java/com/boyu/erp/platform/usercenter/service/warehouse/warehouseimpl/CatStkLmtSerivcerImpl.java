package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.CatStkLmt;
import com.boyu.erp.platform.usercenter.mapper.warehouse.CatStkLmtMapper;
import com.boyu.erp.platform.usercenter.model.wareh.CatStkLmtModel;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.warehouse.CatStkLmtSerivce;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.warehouse.CatStkLmtVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatStkLmtSerivcerImpl implements CatStkLmtSerivce {
    @Autowired
    private CatStkLmtMapper catStkLmtMapper;
    @Autowired
    private SysUnitService unitService;

    /**
     * 功能描述:   库存策略 全局代表组织Id为0 (全局即对所有组织有效。在判断时)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/18 15:38
     */
    @Override
    public PageInfo<CatStkLmtVo> selectPage(Integer page, Integer size, CatStkLmtModel model, SysUser user) {
        if ("GL".equalsIgnoreCase(model.getScopeUnit())) {
            //全局
            model.setUnitId(0L);
        }
        if ("CP".equalsIgnoreCase(model.getScopeUnit())) {
            //公司
            model.setUnitId(user.getOwnerId());
        }
        if (model.getUnitId() != null) {
            model.setSql(null);
        }
        if (model.getUnitId() == null && "LU".equalsIgnoreCase(model.getScopeUnit())) {
            List<SysUnit> list = unitService.selectHierarchy(user.getUnit());
            String sql = "";
            if (CollectionUtils.isNotEmpty(list)) {
                for (SysUnit u : unitService.selectHierarchy(user.getUnit())) {
                    sql += u.getUnitId() + ",";
                }
            }
            if (StringUtils.isNotEmpty(sql) && sql.indexOf(",") > 0) {
                sql = sql.substring(0, sql.length() - 1);
            }
            model.setSql(sql);
        }
        PageHelper.startPage(page, size);
        List<CatStkLmtVo> list = catStkLmtMapper.selectList(model);
        PageInfo<CatStkLmtVo> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public int addCatStkLmt(CatStkLmt catStkLmt) {
        return catStkLmtMapper.insertSelective(catStkLmt);
    }

    @Override
    public int addCatStkLmtList(List<CatStkLmt> list) {
        if (CollectionUtils.isNotEmpty(list))
            list.parallelStream().forEach(s -> this.addCatStkLmt(s));
        return 0;
    }

    @Override
    public int updateCatStkLmt(CatStkLmt catStkLmt) {
        return catStkLmtMapper.updateByPrimaryKeySelective(catStkLmt);
    }

    @Override
    public int updateCatStkLmtList(List<CatStkLmt> list) {
        if (CollectionUtils.isNotEmpty(list))
            list.parallelStream().forEach(s -> this.updateCatStkLmt(s));
        return 0;
    }

    @Override
    public int deleteCatStkLmt(CatStkLmt catStkLmt) {
        return catStkLmtMapper.deleteByPrimaryKey(catStkLmt);
    }

    @Override
    public int deleteCatStkLmtList(List<CatStkLmt> list) {
        if (CollectionUtils.isNotEmpty(list))
            list.parallelStream().forEach(s -> this.deleteCatStkLmt(s));
        return 0;
    }
    /**
     * 不保留组织设置库存策略时删除
     */
    @Override
    public void deleteCatStkLmtAll(CatStkLmt catStkLmt) {
        catStkLmtMapper.deleteProdKey(catStkLmt);
    }


}
