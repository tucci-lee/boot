package com.tuccicode.boot.task.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.task.domain.dataobject.TaskLogDO;
import com.tuccicode.boot.task.domain.entity.TaskLogListQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface TaskLogMapper extends BaseMapper<TaskLogDO> {

    Page<TaskLogDO> selectPage(Page<TaskLogDO> page, @Param("q") TaskLogListQuery query);
}
