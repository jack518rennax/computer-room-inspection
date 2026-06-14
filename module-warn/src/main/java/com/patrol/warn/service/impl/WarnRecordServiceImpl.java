package com.patrol.warn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.warn.entity.WarnRecord;
import com.patrol.warn.mapper.WarnRecordMapper;
import com.patrol.warn.service.WarnRecordService;
import org.springframework.stereotype.Service;

/**
 * 预警记录 Service 实现
 */
@Service
public class WarnRecordServiceImpl extends ServiceImpl<WarnRecordMapper, WarnRecord> implements WarnRecordService {
}
