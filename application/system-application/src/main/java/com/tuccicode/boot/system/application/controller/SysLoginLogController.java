package com.tuccicode.boot.system.application.controller;

import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.system.application.service.SysLoginLogApplicationService;
import com.tuccicode.boot.system.domain.entity.log.SysLoginLogQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("log/login")
public class SysLoginLogController {

    private final SysLoginLogApplicationService sysLoginLogApplicationService;

    public SysLoginLogController(SysLoginLogApplicationService sysLoginLogApplicationService) {
        this.sysLoginLogApplicationService = sysLoginLogApplicationService;
    }

    @RequiresPermissions(value = {"log:login:list"})
    @GetMapping
    public Response list(SysLoginLogQuery query){
        return sysLoginLogApplicationService.list(query);
    }
}
