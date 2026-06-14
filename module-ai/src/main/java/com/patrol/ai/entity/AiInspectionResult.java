package com.patrol.ai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI 巡检结果（升级自 AiRecord）
 *
 * @author patrol-team
 */
@Data
@TableName("ai_inspection_result")
public class AiInspectionResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联巡检任务ID */
    private Long taskId;

    /** 关联巡检点位ID */
    private Long pointId;

    /** 原始图片URL */
    private String originalImageUrl;

    /** 识别结论（JSON：设备状态/异常类型/置信度等） */
    private String recognitionJson;

    /** 是否异常：0=正常，1=异常 */
    private Integer isAbnormal;

    /** 置信度（0~1） */
    private Double confidence;

    /** 使用的AI模型名称 */
    private String modelName;

    /** 状态：0=处理中，1=已完成，2=失败 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}
