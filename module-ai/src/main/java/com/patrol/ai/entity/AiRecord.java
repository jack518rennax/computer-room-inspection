package com.patrol.ai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI 识别/分析记录
 *
 * @author patrol-team
 */
@Data
@TableName("ai_record")
public class AiRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联任务 ID */
    private Long taskId;

    /** 关联点位 ID */
    private Long locationId;

    /** 图片 URL */
    private String imageUrl;

    /** 分析结果（JSON） */
    private String analysisResult;

    /** 置信度 */
    private Double confidence;

    /** 使用的模型名称 */
    private String modelName;

    /** 状态：0=处理中，1=已完成，2=失败 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
