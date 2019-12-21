package com.boyu.erp.platform.usercenter.entity.brand;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * user_bg (用户品牌分组)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class UserBg extends BaseData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 属主Id
     */
    private Long ownerId;

    /**
     * 品牌分组Id
     */
    private String bgId;
}