package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.system.dataobject.SysLoginLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLogDO> {

    Page<SysLoginLogDO> selectList(Page<SysLoginLogDO> page, @Param("q") SysLoginLogQuery query);
}
