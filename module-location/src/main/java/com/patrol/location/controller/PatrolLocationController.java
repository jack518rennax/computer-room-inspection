package com.patrol.location.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.patrol.common.R;
import com.patrol.location.dto.PatrolLocationExcelDTO;
import com.patrol.location.entity.PatrolLocation;
import com.patrol.location.service.PatrolLocationService;
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
 * 巡检点位 Controller
 *
 * @author patrol-team
 */
@Slf4j
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class PatrolLocationController {

    private final PatrolLocationService patrolLocationService;

    // ==================== 基础 CRUD ====================

    /** 列表 */
    @GetMapping("/list")
    public R<List<PatrolLocation>> list() {
        return R.ok(patrolLocationService.list());
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public R<PatrolLocation> detail(@PathVariable Long id) {
        PatrolLocation location = patrolLocationService.getById(id);
        return location != null ? R.ok(location) : R.error(404, "点位不存在");
    }

    /** 新增 */
    @PostMapping
    public R<Void> create(@RequestBody PatrolLocation location) {
        location.setCreateTime(LocalDateTime.now());
        boolean saved = patrolLocationService.save(location);
        return saved ? R.ok() : R.error("新增点位失败");
    }

    /** 修改 */
    @PutMapping
    public R<Void> update(@RequestBody PatrolLocation location) {
        location.setUpdateTime(LocalDateTime.now());
        boolean updated = patrolLocationService.updateById(location);
        return updated ? R.ok() : R.error("修改点位失败");
    }

    /** 删除（逻辑删除） */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = patrolLocationService.removeById(id);
        return removed ? R.ok() : R.error("删除点位失败");
    }

    // ==================== Excel 导出 ====================

    /**
     * 导出巡检点位为 Excel（xlsx）
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<PatrolLocation> locations = patrolLocationService.list();
        List<PatrolLocationExcelDTO> excelData = locations.stream()
                .map(this::toExcelDTO)
                .collect(Collectors.toList());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("巡检点位.xlsx", "UTF-8").replace("+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        EasyExcel.write(response.getOutputStream(), PatrolLocationExcelDTO.class)
                .sheet("巡检点位")
                .doWrite(excelData);

        log.info("导出巡检点位 {} 条", excelData.size());
    }

    // ==================== Excel 导入 ====================

    /**
     * 从 Excel 文件导入巡检点位
     */
    @PostMapping("/import")
    public R<String> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return R.error(400, "上传文件不能为空");
        }

        List<PatrolLocationExcelDTO> dtoList = new ArrayList<>();

        EasyExcel.read(file.getInputStream(), PatrolLocationExcelDTO.class,
                new ReadListener<PatrolLocationExcelDTO>() {
                    @Override
                    public void invoke(PatrolLocationExcelDTO data, AnalysisContext context) {
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
        List<PatrolLocation> toSave = new ArrayList<>();

        for (PatrolLocationExcelDTO dto : dtoList) {
            if (!StringUtils.hasText(dto.getName())) {
                skipCount++;
                log.warn("跳过空名称行");
                continue;
            }
            toSave.add(toPatrolLocation(dto));
            successCount++;
        }

        if (!toSave.isEmpty()) {
            patrolLocationService.saveBatch(toSave);
        }

        String msg = String.format("导入完成：成功 %d 条，跳过 %d 条", successCount, skipCount);
        log.info(msg);
        return R.ok(msg);
    }

    // ==================== DTO ↔ Entity 转换 ====================

    private PatrolLocationExcelDTO toExcelDTO(PatrolLocation location) {
        PatrolLocationExcelDTO dto = new PatrolLocationExcelDTO();
        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setCode(location.getCode());
        dto.setAddress(location.getAddress());
        dto.setArea(location.getArea());
        dto.setType(location.getType());
        dto.setDescription(location.getDescription());
        dto.setStatus(location.getStatus());
        dto.setStatusName(location.getStatus() != null && location.getStatus() == 1 ? "启用" : "禁用");
        return dto;
    }

    private PatrolLocation toPatrolLocation(PatrolLocationExcelDTO dto) {
        PatrolLocation location = new PatrolLocation();
        location.setName(dto.getName());
        location.setCode(dto.getCode());
        location.setAddress(dto.getAddress());
        location.setArea(dto.getArea());
        location.setType(dto.getType());
        location.setDescription(dto.getDescription());
        location.setStatus("启用".equals(dto.getStatusName()) ? 1 : 0);
        location.setCreateTime(LocalDateTime.now());
        return location;
    }
}
