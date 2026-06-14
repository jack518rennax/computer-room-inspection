package com.patrol.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
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

    /** 任务名称 */
    private String name;

    /** 任务描述 */
    private String description;

    /** 关联点位 ID */
    private Long locationId;

    /** Cron 表达式 */
    private String cronExpression;

    /** 任务状态：0=停止，1=运行 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
