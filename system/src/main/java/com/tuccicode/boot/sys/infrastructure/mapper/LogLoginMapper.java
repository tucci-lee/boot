package com.tuccicode.boot.sys.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.sys.domain.entity.log.LogLoginQuery;
import com.tuccicode.boot.sys.infrastructure.dataobject.LogLoginDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface LogLoginMapper extends BaseMapper<LogLoginDO> {

    Page<LogLoginDO> selectList(Page<LogLoginDO> page, @Param("q") LogLoginQuery query);
}
