package com.tuccicode.boot.domain.task.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.task.dataobject.TaskLogDO;
import com.tuccicode.boot.domain.task.entity.TaskLog;
import com.tuccicode.boot.domain.task.entity.TaskLogQuery;
import com.tuccicode.boot.domain.task.convertor.TaskLogConvertor;
import com.tuccicode.boot.domain.task.mapper.TaskLogMapper;
import com.tuccicode.boot.domain.task.service.TaskLogService;
import com.tuccicode.raccoon.dto.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class TaskLogServiceImpl implements TaskLogService {

    private final TaskLogMapper taskLogMapper;

    public TaskLogServiceImpl(TaskLogMapper taskLogMapper) {
        this.taskLogMapper = taskLogMapper;
    }

    @Override
    public Long create(TaskLog log) {
        TaskLogDO taskLogDO = TaskLogConvertor.toCreateDO(log);
        taskLogMapper.insert(taskLogDO);
        return taskLogDO.getId();
    }

    @Override
    public void update(TaskLog log) {
        TaskLogDO taskLogDO = TaskLogConvertor.toUpdateDO(log);
        taskLogMapper.updateById(taskLogDO);
    }
}
