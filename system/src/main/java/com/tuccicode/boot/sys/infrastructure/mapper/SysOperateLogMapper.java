package com.tuccicode.boot.sys.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.sys.domain.entity.log.SysOperateLogQuery;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysOperateLogDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface SysOperateLogMapper extends BaseMapper<SysOperateLogDO> {

    Page<SysOperateLogDO> selectList(Page<SysOperateLogDO> page, @Param("q") SysOperateLogQuery query);
}
