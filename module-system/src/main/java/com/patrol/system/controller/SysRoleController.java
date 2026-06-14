package com.patrol.system.controller;

import com.patrol.common.R;
import com.patrol.framework.annotation.OperLog;
import com.patrol.system.entity.SysRole;
import com.patrol.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统角色 Controller
 *
 * @author patrol-team
 */
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/list")
    public R<List<SysRole>> list() {
        return R.ok(sysRoleService.list());
    }

    @GetMapping("/detail/{id}")
    public R<SysRole> detail(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return role != null ? R.ok(role) : R.error(404, "角色不存在");
    }

    @OperLog(value = "新增角色", type = OperLog.OperType.CREATE)
    @PostMapping
    public R<Void> create(@RequestBody SysRole role) {
        role.setCreateTime(LocalDateTime.now());
        boolean saved = sysRoleService.save(role);
        return saved ? R.ok() : R.error("新增角色失败");
    }

    @OperLog(value = "修改角色", type = OperLog.OperType.UPDATE)
    @PutMapping
    public R<Void> update(@RequestBody SysRole role) {
        role.setUpdateTime(LocalDateTime.now());
        boolean updated = sysRoleService.updateById(role);
        return updated ? R.ok() : R.error("修改角色失败");
    }

    @OperLog(value = "删除角色", type = OperLog.OperType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = sysRoleService.removeById(id);
        return removed ? R.ok() : R.error("删除角色失败");
    }
}
