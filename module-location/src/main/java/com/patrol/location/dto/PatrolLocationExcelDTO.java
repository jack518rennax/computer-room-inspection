package com.patrol.location.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检点位 Excel DTO
 *
 * @author patrol-team
 */
@Data
public class PatrolLocationExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    private Long id;

    @ExcelProperty("点位名称")
    @ColumnWidth(20)
    private String name;

    @ExcelProperty("点位编码")
    @ColumnWidth(15)
    private String code;

    @ExcelProperty("详细地址")
    @ColumnWidth(30)
    private String address;

    @ExcelProperty("所属区域")
    @ColumnWidth(15)
    private String area;

    @ExcelProperty("点位类型")
    @ColumnWidth(15)
    private String type;

    @ExcelProperty("描述")
    @ColumnWidth(30)
    private String description;

    @ExcelProperty("状态")
    @ColumnWidth(10)
    private String statusName;

    @ExcelIgnore
    private Integer status;
}
