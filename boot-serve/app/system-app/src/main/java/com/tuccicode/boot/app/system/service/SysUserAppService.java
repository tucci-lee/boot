package com.tuccicode.boot.app.system.service;

import com.tuccicode.boot.app.system.assembler.SysUserAssembler;
import com.tuccicode.boot.app.system.dto.body.ChangePasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserCreateBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdateBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdateLockBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdatePasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserRoleUpdateBody;
import com.tuccicode.boot.app.system.dto.vo.SysUserVO;
import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.system.entity.user.SysUser;
import com.tuccicode.boot.domain.system.entity.user.SysUserQuery;
import com.tuccicode.boot.domain.system.service.SysUserService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.exception.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysUserAppService {

    private final SysUserService sysUserService;

    public SysUserAppService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    public Response page(SysUserQuery query) {
        PageResponse<SysUser> page = sysUserService.page(query);
        List<SysUserVO> sysUserVOList = page.getData().stream()
                .map(SysUserAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysUserVOList, page.getTotal());
    }

    public Response create(SysUserCreateBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.create(sysUser);
        return Response.success();
    }

    public Response update(SysUserUpdateBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.update(sysUser);
        return Response.success();
    }

    public Response updatePassword(SysUserUpdatePasswordBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.updatePassword(sysUser);
        return Response.success();
    }

    public Response updateLock(SysUserUpdateLockBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.updateLock(sysUser);
        return Response.success();
    }

    public Response delete(Long uid) {
        sysUserService.delete(uid);
        return Response.success();
    }

    public Response updateRole(SysUserRoleUpdateBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.updateRole(sysUser);
        return Response.success();
    }

    public Response changePassword(ChangePasswordBody body, Long uid) {
        SysUser sysUser = sysUserService.getByUid(uid);
        Assert.isTrue(sysUserService.verifyPassword(body.getOldPassword(), sysUser.getPassword()), BootBizCode.PASSWORD_ERROR);

        SysUser updateUser = new SysUser()
                .setUid(uid)
                .setPassword(body.getPassword());
        sysUserService.updatePassword(updateUser);
        return Response.success();
    }
}
