package com.patrol.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.ai.entity.AiRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI 记录 Mapper
 *
 * @author patrol-team
 */
@Mapper
public interface AiRecordMapper extends BaseMapper<AiRecord> {
}
