package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.aspect.Operate;
import com.tuccicode.boot.app.system.dto.body.SysUserCreateBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdateBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdateLockBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdatePasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserRoleUpdateBody;
import com.tuccicode.boot.app.system.service.SysRoleAppService;
import com.tuccicode.boot.app.system.service.SysUserAppService;
import com.tuccicode.boot.domain.system.entity.user.SysUserQuery;
import com.tuccicode.raccoon.dto.Response;
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
@RequestMapping("sys/user")
public class SysUserController {

    private final SysUserAppService sysUserAppService;
    private final SysRoleAppService sysRoleAppService;

    public SysUserController(SysUserAppService sysUserAppService,
                             SysRoleAppService sysRoleAppService) {
        this.sysUserAppService = sysUserAppService;
        this.sysRoleAppService = sysRoleAppService;
    }

    /**
     * 用户列表查询
     *
     * @param query 查询条件
     * @return SysUserVO
     */
    @RequiresPermissions(value = {"sys:user:list"})
    @GetMapping
    public Response page(SysUserQuery query) {
        return sysUserAppService.page(query);
    }

    /**
     * 添加用户
     *
     * @param body 用户信息
     * @return Response
     */
    @Operate("添加用户")
    @RequiresPermissions(value = {"sys:user:create"})
    @PostMapping
    public Response create(@Validated @RequestBody SysUserCreateBody body) {
        return sysUserAppService.create(body);
    }

    /**
     * 修改用户
     *
     * @param body 修改信息
     * @return Response
     */
    @Operate("修改用户")
    @RequiresPermissions(value = {"sys:user:update"})
    @PutMapping
    public Response update(@Validated @RequestBody SysUserUpdateBody body) {
        return sysUserAppService.update(body);
    }

    /**
     * 修改密码
     *
     * @param body 密码信息
     * @return Response
     */
    @Operate("修改用户密码")
    @RequiresPermissions(value = {"sys:user:update:password"})
    @PutMapping("password")
    public Response updatePassword(@Validated @RequestBody SysUserUpdatePasswordBody body) {
        return sysUserAppService.updatePassword(body);
    }

    /**
     * 修改锁定状态
     *
     * @param body 锁定信息
     * @return Response
     */
    @Operate("修改用户锁定状态")
    @RequiresPermissions(value = {"sys:user:update:lock"})
    @PutMapping("lock")
    public Response updateLock(@Validated @RequestBody SysUserUpdateLockBody body) {
        return sysUserAppService.updateLock(body);
    }

    /**
     * 删除用户
     *
     * @param uid 删除的uid
     * @return Response
     */
    @Operate("删除用户")
    @RequiresPermissions(value = {"sys:user:delete"})
    @DeleteMapping("{uid}")
    public Response delete(@PathVariable Long uid) {
        return sysUserAppService.delete(uid);
    }


    /**
     * 查询用户关联的角色id
     *
     * @param uid uid
     * @return 角色id
     */
    @RequiresPermissions(value = {"sys:user:list"})
    @GetMapping("role/{uid}")
    public Response listRole(@PathVariable Long uid) {
        return sysRoleAppService.listIdByUid(uid);
    }

    /**
     * 修改用户关联的角色
     *
     * @return Response
     */
    @Operate("修改用户关联的角色")
    @RequiresPermissions(value = {"sys:user:update"})
    @PutMapping("role")
    public Response updateRole(@Validated @RequestBody SysUserRoleUpdateBody body) {
        return sysUserAppService.updateRole(body);
    }
}
