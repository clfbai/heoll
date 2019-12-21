package com.boyu.erp.platform.usercenter.utils;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PrsnlUtils {


    public static List<SysPrsnlVo> getPrsnl(List<SysPrsnlVo> list, SysUser user) {

        List<SysPrsnlVo> listvo = new ArrayList<>();
        List<SysPrsnlVo> gt = new ArrayList<>();
        SysPrsnlVo v = new SysPrsnlVo();
        for (SysPrsnlVo vo : list) {
            if (vo.getUnitId().equals(user.getOwnerId())) {
                v = vo;
                gt.add(vo);
            }
        }
        for (SysPrsnlVo vo : list) {
            if (v.getUnitId().equals(vo.getShuunitId())) {
                listvo.add(vo);
            }
        }
        // 删除掉当前用户  当前用户属主Id  与unitId 可能一致  会导致递归内存溢出
        listvo.remove(v);
        for (SysPrsnlVo vo : listvo) {
            setDomainVo(vo.getUnitId(), list, gt);
        }
        gt.addAll(listvo);
        return gt;
    }

    private static List<SysPrsnlVo> setDomainVo(Long unitId, List<SysPrsnlVo> list, List<SysPrsnlVo> gt) {
        List<SysPrsnlVo> chelist = new ArrayList<>();
        for (SysPrsnlVo vo : list) {
            if (vo.getShuunitId().equals(unitId)) {
                chelist.add(vo);
                gt.add(vo);
            }
        }
        if (chelist.size() == 0) {
            return null;
        }
        for (SysPrsnlVo vo : chelist) {
            setDomainVo(vo.getUnitId(), list, gt);
        }
        return null;
    }


    public static List<SysPrsnlVo> getPrsnlisNull(List<SysPrsnlVo> max, List<SysPrsnlVo> like, SysUser user, SysPrsnlVo prsnl) {
        //用户能查看到结果集
        List<SysPrsnlVo> restMax = getPrsnl(max, user);
        //模糊查询返回结果集
        List<SysPrsnlVo> returnMax = new ArrayList<>();
        if (restMax == null || like == null) {
            return null;
        }
        for (SysPrsnlVo vo : restMax) {
            for (SysPrsnlVo v : like) {
                if (vo.getPrsnlId().equals(v.getPrsnlId())) {
                    returnMax.add(vo);
                }
            }
        }
        return returnMax;
    }
}
