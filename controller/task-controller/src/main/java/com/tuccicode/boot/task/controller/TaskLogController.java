package com.tuccicode.boot.task.controller;

import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.task.application.service.TaskLogApplicationService;
import com.tuccicode.boot.task.domain.entity.TaskLogListQuery;
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

    private final TaskLogApplicationService taskLogApplicationService;

    public TaskLogController(TaskLogApplicationService taskLogApplicationService) {
        this.taskLogApplicationService = taskLogApplicationService;
    }

    @RequiresPermissions(value = {"task:list"})
    @GetMapping
    public Response list(@Validated TaskLogListQuery query){
        return taskLogApplicationService.list(query);
    }
}
