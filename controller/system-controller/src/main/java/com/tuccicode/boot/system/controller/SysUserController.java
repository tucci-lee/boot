package com.tuccicode.boot.system.controller;

import com.tuccicode.boot.common.aspect.Operate;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.system.application.dto.body.SysUserAddBody;
import com.tuccicode.boot.system.application.dto.body.SysUserEditBody;
import com.tuccicode.boot.system.application.dto.body.SysUserEditLockBody;
import com.tuccicode.boot.system.application.dto.body.SysUserEditPasswordBody;
import com.tuccicode.boot.system.application.dto.body.SysUserRoleEditBody;
import com.tuccicode.boot.system.application.service.SysRoleApplicationService;
import com.tuccicode.boot.system.application.service.SysUserApplicationService;
import com.tuccicode.boot.system.domain.entity.user.SysUserQuery;
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

    private final SysUserApplicationService sysUserApplicationService;
    private final SysRoleApplicationService sysRoleApplicationService;

    public SysUserController(SysUserApplicationService sysUserApplicationService,
                             SysRoleApplicationService sysRoleApplicationService) {
        this.sysUserApplicationService = sysUserApplicationService;
        this.sysRoleApplicationService = sysRoleApplicationService;
    }

    /**
     * 用户列表查询
     *
     * @param query 查询条件
     * @return SysUserVO
     */
    @RequiresPermissions(value = {"sys:user:list"})
    @GetMapping
    public Response list(SysUserQuery query) {
        return sysUserApplicationService.list(query);
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
        return sysUserApplicationService.add(body);
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
        return sysUserApplicationService.edit(body);
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
        return sysUserApplicationService.editPassword(body);
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
        return sysUserApplicationService.editLock(body);
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
        return sysUserApplicationService.delete(uid);
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
        return sysRoleApplicationService.listIdByUid(uid);
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
        return sysUserApplicationService.editRole(body);
    }
}
