package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.FunRule;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.San;
import com.boyu.erp.platform.usercenter.entity.warehouse.SanDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.SanMapper;
import com.boyu.erp.platform.usercenter.model.wareh.SanFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.SanModel;
import com.boyu.erp.platform.usercenter.service.system.FunRuleService;
import com.boyu.erp.platform.usercenter.service.warehouse.SanDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.SanService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.SanDtlVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.SanVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 库存调整单服务
 * @author HHe
 * @date 2019/10/6 16:52
 */
@Service
@Transactional
public class SanServiceImpl implements SanService {
    @Autowired
    private WarehService warehService;
    @Autowired
    private SanMapper sanMapper;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private SanDtlService sanDtlService;
    @Autowired
    private FunRuleService funRuleService;
    /**
     * 根据筛选条件分页查询列表
     * @author HHe
     * @date 2019/10/6 17:09
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SanVO> queryList(Integer page, Integer size, SanFilterModel sanFilterModel, SysUser sysUser) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException {
        List<SanVO> list = new ArrayList<>();
        if(sanFilterModel.getWarehNum()!=null&&!"".equals(sanFilterModel.getWarehNum())){
            //查询判断仓库编号
            List<Long> warehIds = warehService.judgeReWarehIds(sanFilterModel.getWarehNum(), sanFilterModel.getUnitId());
            sanFilterModel.setWarehIds(warehIds);
        }
        PageHelper.startPage(page, size);
        if(false){
            //超管
        }else{
            list = sanMapper.queryList(sanFilterModel,sysUser.getDomain().getUnitId());
        }
        list = this.packList(list);
        PageInfo<SanVO> info = new PageInfo<>(list);
        return info;
    }
    /**
     * 加载库存调整表下拉
     * @author HHe
     * @date 2019/10/6 17:52
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> loadSanPullDown(SysUser sysUser) {
        Map<String, String> fMap = new HashMap<>();
        fMap.put("progress", "SAN_PROG");
        fMap.put("stkForm", "STK_FORM");
        fMap.put("sadType", "SAD_TYPE");
        return delivUtil.getCodeDtlMap2(fMap);
    }
    /**
     * 查询详情
     * @author HHe
     * @date 2019/10/7 10:40
     */
    @Override
    @Transactional(readOnly = true)
    public SanVO queryDetails(String sanNum, SysUser sysUser) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException {
        //根据编号和组织查询详情
        San san = sanMapper.querySanByNumAndUnit(sanNum,sysUser.getDomain().getUnitId());
        if(san==null){
            throw new ServiceException("201", "单据信息错误");
        }
        SanVO sanVO = new SanVO();
        BeanUtils.copyProperties(san,sanVO);
        sanVO = this.packList(Arrays.asList(new SanVO[]{sanVO})).get(0);
        //查询明细集合
        SanDtl sanDtl = new SanDtl();
        sanDtl.setSanNum(sanNum);
        List<SanDtlVO> sanDtlVOList = sanDtlService.queryListBySanDtl(sanDtl,sysUser);
        //封装商品
        sanDtlVOList = delivUtil.queryProdDetailsByProdIdList(sanDtlVOList,new HashMap<>());
        sanVO.setSanDtlVOList(sanDtlVOList);
        return sanVO;
    }
    /**
     * 添加库存调整表
     * @author HHe
     * @date 2019/10/7 10:55
     */
    @Override
    public int addSan(SanModel sanModel, SysUser sysUser) {
        //添加修改流程控制
        sanModel = this.addUpdateControl(sanModel,sysUser);
        //定义编号和时间
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setUnitId(sysUser.getDomain().getUnitId());
        refNumDtl.setRefNumId("SAN_NUM");
        String sanNum = delivUtil.createDocNum(refNumDtl);
        Date nowDate = new Date();
        sanModel.setSanNum(sanNum);
        sanModel.setDocDate(nowDate);
//        sanModel.setUnitId(sysUser.getDomain().getUnitId());
        sanModel.setTtlQty(new BigDecimal("0"));
        sanModel.setTtlBox(0);
        sanModel.setEffective("F");
        sanModel.setSuspended("F");
        sanModel.setCancelled("F");
        sanModel.setProgress("PG");
        //添加明细
        if(sanModel.getSanDtlList()!=null&&sanModel.getSanDtlList().size()>0){
            sanDtlService.addSanDtlByList(sanModel.getSanDtlList(),sanModel.getUnitId());
        }
        return sanMapper.insertSelective(sanModel);
    }

    /**
     * 修改库存调整表
     * @author HHe
     * @date 2019/10/7 14:54
     */
    @Override
    public int updateSan(SanModel sanModel, SysUser sysUser) {
        //查询单据详情
        San san = sanMapper.querySanByProp(sanModel,sysUser.getDomain().getUnitId());
        if(san==null){
            throw new ServiceException("201", "单据信息有误");
        }
        if(!"PG".equals(san.getProgress())&&!"F".equals(san.getSuspended())&&!"F".equals(san.getCancelled())){
            throw new ServiceException("201","单据状态不可修改");
        }
        //添加修改流程控制
        sanModel = this.addUpdateControl(sanModel,sysUser);
        //删除所有相关明细，再添加
        SanDtl sanDtl = new SanDtl();
        sanDtl.setSanNum(sanModel.getSanNum());
        sanDtlService.delSanDtlBySanDtl(sanDtl,sysUser);
        sanDtlService.addSanDtlByList(sanModel.getSanDtlList(), sanModel.getUnitId());
//        sanModel.setUnitId(sysUser.getDomain().getUnitId());
        return sanMapper.updateByPrimaryKeySelective(sanModel);
    }

    /**
     * 库存调整表功能列表展示
     * @author HHe
     * @date 2019/10/9 11:34
     */
    @Override
    public List<OperationVo> queryFunList(San pSan) {
        String url = "/warehouse/san/sanfunction";
        //查询单据信息
        San san = sanMapper.querySanByProp(pSan, pSan.getUnitId());
        if(san==null){
            throw new ServiceException("201","单据信息错误");
        }
        //查询出规则集合
        FunRule funRule = new FunRule();
        funRule.setDocType("SAN");
        List<FunRule> funRuleList = funRuleService.queryFunctionList(funRule);
        FunRule fr = new FunRule();
        for (FunRule rule : funRuleList) {
            //判断撤销
            if("T".equals(san.getCancelled())){
                if("T".equals(rule.getCancelled())){
                    fr.setFunction(rule.getFunction());
                    break;
                }
                continue;
            }
            //判断挂起
            if("T".equals(san.getSuspended())){
                if("T".equals(rule.getSuspended())){
                    fr.setFunction(rule.getFunction());
                    break;
                }
                continue;
            }
            //匹配进度
            if(rule.getProgress().equals(san.getProgress())){
                fr.setFunction(rule.getFunction());
            }
        }
        return delivUtil.parseFun(fr.getFunction(),url);
    }

    /**
     * 确认单据
     * @author HHe
     * @date 2019/10/9 17:17
     */
    @Override
    public int confirm(SanModel sanModel) {
        //查询
        San san = this.judgeExistSan(sanModel.getSanNum(), sanModel.getUnitId());
        //判断
        if(!"PG".equals(san.getProgress())||!"F".equals(san.getSuspended())||!"F".equals(san.getCancelled())){
            throw new ServiceException("201","单据状态不可修改");
        }
        //修改状态
        San pSan = new San();
        pSan.setSanNum(sanModel.getSanNum());
        pSan.setUnitId(sanModel.getUnitId());
        pSan.setProgress("CN");
        return sanMapper.updateByPrimaryKeySelective(pSan);
    }

    /**
     * 重做单据
     * @author HHe
     * @date 2019/10/10 9:43
     */
    @Override
    public int redo(SanModel sanModel) {
        //查询单据
        San san = this.judgeExistSan(sanModel.getSanNum(), sanModel.getUnitId());
        //判断单据状态
        if(!"CN".equals(san.getProgress())||!"F".equals(san.getSuspended())||!"F".equals(san.getCancelled())){
            throw new ServiceException("201", "单据状态不可修改");
        }
        San pSan = new San();
        pSan.setSanNum(sanModel.getSanNum());
        pSan.setUnitId(sanModel.getUnitId());
        pSan.setProgress("PG");
        return sanMapper.updateByPrimaryKeySelective(pSan);
    }

    /**
     * 审核
     * @author HHe
     * @date 2019/10/10 10:12
     */
    @Override
    public int check(SanModel sanModel) {
        //查询单据
        San san = this.judgeExistSan(sanModel.getSanNum(), sanModel.getUnitId());
        //判断单据状态
        if(!"CN".equals(san.getProgress())||!"F".equals(san.getSuspended())||!"F".equals(san.getCancelled())){
            throw new ServiceException("201", "单据状态不可修改");
        }
        San pSan = new San();
        pSan.setSanNum(sanModel.getSanNum());
        pSan.setUnitId(sanModel.getUnitId());
        pSan.setProgress("CK");
        return sanMapper.updateByPrimaryKeySelective(pSan);
    }

    /**
     * 反审
     * @author HHe
     * @date 2019/10/10 10:13
     */
    @Override
    public int uncheck(SanModel sanModel) {
        //查询单据
        San san = this.judgeExistSan(sanModel.getSanNum(), sanModel.getUnitId());
        //判断单据状态
        if(!"CK".equals(san.getProgress())||!"F".equals(san.getSuspended())||!"F".equals(san.getCancelled())){
            throw new ServiceException("201", "单据状态不可修改");
        }
        San pSan = new San();
        pSan.setSanNum(sanModel.getSanNum());
        pSan.setUnitId(sanModel.getUnitId());
        pSan.setProgress("CN");
        return sanMapper.updateByPrimaryKeySelective(pSan);
    }
    /**
     * 挂起
     * @author HHe
     * @date 2019/10/10 10:13
     */
    @Override
    public int suspend(SanModel sanModel) {
        //查询单据
        San san = this.judgeExistSan(sanModel.getSanNum(), sanModel.getUnitId());
        //判断单据状态
        if("PS".equals(san.getProgress())||!"F".equals(san.getSuspended())||!"F".equals(san.getCancelled())){
            throw new ServiceException("201", "单据状态不可修改");
        }
        San pSan = new San();
        pSan.setSanNum(sanModel.getSanNum());
        pSan.setUnitId(sanModel.getUnitId());
        pSan.setSuspended("T");
        return sanMapper.updateByPrimaryKeySelective(pSan);
    }
    /**
     * 恢复
     * @author HHe
     * @date 2019/10/10 10:13
     */
    @Override
    public int resume(SanModel sanModel) {
        //查询单据
        San san = this.judgeExistSan(sanModel.getSanNum(), sanModel.getUnitId());
        //判断单据状态
        if(!"T".equals(san.getSuspended())){
            throw new ServiceException("201", "单据状态不可修改");
        }
        San pSan = new San();
        pSan.setSanNum(sanModel.getSanNum());
        pSan.setUnitId(sanModel.getUnitId());
        pSan.setSuspended("F");
        return sanMapper.updateByPrimaryKeySelective(pSan);
    }
    /**
     * 作废
     * @author HHe
     * @date 2019/10/10 10:13
     */
    @Override
    public int abolish(SanModel sanModel) {
        //查询单据
        San san = this.judgeExistSan(sanModel.getSanNum(), sanModel.getUnitId());
        //判断单据状态
        if(!DelivUtil.equalsHave(san.getProgress(),"PG","CN","CK")||!"F".equals(san.getSuspended())||!"F".equals(san.getCancelled())){
            throw new ServiceException("201", "单据状态不可修改");
        }
        San pSan = new San();
        pSan.setSanNum(sanModel.getSanNum());
        pSan.setUnitId(sanModel.getUnitId());
        pSan.setCancelled("T");
        return sanMapper.updateByPrimaryKeySelective(pSan);
    }
    /**
     * 过账
     * @author HHe
     * @date 2019/10/10 10:14
     */
    @Override
    public int post(SanModel sanModel) {
        return 0;
    }


    /**
     * 判断单据是否存在
     * @author HHe
     * @date 2019/10/10 9:45
     */
    public San judgeExistSan(String sanNum,Long unitId){
        San san = sanMapper.querySanByNumAndUnit(sanNum, unitId);
        if(san==null){
            throw new ServiceException("201","单据信息错误");
        }
        return san;
    }
    /**
     * 添加修改流程控制
     * @author HHe
     * @date 2019/10/8 9:46
     */
    public SanModel addUpdateControl(SanModel sanModel, SysUser sysUser){
        Date nowDate = new Date();
        if(sanModel.getSadType()==null ||"".equals(sanModel.getSadType())){
            throw new ServiceException("201","调整类别不可为空");
        }
        if(sanModel.getFsclDate()==null){
            sanModel.setFsclDate(nowDate);
        }
        if(sanModel.getStkForm()==null||"".equals(sanModel.getStkForm())){
            sanModel.setStkForm("BN");
        }
        //操作员
        sanModel.setOprId(sysUser.getPrsnl().getPrsnlId());
        //操作时间
        sanModel.setOpTime(new Timestamp(nowDate.getTime()));
        return sanModel;
    }
    /**
     * 删除库存调整表
     * @author HHe
     * @date 2019/10/7 14:26
     */
    @Override
    public int delSan(String sanNum, SysUser sysUser) {
        //查询详情
        San san = sanMapper.querySanByNumAndUnit(sanNum, sysUser.getDomain().getUnitId());
        //判断
        if(!"PG".equals(san.getProgress())&&!"CN".equals(san.getProgress())||!"F".equals(san.getSuspended())||!"F".equals(san.getCancelled())){
            throw new ServiceException("201", "单据状态不可修改");
        }
        //删除明细
        SanDtl sanDtl = new SanDtl();
        sanDtl.setSanNum(sanNum);
        sanDtlService.delSanDtlBySanDtl(sanDtl,sysUser);
        San propSan = new San();
        propSan.setSanNum(sanNum);
        return sanMapper.delSan(propSan,sysUser.getDomain().getUnitId());
    }


    /**
     * Assist
     * 封装库存调整表集合
     * @author HHe
     * @date 2019/10/6 17:59
     */
    public List<SanVO> packList(List<SanVO> sanVOList) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("sys_unit",new String[]{"warehId","fsclUnitId"});
        map.put("sys_prsnl",new String[]{"oprId","chkrId","acckId"});
        Map<String, String> uMap = new HashMap<>();
        uMap.put("fsclUnitId","fsclUnitCode-unitCode");
        map.put("unitProp", uMap);
        sanVOList = delivUtil.loadCodeName2VO2(map,sanVOList);
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("locAdopted", "BOOLEA");
        codeMap.put("stkForm", "STK_FORM");
        codeMap.put("sadType", "SAD_TYPE");
        codeMap.put("effective", "BOOLEA");
        codeMap.put("progress", "SAN_PROG");
        codeMap.put("suspended", "BOOLEA");
        codeMap.put("cancelled", "BOOLEA");
        sanVOList = delivUtil.loadCPByCodeDtl(codeMap, sanVOList);
        return sanVOList;
    }
}
