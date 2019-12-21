package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStk;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkPg;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdUidCnflMaaper;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdUidFileMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitClsfMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitOwnerMapper;
import com.boyu.erp.platform.usercenter.mapper.system.CostGrpMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform_text
 * @description: 仓库档案工具类
 * @author: clf
 * @create: 2019-06-29 10:55
 */
@Slf4j
@Component
@Transactional
public class WarehUtils {

    private final String tableName = "wareh_a";
    //LOC_STK、LOC_STK_PG、LOC_BST、LOC_BST_PG。
    @Autowired
    private LocStkMapper locStkMapper;
    @Autowired
    private LocStkPgMapper locStkPgMapper;
    @Autowired
    private LocBstMapper locBstMapper;
    @Autowired
    private LocBstPgMapper locBstPgMapper;

    @Autowired
    private WarehBstMapper warehBstMapper;

    @Autowired
    private WarehBstPgMapper warehBstPgMapper;

    @Autowired
    private SysUnitMapper unitMapper;

    @Autowired
    private FiledUtils filedUtils;

    @Autowired
    private WarehStkMapper warehStkMapper;

    @Autowired
    private WarehMapper warehMapper;

    @Autowired
    private ProdUidCnflMaaper prodUidCnflMaaper;

    @Autowired
    private ProdUidFileMapper prodUidFileMapper;

    @Autowired
    private SysUnitClsfMapper unitClsfMapper;

    @Autowired
    private SysUnitOwnerMapper unitOwnerMapper;

    @Autowired
    private SysParameterMapper parameterMapper;

    @Autowired
    private CostGrpMapper costGrpMapper;

    @Autowired
    private WarehStkPgMapper warehStkPgMapper;

    /**
     * 1.从现有组织添加为仓库
     * 2. 增加新组织仓库
     * 3.目前增加仓库都是业务仓库(即同时需要开设会计仓库，并且会计仓库仅支持和业务仓库结构一致）
     * 会计仓库有关结算 （某个组织可以是桃花季下属(加盟店) 同时它可以选择其他会计组织为其清算账务这样在系统中他的会计仓库桃花季无法记录,即会计仓库不是桃花季)
     */
    public int createWareh(SysUnit unit, SysUser user, WarehVo warehVo) {
        int a = 0;
        //2.2
        Wareh wareh = new Wareh();
        BeanUtils.copyProperties(warehVo, wareh);
        //根据参数赋值
        setParamWareh(wareh);
        wareh.setWarehId(unit.getUnitId());
        wareh.setUpdTime(new Date());
        a += warehMapper.insertWareh(wareh);
        //2.3
        SysUnitClsf unitClsf = new SysUnitClsf();
        unitClsf.setUnitId(unit.getUnitId());
        unitClsf.setOwnerId(warehVo.getOwnerId());
        unitClsf.setUnitType("WH");
        a += unitClsfMapper.insertSysUnitClsf(unitClsf);
        unitClsf.setUnitType("SW");
        a += unitClsfMapper.insertSysUnitClsf(unitClsf);

        //2.4
        SysUnitOwner unitOwner = new SysUnitOwner();
        unitOwner.setUnitId(unit.getUnitId());
        unitOwner.setOwnerId(warehVo.getOwnerId());
        unitOwner.setUnitNum(warehVo.getUnitNum());
        a += addUnitOwner(unitOwner);

        a += addCostGrp(unit.getUnitId());

        return a;
    }

    //添组织属组
    public int addUnitOwner(SysUnitOwner unitOwner) {
        unitOwner.setDeleted(CommonFainl.FALSE);
        return unitOwnerMapper.insertUnitOwner(unitOwner);
    }

    //添加成本组
    public int addCostGrp(Long unitId) {
        CostGrp costGrp = new CostGrp();
        costGrp.setUnitId(unitId);
        costGrp.setCostGrpId(0L);
        costGrp.setCostGrpName(CommonFainl.COSTNAME);
        return costGrpMapper.insertCostGrp(costGrp);
    }


    public Wareh setParamWareh(Wareh wareh) {
        /**
         * WAREHOUSE_DEFAULT_STOCK_ADOPTED      (仓库是否启用库存管理参数)
         *
         * WAREHOUSE_DEFAULT_UID_ADOPTED  (仓库启用唯一码参数)
         *
         * WAREHOUSE_DEFAULT_BOXED_STOCK_ADOPTED  (仓库启用装箱库存参数)
         *
         * WAREHOUSE_DEFAULT_LOCATION_ADOPTED     (仓库启用货位管理参数)
         *
         * WAREHOUSE_DEFAULT_NEGATIVE_LOCATION_STOCK_ALLOWED(仓库是否允许货位负库存参数)
         *
         * WAREHOUSE_DEFAULT_NEGATIVE_STOCK_ALLOWED  (仓库是否允负库存许参数)
         *
         *
         * WAREHOUSE_DEFAULT_ASSORTED_STOCK_ADOPTED  (仓库是否启用配码库存管理参数)
         * */

        if (StringUtils.isBlank(getWarehParameter("WAREHOUSE_DEFAULT_STOCK_ADOPTED"))) {
            //库存管理默认启用
            wareh.setStkAdopted(CommonFainl.TRUE);
        } else {
            wareh.setStkAdopted(getWarehParameter("WAREHOUSE_DEFAULT_STOCK_ADOPTED"));
        }
        if (StringUtils.isBlank(getWarehParameter("WAREHOUSE_DEFAULT_UID_ADOPTED"))) {
            //唯一码默认否
            wareh.setUidAdopted(CommonFainl.FALSE);
        } else {
            wareh.setUidAdopted(getWarehParameter("WAREHOUSE_DEFAULT_UID_ADOPTED"));
        }
        //配码目前不需要
        wareh.setAstAdopted("F");
        if (StringUtils.isBlank(getWarehParameter("WAREHOUSE_DEFAULT_BOXED_STOCK_ADOPTED"))) {
            //装箱库存默认否
            wareh.setBstAdopted(CommonFainl.FALSE);
        } else {
            wareh.setBstAdopted(getWarehParameter("WAREHOUSE_DEFAULT_BOXED_STOCK_ADOPTED"));
        }
        if (StringUtils.isBlank(getWarehParameter("AREHOUSE_DEFAULT_LOCATION_ADOPTED"))) {
            //货位管理默认否
            wareh.setLocAdopted(CommonFainl.FALSE);
        } else {
            wareh.setLocAdopted(getWarehParameter("AREHOUSE_DEFAULT_LOCATION_ADOPTED"));
        }
        if (StringUtils.isBlank(getWarehParameter("WAREHOUSE_DEFAULT_NEGATIVE_LOCATION_STOCK_ALLOWED"))) {
            //货位负库存默认否
            wareh.setNegLocStk(CommonFainl.FALSE);
        } else {
            wareh.setNegLocStk(getWarehParameter("WAREHOUSE_DEFAULT_NEGATIVE_LOCATION_STOCK_ALLOWED"));
        }
        if (StringUtils.isBlank(getWarehParameter("WAREHOUSE_DEFAULT_NEGATIVE_LOCATION_STOCK_ALLOWED"))) {
            //仓库允负库存 默认否
            wareh.setNegStk(CommonFainl.FALSE);
        } else {
            wareh.setNegStk(getWarehParameter("WAREHOUSE_DEFAULT_NEGATIVE_LOCATION_STOCK_ALLOWED"));
        }
        return wareh;
    }

    //设置无需参数控制默认值
    public void setParam(Wareh wareh) {
        //启用唯一码验收
        wareh.setAcptUidReqd(CommonFainl.FALSE);
        //启用唯一码分储
        wareh.setPaUidReqd(CommonFainl.FALSE);
        //启用唯一码分拣
        wareh.setPickUidReqd(CommonFainl.FALSE);
        //启用唯一码复核
        wareh.setRckUidReqd(CommonFainl.FALSE);
        //启用唯一码装箱
        wareh.setBoxUidReqd(CommonFainl.FALSE);
    }


    /**
     * 查询参数是否存在
     */
    public String getWarehParameter(String s) {
        String str = parameterMapper.findById(s) == null ? "" : parameterMapper.findById(s).getParmVal();
        return str;
    }


    /**
     * 判断仓库是否存在
     */
    public void isNotWareh(WarehVo wareh) {
        Wareh s = warehMapper.selectByWarehId(wareh.getWarehId());
        if (s != null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "仓库不能属于多个属主");
        }
    }


    /**
     * 查询仓库不可修改字段
     */
    public List<String> isNotUpdateWareh() {
        String str = tableName + ParameterString.TABLE_NOT_UPDATE;
        str = parameterMapper.findById(str) == null ? "" : parameterMapper.findById(str).getParmVal();
        if (StringUtils.isNotBlank(str)) {
            List<String> list = filedUtils.creatHump(filedUtils.StringSlip(str));
            return list;
        }
        return null;
    }

    /**
     * 不能修改字段设置为空
     */
    public void setUpdateWareh(Wareh wareh, List<String> lit) throws Exception {
        Field[] fields = wareh.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            for (String s : lit) {
                if (f.getName().equalsIgnoreCase(s)) {
                    log.info("不能直接修改字段" + f.getName());
                    f.set(wareh, null);
                }
            }
        }
    }

    /**
     * 判断仓库库存是否为空
     *
     * @param w
     * @return false 不为空 true 为空
     * <p>
     * 判断仓库存是否为空的依据 :
     * 1.在 wareh_stk(仓库库存表) 没有仓库的记录 则库存为空
     * 2.wareh_stk 存在记录时 stk_on_hand(实际库存)和qty_hldn（挂账库存）都为0或null 则为空否则非空
     */
    public boolean isWarehEmpty(Wareh w) {
        List<WarehStk> list = new ArrayList<>();
        //不存在库存记录
        if (warehStkMapper.selectCountWareh(w) == 0) {
            return true;
        } else {
            //如果存在库存记录取实际库存或者挂账库存不为0的记录
            list = warehStkMapper.selectWareh(w).parallelStream().filter(s -> !s.getQtyHldn().equals(0f) || !s.getQtyHldn().equals(0f)).collect(Collectors.toList());
            list.parallelStream().forEach(s -> System.out.println("库存对象    " + s));
            return list == null ? true : list.size() <= 0;
        }
    }

    /**
     * 判断仓库未决库存是否为空
     *
     * @param w
     * @return false 不为空 true 为空
     * <p>
     * 判断仓库存是否为空的依据 :
     * 1.在 wareh_stk_pg(仓库库存表) 没有仓库的记录 则库存为空
     * 2.wareh_stk_pg 存在记录时 qty(数量)为0或null 则为空否则非空
     */
    public boolean isWarehPgEmpty(Wareh w) {
        List<WarehStkPg> list = new ArrayList<>();
        //不存在库存记录
        if (warehStkPgMapper.selectCountWareh(w.getWarehId()) == 0) {
            return true;
        } else {
            //如果存在库存记录取实际库存或者挂账库存不为0的记录
            WarehStkPg pg = new WarehStkPg();
            pg.setWarehId(w.getWarehId());
            list = warehStkPgMapper.selectByWareh(pg).parallelStream().filter(s -> !s.getQty().equals(0f)).collect(Collectors.toList());
            list.parallelStream().forEach(s -> System.out.println("未决库存对象    " + s));
            return list == null ? true : list.size() <= 0;
        }
    }


    /**
     * 判断仓库类型 门店类型为true(不能删除)
     */
    public boolean warehType(SysUnitClsf w) {
        List<SysUnitClsf> list = unitClsfMapper.selectByUnit(w).parallelStream().filter(s -> s.getUnitType().equalsIgnoreCase(CommonFainl.UNITTYPE_SH)).collect(Collectors.toList());
        list.stream().forEach(s -> System.out.println("组织类型对象： " + s));
        return list != null ? list.size() > 0 : false;
    }

    /**
     * enableUID：启用或禁用唯一码管理。
     * 删除`prod_uid_cnfl`(唯一商品冲突码表)记录
     * 删除`prod_uid_file`(唯一商品冲突档案表)记录
     * 删除库存表记录
     * 删除未决库存表记录
     */
    public int enableUIDFalse(Wareh w) {
        //设置唯一码字段
        w.setUidAdopted(CommonFainl.FALSE);
        //删除记录WAREH_STK、WAREH_STK_PG、
        return prodUidCnflMaaper.deleteProdUidCnfl(w.getWarehId()) +
                prodUidFileMapper.deleteProdUidFile(w.getWarehId()) +
                warehStkMapper.deleteByWarehStkWarehId(w.getWarehId()) +
                warehStkPgMapper.deleteWarehId(w.getWarehId());
    }

    /**
     * enableBoxedStock：启用或者禁用装箱库存管理。
     * 1.锁定仓库，设置BST_ADOPTED标记。
     * 2.如果禁用，则删除以下各表记录：WAREH_BST、WAREH_BST_PG、LOC_BST、LOC_BST_PG。
     */
    public int enableBoxedStock(Wareh w) {
        //1.
        w.setBstAdopted(CommonFainl.FALSE);
        Long l = w.getWarehId();
        return warehBstMapper.deleteWarehBstWareh(l) +
                warehBstPgMapper.deleteWarehBstPgWareh(l) +
                locBstMapper.deleteLocBstWareh(l) +
                locBstPgMapper.deleteLocBstPgWareh(l);
    }

    /**
     * enableLocation：启用或者禁用货位管理。
     * 1）锁定仓库，设置LOC_ADOPTED标记。
     * 2）如果启用：
     * 获取仓库DELIV_LOC_ID作为启用货位管理后的临时存放货位。
     * 如果DELIV_LOC_ID为空，则提示错误：MSG_DELIV_LOC_ID_MUST_BE_SET。
     * 根据WAREH_STK、WAREH_AST、WAREH_BST，生成LOC_STK、LOC_AST、LOC_BST记录。
     * WAREH_STK中获取STK_ON_HAND、QTY_AST、QTY_BST、QTY_PCKD。WAREH_AST中获取STK_ON_HAND。WAREH_BST中获取STK_ON_HAND。其它字段为0。
     * 3）如果禁用，则删除以下各表记录：LOC_STK、LOC_STK_PG、LOC_BST、LOC_BST_PG。
     * 目前支持禁用
     */
    public int enableLocation(Wareh w) {
        w.setLocAdopted(CommonFainl.FALSE);
        Long l = w.getWarehId();
        int a = locStkMapper.deleteLocStkWareh(l);
        a += locStkPgMapper.deleteLocStkPgWareh(l);
        a += locBstMapper.deleteLocBstWareh(l);
        a += locBstPgMapper.deleteLocBstPgWareh(l);
        return a;
    }

    /**
     * 功能描述: 根据参数设置出入库方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/2 13:37
     */
    public int createWarehWay(Long warehId) {
        return 0;
    }


}
