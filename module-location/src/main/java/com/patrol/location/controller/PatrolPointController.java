package com.patrol.location.controller;

import com.patrol.common.R;
import com.patrol.location.entity.PatrolPoint;
import com.patrol.location.service.PatrolPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 巡检点位 Controller
 */
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PatrolPointController {

    private final PatrolPointService patrolPointService;

    @GetMapping("/list")
    public R<List<PatrolPoint>> list() {
        return R.ok(patrolPointService.list());
    }

    @GetMapping("/detail/{id}")
    public R<PatrolPoint> detail(@PathVariable Long id) {
        PatrolPoint point = patrolPointService.getById(id);
        return point != null ? R.ok(point) : R.error(404, "点位不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody PatrolPoint point) {
        point.setCreateTime(LocalDateTime.now());
        if (point.getStatus() == null) point.setStatus(1);
        boolean saved = patrolPointService.save(point);
        return saved ? R.ok() : R.error("新增点位失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody PatrolPoint point) {
        point.setUpdateTime(LocalDateTime.now());
        boolean updated = patrolPointService.updateById(point);
        return updated ? R.ok() : R.error("修改点位失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = patrolPointService.removeById(id);
        return removed ? R.ok() : R.error("删除点位失败");
    }
}
