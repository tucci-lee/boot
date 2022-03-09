package com.tuccicode.boot.system.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.system.domain.dataobject.SysLoginLogDO;
import com.tuccicode.boot.system.domain.entity.log.SysLoginLogQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLogDO> {

    Page<SysLoginLogDO> selectPage(Page<SysLoginLogDO> page, @Param("q") SysLoginLogQuery query);
}
