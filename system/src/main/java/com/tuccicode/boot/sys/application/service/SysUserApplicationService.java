package com.tuccicode.boot.sys.application.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.sys.application.assembler.SysUserAssembler;
import com.tuccicode.boot.sys.application.dto.body.ChangePasswordBody;
import com.tuccicode.boot.sys.application.dto.body.SysUserAddBody;
import com.tuccicode.boot.sys.application.dto.body.SysUserEditBody;
import com.tuccicode.boot.sys.application.dto.body.SysUserEditLockBody;
import com.tuccicode.boot.sys.application.dto.body.SysUserEditPasswordBody;
import com.tuccicode.boot.sys.application.dto.body.SysUserRoleEditBody;
import com.tuccicode.boot.sys.application.dto.vo.SysUserVO;
import com.tuccicode.boot.sys.domain.entity.user.SysUser;
import com.tuccicode.boot.sys.domain.entity.user.SysUserQuery;
import com.tuccicode.boot.sys.domain.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysUserApplicationService {

    private final SysUserService sysUserService;

    public SysUserApplicationService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    public Response list(SysUserQuery query) {
        PageResponse<SysUser> page = sysUserService.list(query);
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
