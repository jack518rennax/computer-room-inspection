package com.patrol.rectify.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 隐患整改记录
 *
 * @author patrol-team
 */
@Data
@TableName("rectify_record")
public class RectifyRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联任务 ID */
    private Long taskId;

    /** 关联点位 ID */
    private Long locationId;

    /** 问题标题 */
    private String title;

    /** 问题描述 */
    private String description;

    /** 严重程度 */
    private String severity;

    /** 状态：0=待整改，1=整改中，2=已完成 */
    private Integer status;

    /** 处理人 */
    private String handler;

    /** 整改结果 */
    private String result;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
