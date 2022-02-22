package com.tuccicode.boot.system.controller;

import com.tuccicode.boot.common.aspect.Operate;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.system.application.dto.body.SysRoleAddBody;
import com.tuccicode.boot.system.application.dto.body.SysRoleEditBody;
import com.tuccicode.boot.system.application.dto.body.SysRoleResEditBody;
import com.tuccicode.boot.system.application.service.SysResApplicationService;
import com.tuccicode.boot.system.application.service.SysRoleApplicationService;
import com.tuccicode.boot.system.domain.entity.role.SysRoleQuery;
import org.apache.shiro.authz.annotation.Logical;
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
@RequestMapping("sys/role")
public class SysRoleController {

    private final SysRoleApplicationService sysRoleApplicationService;
    private final SysResApplicationService sysResApplicationService;

    public SysRoleController(SysRoleApplicationService sysRoleApplicationService,
                             SysResApplicationService sysResApplicationService) {
        this.sysRoleApplicationService = sysRoleApplicationService;
        this.sysResApplicationService = sysResApplicationService;
    }

    /**
     * 查询角色列表
     *
     * @param query 查询条件
     * @return 角色列表
     */
    @RequiresPermissions(value = {"sys:role:list", "sys:user:add", "sys:user:edit"}, logical = Logical.OR)
    @GetMapping
    public Response list(SysRoleQuery query) {
        return sysRoleApplicationService.list(query);
    }

    /**
     * 添加角色
     *
     * @param body 角色信息
     * @return Response
     */
    @Operate("添加角色")
    @RequiresPermissions(value = {"sys:role:add"})
    @PostMapping
    public Response add(@Validated @RequestBody SysRoleAddBody body) {
        return sysRoleApplicationService.add(body);
    }

    /**
     * 修改角色
     *
     * @param body 角色信息
     * @return Response
     */
    @Operate("修改角色")
    @RequiresPermissions(value = {"sys:role:edit"})
    @PutMapping
    public Response edit(@Validated @RequestBody SysRoleEditBody body) {
        return sysRoleApplicationService.edit(body);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return Response
     */
    @Operate("删除角色")
    @DeleteMapping("{id}")
    public Response delete(@PathVariable Long id) {
        return sysRoleApplicationService.delete(id);
    }

    /**
     * 查询角色关联的资源id
     *
     * @param id 角色id
     * @return 资源id
     */
    @RequiresPermissions(value = {"sys:role:list"}, logical = Logical.OR)
    @GetMapping("res/{id}")
    public Response listRes(@PathVariable Long id) {
        return sysResApplicationService.listIdByRoleId(id);
    }

    /**
     * 修改角色关联的资源
     *
     * @param body 修改的信息
     * @return Response
     */
    @Operate("修改角色关联的资源")
    @RequiresPermissions(value = {"sys:role:edit"})
    @PutMapping("res")
    public Response editRes(@Validated @RequestBody SysRoleResEditBody body) {
        return sysRoleApplicationService.editRes(body);
    }
}
