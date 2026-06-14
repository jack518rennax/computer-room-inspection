package com.patrol.rectify.controller;

import com.patrol.common.R;
import com.patrol.rectify.entity.RectifyRecord;
import com.patrol.rectify.service.RectifyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 整改记录 Controller
 *
 * @author patrol-team
 */
@RestController
@RequestMapping("/rectify")
@RequiredArgsConstructor
public class RectifyRecordController {

    private final RectifyRecordService rectifyRecordService;

    /** 列表 */
    @GetMapping("/list")
    public R<List<RectifyRecord>> list() {
        return R.ok(rectifyRecordService.list());
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public R<RectifyRecord> detail(@PathVariable Long id) {
        RectifyRecord record = rectifyRecordService.getById(id);
        return record != null ? R.ok(record) : R.error(404, "整改记录不存在");
    }

    /** 新增 */
    @PostMapping
    public R<Void> create(@RequestBody RectifyRecord record) {
        record.setCreateTime(LocalDateTime.now());
        boolean saved = rectifyRecordService.save(record);
        return saved ? R.ok() : R.error("新增整改记录失败");
    }

    /** 修改 */
    @PutMapping
    public R<Void> update(@RequestBody RectifyRecord record) {
        record.setUpdateTime(LocalDateTime.now());
        boolean updated = rectifyRecordService.updateById(record);
        return updated ? R.ok() : R.error("修改整改记录失败");
    }

    /** 删除（逻辑删除） */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = rectifyRecordService.removeById(id);
        return removed ? R.ok() : R.error("删除整改记录失败");
    }
}
