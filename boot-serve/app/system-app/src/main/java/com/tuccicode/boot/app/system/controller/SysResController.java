package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.aspect.Operate;
import com.tuccicode.boot.app.shiro.PrincipalUtils;
import com.tuccicode.boot.app.system.dto.body.SysResCreateBody;
import com.tuccicode.boot.app.system.dto.body.SysResUpdateBody;
import com.tuccicode.boot.app.system.service.SysResAppService;
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
@RequestMapping("sys/res")
public class SysResController {

    private final SysResAppService sysResAppService;

    public SysResController(SysResAppService sysResAppService) {
        this.sysResAppService = sysResAppService;
    }

    /**
     * 用户自己拥有的菜单
     *
     * @return List<SysRes>
     */
    @GetMapping("owned_menus")
    public Response ownedMenus() {
        long uid = PrincipalUtils.getUid();
        return sysResAppService.listMenuByUid(uid);
    }

    /**
     * 查询所有的资源
     *
     * @return List<SysRes>
     */
    @RequiresPermissions(value = {"sys:res:list", "sys:role:list"}, logical = Logical.OR)
    @GetMapping
    public Response list() {
        return sysResAppService.list();
    }

    /**
     * 添加资源
     *
     * @param body 资源信息
     * @return Response
     */
    @Operate("添加资源")
    @RequiresPermissions(value = {"sys:res:create"})
    @PostMapping
    public Response create(@Validated @RequestBody SysResCreateBody body) {
        return sysResAppService.create(body);
    }

    /**
     * 删除资源
     *
     * @return Response
     */
    @Operate("删除资源")
    @RequiresPermissions(value = {"sys:res:delete"})
    @DeleteMapping("{id}")
    public Response delete(@PathVariable Long id) {
        return sysResAppService.delete(id);
    }

    /**
     * 修改资源
     *
     * @param body 资源信息
     * @return Response
     */
    @Operate("修改资源")
    @RequiresPermissions(value = {"sys:res:update"})
    @PutMapping
    public Response update(@Validated @RequestBody SysResUpdateBody body) {
        return sysResAppService.update(body);
    }

}
