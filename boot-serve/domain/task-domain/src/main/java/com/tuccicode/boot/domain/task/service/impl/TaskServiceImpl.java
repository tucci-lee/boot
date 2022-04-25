package com.tuccicode.boot.domain.task.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.task.entity.TaskQuery;
import com.tuccicode.boot.domain.task.convertor.TaskConvertor;
import com.tuccicode.boot.domain.task.dataobject.TaskDO;
import com.tuccicode.boot.domain.task.entity.Task;
import com.tuccicode.boot.domain.task.mapper.TaskMapper;
import com.tuccicode.boot.domain.task.service.TaskService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.exception.Assert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public Task getById(Long id){
        TaskDO taskDO = taskMapper.selectById(id);
        return TaskConvertor.toEntity(taskDO);
    }

    @Override
    public PageResponse<Task> list(TaskQuery query) {
        Page<TaskDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        taskMapper.selectPage(page, query);
        List<Task> taskList = page.getRecords()
                .stream()
                .map(TaskConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(taskList, (int) page.getTotal());
    }

    @Override
    public List<Task> list() {
        List<TaskDO> taskDOList = taskMapper.selectList();
        return taskDOList.stream()
                .map(TaskConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long create(Task task) {
        TaskDO createTask = TaskConvertor.toCreateDO(task);
        synchronized (this) {
            Assert.isNull(taskMapper.selectByName(createTask.getName()), BootBizCode.TASK_NAME_EXIST);
            taskMapper.insert(createTask);
        }
        return createTask.getId();
    }

    @Override
    public void update(Task task) {
        TaskDO updateTask = TaskConvertor.toUpdateDO(task);
        synchronized (this) {
            TaskDO queryTask = taskMapper.selectByName(updateTask.getName());
            Assert.isTrue(queryTask == null || queryTask.getId().equals(updateTask.getId()), BootBizCode.TASK_NAME_EXIST);
            taskMapper.updateById(updateTask);
        }
    }

    @Override
    public void delete(Long id) {
        TaskDO updateTask = new TaskDO()
                .setId(id)
                .setIsDeleted(true);
        taskMapper.updateById(updateTask);
    }

    @Override
    public void updateStatus(Task task) {
        TaskDO updateTask = TaskConvertor.toUpdateStatusDO(task);
        taskMapper.updateById(updateTask);
    }
}
