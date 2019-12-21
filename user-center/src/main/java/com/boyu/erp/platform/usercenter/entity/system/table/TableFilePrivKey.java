package com.boyu.erp.platform.usercenter.entity.system.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类描述:  表关键字段返回类型
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/30 15:27
 */
@Data
@NoArgsConstructor
public class TableFilePrivKey {


    private List<TableFile> tableFiles;
    /**
     * 权限
     */
    private String privKey;
}
