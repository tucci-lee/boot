package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.domain.system.dataobject.SysLoginVersionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tucci.lee
 */
@Mapper
public interface SysLoginVersionMapper extends BaseMapper<SysLoginVersionDO> {

    int updateVersion(Long uid);
}
