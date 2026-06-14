package com.patrol.warn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 预警规则
 *
 * @author patrol-team
 */
@Data
@TableName("warn_rule")
public class WarnRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 规则名称 */
    private String ruleName;

    /** 适用设备类型 */
    private String deviceType;

    /** 规则表达式（JSON：阈值、条件） */
    private String ruleExpression;

    /** 告警级别：0=信息，1=警告，2=严重 */
    private Integer alarmLevel;

    /** 是否启用：0=否，1=是 */
    private Integer isEnabled;

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
