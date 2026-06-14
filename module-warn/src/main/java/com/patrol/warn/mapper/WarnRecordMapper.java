package com.patrol.warn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.warn.entity.WarnRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预警记录 Mapper
 */
@Mapper
public interface WarnRecordMapper extends BaseMapper<WarnRecord> {
}
