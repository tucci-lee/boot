package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.aspect.Operate;
import com.tuccicode.boot.app.system.dto.body.SysRoleAddBody;
import com.tuccicode.boot.app.system.dto.body.SysRoleEditBody;
import com.tuccicode.boot.app.system.dto.body.SysRoleResEditBody;
import com.tuccicode.boot.app.system.dto.query.SysRoleListQuery;
import com.tuccicode.boot.app.system.service.SysResAppService;
import com.tuccicode.boot.app.system.service.SysRoleAppService;
import com.tuccicode.raccoon.dto.Response;
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

    private final SysRoleAppService sysRoleAppService;
    private final SysResAppService sysResAppService;

    public SysRoleController(SysRoleAppService sysRoleAppService,
                             SysResAppService sysResAppService) {
        this.sysRoleAppService = sysRoleAppService;
        this.sysResAppService = sysResAppService;
    }

    /**
     * 查询角色列表
     *
     * @param query 查询条件
     * @return 角色列表
     */
    @RequiresPermissions(value = {"sys:role:list", "sys:user:add", "sys:user:edit"}, logical = Logical.OR)
    @GetMapping
    public Response list(SysRoleListQuery query) {
        return sysRoleAppService.list(query);
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
        return sysRoleAppService.add(body);
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
        return sysRoleAppService.edit(body);
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
        return sysRoleAppService.delete(id);
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
        return sysResAppService.listIdByRoleId(id);
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
        return sysRoleAppService.editRes(body);
    }
}
