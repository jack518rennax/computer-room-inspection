package com.patrol.location.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patrol.location.entity.PatrolQrcode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检二维码 Mapper
 */
@Mapper
public interface PatrolQrcodeMapper extends BaseMapper<PatrolQrcode> {
}
