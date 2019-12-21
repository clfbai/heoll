package com.boyu.erp.platform.usercenter.TPOS.entity.godown;

import lombok.Data;

import java.util.List;

@Data
public class OrderComfirm {
    private List<OrderLineConfirm> OrderLine;
}
