package com.tuccicode.boot.task.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.task.domain.entity.TaskLog;
import com.tuccicode.boot.task.domain.entity.TaskLogListQuery;
import com.tuccicode.boot.task.domain.service.TaskLogService;
import com.tuccicode.boot.task.infrastructure.convertor.TaskLogConvertor;
import com.tuccicode.boot.task.infrastructure.dataobject.TaskLogDO;
import com.tuccicode.boot.task.infrastructure.mapper.TaskLogMapper;
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
    public PageResponse<TaskLog> list(TaskLogListQuery query) {
        Page<TaskLogDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        taskLogMapper.selectPage(page, query);
        List<TaskLog> taskLogList = page.getRecords()
                .stream()
                .map(TaskLogConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(taskLogList, (int) page.getTotal());
    }

    @Override
    public Long add(TaskLog log) {
        TaskLogDO taskLogDO = TaskLogConvertor.toAddDO(log);
        taskLogMapper.insert(taskLogDO);
        return taskLogDO.getId();
    }

    @Override
    public void edit(TaskLog log) {
        TaskLogDO taskLogDO = TaskLogConvertor.toEditDO(log);
        taskLogMapper.updateById(taskLogDO);
    }
}
