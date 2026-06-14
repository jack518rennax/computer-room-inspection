package com.patrol.rectify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.rectify.entity.RectifyOrder;
import com.patrol.rectify.mapper.RectifyOrderMapper;
import com.patrol.rectify.service.RectifyOrderService;
import org.springframework.stereotype.Service;

/**
 * 整改工单 Service 实现
 */
@Service
public class RectifyOrderServiceImpl extends ServiceImpl<RectifyOrderMapper, RectifyOrder> implements RectifyOrderService {
}
