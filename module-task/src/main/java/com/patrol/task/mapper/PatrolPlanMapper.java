package com.patrol.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.task.entity.PatrolPlan;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检计划 Mapper
 */
@Mapper
public interface PatrolPlanMapper extends BaseMapper<PatrolPlan> {
}
