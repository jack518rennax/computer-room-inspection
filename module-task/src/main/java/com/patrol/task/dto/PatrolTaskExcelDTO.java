package com.patrol.task.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

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
    private String taskName;

    @ExcelProperty("巡检日期")
    @ColumnWidth(15)
    private LocalDate taskDate;

    @ExcelProperty("计划ID")
    @ColumnWidth(10)
    private Long planId;

    @ExcelProperty("点位ID")
    @ColumnWidth(10)
    private Long pointId;

    @ExcelProperty("状态")
    @ColumnWidth(12)
    private String statusName;

    @ExcelIgnore
    private Integer status;

    @ExcelProperty("巡检员ID")
    @ColumnWidth(12)
    private Long inspectorId;
}
