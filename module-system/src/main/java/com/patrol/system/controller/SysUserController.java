package com.patrol.system.controller;

import com.patrol.common.R;
import com.patrol.system.entity.SysUser;
import com.patrol.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户 Controller
 *
 * @author patrol-team
 */
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public R<List<SysUser>> list() {
        return R.ok(sysUserService.list());
    }

    @GetMapping("/detail/{id}")
    public R<SysUser> detail(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return user != null ? R.ok(user) : R.error(404, "用户不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        boolean saved = sysUserService.save(user);
        return saved ? R.ok() : R.error("新增用户失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody SysUser user) {
        user.setPassword(null);
        user.setUpdateTime(LocalDateTime.now());
        boolean updated = sysUserService.updateById(user);
        return updated ? R.ok() : R.error("修改用户失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = sysUserService.removeById(id);
        return removed ? R.ok() : R.error("删除用户失败");
    }
}
