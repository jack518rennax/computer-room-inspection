package com.patrol.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    private Long ruleId;
    private Long taskId;
    private Long pointId;
    private String warnContent;
    private Integer status;
    private Long dangerId;
    private LocalDateTime createTime;
}
