package com.patrol.location.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patrol.location.entity.PatrolQrcode;
import com.patrol.location.mapper.PatrolQrcodeMapper;
import com.patrol.location.service.PatrolQrcodeService;
import org.springframework.stereotype.Service;

/**
 * 巡检二维码 Service 实现
 */
@Service
public class PatrolQrcodeServiceImpl extends ServiceImpl<PatrolQrcodeMapper, PatrolQrcode> implements PatrolQrcodeService {
}
