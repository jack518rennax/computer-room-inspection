package com.patrol.warn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 预警记录
 *
 * @author patrol-team
 */
@Data
@TableName("warn_record")
public class WarnRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联预警规则ID */
    private Long ruleId;

    /** 关联巡检任务ID */
    private Long taskId;

    /** 关联巡检点位ID */
    private Long pointId;

    /** 预警内容 */
    private String warnContent;

    /** 状态：0=待处理，1=已忽略，2=已派单 */
    private Integer status;

    /** 关联隐患记录ID（派单后回填） */
    private Long dangerId;

    /** 预警时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;
}
