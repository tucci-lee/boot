package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.domain.system.dataobject.SysLoginVersionDO;

/**
 * @author tucci.lee
 */
public interface SysLoginVersionMapper extends BaseMapper<SysLoginVersionDO> {

    int updateVersion(Long uid);
}
