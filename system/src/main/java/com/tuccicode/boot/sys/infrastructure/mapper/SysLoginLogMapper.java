package com.tuccicode.boot.sys.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.sys.domain.entity.log.SysLoginLogQuery;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysLoginLogDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLogDO> {

    Page<SysLoginLogDO> selectList(Page<SysLoginLogDO> page, @Param("q") SysLoginLogQuery query);
}
