package com.patrol.warn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.warn.entity.WarnRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预警规则 Mapper
 */
@Mapper
public interface WarnRuleMapper extends BaseMapper<WarnRule> {
}
