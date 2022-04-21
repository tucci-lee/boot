package com.tuccicode.boot.app.system.service;

import com.tuccicode.boot.app.system.assembler.SysUserAssembler;
import com.tuccicode.boot.app.system.dto.body.ChangePasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserAddBody;
import com.tuccicode.boot.app.system.dto.body.SysUserEditBody;
import com.tuccicode.boot.app.system.dto.body.SysUserEditLockBody;
import com.tuccicode.boot.app.system.dto.body.SysUserEditPasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserRoleEditBody;
import com.tuccicode.boot.app.system.dto.query.SysUserListQuery;
import com.tuccicode.boot.app.system.dto.vo.SysUserVO;
import com.tuccicode.boot.domain.system.entity.user.SysUser;
import com.tuccicode.boot.domain.system.entity.user.SysUserQuery;
import com.tuccicode.boot.domain.system.service.SysUserService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.beans.BeanUtils;
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

    public Response list(SysUserListQuery query) {
        SysUserQuery userQuery = new SysUserQuery();
        BeanUtils.copyProperties(query, userQuery);
        PageResponse<SysUser> page = sysUserService.list(userQuery);
        List<SysUserVO> sysUserVOList = page.getData().stream()
                .map(SysUserAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysUserVOList, page.getTotal());
    }

    public Response add(SysUserAddBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.add(sysUser);
        return Response.success();
    }

    public Response edit(SysUserEditBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.edit(sysUser);
        return Response.success();
    }

    public Response editPassword(SysUserEditPasswordBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.editPassword(sysUser);
        return Response.success();
    }

    public Response editLock(SysUserEditLockBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.editLock(sysUser);
        return Response.success();
    }

    public Response delete(Long uid) {
        sysUserService.delete(uid);
        return Response.success();
    }

    public Response editRole(SysUserRoleEditBody body) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(body, sysUser);
        sysUserService.editRole(sysUser);
        return Response.success();
    }

    public Response changePassword(ChangePasswordBody body, Long uid) {
        SysUser editUser = new SysUser()
                .setUid(uid)
                .setPassword(body.getPassword())
                .setOldPassword(body.getOldPassword());
        sysUserService.editPassword(editUser);
        return Response.success();
    }
}
