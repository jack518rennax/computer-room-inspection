package com.patrol.location.controller;

import com.patrol.common.R;
import com.patrol.location.entity.PatrolQrcode;
import com.patrol.location.service.PatrolQrcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 巡检二维码 Controller
 */
@RestController
@RequestMapping("/qrcode")
@RequiredArgsConstructor
public class PatrolQrcodeController {

    private final PatrolQrcodeService patrolQrcodeService;

    @GetMapping("/list")
    public R<List<PatrolQrcode>> list() {
        return R.ok(patrolQrcodeService.list());
    }

    @GetMapping("/detail/{id}")
    public R<PatrolQrcode> detail(@PathVariable Long id) {
        PatrolQrcode qrcode = patrolQrcodeService.getById(id);
        return qrcode != null ? R.ok(qrcode) : R.error(404, "二维码不存在");
    }

    /** 生成二维码 */
    @PostMapping("/generate")
    public R<PatrolQrcode> generate(@RequestParam Long pointId) {
        PatrolQrcode qrcode = new PatrolQrcode();
        qrcode.setPointId(pointId);
        qrcode.setCodeValue(UUID.randomUUID().toString().replace("-", ""));
        qrcode.setGenTime(LocalDateTime.now());
        qrcode.setCreateTime(LocalDateTime.now());
        boolean saved = patrolQrcodeService.save(qrcode);
        return saved ? R.ok("生成成功", qrcode) : R.error("生成失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = patrolQrcodeService.removeById(id);
        return removed ? R.ok() : R.error("删除失败");
    }
}
