package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CheckArr implements Serializable {

    private String type="0";

    private String checked="0";
}
