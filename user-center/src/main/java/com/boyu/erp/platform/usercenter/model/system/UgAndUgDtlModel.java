package com.boyu.erp.platform.usercenter.model.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUg;
import com.boyu.erp.platform.usercenter.entity.system.SysUgDtl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UgAndUgDtlModel implements Serializable {
    /**
     * 组织分组对象
     */
    private SysUg ug;
    /**
     * 添加组织分组明细集合对象
     */
    private List<SysUgDtl> addUgDtl = new ArrayList<>();

    /**
     * 删除组织分组明细集合对象
     */
    private List<SysUgDtl> deleteUgDtl = new ArrayList<>();
}
