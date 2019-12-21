package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.BatchRefNum;
import com.boyu.erp.platform.usercenter.mapper.warehouse.BatchRefNumMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.BatchRefNumSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类描述:
 *
 * @Description: 商品生产批次编号服务
 * @auther: CLF
 * @date: 2019/7/8 19:32
 */
@Slf4j
@Service
@Transactional
public class BatchRefNumSerivceImpl implements BatchRefNumSerivce {
    @Autowired
    private BatchRefNumMapper batchRefNumMapper;

    /**
     * 功能描述:
     *
     * @Description: 增加批次号编号
     * @param: batchRefNum
     * @return: int
     * @auther: CLF
     * @date: 2019/7/9 9:19
     */
    @Override
    public int addBatchRefNum(BatchRefNum batchRefNum) {
        return batchRefNumMapper.insertBatchRefNum(batchRefNum);
    }

    /**
     * 功能描述:
     *
     * @Description: 查询批次号编号
     * @param: batchRefNum
     * @return: batchRefNum
     * @auther: CLF
     * @date: 2019/7/9 9:19
     */
    @Override
    @Transactional(readOnly = true)
    public BatchRefNum selectBatchRefNum(BatchRefNum batchRefNum) {
        return batchRefNumMapper.getBatchRefNum(batchRefNum);
    }

    /**
     * 功能描述:
     *
     * @Description: 修改批次号编号
     * @param: batchRefNum
     * @return: batchRefNum
     * @auther: CLF
     * @date: 2019/7/9 9:19
     */
    @Override
    public int updateBatchRefNum(BatchRefNum batchRefNum) {
        return batchRefNumMapper.updateBatchRefNum(batchRefNum);
    }
}
