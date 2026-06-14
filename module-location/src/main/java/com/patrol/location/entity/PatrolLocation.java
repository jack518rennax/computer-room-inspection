package com.patrol.location.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 巡检点位 / 机房位置
 *
 * @author patrol-team
 */
@Data
@TableName("patrol_location")
public class PatrolLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 点位名称 */
    private String name;

    /** 点位编码 */
    private String code;

    /** 详细地址 */
    private String address;

    /** 所属区域 */
    private String area;

    /** 点位类型 */
    private String type;

    /** 描述 */
    private String description;

    /** 状态：0=禁用，1=启用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
