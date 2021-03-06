package com.tuccicode.boot.domain.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.task.dataobject.TaskLogDO;
import com.tuccicode.boot.domain.task.entity.TaskLogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
@Mapper
public interface TaskLogMapper extends BaseMapper<TaskLogDO> {

    Page<TaskLogDO> selectList(Page<TaskLogDO> page, @Param("q") TaskLogQuery query);
}
