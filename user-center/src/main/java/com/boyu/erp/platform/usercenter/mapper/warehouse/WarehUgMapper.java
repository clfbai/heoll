package com.boyu.erp.platform.usercenter.mapper.warehouse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
/**
 * 仓库分组Mapper
 * @author HHe
 * @date 2019/9/5 10:51
 */
@Repository
public interface WarehUgMapper {
    /**
     * 根据组织分组多选加载仓库分组
     * @author HHe
     * @date 2019/9/5 10:46
     */
    List<Map<String,String>> loadWarehUg(List<Long> sysUgIds);
    /**
     * 根据组织分组Id查询仓库分组Id集合
     * @author HHe
     * @date 2019/10/13 11:32
     */
    List<Long> queryWarehUgIdsBySysUgId(@Param("unitUgIds") List<Long> unitUgIds);
}
