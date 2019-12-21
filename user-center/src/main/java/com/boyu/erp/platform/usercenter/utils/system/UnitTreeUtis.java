package com.boyu.erp.platform.usercenter.utils.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.vo.system.UnitTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:  组织树形结构工具类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/5 14:41
 */
@Slf4j
@Component
public class UnitTreeUtis {
    @Autowired
    private SysUnitMapper unitMapper;

    //查询当前组织下所有下级组织
    public List<SysUnit> getUnit(Long unitId) {
        SysUnit unit = unitMapper.selectByPrimaryKey(unitId);
        return unitMapper.findHierarchy(unit);
    }


    public UnitTreeVo getUnitTree(Long unitId) {
        List<SysUnit> list = this.getUnit(unitId);
        //转换集合
        return this.method(list,unitId);
    }

    //查询当前组织下所有下级组织
    public List<SysUnit> getUnitByType(SysUnit unit) {
        SysUnit sysUnit = unitMapper.selectByPrimaryKey(unit.getUnitId());
        //查询不到主节点    添加进去
        List<SysUnit> list = unitMapper.findHierarchyByType(unit.getUnitType(),sysUnit.getUnitId()+"",sysUnit.getUnitHierarchy());
        list.add(sysUnit);
        return list;
    }

    public UnitTreeVo getUnitTreeByType(SysUnit unit) {
        List<SysUnit> list = this.getUnitByType(unit);
        //转换集合
        return this.method(list,unit.getUnitId());

    }

    private UnitTreeVo method(List<SysUnit> list,Long unitId) {
        //转换集合
        List<UnitTreeVo> listVo = new ArrayList<>();
        //返回集合
        UnitTreeVo returnList = new UnitTreeVo();
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysUnit u : list) {
                UnitTreeVo vo = new UnitTreeVo();
                BeanUtils.copyProperties(u, vo);
                vo.setParentId(0L);
                //层级从1开始
                vo.setLevel(1);
                listVo.add(vo);
            }

            for (UnitTreeVo vo : listVo) {
                if (vo.getUnitId().equals(unitId)) {
                    //根节点
                    vo.setTreeVos(setTree(vo, listVo));
                    returnList = vo;
                }
            }
            return returnList;
        }
        return null;
    }

    private List<UnitTreeVo> setTree(UnitTreeVo vo, List<UnitTreeVo> listVo) {
        //取传入层级 调用一次自增
        int a = vo.getLevel();
        a++;
        List<UnitTreeVo> childList = new ArrayList<>();
        for (UnitTreeVo addVo : listVo) {
            if (addVo.getUnitHierarchy().equalsIgnoreCase(vo.getUnitHierarchy() + "|" + addVo.getUnitCode())) {
                addVo.setParentId(vo.getUnitId());
                //层级赋值
                addVo.setLevel(a);
                //有下级节点
                vo.setLast(false);
                childList.add(addVo);
            }
        }
        if (childList.size() == 0) {
            //没有下级节点
            vo.setLast(true);
            List<UnitTreeVo> list = new ArrayList<>();

            return list;
        }
        for (UnitTreeVo addVo : childList) {
            addVo.setTreeVos(setTree(addVo, listVo));
        }
        return childList;
    }


}
