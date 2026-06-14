package com.patrol.rectify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.rectify.entity.DangerRecord;
import com.patrol.rectify.mapper.DangerRecordMapper;
import com.patrol.rectify.service.DangerRecordService;
import org.springframework.stereotype.Service;

/**
 * 隐患记录 Service 实现
 */
@Service
public class DangerRecordServiceImpl extends ServiceImpl<DangerRecordMapper, DangerRecord> implements DangerRecordService {
}
