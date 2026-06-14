package com.patrol.task.controller;

import com.patrol.common.R;
import com.patrol.task.entity.PatrolPlan;
import com.patrol.task.service.PatrolPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 巡检计划 Controller
 */
@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PatrolPlanController {

    private final PatrolPlanService patrolPlanService;

    @GetMapping("/list")
    public R<List<PatrolPlan>> list() {
        return R.ok(patrolPlanService.list());
    }

    @GetMapping("/detail/{id}")
    public R<PatrolPlan> detail(@PathVariable Long id) {
        PatrolPlan plan = patrolPlanService.getById(id);
        return plan != null ? R.ok(plan) : R.error(404, "计划不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody PatrolPlan plan) {
        plan.setCreateTime(LocalDateTime.now());
        if (plan.getStatus() == null) plan.setStatus(1);
        boolean saved = patrolPlanService.save(plan);
        return saved ? R.ok() : R.error("新增计划失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody PatrolPlan plan) {
        plan.setUpdateTime(LocalDateTime.now());
        boolean updated = patrolPlanService.updateById(plan);
        return updated ? R.ok() : R.error("修改计划失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = patrolPlanService.removeById(id);
        return removed ? R.ok() : R.error("删除计划失败");
    }
}
