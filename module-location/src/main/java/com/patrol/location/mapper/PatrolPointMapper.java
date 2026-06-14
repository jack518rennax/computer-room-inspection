package com.patrol.location.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.location.entity.PatrolPoint;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检点位 Mapper
 */
@Mapper
public interface PatrolPointMapper extends BaseMapper<PatrolPoint> {
}
