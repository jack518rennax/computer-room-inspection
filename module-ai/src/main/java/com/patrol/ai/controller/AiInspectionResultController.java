package com.patrol.ai.controller;

import com.patrol.common.R;
import com.patrol.ai.entity.AiInspectionResult;
import com.patrol.ai.service.AiInspectionResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI巡检结果 Controller
 */
@RestController
@RequestMapping("/ai/result")
@RequiredArgsConstructor
public class AiInspectionResultController {

    private final AiInspectionResultService aiInspectionResultService;

    @GetMapping("/list")
    public R<List<AiInspectionResult>> list() {
        return R.ok(aiInspectionResultService.list());
    }

    @GetMapping("/detail/{id}")
    public R<AiInspectionResult> detail(@PathVariable Long id) {
        AiInspectionResult result = aiInspectionResultService.getById(id);
        return result != null ? R.ok(result) : R.error(404, "AI结果不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody AiInspectionResult result) {
        result.setCreateTime(LocalDateTime.now());
        if (result.getStatus() == null) result.setStatus(0);
        boolean saved = aiInspectionResultService.save(result);
        return saved ? R.ok() : R.error("新增AI结果失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = aiInspectionResultService.removeById(id);
        return removed ? R.ok() : R.error("删除AI结果失败");
    }
}
