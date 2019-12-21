package com.boyu.erp.platform.usercenter.mapper.system;

import com.boyu.erp.platform.usercenter.entity.system.FsclPerd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 会计区间mapper
 * @author HHe
 * @date 2019/10/21 17:24
 */
@Repository
public interface FsclPerdMapper {
    int insert(FsclPerd record);

    int insertSelective(FsclPerd record);

    int updateByPrimaryKeySelective(FsclPerd record);

    int updateByPrimaryKey(FsclPerd record);
    /**
     * 查询会计区间
     * @author HHe
     * @date 2019/10/21 17:24
     */
    FsclPerd queryTimeQByPerd(@Param("curFsclYear") Long curFsclYear, @Param("curFsclPerd") Long curFsclPerd);
}