package com.tuccicode.boot.system.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.system.infrastructure.dataobject.SysLoginVersionDO;

/**
 * @author tucci.lee
 */
public interface SysLoginVersionMapper extends BaseMapper<SysLoginVersionDO> {

    int updateVersion(Long uid);
}
