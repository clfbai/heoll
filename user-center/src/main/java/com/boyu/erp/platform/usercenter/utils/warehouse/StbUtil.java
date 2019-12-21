package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitClsf;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysRefNumDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysRefNumMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitClsfMapper;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;
import com.boyu.erp.platform.usercenter.service.warehouse.StbBoxService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 调拨单工具类
 */
@Slf4j
@Component
public class StbUtil {
    @Autowired
    private WarehService warehService;

    @Autowired
    private StbService stbService;

    @Autowired
    private StbDtlService stbDtlService;

    @Autowired
    private StbBoxService stbBoxService;

    @Autowired
    private SysRefNumDtlMapper refNumDtlMapper;

    @Autowired
    private SysRefNumMapper refNumMapper;

    @Autowired
    private SysUnitClsfMapper unitClsfMapper;


    /**
     * 随机字符
     */
    public String getNum() {
        String[] str = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int index = (int) (Math.random() * str.length + 0);
        return str[index];
    }

    /**
     * 获取编号集合装换为Long集合并排序
     */
    public List<Long> getLong(Long unitId, String str) {
        List<String> getlist = new ArrayList<>();
        List<String> getTow = new ArrayList<>();
        List<Long> getLong = new ArrayList<>();
        //查询组织入库单编号 添加到集合
        stbService.getStbNum(unitId).stream().forEach(s -> getlist.add(s.get("stb_num")));
        //截取字母后数字集合
        getTow = getlist.stream().filter(s -> s.substring(0, 1).equals(str)).collect(Collectors.toList());
        if (getTow.size() > 0) {
            //转为 Long 集合
            getTow.forEach(s -> getLong.add(Long.parseLong(s.substring(1, s.length()))));
        }
        //升序
        Collections.sort(getLong);
        return getLong;
    }

    /**
     * 生成入库单编号（即库存编号）
     * 规则 根据 编号表 stb_num Id  获得 编号最大长度
     * 首位随机生成字符 + 自增的数字 当同一个字母下的数字位数用完 会递归检查再次随机生成字母。
     * 当所有字母都用完 请改变 编号最大长度 (toNum 的最大位数 最大14位) 同一组织 每秒生成一单 够使用3年以上
     */

    public String generateGrnNum(Long unitId) throws ServiceException {
        String refNumId = "stb_num";
        SysRefNum refNum = new SysRefNum();
        refNum.setRefNumId(refNumId);
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setRefNumId(refNumId);
        //不区分组织统一用100组织代替
        refNumDtl.setUnitId(100L);
        refNumDtl = refNumDtlMapper.finbyId(refNumDtl);
        refNum = refNumMapper.findByByRefNum(refNum);
        //没有创建编号
        if (refNum == null) {
            refNum = new SysRefNum();
            refNum.setRefNumId(refNumId);
            refNum.setDescription("入库单编号");
            refNum.setFromNum(1L);
            refNum.setToNum(1000000000000000000L - 1L);
            refNum.setStepSize(1L);
            refNum.setUpdateTime(new Date());
            refNumMapper.insertSelective(refNum);
        }
        if (refNumDtl == null) {
            refNumDtl = new SysRefNumDtl();
            refNumDtl.setRefNumId(refNumId);
            refNumDtl.setUnitId(unitId);
            refNumDtl.setUpdateTime(new Date());
            refNumDtlMapper.insertSelective(refNumDtl);
        }
        //最新编号
        Long news = refNumDtl.getLastNum();
        Long kais = refNum.getFromNum();
        Long stepSize = refNum.getStepSize();
        int lenth = refNum.getToNum().toString().length();
        String str = "";
        if (StringUtils.isBlank(refNumDtl.getPrefix())) {
            str = getNum();
        } else {
            str = refNumDtl.getPrefix();
        }
        //如果第一次创建编号
        if (news == null || (news != null && news < 0L)) {
            if (kais == null || stepSize == null) {
                throw new ServiceException("403", "入库单编号增量为空或入库单编号起始编号为空");
            }
            news = kais + stepSize;
        } else {
            news += stepSize;
        }
        if ((news + "").length() >= lenth) {
            throw new ServiceException("403", "入库单编号已到达最大值，请修改最大编号值，或联系维护人员");
        }
        refNumDtl.setLastNum(news);
        refNumDtlMapper.updateByPrimaryKeySelective(refNumDtl);

        String max = str + news;
        String ss = "";
        for (int i = 0; i < lenth - max.length(); i++) {
            ss += "0";
        }
        return str + ss + news;
    }


    /**
     * 准备部分需要关联查询基础数据
     */
    public void prepareGrnInfo(GrnVo grnVo) {
        grnVo.setStbNum(grnVo.getGrnNum());
        grnVo.setDocDate(new Date());
        if ("F".equals(grnVo.getFsclDateAptd())) {
            grnVo.setFsclDate(new Date());
        }
        grnVo.setOpTime(new Date());
    }

    /**
     * 每次更新/删除/修改库存单明细或者库存单装箱，都会更新主表关联字段
     * updateType: D:明细 B:装箱
     *
     * @param stbVo
     * @param updateType
     * @return
     */
    public int reCalStbInfo(Stb stbVo, String updateType) {
        Stb stb = stbService.getStbByPk(stbVo);
        BigDecimal ttlExpdBox = new BigDecimal(0);
        BigDecimal ttlExpdQty = new BigDecimal(0);
        BigDecimal ttlQty = new BigDecimal(0);
        BigDecimal ttlBox = new BigDecimal(0);
        BigDecimal ttlVal = new BigDecimal(0);
        BigDecimal ttlRwd = new BigDecimal(0);
        BigDecimal ttlTax = new BigDecimal(0);
        BigDecimal ttlMkv = new BigDecimal(0);
        BigDecimal ttlCost = new BigDecimal(0);
        if ("D".equals(updateType)) {
            StbDtl dtl = new StbDtl();
            dtl.setUnitId(stbVo.getUnitId());
            dtl.setStbNum(stbVo.getStbNum());
            List<StbDtlVo> stbDtlList = stbDtlService.getStbDtlList(dtl);
            for (StbDtlVo _stdDtl : stbDtlList) {
                ttlExpdQty = ttlExpdQty.add(_stdDtl.getExpdQty());
                ttlQty = ttlQty.add(_stdDtl.getQty());
                ttlVal = ttlVal.add(_stdDtl.getVal());
                ttlRwd = ttlRwd.add(_stdDtl.getRwd());
                ttlTax = ttlTax.add(_stdDtl.getTax());
                ttlMkv = ttlMkv.add(_stdDtl.getMkv());
                ttlCost = ttlCost.add(_stdDtl.getCost());
            }
            stb.setTtlExpdQty(ttlExpdQty);
            stb.setTtlQty(ttlQty);
            stb.setTtlVal(ttlVal);
            stb.setTtlRwd(ttlRwd);
            stb.setTtlTax(ttlTax);
            stb.setTtlMkv(ttlMkv);
            stb.setTtlCost(ttlCost);
        } else if ("B".equals(updateType)) {
            StbBox dtl = new StbBox();
            dtl.setUnitId(stbVo.getUnitId());
            dtl.setStbNum(stbVo.getStbNum());
            List<StbBox> stbBoxList = stbBoxService.getStbBoxList(dtl);
            for (StbBox _stbBox : stbBoxList) {
                ttlExpdBox = ttlExpdBox.add(_stbBox.getExpdBox());
                ttlBox = ttlBox.add(_stbBox.getBox());
            }
            stb.setTtlExpdBox(ttlExpdBox);
            stb.setTtlBox(ttlBox);
        }
        return stbService.update(stb);
    }


    /**
     * 功能描述: 生成入
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/1 10:04
     */
    public void setStbNum(Stb stb, Grn grn, List<StbDtl> stbDtls, SysUser user) {
        //生成入库单编号
        String stb_num = this.generateGrnNum(grn.getUnitId());
        //赋值
        grn.setGrnNum(stb_num);
        //入库单进度为确认状态
        grn.setProgress("PG");

        stb.setStbNum(stb_num);
        /**
         * 入库单明细 一张入库单会对应多分商品Id
         * */
        if (CollectionUtils.isNotEmpty(stbDtls)) {
            stbDtls.stream().forEach(stbDtl -> stbDtl.setUnitId(stb.getUnitId()));
            stbDtls.stream().forEach(stbDtl -> stbDtl.setStbNum(stb_num));
        }
    }


    /**
     * 功能描述:  Stb Grn Stb_Dtl 赋值
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/31 19:47
     */
    public void setGrnModel(GrnDtlModel model, SysUser user) {

        //取 收货仓库的信息赋值 入库控制
        Wareh wareh = warehService.selectByWarehId(model.getWarehRcvTask().getWarehId());
        //入库方式
        String rvcMode = model.getWarehRcvTask().getRcvMode();
        Grn grn = model.getGrn();
        grn.setUnitId(model.getWarehRcvTask().getUnitId());
        grn.setRcvMode(rvcMode);
        grn.setFsclRcvMode(rvcMode);
        //Grn 表 启用验收
        grn.setAcptReqd(wareh.getAcptUidReqd());
        //默认不起用分储
        grn.setPaReqd(CommonFainl.FALSE);
        //收货开始时间为当前生成订单时间
        grn.setTkovStTime(new Date());
        Stb stb = model.getStb();
        stb.setUnitId(model.getWarehRcvTask().getUnitId());
        //Stb 表
        // 单据日期
        stb.setDocDate(new Date());
        //Stb 表 启用装箱
        stb.setBoxReqd(wareh.getBoxUidReqd());
        //配码 不起用
        stb.setBxiEnabled(CommonFainl.FALSE);
        //货位管理
        stb.setLocAdopted(wareh.getLocAdopted());
        //默认不起用预定义装箱
        stb.setBoxSchd(CommonFainl.FALSE);
        //及时结算
        stb.setInstStl(CommonFainl.FALSE);
        //采购入库需要修改成本
        if ("PURC".equals(rvcMode)) {
            stb.setCostChg(CommonFainl.TRUE);
        }
        stb.setSrcDocType(model.getWarehRcvTask().getTaskDocType());
        //查找会计组织
        SysUnitClsf unitClsf = new SysUnitClsf();
        unitClsf.setUnitType("SW");
        unitClsf.setOwnerId(model.getWarehRcvTask().getUnitId());
        unitClsf.setUnitId(stb.getWarehId());
        Long s = unitClsfMapper.selectByPrimaryKey(unitClsf) == null ? 0L : model.getWarehRcvTask().getUnitId();
        //会计组织Id 0 不存在
        stb.setFsclUnitId(s);
        //约定会计日期 默认F
        stb.setFsclDateAptd(CommonFainl.FALSE);
        //入库
        stb.setDrType("R");
        //过账控制
        stb.setPostCtrl("U");
        //操作员
        stb.setOprId(user.getUserId());
        //操时间
        stb.setOpTime(new Date());
        //已生效("t","f")
        stb.setEffective(CommonFainl.TRUE);
        //挂起("t","f")
        stb.setSuspended(CommonFainl.FALSE);
        // 撤销("t","f")
        stb.setCancelled(CommonFainl.FALSE);
        //已冲单("t","f")
        stb.setReversed(CommonFainl.FALSE);
        //是否冲单("t","f")
        stb.setIsRev(CommonFainl.FALSE);
        model.setGrn(grn);
        model.setStb(stb);
        this.setStbNum(model.getStb(), model.getGrn(), model.getStbDtls(), user);
    }
}
