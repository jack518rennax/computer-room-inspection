package com.patrol.rectify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.rectify.entity.RectifyRecord;
import com.patrol.rectify.mapper.RectifyRecordMapper;
import com.patrol.rectify.service.RectifyRecordService;
import org.springframework.stereotype.Service;

/**
 * 整改记录 Service 实现
 *
 * @author patrol-team
 */
@Service
public class RectifyRecordServiceImpl extends ServiceImpl<RectifyRecordMapper, RectifyRecord> implements RectifyRecordService {
}
