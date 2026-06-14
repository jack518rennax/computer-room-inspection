package com.patrol.warn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.warn.entity.WarnRule;
import com.patrol.warn.mapper.WarnRuleMapper;
import com.patrol.warn.service.WarnRuleService;
import org.springframework.stereotype.Service;

/**
 * 预警规则 Service 实现
 */
@Service
public class WarnRuleServiceImpl extends ServiceImpl<WarnRuleMapper, WarnRule> implements WarnRuleService {
}
