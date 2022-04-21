package com.tuccicode.boot.app.task.controller;

import com.tuccicode.boot.app.task.service.TaskLogAppService;
import com.tuccicode.boot.domain.task.entity.TaskLogListQuery;
import com.tuccicode.raccoon.dto.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("task/log")
public class TaskLogController {

    private final TaskLogAppService taskLogAppService;

    public TaskLogController(TaskLogAppService taskLogAppService) {
        this.taskLogAppService = taskLogAppService;
    }

    @RequiresPermissions(value = {"task:list"})
    @GetMapping
    public Response list(@Validated TaskLogListQuery query){
        return taskLogAppService.list(query);
    }
}
