package com.patrol.task.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.patrol.common.R;
import com.patrol.task.dto.PatrolTaskExcelDTO;
import com.patrol.task.entity.PatrolTask;
import com.patrol.task.service.PatrolTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 巡检任务 Controller
 *
 * @author patrol-team
 */
@Slf4j
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class PatrolTaskController {

    private final PatrolTaskService patrolTaskService;

    // ==================== 基础 CRUD ====================

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

    // ==================== Excel 导出 ====================

    /**
     * 导出巡检任务为 Excel（xlsx）
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<PatrolTask> tasks = patrolTaskService.list();
        List<PatrolTaskExcelDTO> excelData = tasks.stream()
                .map(this::toExcelDTO)
                .collect(Collectors.toList());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("巡检任务.xlsx", "UTF-8").replace("+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        EasyExcel.write(response.getOutputStream(), PatrolTaskExcelDTO.class)
                .sheet("巡检任务")
                .doWrite(excelData);

        log.info("导出巡检任务 {} 条", excelData.size());
    }

    // ==================== Excel 导入 ====================

    /**
     * 从 Excel 文件导入巡检任务
     */
    @PostMapping("/import")
    public R<String> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return R.error(400, "上传文件不能为空");
        }

        List<PatrolTaskExcelDTO> dtoList = new ArrayList<>();

        EasyExcel.read(file.getInputStream(), PatrolTaskExcelDTO.class,
                new ReadListener<PatrolTaskExcelDTO>() {
                    @Override
                    public void invoke(PatrolTaskExcelDTO data, AnalysisContext context) {
                        dtoList.add(data);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        log.info("Excel 解析完成，共 {} 行", context.readRowHolder().getRowIndex());
                    }

                    @Override
                    public void onException(Exception exception, AnalysisContext context) {
                        log.error("Excel 解析异常: 第 {} 行", context.readRowHolder().getRowIndex(), exception);
                        throw new RuntimeException("Excel 解析异常，请检查文件格式", exception);
                    }
                }).sheet().doRead();

        // 基础校验 + 转换 + 批量保存
        int successCount = 0;
        int skipCount = 0;
        List<PatrolTask> toSave = new ArrayList<>();

        for (PatrolTaskExcelDTO dto : dtoList) {
            if (!StringUtils.hasText(dto.getName())) {
                skipCount++;
                log.warn("跳过空名称行");
                continue;
            }
            toSave.add(toPatrolTask(dto));
            successCount++;
        }

        if (!toSave.isEmpty()) {
            patrolTaskService.saveBatch(toSave);
        }

        String msg = String.format("导入完成：成功 %d 条，跳过 %d 条", successCount, skipCount);
        log.info(msg);
        return R.ok(msg);
    }

    // ==================== DTO ↔ Entity 转换 ====================

    private PatrolTaskExcelDTO toExcelDTO(PatrolTask task) {
        PatrolTaskExcelDTO dto = new PatrolTaskExcelDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setLocationId(task.getLocationId());
        dto.setCronExpression(task.getCronExpression());
        dto.setStatus(task.getStatus());
        dto.setStatusName(task.getStatus() != null && task.getStatus() == 1 ? "运行中" : "已停止");
        return dto;
    }

    private PatrolTask toPatrolTask(PatrolTaskExcelDTO dto) {
        PatrolTask task = new PatrolTask();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setLocationId(dto.getLocationId());
        task.setCronExpression(dto.getCronExpression());
        // 状态转换："运行中" → 1, 其他 → 0
        task.setStatus("运行中".equals(dto.getStatusName()) ? 1 : 0);
        task.setCreateTime(LocalDateTime.now());
        return task;
    }
}
