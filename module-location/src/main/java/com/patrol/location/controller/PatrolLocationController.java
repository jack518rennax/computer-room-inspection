package com.patrol.location.controller;

import com.patrol.common.R;
import com.patrol.location.entity.PatrolLocation;
import com.patrol.location.service.PatrolLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 巡检点位 Controller
 *
 * @author patrol-team
 */
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class PatrolLocationController {

    private final PatrolLocationService patrolLocationService;

    /** 列表 */
    @GetMapping("/list")
    public R<List<PatrolLocation>> list() {
        return R.ok(patrolLocationService.list());
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public R<PatrolLocation> detail(@PathVariable Long id) {
        PatrolLocation location = patrolLocationService.getById(id);
        return location != null ? R.ok(location) : R.error(404, "点位不存在");
    }

    /** 新增 */
    @PostMapping
    public R<Void> create(@RequestBody PatrolLocation location) {
        location.setCreateTime(LocalDateTime.now());
        boolean saved = patrolLocationService.save(location);
        return saved ? R.ok() : R.error("新增点位失败");
    }

    /** 修改 */
    @PutMapping
    public R<Void> update(@RequestBody PatrolLocation location) {
        location.setUpdateTime(LocalDateTime.now());
        boolean updated = patrolLocationService.updateById(location);
        return updated ? R.ok() : R.error("修改点位失败");
    }

    /** 删除（逻辑删除） */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = patrolLocationService.removeById(id);
        return removed ? R.ok() : R.error("删除点位失败");
    }
}
