package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.BatchRefNum;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.warehouse.BatchRefNumSerivce;
import com.boyu.erp.platform.usercenter.utils.common.BaseDateUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchRefNumUtils {

    @Autowired
    private BatchRefNumSerivce batchRefNumSerivce;

    /**
     * 功能描述:判断新增或修改批次号
     *
     * @param: BatchRefNum b
     * @return: String(批次号)
     * @auther: CLF
     * @date: 2019/7/9 10:17
     */
    public String addAndUpdate(BatchRefNum b, SysUser u) throws Exception {

        if (batchRefNumSerivce.selectBatchRefNum(b) == null) {
            return addBatchRefNum(b, u);
        }
        return updateBatchRefNum(batchRefNumSerivce.selectBatchRefNum(b), u);
    }

    /**
     * 功能描述: 新增编号返回批次号
     *
     * @param: BatchRefNum b
     * @return: String
     * @auther: CLF
     * @date: 2019/7/9 10:00
     */
    public String addBatchRefNum(BatchRefNum b, SysUser u) throws Exception {
        b.setMaxNum(1L);
        BaseDateUtils.setBeasDate(b, u, CommonFainl.ADD);
        batchRefNumSerivce.addBatchRefNum(b);
        return creatBatchId(batchRefNumSerivce.selectBatchRefNum(b));
    }

    /**
     * 功能描述:组织商品已存在返回批次号
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/9 10:00
     */
    public String updateBatchRefNum(BatchRefNum b, SysUser u) throws Exception {
        b.setMaxNum(b.getMaxNum() + b.getStepSize());
        BaseDateUtils.setBeasDate(b, u, CommonFainl.UPDATE);
        batchRefNumSerivce.updateBatchRefNum(b);
        return creatBatchId(b);
    }

    /**
     * 功能描述:根据批次号编号表产生批次号
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/9 10:00
     */
    public String creatBatchId(BatchRefNum b) {
        String fill = "";
        String s = b.getProdId() + "" + b.getUnitId();
        String spei = String.valueOf(b.getMaxNum() + b.getStepSize());
        if (Integer.parseInt(b.getBatchNum()) == spei.length()) {
            throw new ServiceException(ResultCode.DB_FAILURE, "批次号已到达最大值，请修改批次增量位数");
        }
        if (Integer.parseInt(b.getBatchNum()) > spei.length()) {
            for (int i = 0; i < Integer.parseInt(b.getBatchNum()) - spei.length(); i++) {
                fill += b.getBatchFill();
            }
            fill = fill + spei;
        }
        return s + fill;
    }


}
