package com.boyu.erp.platform.usercenter.utils;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DomainUtils {

    public static List<Object> getDomain(List<DomainAndUnitVo> list, SysUser user) {
        List<DomainAndUnitVo> listvo = new ArrayList<>();
        log.info("-------------------->查看用户组织:   " + user.getOwnerId());
        DomainAndUnitVo v = new DomainAndUnitVo();
        v = null;
        for (DomainAndUnitVo vo : list) {
            if (vo.getUnitId().equals(user.getOwnerId())) {
                v = vo;
            }
        }
        log.info("组织对象:   " + v.getUnitId());
        if (v == null) {
            return null;
        }
        List<Object> gt = new ArrayList<>();
        for (DomainAndUnitVo vo : list) {
            if (v.getUnitId().equals(vo.getCtrlUnitId())) {
                listvo.add(vo);
            }
        }
        //删除掉当前组织  因为默认CtrlUnitId等于UnitId 这样会在递归时会内存溢出 (或有可能当前组织的)
        listvo.remove(v);
        log.info("递归对象:   " + listvo.size());
        if (listvo.size() > 0) {
            for (DomainAndUnitVo vo : listvo) {
                setDomainVo(vo.getUnitId(), list, gt);
            }
        }
        gt.add(v);
        if (listvo.size() > 0) {
            gt.addAll(listvo);
        }
        return gt;
    }

    private static List<Object> setDomainVo(Long unitId, List<DomainAndUnitVo> list, List<Object> gt) {
        List<DomainAndUnitVo> chelist = new ArrayList<>();
        for (DomainAndUnitVo vo : list) {
            if (vo.getCtrlUnitId().equals(unitId)) {
                chelist.add(vo);
                gt.add(vo);
            }
        }
        if (chelist.size() == 0) {
            return null;
        }
        for (DomainAndUnitVo vo : chelist) {
            /**
             *重复步骤1.
             * */
            setDomainVo(vo.getUnitId(), list, gt);
        }
        return null;
    }

    public static List<Object> likeDomain(List<DomainAndUnitVo> max, List<DomainAndUnitVo> list, SysUser user) {
        List<Object> restMax = getDomain(max, user);
        List<DomainAndUnitVo> v = new ArrayList<>();
        List<Object> restList = new ArrayList<>();

        for (Object vo : restMax) {
            DomainAndUnitVo vos = new DomainAndUnitVo();
            if (vo != null) {
                vos = (DomainAndUnitVo) vo;
                v.add(vos);
            }
        }
        for (DomainAndUnitVo vo : v) {
            for (DomainAndUnitVo vos : list) {
                if (vo.getUnitId().equals(vos.getUnitId())) {
                    restList.add(vos);
                }
            }
        }

        return restList;
    }

}
