package com.patrol.ai.controller;

import com.patrol.common.R;
import com.patrol.ai.entity.AiRecord;
import com.patrol.ai.service.AiRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI 识别记录 Controller
 *
 * @author patrol-team
 */
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiRecordController {

    private final AiRecordService aiRecordService;

    /** 列表 */
    @GetMapping("/list")
    public R<List<AiRecord>> list() {
        return R.ok(aiRecordService.list());
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public R<AiRecord> detail(@PathVariable Long id) {
        AiRecord record = aiRecordService.getById(id);
        return record != null ? R.ok(record) : R.error(404, "AI 记录不存在");
    }

    /** 新增 */
    @PostMapping
    public R<Void> create(@RequestBody AiRecord record) {
        record.setCreateTime(LocalDateTime.now());
        boolean saved = aiRecordService.save(record);
        return saved ? R.ok() : R.error("新增 AI 记录失败");
    }

    /** 修改 */
    @PutMapping
    public R<Void> update(@RequestBody AiRecord record) {
        boolean updated = aiRecordService.updateById(record);
        return updated ? R.ok() : R.error("修改 AI 记录失败");
    }

    /** 删除（逻辑删除） */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = aiRecordService.removeById(id);
        return removed ? R.ok() : R.error("删除 AI 记录失败");
    }
}
