package com.patrol.location.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.location.entity.PatrolPoint;
import com.patrol.location.mapper.PatrolPointMapper;
import com.patrol.location.service.PatrolPointService;
import org.springframework.stereotype.Service;

/**
 * 巡检点位 Service 实现
 */
@Service
public class PatrolPointServiceImpl extends ServiceImpl<PatrolPointMapper, PatrolPoint> implements PatrolPointService {
}
