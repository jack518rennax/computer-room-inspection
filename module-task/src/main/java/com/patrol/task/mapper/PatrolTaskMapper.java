package com.patrol.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.task.entity.PatrolTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检任务 Mapper
 *
 * @author patrol-team
 */
@Mapper
public interface PatrolTaskMapper extends BaseMapper<PatrolTask> {
}
