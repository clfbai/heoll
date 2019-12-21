package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatch;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchReverse;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchNumMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchReverseMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.UnitBatchNumService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform_text
 * @description: 批次序号服务
 * @author: clf
 * @create: 2019-07-05 14:51
 */
@Slf4j
@Service
@Transactional
public class UnitBatchNumServiceImpl implements UnitBatchNumService {
    @Autowired
    private SysDomainMapper domainMapper;
    @Autowired
    private UnitBatchReverseMapper unitBatchReverseMapper;
    @Autowired
    private UnitBatchMapper unitBatchMapper;
    @Autowired
    private UnitBatchNumMapper unitBatchNumMapper;


    /**
     * @Description: 生产入库或采购入库(供应商未介入)增加批次序号
     * @auther: CLF
     * @date: 2019/7/9 15:36
     */
    @Override
    public int addUnitBatchNum(UnitBatchNum unitBatchNum) {
        return unitBatchNumMapper.insertBatchNum(unitBatchNum);
    }

    /**
     * 取消出、入库时批次恢复
     * 1.判断批次商品额剩余数量改变了没有，改变不能取消
     * 2.判断批次对应供应商是否介入，不介入删除批次
     * 3.供应商介入或者采购商是顾客(-1L)组织时批次还原
     *
     * @param prodIdList (取消商品集合)
     */
    @Override
    public int reverseReceive(UnitBatchNum unitBatchNum, List<Long> prodIdList, SysUser user) throws ServiceException {
        verifyBatchNumExcp(unitBatchNum);
        List<UnitBatchReverse> reverses = unitBatchReverseMapper.selectList(unitBatchNum);
        if (CollectionUtils.isEmpty(reverses)) {
            log.info("没有批次流水日志无需回退");
            return 0;
        }
        List<UnitBatchNum> batchNums = new ArrayList<>();
        List<UnitBatch> batchs = new ArrayList<>();
        for (UnitBatchReverse se : reverses) {
            UnitBatchNum batchNum = new UnitBatchNum();
            UnitBatch batch = new UnitBatch();
            BeanUtils.copyProperties(se, batchNum);
            BeanUtils.copyProperties(se, batch);
            batchNum.setBatchNumber(se.getNum());

            batchs.add(batch);
            batchNums.add(batchNum);
        }
        //所有批次序号

        batchNums = unitBatchNumMapper.selectUnitBatchNum(batchNums);
        //所有批次
        batchs = unitBatchMapper.selectUnitBatchList(batchs);
        if (CollectionUtils.isEmpty(reverses) || CollectionUtils.isEmpty(batchNums) || CollectionUtils.isEmpty(batchs)) {
            log.info("没有查询到任何批次无需修改");
            return 0;
        }
        verifyBatchNum(batchNums);

        //采购商
        Long vendeeId = unitBatchNum.getVendeeId();
        //供应商
        Long venderId = unitBatchNum.getVenderId();
        //供应商是否介入
        boolean domain = domainMapper.selectByPrimaryKey(venderId) == null;
        //采购、生产入库或采购供应商不介入
        if (vendeeId == venderId || domain) {
            unitBatchNumMapper.deleteBatchNumList(batchNums);
            unitBatchMapper.deleteUnitBatchList(batchs);
            updateReverses(reverses);
        }
        //供应商介入或出库组织为顾客
        if (vendeeId != venderId && (!domain || vendeeId == -1L)) {
            setVendder(batchNums, batchs, reverses, unitBatchNum.getDocType(), vendeeId, venderId);
        }
        return 0;
    }


    /**
     * 校验参数
     */
    public void verifyBatchNumExcp(UnitBatchNum unitBatchNum) throws ServiceException {
        if (StringUtils.NullEmpty(unitBatchNum.getDocNum())) {
            throw new ServiceException("403", "生成批次单据编号为空");
        }
        if (StringUtils.NullEmpty(unitBatchNum.getDocType())) {
            throw new ServiceException("403", "生成批次单据类型为空");
        }
        if (StringUtils.NullEmpty(unitBatchNum.getUnitId() + "")) {
            throw new ServiceException("403", "批次组织Id为空");
        }
        if (StringUtils.NullEmpty(unitBatchNum.getVendeeId() + "")) {
            throw new ServiceException("403", "批次采购商Id为空");
        }
        if (StringUtils.NullEmpty(unitBatchNum.getVenderId() + "")) {
            throw new ServiceException("403", "批次供应商Id为空");
        }
    }

    /**
     * 检查批次信息是否该变
     */
    public void verifyBatchNum(List<UnitBatchNum> list) throws ServiceException {
        //检查批次是否有售卖记录存在不可取消出入库
        for (UnitBatchNum num : list) {
            if (!num.getSurplusQuantity().equals(num.getPurchaseQuantity())) {
                throw new ServiceException("403", "批次Id:【" + num.getBatchId() + " 剩余数量已变动不能回滚");
            }
        }
    }


    /**
     * 根据单据类型赋值供应商、采购商
     *
     * @param vendeeId 采购商
     * @param venderId 供应商
     * @param type     单据类型
     */
    public void setVendder(List<UnitBatchNum> list, List<UnitBatch> batchs, List<UnitBatchReverse> reverses, String type, Long vendeeId, Long venderId) {
        //采购商批次序号
        List<UnitBatchNum> vendeeList = list.stream().filter(s -> s.getUnitId().equals(vendeeId)).collect(Collectors.toList());
        //采购商批次
        List<UnitBatch> vendeeBatchList = batchs.stream().filter(s -> s.getUnitId().equals(vendeeId)).collect(Collectors.toList());

        //出库时取消将供应商批次回滚、采购商批次回滚(顾客组织回滚)
        if ("D".equalsIgnoreCase(type)) {
            reset(list, batchs, reverses, type, vendeeId);
        }
        //入库时取消将采购商批次删除，供应商批次回滚
        if ("R".equalsIgnoreCase(type)) {
            //采购商批次序号信息删除
            unitBatchNumMapper.deleteBatchNumList(vendeeList);
            //采购商批次信息删除
            unitBatchMapper.deleteUnitBatchList(vendeeBatchList);
            //还原批次信息
            reset(list, batchs, reverses, type, vendeeId);
        }
        if ("D".equalsIgnoreCase(type) || "R".equalsIgnoreCase(type)) {
            updateReverses(reverses);

        }
        throw new ServiceException("403", "单据类型有误");
    }


    /**
     * 检查还原批次信息
     */
    public void reset(List<UnitBatchNum> list, List<UnitBatch> batchs, List<UnitBatchReverse> reverses, String type, Long vedeeId) {
        List<UnitBatchNum> retunVenderBatchNum = new ArrayList<>();
        List<UnitBatch> retunVenderBatch = new ArrayList<>();
        List<UnitBatchNum> retunVendeeBatchNum = new ArrayList<>();
        List<UnitBatch> retunVendeeBatch = new ArrayList<>();
        for (UnitBatchReverse se : reverses) {
            for (UnitBatchNum num : list) {
                if (num.getBatchId().equalsIgnoreCase(se.getBatchId())
                        && num.getProdId().equals(se.getProdId())
                        && num.getBatchNumber().equals(se.getNum())
                        && num.getUnitId().equals(se.getUnitId())) {
                    //修改供应商剩余数量
                    num.setSurplusQuantity(num.getSurplusQuantity() + se.getMoveQty());
                    //销售数量
                    num.setMarketQuantity(num.getMarketQuantity() - se.getMoveQty());
                    num.setReturnableUnitQuantity(num.getReturnableUnitQuantity() + se.getMoveQty());
                    retunVenderBatchNum.add(num);
                }
                //采购商批次还原
                if ("D".equalsIgnoreCase("type")) {
                    if (num.getBatchId().equalsIgnoreCase(se.getBatchId())
                            && num.getProdId().equals(se.getProdId())
                            && num.getBatchNumber().equals(se.getNum())
                            && num.getUnitId().equals(se.getUnitId())
                            && se.getUnitId().equals(vedeeId)) {
                        //修改采购商剩余数量
                        num.setSurplusQuantity(num.getSurplusQuantity() - se.getMoveQty());
                        //采购数量
                        num.setPurchaseQuantity(num.getPurchaseQuantity() - se.getMoveQty());
                        num.setReturnableUnitQuantity(num.getReturnableUnitQuantity() - se.getMoveQty());
                        retunVendeeBatchNum.add(num);

                    }
                }
                for (UnitBatch batch_2 : batchs) {
                    if (se.getBatchId().equalsIgnoreCase(batch_2.getBatchId())
                            && se.getProdId().equals(batch_2.getProdId())
                            && se.getNum().equals(batch_2.getNumber())
                            && se.getUnitId().equals(batch_2.getUnitId())) {
                        //修改供应商剩余数量
                        batch_2.setSurplusQuantity(se.getMoveQty() + batch_2.getSurplusQuantity());
                        //修改销售数量
                        batch_2.setMarketQuantity(batch_2.getSurplusQuantity() - se.getMoveQty());
                        retunVenderBatch.add(batch_2);
                    }
                    //采购商批次还原
                    if ("D".equalsIgnoreCase("type")) {
                        if (se.getBatchId().equalsIgnoreCase(batch_2.getBatchId())
                                && se.getProdId().equals(batch_2.getProdId())
                                && se.getNum().equals(batch_2.getNumber())
                                && se.getUnitId().equals(batch_2.getUnitId())
                                && se.getUnitId().equals(vedeeId)) {
                            //修改供应商剩余数量
                            batch_2.setSurplusQuantity(batch_2.getSurplusQuantity() - se.getMoveQty());
                            //修改销售数量
                            batch_2.setPurchaseQuantity(batch_2.getProductionQuantity() - se.getMoveQty());
                            retunVendeeBatch.add(batch_2);
                        }
                    }
                }
            }
        }
        log.info("Vender:[供应商批次序号还原] " + retunVenderBatchNum);
        log.info("Vender:[供应商批次还原] " + retunVenderBatch);
        log.info("Vendee:[采购商批次序号还原] " + retunVendeeBatchNum);
        log.info("Vendee:[采购商批次还原] " + retunVendeeBatch);
        //还原供应商批次信息
        unitBatchNumMapper.updateBatchNumList(retunVenderBatchNum);
        unitBatchMapper.updateUnitBatchList(retunVenderBatch);
        //还原采购商批次信息
        if (CollectionUtils.isNotEmpty(retunVendeeBatch) && CollectionUtils.isNotEmpty(retunVendeeBatchNum)) {
            unitBatchNumMapper.updateBatchNumList(retunVendeeBatchNum);
            unitBatchMapper.updateUnitBatchList(retunVendeeBatch);
        }
    }

    private void updateReverses(List<UnitBatchReverse> reverses) {
        //设置批次流水逻辑删除
        reverses.stream().forEach(s -> s.setIsDel(CommonFainl.FALSE));
        unitBatchReverseMapper.updateUnitBatchReverseList(reverses);
    }

}
