package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatch;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchDetail;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchReverse;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchDetailMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchNumMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchReverseMapper;
import com.boyu.erp.platform.usercenter.model.batch.BatchModel;
import com.boyu.erp.platform.usercenter.utils.system.SysRefNumUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述: 批次号序号工具类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/9 11:50
 */
@Slf4j
@Component
@Transactional
public class UnitBatchNumUtils {
    @Autowired
    private SysRefNumUtils sysRefNumUtils;
    @Autowired
    private UnitBatchReverseMapper unitBatchReverseMapper;
    @Autowired
    private UnitBatchNumMapper unitBatchNumMapper;
    @Autowired
    private UnitBatchMapper unitBatchMapper;
    @Autowired
    private UnitBatchDetailMapper unitBatchDetailMapper;

    public int addBatchNum(UnitBatchNum unitBatchNum, UnitBatch batch, SysUser user) {
        return 0;
    }

    /**
     * 功能描述:  通过采购商、供应商、采购数量挑选批次信息并排序
     *
     * @param: unitBatchNum user
     * @return:
     * @auther: CLF
     * @date: 2019/7/9 12:00
     */
    public int addBatchNumText(BatchModel model, SysUser user) throws Exception {
        UnitBatchNum ub = model.getUnitBatchNum();
        //采购总数量
        Integer quantity = ub.getPurchaseQuantity();
        //采购组织Id
        Long unitVendeeId = ub.getVendeeId();
        //供应商组织Id
        Long unitVenderId = ub.getVenderId();
        //商品Id
        Long prodId = ub.getProdId();
        List<UnitBatchNum> list;
        String docType = model.getUnitBatchNum().getDocType();
        String docNume = model.getUnitBatchNum().getDocNum();
        /**
         * CommonFainl.BatchTypeIl： 供应商介入采购
         * */
        int rest = 0;
        if (CommonFainl.BatchTypeIl.equalsIgnoreCase(model.getUnitBatch().getBatchCreatType())) {
            //通过采购数量获取供应商批次
            list = getVenderBatchNum(unitVenderId, prodId, quantity);
            if (CollectionUtils.isEmpty(list)) {
                throw new ServiceException("403", "供应商: " + unitVenderId + " 对应的商品Id: " + prodId + "批次为空,");
            }
            for (UnitBatchNum unitBatch : list) {
                int p = quantity - unitBatch.getSurplusQuantity() > 0 ? unitBatch.getSurplusQuantity() : quantity;
                //每一批对应销售数量
                quantity -= unitBatch.getSurplusQuantity();
                /**
                 * 修改供应商信息
                 * */
                rest += updateVendeer(unitBatch, unitVendeeId, p, docType, docNume, user);
                /**
                 * 修改采购商信息
                 * */
                rest += updateVendee(unitBatch, ub, p, docType, docNume, user);
            }
        }
        return rest;

    }


    /**
     * 功能描述: 通过采购数量获取供应商批次
     *
     * @param: unitVendeeId 供应商组织Id
     * @param: prodId   商品Id
     * @param: quantity 采购总数量
     * @return: listgoods(按照 BatchNumber批次序号 升序排列后的集合)
     * @auther: CLF
     * @date: 2019/7/11 14:38
     */
    public List<UnitBatchNum> getVenderBatchNum(Long unitVendeeId, Long prodId, Integer quantity) {
        List<UnitBatchNum> nums = new ArrayList<>();
        UnitBatchNum u = new UnitBatchNum();
        u.setUnitId(unitVendeeId);
        u.setProdId(prodId);
        List<UnitBatchNum> list = unitBatchNumMapper.getUnitBatch(u).parallelStream().filter(s -> s.getSurplusQuantity() > 0).collect(Collectors.toList());
        if (list != null && list.size() > 0) {
            /**
             * 按照 BatchNumber排序
             * */
            // listgoods.sort((x, y) -> Long.compare(x.getBatchNumber(), y.getBatchNumber()));
            list.sort(Comparator.comparingLong(UnitBatchNum::getBatchNumber));
            //特殊批次
            List<UnitBatchNum> spi = list.stream().filter(s -> CommonFainl.BatchType_SPI.equalsIgnoreCase(s.getBatchType())).collect(Collectors.toList());
            //最后处理spi 批次信息 同时进行排序
            spi.sort(Comparator.comparingLong(UnitBatchNum::getBatchNumber));
            list.removeAll(spi);
            list.addAll(spi);
            int a = this.nextList(list, quantity);
            for (int i = 0; i <= a; i++) {
                nums.add(list.get(i));
            }
            return nums;
        }
        //如果供应商批次只有有一批
        if (list != null && list.size() == 1) {
            // 购买数量大于批次数量 抛出异常 这里涉及到负库存问题
            if (list.get(0).getSurplusQuantity() < quantity)
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "供应商商品批次已售完，检查你的出库或购买数量");
        }
        return list;
    }

    /**
     * 功能描述: 通过购买数量判断供应商需要卖出几批货(这里按照先进先出)
     *
     * @param ：listgoods(排序好后的所有批次)
     * @param ：quantity(采购商购买数量)
     * @return j (返回供应商卖货批次集合下标)
     * @auther: CLF
     * @date: 2019/7/11 17:37
     */
    private int nextList(List<UnitBatchNum> list, Integer quantity) {
        for (int j = 0; j < list.size(); j++) {
            quantity = quantity - list.get(j).getSurplusQuantity();
            if (quantity <= 0) {
                return j;
            }
        }
        Integer sum = list.stream().collect(Collectors.summingInt(UnitBatchNum::getSurplusQuantity));

        throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "供应商商品Id【" + list.get(0).getProdId() + "】批次剩余数量为【" + sum + "】，检查你的出库或购买数量");
    }

    /**

     */
    /**
     * 功能描述:  修改供应商批次想关信息
     *
     * @param p            (批次采购数量(对应供应商批次数量))
     * @param u            (供应商信息)
     * @param unitVendeeId (采购商Id)
     * @return:
     * @auther: CLF
     * @date: 2019/7/11 17:36
     */
    public int updateVendeer(UnitBatchNum u, Long unitVendeeId, Integer p, String docType, String docNum, SysUser user) {
        int a;
        System.out.println("修改供应商对象：    " + u);
        System.out.println("传入供应商批次销售数量：   " + p);
        //批次详情对象
        UnitBatchDetail und = new UnitBatchDetail();
        BeanUtils.copyProperties(u, und);
        //销售数量
        u.setMarketQuantity(u.getMarketQuantity() + p);
        //剩余数量
        u.setSurplusQuantity(u.getSurplusQuantity() - p);
        if (unitVendeeId == -1L) {
            //顾客可退
            u.setReturnableShopQuantity(u.getReturnableShopQuantity() + p);
            und.setDescription("顾客购买");
        } else {
            //门店可退
            u.setReturnableUnitQuantity(u.getReturnableUnitQuantity() + p);
            und.setDescription("门店购买");
        }
        /**
         * 修改供应商批次序号序号信息
         * */
        a = unitBatchNumMapper.updateBatchNum(u);

        UnitBatch uB = new UnitBatch();
        BeanUtils.copyProperties(u, uB);
        uB.setNumber(u.getBatchNumber());
        //获取需要修改对象
        uB = unitBatchMapper.selectUnitBatch(uB);
        //剩余数量
        uB.setSurplusQuantity(uB.getSurplusQuantity() - p);
        //销售数量
        uB.setMarketQuantity(uB.getMarketQuantity() + p);
        a += unitBatchMapper.updateUnitBatch(uB);
        /**
         * 增加供应商批次序号详情信息
         * */
        und.setBatchReverse(CommonFainl.BatchTypeUTMT);
        und.setBatchType(CommonFainl.BatchType_NOL);
        und.setVenderId(und.getUnitId());
        und.setVendeeId(unitVendeeId);
        //销售数量
        und.setQuantitymarketQuantity(p);
        //采购数量
        und.setPurchaseQuantity(0);
        und.setOprId(user.getUserId());
        und.setCreatTime(new Date());
        und.setBatchNumber(Long.parseLong(u.getBatchNumber() + ""));
        //这里直接添加是为了后期可以记录顾客Id(原本只准备记录、一条特殊组织Id -1L)
        a += unitBatchDetailMapper.insertBatchDetail(und);
        //记录供应商异动
        a += addUnitBatchReverse(u, p, docType, docNum);
        return a;
    }

    /**
     * 功能描述:  修改供应商批次想关信息
     *
     * @param p  (采购数量)
     * @param u  (供应商信息)
     * @param ub (采购商)
     * @return:
     * @auther: CLF
     * @date: 2019/7/11 17:50
     */
    public int updateVendee(UnitBatchNum u, UnitBatchNum ub, int p, String docType, String docNum, SysUser user) throws Exception {

        int a = 0;
        /**
         * 1.判断采购商是否为顾客
         * 1.1 顾客批次号和批次序号只记录一条信息后面修改该信息
         *2.判断采购商对应商品编号是否存在
         * 2.1不存批次号编号添加
         * 2.2 存在批次号编号修改
         * */
        UnitBatchDetail udtl = new UnitBatchDetail();

        BeanUtils.copyProperties(ub, udtl);
        udtl.setBatchId(u.getBatchId());

        UnitBatch batch = new UnitBatch();
        //采购商Id
        Long unitVendeeId = ub.getVendeeId();
        Long munber;
        if (unitVendeeId != -1L) {
            BeanUtils.copyProperties(ub, batch);

            sysRefNumUtils.instrSysRefNumDtl(u.getProdId() + "", unitVendeeId, user);
            //最新批次序号
            munber = sysRefNumUtils.getLastNum(u.getProdId() + "", unitVendeeId);
            //设置序号
            batch.setNumber(Integer.parseInt(munber + ""));
            batch.setBatchId(u.getBatchId());
            //增加采购批次
            a += addUnitBatch(ub, batch, p);
            //采购明细
            udtl.setBatchNumber(munber);
            //增加批次序号信息
            a += addUnitBatchNum(ub, batch, p);
        } else {
            munber = -1L;
            batch.setUnitId(unitVendeeId);
            batch.setProdId(u.getProdId());
            batch.setBatchId(u.getBatchId());
            batch.setNumber(Integer.parseInt(munber + ""));
            //这里查询
            if (unitBatchMapper.selectCilentUnitBatch(batch) != null) {
                /**
                 * 已经存在顾客组织
                 * */
                batch = unitBatchMapper.selectCilentUnitBatch(batch);
                //剩余数量
                batch.setSurplusQuantity(batch.getSurplusQuantity() + p);
                //采购数量
                batch.setPurchaseQuantity(batch.getPurchaseQuantity() + p);
                batch.setProductionQuantity(batch.getProductionQuantity());
                batch.setMarketQuantity(batch.getMarketQuantity());
            } else {
                /**
                 * 顾客组织不存在
                 * */
                //剩余数量
                batch.setSurplusQuantity(p);
                System.out.println("111    " + p);
                //采购数量
                batch.setPurchaseQuantity(p);
                //生产数量
                batch.setProductionQuantity(0);
                batch.setMarketQuantity(0);
            }
            batch.setPrice(ub.getPrice());
            batch.setBatchCreatType(CommonFainl.BatchTypeIl);
            //顾客批次修改
            a += addAndUpdateUnitBatch(batch);
            /**
             * 顾客批次序号
             * */
            a += addAndUpdateClinetNum(ub, u.getBatchId(), p);
            udtl.setBatchNumber(munber);
        }
        /**
         * 组织批次明细操作
         * */
        a += addBatchDtil(udtl, user, p);
        log.info("采购商：" + u);
        //记录采购商异动
        u.setUnitId(unitVendeeId);
        u.setBatchNumber(Integer.parseInt(munber + ""));
        a += addUnitBatchReverse(u, p, docType, docNum);
        return a;
    }


    /**
     * 功能描述: 判断顾客组织信息是否存在存在修改不存在新增
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/12 9:42
     */
    public int addAndUpdateUnitBatch(UnitBatch batch) {
        if (unitBatchMapper.selectUnitBatch(batch) == null) {
            return unitBatchMapper.insertUnitBatch(batch);
        }
        return unitBatchMapper.updateUnitBatch(batch);
    }

    /**
     * 功能描述: 增加组织批次信息
     *
     * @param batch (批次对象)
     * @param ub    (传入 批次序号信息)
     * @param p     (批次采购数量)
     * @return:
     * @auther: CLF
     * @date: 2019/7/12 12:10
     */
    private int addUnitBatch(UnitBatchNum ub, UnitBatch batch, Integer p) {

        //剩余数量
        batch.setSurplusQuantity(p);
        //采购数量
        batch.setPurchaseQuantity(p);
        //生产数量
        batch.setProductionQuantity(0);
        //采购价格
        batch.setPrice(ub.getPrice());
        batch.setMarketQuantity(0);
        batch.setBatchCreatType(CommonFainl.BatchTypeIl);
        return unitBatchMapper.insertUnitBatch(batch);
    }


    /**
     * 功能描述:增加顾客组织信息(若顾客组织存在则修改)
     *
     * @param ub      (采购详情(批次序号)对象)
     * @param batchId (批次号)
     * @param p       (采购批次数量)
     * @return:
     * @auther: CLF
     * @date: 2019/7/12 9:48
     */
    public int addAndUpdateClinetNum(UnitBatchNum ub, String batchId, int p) {
        ub.setBatchId(batchId);

        if (unitBatchNumMapper.getClinet(ub) == null) {
            ub.setReturnableShopQuantity(p);
            ub.setSurplusQuantity(p);
            //采购数量
            ub.setPurchaseQuantity(p);
            System.out.println("采购批次数量：  " + p);
        } else {
            ub = unitBatchNumMapper.getClinet(ub);
            //不为空加上之前可退
            ub.setReturnableShopQuantity(ub.getReturnableShopQuantity() + p);
            //剩余数量 不为空加上之前
            ub.setSurplusQuantity(ub.getSurplusQuantity() + p);
            System.out.println("采购批次之前数量：  " + ub.getPurchaseQuantity());
            //采购数量
            ub.setPurchaseQuantity(ub.getPurchaseQuantity() + p);
            System.out.println("采购批次数量：  " + p);
        }
        ub.setMarketQuantity(0);
        ub.setCreatTime(new Date());
        ub.setReturnableUnitQuantity(0);
        //这里价格没有意义
        ub.setPrice(ub.getPrice());
        //供应商也不重要
        ub.setVenderId(ub.getVenderId());
        //采购商是顾客组织
        ub.setVendeeId(ub.getUnitId());
        //顾客序号
        ub.setBatchNumber(-1);
        ub.setBatchType(CommonFainl.BatchType_NOL);

        if (unitBatchNumMapper.getClinet(ub) == null) {
            return unitBatchNumMapper.insertBatchNum(ub);
        }

        return unitBatchNumMapper.updateBatchNum(ub);
    }


    /**
     * 功能描述: 增加批次明细
     *
     * @param udtl (明细对象)
     * @param user (操作用户)
     * @return:
     * @auther: CLF
     * @date: 2019/7/12 10:05
     */
    public int addBatchDtil(UnitBatchDetail udtl, SysUser user, Integer p) {

        //顾客
        if (udtl.getUnitId().equals(-1L)) {
            udtl.setDescription("顾客购买");
            udtl.setBatchReverse(CommonFainl.BatchTypeUTMT);
        }
        if (!udtl.getUnitId().equals(-1L)) {
            udtl.setDescription("组织采购");
            udtl.setBatchReverse(CommonFainl.BatchTypeUTPE);
        }
        udtl.setPurchaseQuantity(p);
        udtl.setQuantitymarketQuantity(0);
        udtl.setBatchType(CommonFainl.BatchType_NOL);
        udtl.setOprId(user.getUserId());
        udtl.setCreatTime(new Date());
        return unitBatchDetailMapper.insertBatchDetail(udtl);
    }


    /**
     * 功能描述:  增加采购商批次序号
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/12 12:48
     */
    private int addUnitBatchNum(UnitBatchNum ub, UnitBatch batch, int p) {
        String batchId = batch.getBatchId();
        int num = batch.getNumber();
        ub.setBatchId(batchId);
        ub.setBatchNumber(num);
        ub.setMarketQuantity(0);
        ub.setSurplusQuantity(p);
        ub.setPurchaseQuantity(p);
        ub.setReturnableShopQuantity(0);
        ub.setReturnableUnitQuantity(0);
        ub.setCreatTime(new Date());
        ub.setBatchType(CommonFainl.BatchType_NOL);
        return unitBatchNumMapper.insertBatchNum(ub);
    }

    /**
     * 记录异动信息
     */
    public int addUnitBatchReverse(UnitBatchNum num, int p, String docType, String docNum) {
        log.info("[num]" + num);
        //添加销售给顾客批次和数量 在取消出库、入库时查询
        UnitBatchReverse unitBatchReverse = new UnitBatchReverse();
        //"batchId", "prodId", "unitId", "docNum", "docType"
        BeanUtils.copyProperties(num, unitBatchReverse);
        unitBatchReverse.setNum(num.getBatchNumber());
        unitBatchReverse.setDocNum(docNum);
        unitBatchReverse.setDocType(docType);
        unitBatchReverse.setMoveQty(p);
        log.info("[unitBatchReverse ]" + unitBatchReverse);
        return unitBatchReverseMapper.insertSelective(unitBatchReverse);
    }

}
