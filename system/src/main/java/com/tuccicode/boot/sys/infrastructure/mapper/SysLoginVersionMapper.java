package com.tuccicode.boot.sys.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysLoginVersionDO;

/**
 * @author tucci.lee
 */
public interface SysLoginVersionMapper extends BaseMapper<SysLoginVersionDO> {

    int updateVersion(Long uid);
}
