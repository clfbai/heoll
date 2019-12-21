package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitClsfMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitOwnerMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehMapper;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehOptinModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehPopUpFilterModel;
import com.boyu.erp.platform.usercenter.service.system.SysUnitOwnerSerivcer;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDmodeService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRmodeService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.WarehUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehPopUpVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehUnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @program: boyu-platform_text
 * @description: 仓库服务
 * @author: clf
 * @create: 2019-06-26 12:32
 */
@Slf4j
@Service
@Transactional
public class WarehServiceimpl implements WarehService {
    @Autowired
    private WarehMapper warehMapper;
    @Autowired
    private SysUnitClsfMapper unitClsfMapper;
    @Autowired
    private SysUnitMapper unitMapper;
    @Autowired
    private SysUnitOwnerMapper unitOwnerMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private WarehUtils warehUtils;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private SysUnitOwnerSerivcer sysUnitOwnerSerivcer;
    @Autowired
    private WarehRmodeService rmodeService;
    @Autowired
    private WarehDmodeService dmodeService;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehVo> selectWareh(Integer page, Integer size, WarehVo wareh, SysUser user) {
        wareh.setWarehId(user.getOwnerId());
        PageHelper.startPage(page, size);
        List<WarehVo> selectWareh = warehMapper.getUintWareh(wareh);
        PageInfo<WarehVo> pageInfo = new PageInfo<>(selectWareh);
        return pageInfo;
    }


    /**
     * 1.从现有组织添加为仓库
     * 2. 增加新组织仓库
     * 3.目前增加仓库都是业务仓库(即同时需要开设会计仓库，并且会计仓库仅支持和业务仓库结构一致）
     * 会计仓库有关结算 （某个组织可以是桃花季下属(加盟店) 同时它可以选择其他会计组织为其清算账务这样在系统中他的会计仓库桃花季无法记录,即会计仓库不是桃花季)
     */
    @Override
    public int instrWareh(WarehVo warehVo, SysUser user) throws ServiceException {
        int a = 0;
        Long warehId = null;
        /**
         * 1.选择现有组织为仓库
         * 2.1 增加仓库表
         * 2.2 增加组织类型表
         * 2.3 增加组织属主表
         * 2.4 添加成本组(默认缺省)
         * */
        if (warehVo.getWarehId() != null) {
            warehUtils.isNotWareh(warehVo);
            SysUnit unit = unitMapper.selectByPrimaryKey(warehVo.getWarehId());
            BeanUtils.copyProperties(warehVo, unit);
            a = warehUtils.createWareh(unit, user, warehVo);
            warehId = warehVo.getWarehId();
        }
        /**
         * 2.增加新组织为仓库
         * 2.1 增加组织表
         * 2.2 增加仓库表
         * 2.3 增加组织类型表
         * 2.4 增加组织属主表
         * 2.5 添加成本组(默认缺省)
         * */
        if (warehVo.getWarehId() == null) {
            //2.1
            SysUnit unit = new SysUnit();
            Wareh as = new Wareh();
            log.info(warehVo.getUnitId() + " vo" + warehVo.getLmId());
            BeanUtils.copyProperties(warehVo, unit);
            unit.setUnitId(null);
            BeanUtils.copyProperties(warehVo, as);
            unit.setUnitHierarchy(user.getUnit().getUnitHierarchy() + "|" + unit.getUnitCode());
            a = unitMapper.insertSelective(unit);
            a += warehUtils.createWareh(unit, user, warehVo);
            warehId = unit.getUnitId();
        }
        //添加默认入库方式
        rmodeService.addDefaultWarehRmode(warehId);
        //添加默认入库方式
        dmodeService.addDefaultWarehDmode(warehId);
        return a;
    }

    /**
     * 修改仓库信息
     * 1 通过参数判断特殊列的值不能修改
     * 2.修改仓库基本信息
     * 3.修改仓库编号
     * 4.修改组织基本信息
     */
    @Override
    public int udpateWareh(WarehVo warehVo, SysUser user) throws Exception {

        int a;
        SysUnit u = new SysUnit();
        BeanUtils.copyProperties(warehVo, u);
        u.setUnitId(warehVo.getWarehId());
        u.setUpdTime(new Date());
        //组织代码不可更改
        u.setUnitCode(null);
        //组织层级不可更改
        u.setUnitHierarchy(null);
        a = unitMapper.updateByPrimaryKeySelective(u);

        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setUnitNum(warehVo.getUnitNum());
        unitOwner.setUnitId(warehVo.getWarehId());
        unitOwner.setOwnerId(warehVo.getOwnerId());
        a += unitOwnerMapper.updateUnitOwner(unitOwner);

        Wareh wareh = new Wareh();
        BeanUtils.copyProperties(warehVo, wareh);
        warehUtils.setUpdateWareh(wareh, getUpdateFile());
        a += warehMapper.updateByWareh(wareh);
        return a;
    }

    /**
     * ）如果是门店，则不允许删除。
     * 2）调用enableStock，禁用库存管理。
     * 3）删除仓库信息。数据库中不物理删除，而是逻辑删除。
     * 4）删除组织类型，包括WAREHOUSE和FISCAL_WAREHOUSE。删除FISCAL_WAREHOUSE时，对应的属主为FSCL_UNIT_ID，而不是OWNER_ID。
     * 5）判断仓库在当前组织是否还存在有效类型。如果不存在，则调用SysUnitOwnerHome.remove删除仓库属主关系。
     * 6）删除组织信息。
     * enableStock：启用或者禁用库存管理。
     * 1）锁定仓库，判断当前状态是否可以启用或者禁用。注意：暂不修改STK_ADOPTED标记。
     * 2）如果禁用，必须确保当前库存为0：
     * 调用isEmpty，判断当前库存（包含未决）是否为0。如果库存非0，则抛出返回错误信息。
     * 调用enableUID，禁用唯一码管理。
     * 逻辑删除
     * 1.判断仓库 库存
     * 2. 判断会计仓库业务
     * 3 修改仓库表仓库状态
     * 4 修改组织状态
     * 5 删除以下表对应记录
     * wareh_stk(仓库库存表)
     * wareh_stk_pg(仓库未决库存表)
     * wareh_ast、(货位配码库存快照表)
     * wareh_ast_pg、(仓库配码未决库存表)
     * wareh_bst、(仓库装箱库存)
     * wareh_bst_pg、(仓库装箱未决库存)
     * loc_stk、(货位库存)
     * loc_stk_pg、(货位未决库存)
     * loc_ast(货位配码库存)、
     * loc_ast_pg(货位配码未决库存)、
     * loc_bst(货位装箱库存)、
     * loc_bst_pg(货位装箱未决库存)
     */
    @Override
    public int deleteWareH(WarehVo warehVo, SysUser user) throws Exception {
        Wareh w = new Wareh();
        SysUnitClsf clsf = new SysUnitClsf();
        clsf.setOwnerId(warehVo.getOwnerId());
        clsf.setUnitId(warehVo.getWarehId());
        BeanUtils.copyProperties(warehVo, w);
        if (warehUtils.warehType(clsf)) {
            throw new ServiceException(ResultCode.RETURN_FAILURE, "门店类型的组织不能删除");
        }
        if (!warehUtils.isWarehEmpty(w)) {
            throw new ServiceException(ResultCode.RETURN_FAILURE, "库存不为空");
        }
        if (!warehUtils.isWarehPgEmpty(w)) {
            throw new ServiceException(ResultCode.RETURN_FAILURE, "未决库存不为空");
        }
        //禁用仓库唯一管理码
        int a = warehUtils.enableUIDFalse(w);
        //禁用仓库装箱库存管理
        a += warehUtils.enableBoxedStock(w);
        //禁用货位管理
        a += warehUtils.enableLocation(w);
        //'A'活动 'D'删除 'I'非活动
        w.setWarehStatus("D");
        w.setOprId(user.getUserId());
        w.setUpdTime(new Date());
        a += warehMapper.updateByWareh(w);
        //删除组织类型
        a += unitClsfMapper.deleteSysUnitClsfWareh(clsf);
        //删除组织属主
        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setUnitId(w.getWarehId());
        unitOwner.setOwnerId(w.getOwnerId());
        a += unitOwnerMapper.deleteUnitOwner(unitOwner);
        return a;
    }

    /**
     * 功能描述:  根据仓库Id 查询仓库信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/31 11:31
     */
    @Override
    @Transactional(readOnly = true)
    public Wareh selectByWarehId(Long warehId) {
        return warehMapper.selectByWarehId(warehId);
    }

    /**
     * 功能描述:  根据系统参数给仓库字段赋值
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/31 11:32
     */
    @Override
    public Wareh setParamWareh(Wareh wareh) {
        return warehUtils.setParamWareh(wareh);
    }

    /**
     * 查询仓库不可修改字段
     */
    @Override
    public List<String> getUpdateFile() {
        return warehUtils.isNotUpdateWareh();
    }


    /**
     * 查询仓库代码(弹窗)
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehUnitOptionVo> getOptinCode(Integer page, Integer size, WarehOptinModel model, SysUser sessionSysUser) {
        //按组织筛选仓库
        model.setUnitId(model.getUnitId());
        PageHelper.startPage(page, size);
        List<WarehUnitOptionVo> selectWareh = warehMapper.getWarehCodeOption(model);
        PageInfo<WarehUnitOptionVo> pageInfo = new PageInfo<>(selectWareh);
        return pageInfo;
    }

    /**
     * 功能描述: 开设领域增加仓库(针对新建组织为领域,而不是选择原有组织开设领域)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 11:27
     */
    @Override
    public int addDomainWareh(SysUnit unit, SysUser user) {
        Wareh wareh = new Wareh();
        wareh.setWarehId(unit.getUnitId());
        wareh.setOwnerId(unit.getUnitId());
        wareh = warehUtils.setParamWareh(wareh);
        warehUtils.setParam(wareh);
        //允许负动存
        wareh.setNegAtk(CommonFainl.FALSE);
        wareh.setBstAdopted(CommonFainl.FALSE);
        //会计组织Id 取当前组织Id
        wareh.setFsclUnitId(Long.valueOf(unit.getUnitId() + ""));
        //成本组Id 缺省
        wareh.setCostGrpId(0L);
        //组织状态
        wareh.setWarehStatus(CommonFainl.USER_STATUS);
        //操作员Id
        wareh.setOprId(user.getUserId());
        wareh.setUpdTime(new Date());
        //集货区管理
        wareh.setClnAreaAdopted(CommonFainl.FALSE);
        //启用分货复核
        wareh.setClnRckReqd(CommonFainl.FALSE);

        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setOwnerId(unit.getUnitId());
        unitOwner.setUnitId(unit.getUnitId());
        unitOwner.setUnitNum("*");
        int a = warehUtils.addUnitOwner(unitOwner);
        a += warehUtils.addCostGrp(unit.getUnitId());

        SysUnitClsf unitClsf = new SysUnitClsf();
        unitClsf.setUnitId(unit.getUnitId());
        unitClsf.setOwnerId(unit.getUnitId());
        //虚拟仓库
        unitClsf.setUnitType("VW");
        a += unitClsfMapper.insertSysUnitClsf(unitClsf);

        a += warehMapper.insertWareh(wareh);
        return a;
    }

    /**
     * 判断仓库是否可用
     *
     * @author HHe
     * @date 2019/8/26 11:25
     */
    @Override
    public WarehVo judgewarehavailable(Long warehId, Long unitId) {
        return warehMapper.judgewarehavailable(warehId, unitId);
    }

    /**
     * 判断仓库是否可用（PLAN2）
     *
     * @author HHe
     * @date 2019/8/25 18:01
     */
    @Override
    public UnitOptionVo judgewarehavailable1(String warehCode, SysUser sysUser) {
        return warehMapper.judgewarehavailable1(warehCode, sysUser.getDomain().getUnitId());
    }

    /**
     * 仓库弹窗
     *
     * @author HHe
     * @date 2019/9/15 14:51
     */
    @Override
    public List<WarehPopUpVO> warehPopUp(WarehPopUpFilterModel warehPopUpFilterModel, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        if (warehPopUpFilterModel.getWarehOwnerId() == -1L) {
            warehPopUpFilterModel.setWarehOwnerId(sysUser.getDomain().getUnitId());
        }
        //根据筛选条件查询组织集合
        SysUnit sysUnit1 = new SysUnit();
        sysUnit1.setUnitCode(warehPopUpFilterModel.getUnitCode());
        sysUnit1.setUnitName(warehPopUpFilterModel.getUnitName());
        sysUnit1.setInputCode(warehPopUpFilterModel.getInputCode());
        List<SysUnit> sysUnits = sysUnitService.queryUnitLikeProp(sysUnit1);
        List<Long> warehIds = new ArrayList<>();
        for (SysUnit sysUnit : sysUnits) {
            warehIds.add(sysUnit.getUnitId());
        }
        //根据仓库集合查询仓库信息
        warehPopUpFilterModel.setWarehIds(warehIds);
        List<WarehPopUpVO> warehs = warehMapper.warehPopUp(warehPopUpFilterModel);
        Map<String, Object> map = new HashMap<>();
        Map<String, String> unitProp = new HashMap<>();
        unitProp.put("warehId", "inputCode");
        map.put("unitProp", unitProp);
        return delivUtil.loadCodeName2Map(map, warehs);
    }

    /**
     * 根据编号查询仓库信息
     *
     * @author HHe
     * @date 2019/9/17 11:02
     */
    @Override
    public Wareh queryWarehByWarehId(Long warehId) {
        return warehMapper.queryWarehById(warehId);
    }

    /**
     * 根据组织id查询当前组织仓库是否存在
     *
     * @author HHe
     * @date 2019/9/24 10:36
     */
    @Override
    public Wareh querywarehByUnitId(Long warehId, Long ownerId) {
        return warehMapper.querywarehByUnitId(warehId, ownerId);
    }

    /**
     * 判断组织Id是否存在仓库中，并且返回仓库存在的id集合
     * 要先判断仓库warehNum是否为空
     *
     * @author HHe
     * @date 2019/10/6 16:00
     */
    @Override
    public List<Long> judgeReWarehIds(String warehNum, Long unitId) {
        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setOwnerId(unitId);
        unitOwner.setUnitNum(warehNum);
        List<SysUnitOwner> sysUnitOwnerList = sysUnitOwnerSerivcer.queryObjByDimNumAndOwner(unitOwner);
        //请求sysunit得到仓库id查询；
        List<Long> warehIds = new ArrayList<>();
        boolean b = false;
        if (sysUnitOwnerList != null && sysUnitOwnerList.size() > 0) {
            for (SysUnitOwner sysUnitOwner : sysUnitOwnerList) {
                warehIds.add(sysUnitOwner.getUnitId());
            }
            warehIds = this.getSomeWarehId(warehIds, unitId);
            if (warehIds == null && warehIds.size() <= 0) {
                b = true;
            }
        } else {
            b = true;
        }
        if (b) {
            warehIds.add(-1L);
        }
        return warehIds;
    }

    /**
     * 判断组织的仓库中是否存在这些仓库并将其返回
     *
     * @author HHe
     * @date 2019/10/7 12:25
     */
    @Override
    public List<Long> getSomeWarehId(List<Long> warehIds, Long unitId) {
        return warehMapper.judgeReWarehIds(warehIds, unitId);
    }

    /**
     * 初始化功能按钮
     */
    @Override
    public List<PurKeyValue> initButtons(ProdCls model) {
        String para = "warehFuntionButton";
        SysParameter sysParameter = parameterMapper.findById(para);
        if (sysParameter == null) {
            throw new ServiceException("403", "仓库功能按钮参数不存在，参数Id:warehFuntionButton");
        }
        String[] splis = sysParameter.getParmVal().split(";");
        if (splis == null) {
            throw new ServiceException("403", "仓库功能按钮参数值设置有误。");
        }
        List<PurKeyValue> keyValue = new ArrayList<>();
        for (String s : splis) {
            String[] sl = s.split("=");
            PurKeyValue key = new PurKeyValue(sl[0], sl[1]);
            keyValue.add(key);
        }
        return keyValue;
    }


    /**
     * 通用弹框接口
     *
     * @author HHe
     * @date 2019/9/29 11:55
     */
    @Override
    public Object selectObject(CommonWindowModel model, SysUser user) {
        //根据登录组织Id作为ownerId和unitNum查询出对应的sys_unit_owner
        String unitNum = model.getFields().get(0).getOptionValue();
        SysUnitOwner sysUnitOwner = sysUnitOwnerSerivcer.queryObjByNumAndOwner(unitNum, user);
        if (sysUnitOwner == null) {
            throw new ServiceException("201", "组织未定义");
        }
        //根据unit_id到当前登录组织去wareh查询能否使用
        Wareh wareh = warehMapper.querywarehByUnitIdInA(sysUnitOwner.getUnitId(), user.getDomain().getUnitId());
        if (wareh == null) {
            throw new ServiceException("201", "仓库不可用");
        }
        WarehVo warehVo = new WarehVo();
        BeanUtils.copyProperties(wareh, warehVo);
        warehVo.setUnitNum(unitNum);
        //根据Id查询对应的信息封装进warehVo
        Set<Long> warehIds = new TreeSet<>();
        if (warehVo.getWarehId() != null) {
            warehIds.add(warehVo.getWarehId());
        }
        if (warehVo.getCostGrpId() != null) {
            warehIds.add(warehVo.getCostGrpId());
        }
        if (warehVo.getCtrlUnitId() != null) {
            warehIds.add(warehVo.getCtrlUnitId());
        }
        if (warehVo.getFsclUnitId() != null) {
            warehIds.add(warehVo.getFsclUnitId());
        }
        if (warehVo.getPropOwnerId() != null) {
            warehIds.add(warehVo.getPropOwnerId());
        }
        List<SysUnit> sysUnits = sysUnitService.queryUnitByIds(warehIds);
        for (SysUnit sysUnit : sysUnits) {
            if (sysUnit.getUnitId().equals(warehVo.getWarehId())) {
                warehVo.setUnitName(sysUnit.getUnitName());
            }
            if (sysUnit.getUnitId().equals(warehVo.getCostGrpId())) {
                warehVo.setCostGrpName(sysUnit.getUnitName());
            }
            if (sysUnit.getUnitId().equals(warehVo.getCtrlUnitId())) {
                warehVo.setAdUnitName(sysUnit.getUnitName());
                warehVo.setAdUnitCode(sysUnit.getUnitCode());
            }
            if (sysUnit.getUnitId().equals(warehVo.getFsclUnitId())) {
                warehVo.setSwUnitName(sysUnit.getUnitName());
                warehVo.setSwUnitCode(sysUnit.getUnitCode());
            }
            if (sysUnit.getUnitId().equals(warehVo.getPropOwnerId())) {
                warehVo.setPropUnitName(sysUnit.getUnitName());
                warehVo.setPropUnitCode(sysUnit.getUnitCode());
            }
        }
//        Field[] declaredFields = warehVo.getClass().getDeclaredFields();
//        for (Field f : declaredFields) {
//            if(f.getName().contains("Id")){
//                String n = f.getName().split("Id")[0];
//                String codeN = n+"Code";
//                String nameN = n+"Name";
//                Method codeMe = warehVo.getClass().getDeclaredMethod("set"+codeN.substring(0,1).toUpperCase()+codeN.substring(1),warehVo.getClass().getDeclaredField(codeN).getType());
//                Method nameMe = warehVo.getClass().getDeclaredMethod("set"+nameN.substring(0,1).toUpperCase()+codeN.substring(1),warehVo.getClass().getDeclaredField(nameN).getType());
//
//                if(codeMe!=null){
//
//                }
//                if(nameMe!=null){
//
//                }
//            }
//        }
        return warehVo;
    }
}
