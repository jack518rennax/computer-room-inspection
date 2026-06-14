package com.patrol.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 巡检计划
 *
 * @author patrol-team
 */
@Data
@TableName("patrol_plan")
public class PatrolPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 计划名称 */
    private String planName;

    /** 计划描述 */
    private String description;

    /** Cron 执行周期 */
    private String cronExpr;

    /** 计划开始日期 */
    private LocalDate startDate;

    /** 计划结束日期 */
    private LocalDate endDate;

    /** 负责部门ID */
    private Long deptId;

    /** 状态：0=停用，1=启用 */
    private Integer status;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;
}
