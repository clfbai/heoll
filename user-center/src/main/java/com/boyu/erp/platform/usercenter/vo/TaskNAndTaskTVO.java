package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskNAndTaskTVO {
    /**
     * 任务单编号
     */
    private String taskDocNum;
    /**
     * 任务单类型
     */
    private String taskDocType;
}
