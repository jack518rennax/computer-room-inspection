package com.patrol.rectify.controller;

import com.patrol.common.R;
import com.patrol.framework.annotation.OperLog;
import com.patrol.rectify.entity.DangerRecord;
import com.patrol.rectify.service.DangerRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 隐患记录 Controller
 */
@RestController
@RequestMapping("/danger")
@RequiredArgsConstructor
public class DangerRecordController {

    private final DangerRecordService dangerRecordService;

    @GetMapping("/list")
    public R<List<DangerRecord>> list() {
        return R.ok(dangerRecordService.list());
    }

    @GetMapping("/detail/{id}")
    public R<DangerRecord> detail(@PathVariable Long id) {
        DangerRecord record = dangerRecordService.getById(id);
        return record != null ? R.ok(record) : R.error(404, "隐患记录不存在");
    }

    @OperLog(value = "新增隐患记录", type = OperLog.OperType.CREATE)
    @PostMapping
    public R<Void> create(@RequestBody DangerRecord record) {
        record.setCreateTime(LocalDateTime.now());
        if (record.getStatus() == null) record.setStatus(0);
        boolean saved = dangerRecordService.save(record);
        return saved ? R.ok() : R.error("新增隐患记录失败");
    }

    @OperLog(value = "修改隐患记录", type = OperLog.OperType.UPDATE)
    @PutMapping
    public R<Void> update(@RequestBody DangerRecord record) {
        record.setUpdateTime(LocalDateTime.now());
        boolean updated = dangerRecordService.updateById(record);
        return updated ? R.ok() : R.error("修改隐患记录失败");
    }

    @OperLog(value = "删除隐患记录", type = OperLog.OperType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = dangerRecordService.removeById(id);
        return removed ? R.ok() : R.error("删除隐患记录失败");
    }

    /** 审核 */
    @OperLog(value = "审核隐患", type = OperLog.OperType.UPDATE)
    @PutMapping("/{id}/audit")
    public R<Void> audit(@PathVariable Long id,
                         @RequestParam Long auditorId,
                         @RequestParam String opinion,
                         @RequestParam(required = false) Long rectifyOrderId) {
        DangerRecord record = dangerRecordService.getById(id);
        if (record == null) return R.error(404, "隐患记录不存在");
        record.setStatus(rectifyOrderId != null ? 2 : 1);
        record.setAuditorId(auditorId);
        record.setAuditOpinion(opinion);
        record.setAuditTime(LocalDateTime.now());
        if (rectifyOrderId != null) record.setRectifyOrderId(rectifyOrderId);
        record.setUpdateTime(LocalDateTime.now());
        dangerRecordService.updateById(record);
        return R.ok();
    }

    /** 关闭 */
    @OperLog(value = "关闭隐患", type = OperLog.OperType.UPDATE)
    @PutMapping("/{id}/close")
    public R<Void> close(@PathVariable Long id) {
        DangerRecord record = dangerRecordService.getById(id);
        if (record == null) return R.error(404, "隐患记录不存在");
        record.setStatus(5);
        record.setUpdateTime(LocalDateTime.now());
        dangerRecordService.updateById(record);
        return R.ok();
    }
}
