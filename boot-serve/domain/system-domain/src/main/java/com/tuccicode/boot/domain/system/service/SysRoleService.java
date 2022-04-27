package com.tuccicode.boot.domain.system.service;

import com.tuccicode.boot.domain.system.entity.role.SysRole;
import com.tuccicode.boot.domain.system.entity.role.SysRoleQuery;
import com.tuccicode.raccoon.dto.PageResponse;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysRoleService {

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     */
    void create(SysRole sysRole);

    /**
     * 修改角色
     *
     * @param sysRole 角色信息
     */
    void update(SysRole sysRole);

    /**
     * 根绝id删除角色
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 修改角色关联的资源
     *
     * @param sysRole 角色信息
     */
    void updateRes(SysRole sysRole);

    /**
     * 根据uid查询关联的角色
     *
     * @param uid uid
     * @return SysRole
     */
    List<SysRole> listByUid(Long uid);
}
