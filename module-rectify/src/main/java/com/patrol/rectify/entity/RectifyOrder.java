package com.patrol.rectify.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 整改工单
 *
 * @author patrol-team
 */
@Data
@TableName("rectify_order")
public class RectifyOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联隐患记录ID */
    private Long dangerId;

    /** 整改人ID（关联 sys_user） */
    private Long rectifierId;

    /** 整改期限 */
    private LocalDateTime deadline;

    /** 实际完成时间 */
    private LocalDateTime actualFinishTime;

    /** 整改措施 */
    private String rectifyMeasure;

    /** 整改照片URL列表（JSON数组） */
    private String rectifyPhotos;

    /** 状态：0=待整改，1=整改中，2=待验收，3=验收通过，4=验收驳回 */
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
