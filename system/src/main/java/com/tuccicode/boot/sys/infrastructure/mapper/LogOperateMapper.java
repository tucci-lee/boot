package com.tuccicode.boot.sys.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.sys.domain.entity.log.LogOperateQuery;
import com.tuccicode.boot.sys.infrastructure.dataobject.LogOperateDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface LogOperateMapper extends BaseMapper<LogOperateDO> {

    Page<LogOperateDO> selectList(Page<LogOperateDO> page, @Param("q") LogOperateQuery query);
}
