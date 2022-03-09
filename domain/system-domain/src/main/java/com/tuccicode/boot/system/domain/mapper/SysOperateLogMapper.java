package com.tuccicode.boot.system.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.system.domain.dataobject.SysOperateLogDO;
import com.tuccicode.boot.system.domain.entity.log.SysOperateLogQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface SysOperateLogMapper extends BaseMapper<SysOperateLogDO> {

    Page<SysOperateLogDO> selectPage(Page<SysOperateLogDO> page, @Param("q") SysOperateLogQuery query);
}