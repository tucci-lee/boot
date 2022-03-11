package com.tuccicode.boot.system.application.controller;

import com.tuccicode.boot.common.aspect.Operate;
import com.tuccicode.boot.common.shiro.PrincipalUtils;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.boot.system.application.dto.body.SysResAddBody;
import com.tuccicode.boot.system.application.dto.body.SysResEditBody;
import com.tuccicode.boot.system.application.service.SysResApplicationService;
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

    private final SysResApplicationService sysResApplicationService;

    public SysResController(SysResApplicationService sysResApplicationService) {
        this.sysResApplicationService = sysResApplicationService;
    }

    /**
     * 用户自己拥有的菜单
     *
     * @return List<SysRes>
     */
    @GetMapping("owned_menus")
    public Response ownedMenus() {
        long uid = PrincipalUtils.getUid();
        return sysResApplicationService.listMenuByUid(uid);
    }

    /**
     * 查询所有的资源
     *
     * @return List<SysRes>
     */
    @RequiresPermissions(value = {"sys:res:list", "sys:role:list"}, logical = Logical.OR)
    @GetMapping
    public Response list() {
        return sysResApplicationService.list();
    }

    /**
     * 添加资源
     *
     * @param body 资源信息
     * @return Response
     */
    @Operate("添加资源")
    @RequiresPermissions(value = {"sys:res:add"})
    @PostMapping
    public Response add(@Validated @RequestBody SysResAddBody body) {
        return sysResApplicationService.add(body);
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
        return sysResApplicationService.delete(id);
    }

    /**
     * 修改资源
     *
     * @param body 资源信息
     * @return Response
     */
    @Operate("修改资源")
    @RequiresPermissions(value = {"sys:res:edit"})
    @PutMapping
    public Response edit(@Validated @RequestBody SysResEditBody body) {
        return sysResApplicationService.edit(body);
    }

}
