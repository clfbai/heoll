package com.boyu.erp.platform.usercenter.service.priceservice.priceServiceimpl;

import com.boyu.erp.platform.usercenter.entity.Price.PpnDtl;
import com.boyu.erp.platform.usercenter.entity.Price.Xpl;
import com.boyu.erp.platform.usercenter.entity.purchase.PpnScp;
import com.boyu.erp.platform.usercenter.entity.system.PcSynTask;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.Price.PpnDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.Price.PpnMapper;
import com.boyu.erp.platform.usercenter.mapper.Price.XplHMapper;
import com.boyu.erp.platform.usercenter.mapper.Price.XplMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PpnScpMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.PcSynTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
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
 * @program: PpnServiceImpl
 * @description: 采购价格单服务层
 * @author: wz
 * @create: 2019-8-26 9:43
 */
@Slf4j
@Service
@Transactional
public class PpnServiceImpl implements PpnService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PpnMapper ppnMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private PpnDtlMapper ppnDtlMapper;
    @Autowired
    private PpnScpMapper ppnScpMapper;
    @Autowired
    private XplMapper xplMapper;
    @Autowired
    private XplHMapper xplHMapper;
    @Autowired
    private PcSynTaskMapper pcSynTaskMapper;
    @Autowired
    private SysParameterMapper parameterMapper;

    @Override
    public PageInfo<PpnVo> selectAll(PpnVo vo, Integer page, Integer size, SysUser user) {
        List<PpnVo> list = null;
        /**
         * 系统管理员
         * */
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            PageHelper.startPage(page, size);
            list = ppnMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = ppnMapper.selectByUnit(vo);
        }
        PageInfo<PpnVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertPpn(PpnVo vo, SysUser user) {
        //生成采购价格单单号
        String ppnNum = sysRefNumService.viceNum(user,"PPN_NUM");
        vo.setPpnNum(ppnNum);
        vo.setOprId(user.getPrsnl().getPrsnlId());
        vo.setEffective("F");
        vo.setCancelled("F");
        vo.setSuspended("F");
        vo.setProgress("PG");
        Map<String,Object> map = new HashMap<>();
        map.put("spnNum",vo.getPpnNum());
        if (vo.getDtlList() != null && vo.getDtlList().size() > 0) {
            map.put("dtlList",vo.getDtlList());
            ppnDtlMapper.insertByMap(map);
        }
        if (vo.getScpList() != null && vo.getScpList().size() > 0) {
            map.put("scpList",vo.getScpList());
            ppnScpMapper.insertByMap(map);
        }
        return ppnMapper.insertSelective(vo);
    }

    /**
     * 修改
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updatePpn(PpnVo vo, SysUser user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setOprId(user.getPrsnl().getPrsnlId());
        vo.setOpTime(sdf.format(new Date()));
        return ppnMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 删除
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deletePpn(PpnVo vo, SysUser user) {
        //删除明细/范围相关数据
        ppnDtlMapper.deleteByPpn(vo.getUnitId()+"",vo.getPpnNum());
        ppnScpMapper.deleteByPpn(vo.getUnitId()+"",vo.getPpnNum());
        return ppnMapper.deleteByPpn(vo.getUnitId()+"",vo.getPpnNum());
    }

    /**
     * 切换定价范围时删除范围
     * @param vo
     * @return
     */
    @Override
    public int deletePpnScp(PpnVo vo) {
        return ppnScpMapper.deleteByPpn(vo.getUnitId()+"",vo.getPpnNum());
    }

    /**
     * 确认单据 更改状态
     * @param vo
     * @return
     */
    @Override
    public int confirm(PpnVo vo) {
        if(vo.getPrcScp().equals("D")){
            //如果是缺省，新增一条采购单范围，供应商值为0
            ppnScpMapper.insertSelective(new PpnScp(vo.getUnitId(),vo.getPpnNum(),0L));
        }
        vo.setProgress("C");
        return ppnMapper.updateByState(vo);
    }

    /**
     * 重做单据时，如果是缺省。删除缺省的范围  更新状态
     * @param vo
     * @return
     */
    @Override
    public int redo(PpnVo vo) {
        if(vo.getPrcScp().equals("D")){
            //如果是缺省，删除这个范围
            ppnScpMapper.deleteByPpn(vo.getUnitId()+"",vo.getPpnNum());
        }
        vo.setProgress("PG");
        return ppnMapper.updateByState(vo);
    }

    /**
     * 审核单据 补充审核信息  更新状态
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(PpnVo vo,SysUser user) {
        vo.setChkrId(user.getPrsnl().getPrsnlId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setChkTime(sdf.format(new Date()));
        vo.setProgress("CK");
        return ppnMapper.updateByState(vo);
    }

    /**
     * 重审
     * 删除审核信息 更新状态
     * @param vo
     * @return
     */
    @Override
    public int reiterate(PpnVo vo) {
        vo.setChkrId(null);
        vo.setChkTime(null);
        vo.setProgress("C");
        return ppnMapper.updateByState(vo);
    }

    /**
     * 更新状态
     * @param vo
     * @return
     */
    @Override
    public int hangUp(PpnVo vo) {
        vo.setSuspended("T");
        return ppnMapper.updateByState(vo);
    }

    /**
     * 恢复单据
     * @param vo
     * @return
     */
    @Override
    public int recovery(PpnVo vo) {
        vo.setSuspended("F");
        return ppnMapper.updateByState(vo);
    }

    /**
     * 作废单据
     * @param vo
     * @return
     */
    @Override
    public int toVoid(PpnVo vo) {
        vo.setCancelled("T");
        return ppnMapper.updateByState(vo);
    }

    /**
     * 执行单据
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int implement(PpnVo vo, SysUser user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> map = null;
        vo.setProgress("EX");
        vo.setEffective("T");
        vo.setExtrId(user.getPrsnl().getPrsnlId());
        vo.setExecTime(sdf.format(new Date()));
        //查出范围数据
        List<PpnScpVo>  ppnList = ppnScpMapper.selectByPpn(new PpnScpVo(vo.getUnitId(),vo.getPpnNum()));
        //先查出明细数据
        List<PpnDtlVo> dtlList = ppnDtlMapper.selectByPpn(new PpnDtlVo(vo.getUnitId(),vo.getPpnNum()));
        //定价范围是缺省
        if(vo.getPrcScp().equals("D") && vo.getRsvUnit().equals("F")){
            //取购销价格表记录
            List<Xpl> xplList = xplMapper.selectByRsvF("0",vo.getUnitId()+"","E");
            if(xplList!=null && xplList.size()>0){
                map =new HashMap<String,Object>();
                map.put("unitId",vo.getUnitId());
                map.put("xpnNum",vo.getPpnNum());
                map.put("execTime",vo.getExecTime());
                map.put("list",xplList);
                xplHMapper.insertByMap(map);
            }
            map = new HashMap<String, Object>();
            map.put("venderId", "0");
            map.put("vendeeId",vo.getUnitId());
            map.put("prcCtlr","E");
            map.put("dtlList",dtlList);
            //删除xpl中的数据
            xplMapper.deleteByImplementF(map);
        }else{
            map = new HashMap<String, Object>();
            map.put("list",ppnList);
            map.put("dtlList",dtlList);
            map.put("prcCtlr","R");
            //清除相关供应商记录
            xplMapper.deleteByImplementT(map);
        }

        //往xpl表中添加数据
        map =new HashMap<String,Object>();
        map.put("list",ppnList);
        map.put("dtlList",dtlList);
        map.put("xpType",vo.getXpType());
        map.put("expdDate",vo.getExpdDate());
        xplMapper.insertByList(map);

        map.put("unitId",vo.getUnitId());
        map.put("xpnNum",vo.getPpnNum());
        map.put("execTime",vo.getExecTime());
        xplHMapper.insertByList(map);
        if(("T").equals(parameterMapper.findById("VENDER_CREAT_TABLE_FIELDS").getParmVal())){
            //登记价格成本同步任务
            pcSynTaskMapper.insertSelective(new PcSynTask("PP",user.getDomain().getUnitId(),vo.getPpnNum(),vo.getUnitId(),"F",""));
        }

        return ppnMapper.updateByState(vo);
    }


}
