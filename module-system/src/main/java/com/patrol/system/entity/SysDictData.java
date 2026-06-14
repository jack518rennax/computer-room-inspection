package com.patrol.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典数据
 *
 * @author patrol-team
 */
@Data
@TableName("sys_dict_data")
public class SysDictData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联字典编码 */
    private String dictCode;

    /** 字典标签（显示值） */
    private String label;

    /** 字典值（存储值） */
    private String value;

    /** 排序 */
    private Integer sort;

    /** 样式类名 */
    private String cssClass;

    /** 表格回显样式 */
    private String listClass;

    /** 是否默认：0=否，1=是 */
    private Integer isDefault;

    /** 状态：0=禁用，1=启用 */
    private Integer status;

    /** 备注 */
    private String remark;

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
