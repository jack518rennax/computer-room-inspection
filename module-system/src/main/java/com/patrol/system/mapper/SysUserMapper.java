package com.patrol.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 Mapper
 *
 * @author patrol-team
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
