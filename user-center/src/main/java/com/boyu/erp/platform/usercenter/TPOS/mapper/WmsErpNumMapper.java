package com.boyu.erp.platform.usercenter.TPOS.mapper;

import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsErpNumMapper {
    int deleteByPrimaryKey(String taskDocNum);

    int insertSelective(WmsErpNum record);

    /**
     * 根据对象中有的信息查询全部
     * @author HHe
     * @date 2019/11/5 12:28
     */
    WmsErpNum queryByBill(WmsErpNum wmsErpNum);
    /**
     * 删除关联关系
     * @author HHe
     * @date 2019/11/6 17:46
     */
    int delRelation(WmsErpNum wmsErpNum);
}