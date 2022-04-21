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
    public Long add(Task task) {
        TaskDO addTask = TaskConvertor.toAddDO(task);
        synchronized (this) {
            Assert.isNull(taskMapper.selectByName(addTask.getName()), BootBizCode.TASK_NAME_EXIST);
            taskMapper.insert(addTask);
        }
        return addTask.getId();
    }

    @Override
    public void edit(Task task) {
        TaskDO editTask = TaskConvertor.toEditDO(task);
        synchronized (this) {
            TaskDO queryTask = taskMapper.selectByName(editTask.getName());
            Assert.isTrue(queryTask == null || queryTask.getId().equals(editTask.getId()), BootBizCode.TASK_NAME_EXIST);
            taskMapper.updateById(editTask);
        }
    }

    @Override
    public void delete(Long id) {
        TaskDO editTask = new TaskDO()
                .setId(id)
                .setIsDeleted(true);
        taskMapper.updateById(editTask);
    }

    @Override
    public void editStatus(Task task) {
        TaskDO editTask = TaskConvertor.toEditStatusDO(task);
        taskMapper.updateById(editTask);
    }
}
