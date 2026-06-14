package com.patrol.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.system.entity.SysRole;
import com.patrol.system.mapper.SysRoleMapper;
import com.patrol.system.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 系统角色 Service 实现
 *
 * @author patrol-team
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
