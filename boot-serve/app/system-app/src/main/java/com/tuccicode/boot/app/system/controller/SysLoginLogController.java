package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.system.service.SysLoginLogAppService;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLogQuery;
import com.tuccicode.raccoon.dto.Response;
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

    private final SysLoginLogAppService sysLoginLogAppService;

    public SysLoginLogController(SysLoginLogAppService sysLoginLogAppService) {
        this.sysLoginLogAppService = sysLoginLogAppService;
    }

    @RequiresPermissions(value = {"log:login:list"})
    @GetMapping
    public Response list(SysLoginLogQuery query){
        return sysLoginLogAppService.list(query);
    }
}
