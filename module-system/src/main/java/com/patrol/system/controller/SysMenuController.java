package com.patrol.system.controller;

import com.patrol.common.R;
import com.patrol.system.entity.SysMenu;
import com.patrol.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统菜单 Controller
 *
 * @author patrol-team
 */
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /** 列表 */
    @GetMapping("/list")
    public R<List<SysMenu>> list() {
        return R.ok(sysMenuService.list());
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public R<SysMenu> detail(@PathVariable Long id) {
        SysMenu menu = sysMenuService.getById(id);
        return menu != null ? R.ok(menu) : R.error(404, "菜单不存在");
    }

    /** 新增 */
    @PostMapping
    public R<Void> create(@RequestBody SysMenu menu) {
        menu.setCreateTime(LocalDateTime.now());
        boolean saved = sysMenuService.save(menu);
        return saved ? R.ok() : R.error("新增菜单失败");
    }

    /** 修改 */
    @PutMapping
    public R<Void> update(@RequestBody SysMenu menu) {
        menu.setUpdateTime(LocalDateTime.now());
        boolean updated = sysMenuService.updateById(menu);
        return updated ? R.ok() : R.error("修改菜单失败");
    }

    /** 删除（逻辑删除） */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = sysMenuService.removeById(id);
        return removed ? R.ok() : R.error("删除菜单失败");
    }
}
