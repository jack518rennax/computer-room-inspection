package com.patrol.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.task.entity.PatrolTask;
import com.patrol.task.mapper.PatrolTaskMapper;
import com.patrol.task.service.PatrolTaskService;
import org.springframework.stereotype.Service;

/**
 * 巡检任务 Service 实现
 *
 * @author patrol-team
 */
@Service
public class PatrolTaskServiceImpl extends ServiceImpl<PatrolTaskMapper, PatrolTask> implements PatrolTaskService {
}
