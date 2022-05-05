package com.tuccicode.boot.app.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.app.system.assembler.SysRoleAssembler;
import com.tuccicode.boot.app.system.dto.body.SysRoleCreateBody;
import com.tuccicode.boot.app.system.dto.body.SysRoleUpdateBody;
import com.tuccicode.boot.app.system.dto.body.SysRoleResUpdateBody;
import com.tuccicode.boot.app.system.dto.vo.SysRoleVO;
import com.tuccicode.boot.domain.system.dataobject.SysRoleDO;
import com.tuccicode.boot.domain.system.dataobject.SysUserDO;
import com.tuccicode.boot.domain.system.entity.role.SysRole;
import com.tuccicode.boot.domain.system.entity.role.SysRoleQuery;
import com.tuccicode.boot.domain.system.mapper.SysRoleMapper;
import com.tuccicode.boot.domain.system.service.SysRoleService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysRoleAppService {

    private final SysRoleService sysRoleService;
    private final SysRoleMapper sysRoleMapper;

    public SysRoleAppService(SysRoleService sysRoleService,
                             SysRoleMapper sysRoleMapper) {
        this.sysRoleService = sysRoleService;
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * 查询角色列表
     *
     * @param query 查询条件
     * @return 角色列表
     */
    public Response page(SysRoleQuery query) {
        Page<SysRoleDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysRoleMapper.selectAll(page, query);
        List<SysRoleVO> sysRoleVOList = page.getRecords().stream()
                .map(SysRoleAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysRoleVOList, (int) page.getTotal());
    }

    /**
     * 添加角色
     *
     * @param body 角色信息
     * @return Response
     */
    public Response create(SysRoleCreateBody body) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(body, sysRole);
        sysRoleService.create(sysRole);
        return Response.success();
    }

    /**
     * 修改角色
     *
     * @param body 角色信息
     * @return Response
     */
    public Response update(SysRoleUpdateBody body) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(body, sysRole);
        sysRoleService.update(sysRole);
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
    public Response updateRes(SysRoleResUpdateBody body) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(body, sysRole);
        sysRoleService.updateRes(sysRole);
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
