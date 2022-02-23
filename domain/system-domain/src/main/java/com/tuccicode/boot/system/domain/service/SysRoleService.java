package com.tuccicode.boot.system.domain.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.system.domain.entity.role.SysRole;
import com.tuccicode.boot.system.domain.entity.role.SysRoleQuery;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysRoleService {

    /**
     * 分页查询角色列表
     *
     * @param query 查询条件
     * @return Page
     */
    PageResponse<SysRole> list(SysRoleQuery query);

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     */
    void add(SysRole sysRole);

    /**
     * 修改角色
     *
     * @param sysRole 角色信息
     */
    void edit(SysRole sysRole);

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
    void editRes(SysRole sysRole);

    /**
     * 根据uid查询关联的角色
     *
     * @param uid uid
     * @return SysRole
     */
    List<SysRole> listByUid(Long uid);
}
