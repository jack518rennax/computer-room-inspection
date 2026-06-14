package com.patrol.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-角色关联 Mapper
 *
 * @author patrol-team
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
