package com.patrol.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.task.entity.PatrolPlan;
import com.patrol.task.mapper.PatrolPlanMapper;
import com.patrol.task.service.PatrolPlanService;
import org.springframework.stereotype.Service;

/**
 * 巡检计划 Service 实现
 */
@Service
public class PatrolPlanServiceImpl extends ServiceImpl<PatrolPlanMapper, PatrolPlan> implements PatrolPlanService {
}
