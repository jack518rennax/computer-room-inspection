package com.patrol.rectify.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 隐患记录（升级自 RectifyRecord）
 *
 * @author patrol-team
 */
@Data
@TableName("danger_record")
public class DangerRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联巡检任务ID */
    private Long taskId;

    /** 关联巡检点位ID */
    private Long pointId;

    /** 隐患等级：0=一般，1=严重，2=紧急 */
    private Integer dangerLevel;

    /** 隐患描述 */
    private String description;

    /** 发现人ID（关联 sys_user） */
    private Long finderId;

    /** 照片URL列表（JSON数组） */
    private String photoUrls;

    /** 状态：0=待审核，1=待派单，2=已派单，3=待整改，4=待验收，5=关闭 */
    private Integer status;

    /** 审核人ID */
    private Long auditorId;

    /** 审核意见 */
    private String auditOpinion;

    /** 审核时间 */
    private LocalDateTime auditTime;

    /** 生成的整改工单ID */
    private Long rectifyOrderId;

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
