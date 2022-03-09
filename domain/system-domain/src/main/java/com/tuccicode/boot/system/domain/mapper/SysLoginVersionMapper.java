package com.tuccicode.boot.system.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.system.domain.dataobject.SysLoginVersionDO;

/**
 * @author tucci.lee
 */
public interface SysLoginVersionMapper extends BaseMapper<SysLoginVersionDO> {

    int updateVersion(Long uid);
}
