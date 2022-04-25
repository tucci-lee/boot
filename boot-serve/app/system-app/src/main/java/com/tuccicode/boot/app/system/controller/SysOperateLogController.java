package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.system.service.SysOperateLogAppService;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLogQuery;
import com.tuccicode.raccoon.dto.Response;
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

    private final SysOperateLogAppService sysOperateLogAppService;

    public SysOperateLogController(SysOperateLogAppService sysOperateLogAppService) {
        this.sysOperateLogAppService = sysOperateLogAppService;
    }

    @RequiresPermissions(value = {"log:operate:list"})
    @GetMapping
    public Response page(SysOperateLogQuery query){
        return sysOperateLogAppService.page(query);
    }
}
