package com.patrol.location.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.location.entity.PatrolLocation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检点位 Mapper
 *
 * @author patrol-team
 */
@Mapper
public interface PatrolLocationMapper extends BaseMapper<PatrolLocation> {
}
