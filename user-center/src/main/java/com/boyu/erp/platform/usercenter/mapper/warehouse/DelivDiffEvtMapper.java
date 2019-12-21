package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.DelivDiffEvt;
import org.springframework.stereotype.Repository;

@Repository
public interface DelivDiffEvtMapper {
    int insert(DelivDiffEvt record);

    int insertSelective(DelivDiffEvt record);

    int updateByPrimaryKeySelective(DelivDiffEvt record);

    int updateByPrimaryKey(DelivDiffEvt record);
    /**
     * 取消出库差异
     * @author HHe
     * @date 2019/12/2 16:01
     */
    int delete(DelivDiffEvt delivDiffEvt);
}