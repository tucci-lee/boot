package com.tuccicode.boot.controller.sys;

import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.sys.application.service.LogOperateApplicationService;
import com.tuccicode.boot.sys.domain.entity.log.LogOperateQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("log/operate")
public class LogOperateController {

    private final LogOperateApplicationService logOperateApplicationService;

    public LogOperateController(LogOperateApplicationService logOperateApplicationService) {
        this.logOperateApplicationService = logOperateApplicationService;
    }

    @RequiresPermissions(value = {"log:operate:list"})
    @GetMapping
    public Response list(LogOperateQuery query){
        return logOperateApplicationService.list(query);
    }
}
