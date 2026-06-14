package com.patrol.location.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 巡检点位二维码
 *
 * @author patrol-team
 */
@Data
@TableName("patrol_qrcode")
public class PatrolQrcode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联巡检点位ID */
    private Long pointId;

    /** 二维码编码值（全局唯一） */
    private String codeValue;

    /** 生成时间 */
    private LocalDateTime genTime;

    /** 创建时间 */
    private LocalDateTime createTime;
}
