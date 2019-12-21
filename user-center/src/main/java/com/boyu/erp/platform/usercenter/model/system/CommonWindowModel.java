package com.boyu.erp.platform.usercenter.model.system;

import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述: 通用弹窗类模型
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/22 18:25
 */
@Data
@NoArgsConstructor
public class CommonWindowModel implements Serializable {
    /**
     * 查询类别
     */
    private String selectType;
    /**
     * 键对应字段名称,值为取值
     */
    private List<PurKeyValue> fields = new ArrayList<>();
    /**
     * 查询组织id
     */
    private Long unitId;

    private String sql;
    /**
     * 表别名
     */
    private String tableNameAliase;

}
