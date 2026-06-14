package com.patrol.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.system.entity.SysUser;
import com.patrol.system.mapper.SysUserMapper;
import com.patrol.system.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 系统用户 Service 实现
 *
 * @author patrol-team
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
