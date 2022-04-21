package com.tuccicode.boot.domain.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.task.dataobject.TaskLogDO;
import com.tuccicode.boot.domain.task.entity.TaskLogListQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface TaskLogMapper extends BaseMapper<TaskLogDO> {

    Page<TaskLogDO> selectPage(Page<TaskLogDO> page, @Param("q") TaskLogListQuery query);
}
