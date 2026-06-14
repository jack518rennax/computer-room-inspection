package com.patrol.rectify.controller;

import com.patrol.common.R;
import com.patrol.rectify.entity.RectifyOrder;
import com.patrol.rectify.service.RectifyOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 整改工单 Controller
 */
@RestController
@RequestMapping("/rectify/order")
@RequiredArgsConstructor
public class RectifyOrderController {

    private final RectifyOrderService rectifyOrderService;

    @GetMapping("/list")
    public R<List<RectifyOrder>> list() {
        return R.ok(rectifyOrderService.list());
    }

    @GetMapping("/detail/{id}")
    public R<RectifyOrder> detail(@PathVariable Long id) {
        RectifyOrder order = rectifyOrderService.getById(id);
        return order != null ? R.ok(order) : R.error(404, "整改工单不存在");
    }

    @PostMapping
    public R<Void> create(@RequestBody RectifyOrder order) {
        order.setCreateTime(LocalDateTime.now());
        if (order.getStatus() == null) order.setStatus(0);
        boolean saved = rectifyOrderService.save(order);
        return saved ? R.ok() : R.error("新增工单失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody RectifyOrder order) {
        order.setUpdateTime(LocalDateTime.now());
        boolean updated = rectifyOrderService.updateById(order);
        return updated ? R.ok() : R.error("修改工单失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = rectifyOrderService.removeById(id);
        return removed ? R.ok() : R.error("删除工单失败");
    }

    /** 开始整改 */
    @PutMapping("/{id}/start")
    public R<Void> start(@PathVariable Long id) {
        RectifyOrder order = rectifyOrderService.getById(id);
        if (order == null) return R.error(404, "工单不存在");
        order.setStatus(1);
        order.setUpdateTime(LocalDateTime.now());
        rectifyOrderService.updateById(order);
        return R.ok();
    }

    /** 提交整改 */
    @PutMapping("/{id}/submit")
    public R<Void> submit(@PathVariable Long id, @RequestBody RectifyOrder body) {
        RectifyOrder order = rectifyOrderService.getById(id);
        if (order == null) return R.error(404, "工单不存在");
        order.setStatus(2);
        order.setActualFinishTime(LocalDateTime.now());
        if (body.getRectifyMeasure() != null) order.setRectifyMeasure(body.getRectifyMeasure());
        if (body.getRectifyPhotos() != null) order.setRectifyPhotos(body.getRectifyPhotos());
        order.setUpdateTime(LocalDateTime.now());
        rectifyOrderService.updateById(order);
        return R.ok();
    }

    /** 验收通过 */
    @PutMapping("/{id}/accept")
    public R<Void> accept(@PathVariable Long id) {
        RectifyOrder order = rectifyOrderService.getById(id);
        if (order == null) return R.error(404, "工单不存在");
        order.setStatus(3);
        order.setUpdateTime(LocalDateTime.now());
        rectifyOrderService.updateById(order);
        return R.ok();
    }

    /** 验收驳回 */
    @PutMapping("/{id}/reject")
    public R<Void> reject(@PathVariable Long id) {
        RectifyOrder order = rectifyOrderService.getById(id);
        if (order == null) return R.error(404, "工单不存在");
        order.setStatus(4);
        order.setUpdateTime(LocalDateTime.now());
        rectifyOrderService.updateById(order);
        return R.ok();
    }
}
