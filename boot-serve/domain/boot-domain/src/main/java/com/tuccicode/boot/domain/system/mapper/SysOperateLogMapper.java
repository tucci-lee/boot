package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.system.dataobject.SysOperateLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
@Mapper
public interface SysOperateLogMapper extends BaseMapper<SysOperateLogDO> {

    Page<SysOperateLogDO> selectList(Page<SysOperateLogDO> page, @Param("q") SysOperateLogQuery query);
}
