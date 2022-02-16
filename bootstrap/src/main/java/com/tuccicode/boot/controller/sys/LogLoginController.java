package com.tuccicode.boot.controller.sys;

import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.sys.application.service.LogLoginApplicationService;
import com.tuccicode.boot.sys.domain.entity.log.LogLoginQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("log/login")
public class LogLoginController {

    private final LogLoginApplicationService logLoginApplicationService;

    public LogLoginController(LogLoginApplicationService logLoginApplicationService) {
        this.logLoginApplicationService = logLoginApplicationService;
    }

    @RequiresPermissions(value = {"log:login:list"})
    @GetMapping
    public Response list(LogLoginQuery query){
        return logLoginApplicationService.list(query);
    }
}
