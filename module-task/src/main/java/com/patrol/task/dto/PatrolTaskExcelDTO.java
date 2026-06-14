package com.patrol.task.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检任务 Excel DTO
 *
 * @author patrol-team
 */
@Data
public class PatrolTaskExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    private Long id;

    @ExcelProperty("任务名称")
    @ColumnWidth(20)
    private String name;

    @ExcelProperty("任务描述")
    @ColumnWidth(30)
    private String description;

    @ExcelProperty("关联点位ID")
    @ColumnWidth(15)
    private Long locationId;

    @ExcelProperty("Cron表达式")
    @ColumnWidth(20)
    private String cronExpression;

    @ExcelProperty("状态")
    @ColumnWidth(10)
    private String statusName;

    @ExcelIgnore
    private Integer status;
}
