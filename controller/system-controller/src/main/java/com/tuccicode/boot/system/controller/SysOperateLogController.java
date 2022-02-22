package com.tuccicode.boot.system.controller;

import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.system.application.service.SysOperateLogApplicationService;
import com.tuccicode.boot.system.domain.entity.log.SysOperateLogQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("log/operate")
public class SysOperateLogController {

    private final SysOperateLogApplicationService sysOperateLogApplicationService;

    public SysOperateLogController(SysOperateLogApplicationService sysOperateLogApplicationService) {
        this.sysOperateLogApplicationService = sysOperateLogApplicationService;
    }

    @RequiresPermissions(value = {"log:operate:list"})
    @GetMapping
    public Response list(SysOperateLogQuery query){
        return sysOperateLogApplicationService.list(query);
    }
}
