package com.patrol.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.ai.entity.AiRecord;
import com.patrol.ai.mapper.AiRecordMapper;
import com.patrol.ai.service.AiRecordService;
import org.springframework.stereotype.Service;

/**
 * AI 记录 Service 实现
 *
 * @author patrol-team
 */
@Service
public class AiRecordServiceImpl extends ServiceImpl<AiRecordMapper, AiRecord> implements AiRecordService {
}
