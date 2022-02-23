package com.tuccicode.boot.task.controller;

import com.tuccicode.boot.common.aspect.Operate;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.task.application.dto.body.TaskAddBody;
import com.tuccicode.boot.task.application.dto.body.TaskEditBody;
import com.tuccicode.boot.task.application.dto.body.TaskEditStatusBody;
import com.tuccicode.boot.task.application.service.TaskApplicationService;
import com.tuccicode.boot.task.domain.entity.TaskListQuery;
import lombok.extern.java.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("task")
public class TaskController {

    private final TaskApplicationService taskApplicationService;

    public TaskController(TaskApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }

    /**
     * 查询定时任务列表
     *
     * @param query 查询条件
     * @return Response
     */
    @RequiresPermissions(value = {"task:list"})
    @GetMapping
    public Response list(TaskListQuery query) {
        return taskApplicationService.list(query);
    }

    /**
     * 添加定时任务
     *
     * @param body 定时任务信息
     * @return Response
     */
    @Operate("添加定时任务")
    @RequiresPermissions(value = {"task:add"})
    @PostMapping
    public Response add(@Validated @RequestBody TaskAddBody body) {
        return taskApplicationService.add(body);
    }


    /**
     * 修改定时任务
     * @param body 定时任务信息
     * @return Response
     */
    @Operate("修改定时任务")
    @RequiresPermissions(value = {"task:edit"})
    @PutMapping
    public Response edit(@Validated @RequestBody TaskEditBody body){
        return taskApplicationService.edit(body);
    }


    /**
     * 删除定时任务
     * @param id id
     * @return Response
     */
    @Operate("删除定时任务")
    @RequiresPermissions(value = {"task:delete"})
    @DeleteMapping("{id}")
    public Response delete(@PathVariable Long id){
        return taskApplicationService.delete(id);
    }

    @Operate("编辑定时任务状态")
    @RequiresPermissions(value = {"task:editStatus"})
    @PutMapping("status")
    public Response editStatus(@Validated @RequestBody TaskEditStatusBody body) {
        return taskApplicationService.editStatus(body);
    }

    @Operate("执行定时任务状态")
    @RequiresPermissions(value = {"task:start"})
    @PostMapping("start/{id}")
    public Response start(@PathVariable Long id){
        return taskApplicationService.start(id);
    }
}
