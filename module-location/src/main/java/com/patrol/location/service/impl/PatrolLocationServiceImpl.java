package com.patrol.location.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.location.entity.PatrolLocation;
import com.patrol.location.mapper.PatrolLocationMapper;
import com.patrol.location.service.PatrolLocationService;
import org.springframework.stereotype.Service;

/**
 * 巡检点位 Service 实现
 *
 * @author patrol-team
 */
@Service
public class PatrolLocationServiceImpl extends ServiceImpl<PatrolLocationMapper, PatrolLocation> implements PatrolLocationService {
}
