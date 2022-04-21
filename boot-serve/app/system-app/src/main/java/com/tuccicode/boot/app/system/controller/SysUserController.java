package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.aspect.Operate;
import com.tuccicode.boot.app.system.dto.body.SysUserAddBody;
import com.tuccicode.boot.app.system.dto.body.SysUserEditBody;
import com.tuccicode.boot.app.system.dto.body.SysUserEditLockBody;
import com.tuccicode.boot.app.system.dto.body.SysUserEditPasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserRoleEditBody;
import com.tuccicode.boot.app.system.dto.query.SysUserListQuery;
import com.tuccicode.boot.app.system.service.SysRoleAppService;
import com.tuccicode.boot.app.system.service.SysUserAppService;
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
    public Response list(SysUserListQuery query) {
        return sysUserAppService.list(query);
    }

    /**
     * 添加用户
     *
     * @param body 用户信息
     * @return Response
     */
    @Operate("添加用户")
    @RequiresPermissions(value = {"sys:user:add"})
    @PostMapping
    public Response add(@Validated @RequestBody SysUserAddBody body) {
        return sysUserAppService.add(body);
    }

    /**
     * 修改用户
     *
     * @param body 修改信息
     * @return Response
     */
    @Operate("修改用户")
    @RequiresPermissions(value = {"sys:user:edit"})
    @PutMapping
    public Response edit(@Validated @RequestBody SysUserEditBody body) {
        return sysUserAppService.edit(body);
    }

    /**
     * 修改密码
     *
     * @param body 密码信息
     * @return Response
     */
    @Operate("修改用户密码")
    @RequiresPermissions(value = {"sys:user:editPassword"})
    @PutMapping("password")
    public Response editPassword(@Validated @RequestBody SysUserEditPasswordBody body) {
        return sysUserAppService.editPassword(body);
    }

    /**
     * 修改锁定状态
     *
     * @param body 锁定信息
     * @return Response
     */
    @Operate("修改用户锁定状态")
    @RequiresPermissions(value = {"sys:user:editLock"})
    @PutMapping("lock")
    public Response editLock(@Validated @RequestBody SysUserEditLockBody body) {
        return sysUserAppService.editLock(body);
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
    @RequiresPermissions(value = {"sys:user:edit"})
    @PutMapping("role")
    public Response editRole(@Validated @RequestBody SysUserRoleEditBody body) {
        return sysUserAppService.editRole(body);
    }
}
