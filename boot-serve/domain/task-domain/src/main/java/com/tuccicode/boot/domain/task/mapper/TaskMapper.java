package com.tuccicode.boot.domain.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.task.entity.TaskQuery;
import com.tuccicode.boot.domain.task.dataobject.TaskDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface TaskMapper extends BaseMapper<TaskDO> {

    Page<TaskDO> selectList(Page<TaskDO> page, @Param("q") TaskQuery query);

    List<TaskDO> selectAll();

    TaskDO selectByName(String name);

    TaskDO selectById(Long id);
}
