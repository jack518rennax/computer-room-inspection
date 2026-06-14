package com.patrol.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author patrol-team
 */
@Data
@TableName("sys_oper_log")
public class SysOperLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 请求URI */
    private String requestUri;

    /** 请求方法（GET/POST/PUT/DELETE） */
    private String requestMethod;

    /** 操作IP */
    private String ip;

    /** 操作人用户名 */
    private String operator;

    /** 请求参数 */
    private String requestArgs;

    /** 返回结果 */
    private String responseBody;

    /** 耗时（毫秒） */
    private Long costTime;

    /** 操作状态：0=失败，1=成功 */
    private Integer operStatus;

    /** 错误信息 */
    private String errorMsg;

    /** 创建时间 */
    private LocalDateTime createTime;
}
