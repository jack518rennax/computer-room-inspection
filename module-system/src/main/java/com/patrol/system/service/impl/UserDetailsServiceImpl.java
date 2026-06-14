package com.patrol.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.patrol.system.entity.SysMenu;
import com.patrol.system.entity.SysRole;
import com.patrol.system.entity.SysRoleMenu;
import com.patrol.system.entity.SysUser;
import com.patrol.system.entity.SysUserRole;
import com.patrol.system.mapper.SysMenuMapper;
import com.patrol.system.mapper.SysRoleMapper;
import com.patrol.system.mapper.SysRoleMenuMapper;
import com.patrol.system.mapper.SysUserMapper;
import com.patrol.system.mapper.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring Security UserDetailsService 实现
 *
 * @author patrol-team
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId()));
        Set<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());

        List<SysRole> roles = roleIds.isEmpty() ? Collections.emptyList()
                : sysRoleMapper.selectBatchIds(roleIds).stream()
                        .filter(r -> r.getStatus() != null && r.getStatus() == 1)
                        .collect(Collectors.toList());

        List<SysRoleMenu> roleMenus = roleIds.isEmpty() ? Collections.emptyList()
                : sysRoleMenuMapper.selectList(
                        new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, roleIds));
        Set<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());

        List<SysMenu> menus = menuIds.isEmpty() ? Collections.emptyList()
                : sysMenuMapper.selectBatchIds(menuIds).stream()
                        .filter(m -> m.getStatus() != null && m.getStatus() == 1)
                        .collect(Collectors.toList());

        List<SimpleGrantedAuthority> authorities = menus.stream()
                .map(SysMenu::getPerms)
                .filter(StringUtils::hasText)
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        roles.stream()
                .map(SysRole::getRoleCode)
                .filter(StringUtils::hasText)
                .map(code -> "ROLE_" + code)
                .distinct()
                .forEach(code -> authorities.add(new SimpleGrantedAuthority(code)));

        boolean enabled = user.getStatus() != null && user.getStatus() == 1;
        return new User(user.getUsername(), user.getPassword(),
                enabled, true, true, true, authorities);
    }
}
