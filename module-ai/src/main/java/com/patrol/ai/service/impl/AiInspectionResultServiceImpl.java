package com.patrol.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.ai.entity.AiInspectionResult;
import com.patrol.ai.mapper.AiInspectionResultMapper;
import com.patrol.ai.service.AiInspectionResultService;
import org.springframework.stereotype.Service;

/**
 * AI巡检结果 Service 实现
 */
@Service
public class AiInspectionResultServiceImpl extends ServiceImpl<AiInspectionResultMapper, AiInspectionResult>
        implements AiInspectionResultService {
}
