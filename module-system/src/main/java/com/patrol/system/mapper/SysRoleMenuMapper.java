package com.patrol.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色-菜单关联 Mapper
 *
 * @author patrol-team
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
