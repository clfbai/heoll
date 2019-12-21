package com.boyu.erp.platform.usercenter.model.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUserPrivKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserPrivModel implements Serializable {

    private List<SysUserPrivKey> addUserPriv = new ArrayList<>();

    private List<SysUserPrivKey> deleteUserPriv = new ArrayList<>();
}
