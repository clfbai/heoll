package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.BatchRefNum;

/**
 * 类描述:
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/8 19:31
 */
public interface BatchRefNumSerivce {


    int addBatchRefNum(BatchRefNum batchRefNum);

    BatchRefNum selectBatchRefNum(BatchRefNum batchRefNum);

    int updateBatchRefNum(BatchRefNum batchRefNum);
}
