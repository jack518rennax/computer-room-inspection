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
 * 巡检任务
 *
 * @author patrol-team
 */
@Data
@TableName("patrol_task")
public class PatrolTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联巡检计划ID */
    private Long planId;

    /** 关联巡检点位ID */
    private Long pointId;

    /** 任务名称 */
    private String taskName;

    /** 巡检日期 */
    private LocalDate taskDate;

    /** 任务状态：0=待派发，1=待接单，2=巡检中，3=已完成，4=超期 */
    private Integer status;

    /** 计划开始时间 */
    private LocalDateTime planStartTime;

    /** 计划结束时间 */
    private LocalDateTime planEndTime;

    /** 实际开始时间 */
    private LocalDateTime actualStartTime;

    /** 实际结束时间 */
    private LocalDateTime actualEndTime;

    /** 巡检员ID（关联 sys_user） */
    private Long inspectorId;

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
