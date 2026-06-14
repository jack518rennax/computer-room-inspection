package com.patrol.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户-角色关联
 *
 * @author patrol-team
 */
@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户 ID */
    private Long userId;

    /** 角色 ID */
    private Long roleId;
}
