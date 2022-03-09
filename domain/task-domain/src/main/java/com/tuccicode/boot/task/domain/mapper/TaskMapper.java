package com.tuccicode.boot.task.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.task.domain.dataobject.TaskDO;
import com.tuccicode.boot.task.domain.entity.TaskListQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface TaskMapper extends BaseMapper<TaskDO> {

    Page<TaskDO> selectPage(Page<TaskDO> page, @Param("q") TaskListQuery query);

    List<TaskDO> selectList();

    TaskDO selectByName(String name);

    TaskDO selectById(Long id);
}
