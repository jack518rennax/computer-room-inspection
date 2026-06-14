package com.patrol.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色-菜单关联
 *
 * @author patrol-team
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 角色 ID */
    private Long roleId;

    /** 菜单 ID */
    private Long menuId;
}
