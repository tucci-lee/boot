package com.tuccicode.boot.domain.task.service;

import com.tuccicode.boot.domain.task.entity.Task;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface TaskService {
    /**
     * 根据id查询定时任务
     *
     * @param id id
     * @return Task
     */
    Task getById(Long id);
    /**
     * 查询所有的定时任务
     * @return Task
     */
    List<Task> list();

    /**
     * 添加定时任务
     *
     * @param task 定时任务
     */
    Long create(Task task);

    /**
     * 修改定时任务信息
     *
     * @param task 定时任务信息
     */
    void update(Task task);

    /**
     * 删除定时任务
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 修改定时任务状态
     *
     * @param task 定时任务
     */
    void updateStatus(Task task);


}
