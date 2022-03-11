package com.tuccicode.boot.system.application.service;

import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import com.tuccicode.boot.system.application.assembler.SysRoleAssembler;
import com.tuccicode.boot.system.application.dto.body.SysRoleAddBody;
import com.tuccicode.boot.system.application.dto.body.SysRoleEditBody;
import com.tuccicode.boot.system.application.dto.body.SysRoleResEditBody;
import com.tuccicode.boot.system.application.dto.vo.SysRoleVO;
import com.tuccicode.boot.system.domain.entity.role.SysRole;
import com.tuccicode.boot.system.domain.entity.role.SysRoleQuery;
import com.tuccicode.boot.system.domain.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysRoleApplicationService {

    private final SysRoleService sysRoleService;

    public SysRoleApplicationService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 查询角色列表
     *
     * @param query 查询条件
     * @return 角色列表
     */
    public Response list(SysRoleQuery query) {
        PageResponse<SysRole> page = sysRoleService.list(query);
        List<SysRoleVO> sysRoleVOList = page.getData().stream()
                .map(SysRoleAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysRoleVOList, page.getTotal());
    }

    /**
     * 添加角色
     *
     * @param body 角色信息
     * @return Response
     */
    public Response add(SysRoleAddBody body) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(body, sysRole);
        sysRoleService.add(sysRole);
        return Response.success();
    }

    /**
     * 修改角色
     *
     * @param body 角色信息
     * @return Response
     */
    public Response edit(SysRoleEditBody body) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(body, sysRole);
        sysRoleService.edit(sysRole);
        return Response.success();
    }

    /**
     * 根据角色id删除角色
     *
     * @param id 角色id
     * @return Response
     */
    public Response delete(Long id) {
        sysRoleService.delete(id);
        return Response.success();
    }

    /**
     * 修改角色关联的资源
     *
     * @param body 修改信息
     * @return Response
     */
    public Response editRes(SysRoleResEditBody body) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(body, sysRole);
        sysRoleService.editRes(sysRole);
        return Response.success();
    }

    /**
     * 根据uid查询关联的角色id
     *
     * @param uid uid
     * @return 角色id
     */
    public Response listIdByUid(Long uid) {
        List<Long> roleIds = sysRoleService.listByUid(uid)
                .stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());
        return SingletonResponse.success(roleIds);
    }
}
