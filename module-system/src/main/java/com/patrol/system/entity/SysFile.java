package com.patrol.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件表
 *
 * @author patrol-team
 */
@Data
@TableName("sys_file")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 原始文件名 */
    private String originalName;

    /** 存储路径（MinIO objectName） */
    private String storagePath;

    /** 文件类型（MIME） */
    private String fileType;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 业务ID */
    private Long bizId;

    /** 业务类型（如 danger/task/rectify） */
    private String bizType;

    /** 上传人 */
    private String createBy;

    /** 上传时间 */
    private LocalDateTime createTime;
}
