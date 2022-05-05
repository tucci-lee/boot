package com.tuccicode.boot.app.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.app.system.assembler.SysUserAssembler;
import com.tuccicode.boot.app.system.dto.body.ChangePasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserCreateBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdateBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdateLockBody;
import com.tuccicode.boot.app.system.dto.body.SysUserUpdatePasswordBody;
import com.tuccicode.boot.app.system.dto.body.SysUserRoleUpdateBody;
import com.tuccicode.boot.app.system.dto.vo.SysUserVO;
import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.system.dataobject.SysUserDO;
import com.tuccicode.boot.domain.system.entity.dept.SysDept;
import com.tuccicode.boot.domain.system.entity.user.SysUser;
import com.tuccicode.boot.domain.system.entity.user.SysUserQuery;
import com.tuccicode.boot.domain.system.mapper.SysUserMapper;
import com.tuccicode.boot.domain.system.service.SysDeptService;
import com.tuccicode.boot.domain.system.service.SysUserService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.exception.Assert;
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
    private final SysUserMapper userMapper;
    private final SysDeptService sysDeptService;

    public SysUserAppService(SysUserService sysUserService,
                             SysUserMapper userMapper,
                             SysDeptService sysDeptService) {
        this.sysUserService = sysUserService;
        this.userMapper = userMapper;
        this.sysDeptService = sysDeptService;
    }

    public Response page(SysUserQuery query) {
        Page<SysUserDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        userMapper.selectList(page, query);

        List<SysUserVO> sysUserVoList = page.getRecords().stream().map(sysUserDO -> {
            String deptName = null;
            if (sysUserDO.getDeptId() != null) {
                SysDept sysDept = sysDeptService.getById(sysUserDO.getDeptId());
                deptName = sysDept == null ? null : sysDept.getName();
            }
            return SysUserAssembler.toVO(sysUserDO, deptName);
        }).collect(Collectors.toList());
        return PageResponse.success(sysUserVoList, (int) page.getTotal());
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
