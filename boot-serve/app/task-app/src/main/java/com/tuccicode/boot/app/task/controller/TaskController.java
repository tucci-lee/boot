package com.tuccicode.boot.app.task.controller;

import com.tuccicode.boot.app.aspect.Operate;
import com.tuccicode.boot.app.task.dto.body.TaskAddBody;
import com.tuccicode.boot.app.task.dto.body.TaskEditBody;
import com.tuccicode.boot.app.task.dto.body.TaskEditStatusBody;
import com.tuccicode.boot.app.task.service.TaskAppService;
import com.tuccicode.boot.app.util.DemoUtils;
import com.tuccicode.boot.domain.task.entity.TaskListQuery;
import com.tuccicode.raccoon.dto.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    private final TaskAppService taskAppService;

    public TaskController(TaskAppService taskAppService) {
        this.taskAppService = taskAppService;
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
        return taskAppService.list(query);
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
        DemoUtils.notOperateTask();
        return taskAppService.add(body);
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
        DemoUtils.notOperateTask();
        return taskAppService.edit(body);
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
        DemoUtils.notOperateTask();
        return taskAppService.delete(id);
    }

    @Operate("编辑定时任务状态")
    @RequiresPermissions(value = {"task:editStatus"})
    @PutMapping("status")
    public Response editStatus(@Validated @RequestBody TaskEditStatusBody body) {
        DemoUtils.notOperateTask();
        return taskAppService.editStatus(body);
    }

    @Operate("执行定时任务状态")
    @RequiresPermissions(value = {"task:start"})
    @PostMapping("start/{id}")
    public Response start(@PathVariable Long id){
        DemoUtils.notOperateTask();
        return taskAppService.start(id);
    }
}
