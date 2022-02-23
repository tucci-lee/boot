package com.tuccicode.boot.task.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.task.domain.entity.TaskLogListQuery;
import com.tuccicode.boot.task.infrastructure.dataobject.TaskLogDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface TaskLogMapper extends BaseMapper<TaskLogDO> {

    Page<TaskLogDO> selectPage(Page<TaskLogDO> page, @Param("q") TaskLogListQuery query);
}
