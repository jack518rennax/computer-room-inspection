package com.patrol.task.controller;

import com.patrol.common.R;
import com.patrol.task.entity.PatrolTask;
import com.patrol.task.service.PatrolTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 巡检任务 Controller
 *
 * @author patrol-team
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class PatrolTaskController {

    private final PatrolTaskService patrolTaskService;

    @GetMapping("/list")
    public R<List<PatrolTask>> list() {
        return R.ok(patrolTaskService.list());
    }

    @GetMapping("/detail/{id}")
    public R<PatrolTask> detail(@PathVariable Long id) {
        PatrolTask task = patrolTaskService.getById(id);
        return task != null ? R.ok(task) : R.error(404, "任务不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody PatrolTask task) {
        task.setCreateTime(LocalDateTime.now());
        if (task.getStatus() == null) task.setStatus(0);
        boolean saved = patrolTaskService.save(task);
        return saved ? R.ok() : R.error("新增任务失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody PatrolTask task) {
        task.setUpdateTime(LocalDateTime.now());
        boolean updated = patrolTaskService.updateById(task);
        return updated ? R.ok() : R.error("修改任务失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = patrolTaskService.removeById(id);
        return removed ? R.ok() : R.error("删除任务失败");
    }

    /** 接单 */
    @PutMapping("/{id}/accept")
    public R<Void> accept(@PathVariable Long id, @RequestParam Long inspectorId) {
        PatrolTask task = patrolTaskService.getById(id);
        if (task == null) return R.error(404, "任务不存在");
        task.setStatus(1);
        task.setInspectorId(inspectorId);
        task.setUpdateTime(LocalDateTime.now());
        patrolTaskService.updateById(task);
        return R.ok();
    }

    /** 开始巡检 */
    @PutMapping("/{id}/start")
    public R<Void> start(@PathVariable Long id) {
        PatrolTask task = patrolTaskService.getById(id);
        if (task == null) return R.error(404, "任务不存在");
        task.setStatus(2);
        task.setActualStartTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        patrolTaskService.updateById(task);
        return R.ok();
    }

    /** 完成巡检 */
    @PutMapping("/{id}/complete")
    public R<Void> complete(@PathVariable Long id) {
        PatrolTask task = patrolTaskService.getById(id);
        if (task == null) return R.error(404, "任务不存在");
        task.setStatus(3);
        task.setActualEndTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        patrolTaskService.updateById(task);
        return R.ok();
    }
}
