package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:  组织树形结构
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/5 14:22
 */
@Data
@ToString
@NoArgsConstructor
public class UnitTreeVo implements Serializable {


    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 组织层级
     */
    private String unitHierarchy;
    /**
     * 选择类型
     */
    private List<CheckArr> checkArr;

    /**
     * 是否是没有下级节点
     */
    private Boolean last;
    /**
     * 等级
     */
    private Integer level;

    /**
     * 父节点
     */
    private Long parentId;

    private List<UnitTreeVo> treeVos = new ArrayList<>();

    public List<CheckArr> getCheckArr() {
        checkArr = new ArrayList<>();
        checkArr.add(new CheckArr());
        return this.checkArr;
    }
}
