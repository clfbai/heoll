package com.boyu.erp.platform.usercenter.model.system;

import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UnitScopeModel extends UnitDomainVo implements Serializable {

    private Long userId;

    /**
     * 属主ID
     */
    private Long ownerId;




}
