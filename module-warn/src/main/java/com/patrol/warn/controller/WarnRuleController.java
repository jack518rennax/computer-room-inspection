package com.patrol.warn.controller;

import com.patrol.common.R;
import com.patrol.warn.entity.WarnRule;
import com.patrol.warn.service.WarnRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预警规则 Controller
 */
@RestController
@RequestMapping("/warn/rule")
@RequiredArgsConstructor
public class WarnRuleController {

    private final WarnRuleService warnRuleService;

    @GetMapping("/list")
    public R<List<WarnRule>> list() {
        return R.ok(warnRuleService.list());
    }

    @GetMapping("/detail/{id}")
    public R<WarnRule> detail(@PathVariable Long id) {
        WarnRule rule = warnRuleService.getById(id);
        return rule != null ? R.ok(rule) : R.error(404, "规则不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody WarnRule rule) {
        rule.setCreateTime(LocalDateTime.now());
        if (rule.getIsEnabled() == null) rule.setIsEnabled(1);
        if (rule.getAlarmLevel() == null) rule.setAlarmLevel(1);
        boolean saved = warnRuleService.save(rule);
        return saved ? R.ok() : R.error("新增规则失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody WarnRule rule) {
        rule.setUpdateTime(LocalDateTime.now());
        boolean updated = warnRuleService.updateById(rule);
        return updated ? R.ok() : R.error("修改规则失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = warnRuleService.removeById(id);
        return removed ? R.ok() : R.error("删除规则失败");
    }

    /** 启用/禁用 */
    @PutMapping("/{id}/toggle")
    public R<Void> toggle(@PathVariable Long id) {
        WarnRule rule = warnRuleService.getById(id);
        if (rule == null) return R.error(404, "规则不存在");
        rule.setIsEnabled(rule.getIsEnabled() == 1 ? 0 : 1);
        rule.setUpdateTime(LocalDateTime.now());
        warnRuleService.updateById(rule);
        return R.ok();
    }
}
