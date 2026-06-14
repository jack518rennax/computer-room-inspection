package com.patrol.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.system.entity.SysMenu;
import com.patrol.system.mapper.SysMenuMapper;
import com.patrol.system.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 系统菜单 Service 实现
 *
 * @author patrol-team
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}
