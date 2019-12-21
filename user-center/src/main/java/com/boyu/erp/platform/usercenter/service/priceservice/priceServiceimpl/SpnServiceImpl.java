package com.boyu.erp.platform.usercenter.service.priceservice.priceServiceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.Price.Xpl;
import com.boyu.erp.platform.usercenter.entity.sales.SpnScp;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.Price.XplHMapper;
import com.boyu.erp.platform.usercenter.mapper.Price.XplMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SpnDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SpnMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SpnScpMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.service.priceservice.SpnService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.price.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SpnServiceImpl
 * @Description TODO
 * @Date 2019/8/27 11:47
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class SpnServiceImpl implements SpnService {

    @Autowired
    private SpnMapper spnMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private SpnDtlMapper spnDtlMapper;
    @Autowired
    private SpnScpMapper spnScpMapper;
    @Autowired
    private XplMapper xplMapper;
    @Autowired
    private XplHMapper xplHMapper;
    @Autowired
    private SysUnitMapper sysUnitMapper;


    /**
     * 查询销售价格单
     *
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    public PageInfo<SpnVo> selectAll(SpnVo vo, Integer page, Integer size, SysUser user) {
        List<SpnVo> list = null;
        /**
         * 系统管理员
         * */
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            PageHelper.startPage(page, size);
            list = spnMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = spnMapper.selectByUnit(vo);
        }
        PageInfo<SpnVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增销售价格单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertSpn(SpnVo vo, SysUser user) { //生成采购价格单单号
        String spnNum = sysRefNumService.viceNum(user, "SPN_NUM");
        vo.setSpnNum(spnNum);
        vo.setOprId(user.getPrsnl().getPrsnlId());
        vo.setEffective("F");
        vo.setCancelled("F");
        vo.setSuspended("F");
        vo.setProgress("PG");
        Map<String, Object> map = new HashMap<>();
        map.put("spnNum", vo.getSpnNum());
        if (vo.getDtlList() != null && vo.getDtlList().size() > 0) {
            map.put("dtlList", vo.getDtlList());
            spnDtlMapper.insertByMap(map);
        }
        if (vo.getScpList() != null && vo.getScpList().size() > 0) {
            map.put("scpList", vo.getScpList());
            spnScpMapper.insertByMap(map);
        }
        return spnMapper.insertSelective(vo);
    }

    /**
     * 修改销售价格单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updateSpn(SpnVo vo, SysUser user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setOprId(user.getPrsnl().getPrsnlId());
        vo.setOpTime(sdf.format(new Date()));
        return spnMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 删除
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deleteSpn(SpnVo vo, SysUser user) {
        //删除明细/范围相关数据
        spnDtlMapper.deleteBySpn(vo.getUnitId() + "", vo.getSpnNum());
        spnScpMapper.deleteBySpn(vo.getUnitId() + "", vo.getSpnNum());
        return spnMapper.deleteBySpn(vo.getUnitId() + "", vo.getSpnNum());
    }

    /**
     * 切换定价范围时删除范围
     *
     * @param vo
     * @return
     */
    @Override
    public int deleteSpnScp(SpnVo vo) {
        return spnScpMapper.deleteBySpn(vo.getUnitId() + "", vo.getSpnNum());
    }

    /**
     * 确认
     *
     * @param vo
     * @return
     */
    @Override
    public int confirm(SpnVo vo) {
        //查询出当前单据的所有范围
        List<SpnScpVo> list = spnScpMapper.selectBySpn(new SpnScpVo(vo.getUnitId(), vo.getSpnNum()));
        //定价范围为缺省，并且组织层级为空
        if (vo.getPrcScp().equals("D") && StringUtils.NullEmpty(vo.getUnitHierId())) {
            //如果为空就添加记录
            SpnScp scp = spnScpMapper.selectByConfirm(vo.getUnitId() + "", vo.getSpnNum());
            if (scp == null) {
                //删除其他记录
                spnScpMapper.deleteBySpn(vo.getUnitId() + "", vo.getSpnNum());
                //添加记录
                spnScpMapper.insertSelective(new SpnScp(vo.getUnitId(), vo.getSpnNum(), vo.getUnitId(), 0L));
            } else {
                if (list == null) {
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "必须指定销售价格单范围！");
                }
            }
        }

        //对于SPN_SCP.VENDER_ID不是SPN_SCP.UNIT_ID的记录，判断合法性
        if (StringUtils.isNotEmpty(vo.getUnitHierId())) {
            //查询不合法的记录  （SPN_SCP.VENDER_ID不是SPN_SCP.UNIT_ID的下级）
            List<SpnScpVo> legList = spnScpMapper.selectByLeg(vo.getSpnNum());
            if (legList != null) {
                //删除不合法的记录
                spnScpMapper.deleteByList(legList);
            }
        }

        vo.setProgress("C");
        return spnMapper.updateByState(vo);
    }

    /**
     * 重做单据时，如果是缺省。删除缺省的范围  更新状态
     *
     * @param vo
     * @return
     */
    @Override
    public int redo(SpnVo vo) {
        if (vo.getPrcScp().equals("D")) {
            //如果是缺省，删除这个范围
            spnScpMapper.deleteBySpn(vo.getUnitId() + "", vo.getSpnNum());
        }
        vo.setProgress("PG");
        return spnMapper.updateByState(vo);
    }

    /**
     * 审核单据 补充审核信息  更新状态
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(SpnVo vo, SysUser user) {
        vo.setChkrId(user.getPrsnl().getPrsnlId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setChkTime(sdf.format(new Date()));
        vo.setProgress("CK");
        return spnMapper.updateByState(vo);
    }

    /**
     * 重审
     * 删除审核信息 更新状态
     *
     * @param vo
     * @return
     */
    @Override
    public int reiterate(SpnVo vo) {
        vo.setChkrId(null);
        vo.setChkTime(null);
        vo.setProgress("C");
        return spnMapper.updateByState(vo);
    }

    /**
     * 挂起 更新状态
     *
     * @param vo
     * @return
     */
    @Override
    public int hangUp(SpnVo vo) {
        vo.setSuspended("T");
        return spnMapper.updateByState(vo);
    }

    /**
     * 恢复单据
     *
     * @param vo
     * @return
     */
    @Override
    public int recovery(SpnVo vo) {
        vo.setSuspended("F");
        return spnMapper.updateByState(vo);
    }

    /**
     * 作废单据
     *
     * @param vo
     * @return
     */
    @Override
    public int toVoid(SpnVo vo) {
        vo.setCancelled("T");
        return spnMapper.updateByState(vo);
    }

    @Override
    public int implement(SpnVo vo, SysUser user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = null;
        vo.setProgress("EX");
        vo.setEffective("T");
        vo.setExtrId(user.getPrsnl().getPrsnlId());
        vo.setExecTime(sdf.format(new Date()));
        //查出范围数据
        List<SpnScpVo> scpList = spnScpMapper.selectBySpn(new SpnScpVo(vo.getUnitId(), vo.getSpnNum()));

        //查出明细数据
        List<SpnDtlVo> dtlList = spnDtlMapper.selectBySpn(new SpnDtlVo(vo.getUnitId(), vo.getSpnNum()));

        //定价范围是缺省
        if (vo.getPrcScp().equals("D") && vo.getRsvUnit().equals("F")) {
            //取购销价格表记录
            List<Xpl> xplList = xplMapper.selectByRsvF(vo.getUnitId() + "", "0", "R");
            if (xplList != null && xplList.size() > 0) {
                map = new HashMap<String, Object>();
                map.put("unitId", vo.getUnitId());
                map.put("xpnNum", vo.getSpnNum());
                map.put("execTime", vo.getExecTime());
                map.put("prcOpr", vo.getExecTime());
                map.put("list", xplList);
                xplHMapper.insertByMap(map);
            }
            map = new HashMap<String, Object>();
            map.put("venderId", vo.getUnitId());
            map.put("vendeeId","0");
            map.put("prcCtlr","R");
            map.put("dtlList",dtlList);
            //删除xpl中的数据
            xplMapper.deleteByImplementF(map);
        } else {
            map = new HashMap<String, Object>();
            map.put("list",scpList);
            map.put("dtlList",dtlList);
            map.put("prcCtlr","R");
            //清除相关记录
            xplMapper.deleteByImplementT(map);
        }

        //往xpl表中添加数据
        map = new HashMap<String, Object>();
        map.put("list", scpList);
        map.put("dtlList", dtlList);
        map.put("xpType", vo.getXpType());
        map.put("expdDate", vo.getExpdDate());
        xplMapper.insertByList(map);

        map.put("unitId", vo.getUnitId());
        map.put("xpnNum", vo.getSpnNum());
        map.put("execTime", vo.getExecTime());
        xplHMapper.insertByList(map);
        //如果指定组织层级 并向下传递 更新下级采购商价格表
        if (StringUtils.isNotEmpty(vo.getUnitHierId()) && vo.getHandOn().equals("T")) {
            this.subordinate(vo);
        }

        return spnMapper.updateByState(vo);
    }

    //指定组织层级，并向下传递
    private void subordinate(SpnVo vo) {

        //查出明细数据
        List<SpnDtlVo> dtlList = spnDtlMapper.selectBySpn(new SpnDtlVo(vo.getUnitId(), vo.getSpnNum()));
        //PrcScp  D 缺省    U 组织
        //定价范围为缺省时，向下传递范围为供应商的所有下级采购商（不包括供应商）
        //定价范围为组织时，向下传递范围为所有采购商及其下级采购商（包括采购商本身）
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapUnit = new HashMap<>();
        mapUnit.put("vo", new SpnScpVo(vo.getUnitId(), vo.getSpnNum()));
        //需要操作的组织
        List<SysUnit> list = null;
        if (vo.getPrcScp().equals("D")) {
            //查询范围的组织集合
            mapUnit.put("unitId", "vender_id");
            List<SysUnit> scpList = spnScpMapper.selectBySysUnit(mapUnit);
            map.put("type", "VE");
            map.put("scpList", scpList);
            list = sysUnitMapper.findByHierarchyList(map);
        } else if (vo.getPrcScp().equals("U")) {
            //查询范围的组织集合
            mapUnit.put("unitId", "vendee_id");
            List<SysUnit> scpList = spnScpMapper.selectBySysUnit(mapUnit);
            map.put("type", "VE");
            map.put("scpList", scpList);
            list = sysUnitMapper.findByHierarchyList(map);
            //将当前的也加进去
            list.addAll(scpList);
        }

        //获取xpl记录集合
        List<Xpl> xplList = xplMapper.selectByHierarchyList(list);
        map = new HashMap<>();
        map.put("unitId", vo.getUnitId());
        map.put("xpnNum", vo.getSpnNum());
        map.put("execTime", vo.getExecTime());
        map.put("prcOpr", vo.getExecTime());
        map.put("list", xplList);
        xplHMapper.insertByMap(map);

        map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("prcCtlr","R");
        map.put("dtlList",dtlList);
        //删除xpl中的数据
        xplMapper.deleteByHierImplementF(map);

        //往xpl表中添加数据
        map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("dtlList", dtlList);
        map.put("xpType", vo.getXpType());
        map.put("expdDate", vo.getExpdDate());
        xplMapper.insertByHierList(map);

        map.put("unitId", vo.getUnitId());
        map.put("xpnNum", vo.getSpnNum());
        map.put("execTime", vo.getExecTime());
        xplHMapper.insertByHierList(map);
    }

}
