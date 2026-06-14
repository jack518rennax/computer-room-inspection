package com.patrol.location.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 巡检点位（升级自 PatrolLocation）
 *
 * @author patrol-team
 */
@Data
@TableName("patrol_point")
public class PatrolPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属区域 */
    private String areaName;

    /** 设备名称 */
    private String deviceName;

    /** 设备类型（如空调/UPS/配电柜/服务器） */
    private String deviceType;

    /** 点位编码（唯一） */
    private String pointCode;

    /** 详细位置描述 */
    private String locationDetail;

    /** 二维码内容（URL/JSON） */
    private String qrcodeContent;

    /** 二维码图片URL */
    private String qrcodeImageUrl;

    /** 状态：0=禁用，1=启用 */
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
