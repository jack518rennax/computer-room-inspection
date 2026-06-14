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

    /** 列表 */
    @GetMapping("/list")
    public R<List<PatrolTask>> list() {
        return R.ok(patrolTaskService.list());
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public R<PatrolTask> detail(@PathVariable Long id) {
        PatrolTask task = patrolTaskService.getById(id);
        return task != null ? R.ok(task) : R.error(404, "任务不存在");
    }

    /** 新增 */
    @PostMapping
    public R<Void> create(@RequestBody PatrolTask task) {
        task.setCreateTime(LocalDateTime.now());
        boolean saved = patrolTaskService.save(task);
        return saved ? R.ok() : R.error("新增任务失败");
    }

    /** 修改 */
    @PutMapping
    public R<Void> update(@RequestBody PatrolTask task) {
        task.setUpdateTime(LocalDateTime.now());
        boolean updated = patrolTaskService.updateById(task);
        return updated ? R.ok() : R.error("修改任务失败");
    }

    /** 删除（逻辑删除） */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = patrolTaskService.removeById(id);
        return removed ? R.ok() : R.error("删除任务失败");
    }
}
