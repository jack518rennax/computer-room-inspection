package com.patrol.warn.controller;

import com.patrol.common.R;
import com.patrol.warn.entity.WarnRecord;
import com.patrol.warn.service.WarnRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预警记录 Controller
 */
@RestController
@RequestMapping("/warn/record")
@RequiredArgsConstructor
public class WarnRecordController {

    private final WarnRecordService warnRecordService;

    @GetMapping("/list")
    public R<List<WarnRecord>> list() {
        return R.ok(warnRecordService.list());
    }

    @GetMapping("/detail/{id}")
    public R<WarnRecord> detail(@PathVariable Long id) {
        WarnRecord record = warnRecordService.getById(id);
        return record != null ? R.ok(record) : R.error(404, "记录不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody WarnRecord record) {
        record.setCreateTime(LocalDateTime.now());
        if (record.getStatus() == null) record.setStatus(0);
        boolean saved = warnRecordService.save(record);
        return saved ? R.ok() : R.error("新增记录失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = warnRecordService.removeById(id);
        return removed ? R.ok() : R.error("删除记录失败");
    }

    /** 处理（忽略） */
    @PutMapping("/{id}/ignore")
    public R<Void> ignore(@PathVariable Long id) {
        WarnRecord record = warnRecordService.getById(id);
        if (record == null) return R.error(404, "记录不存在");
        record.setStatus(1);
        record.setUpdateTime(LocalDateTime.now());
        warnRecordService.updateById(record);
        return R.ok();
    }

    /** 派发工单 */
    @PutMapping("/{id}/dispatch")
    public R<Void> dispatch(@PathVariable Long id, @RequestParam Long dangerId) {
        WarnRecord record = warnRecordService.getById(id);
        if (record == null) return R.error(404, "记录不存在");
        record.setStatus(2);
        record.setDangerId(dangerId);
        record.setUpdateTime(LocalDateTime.now());
        warnRecordService.updateById(record);
        return R.ok();
    }
}
