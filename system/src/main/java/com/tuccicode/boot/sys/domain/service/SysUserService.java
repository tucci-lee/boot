package com.tuccicode.boot.sys.domain.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.user.SysUser;
import com.tuccicode.boot.sys.domain.entity.user.SysUserQuery;

/**
 * @author tucci.lee
 */
public interface SysUserService {

    /**
     * 根据用户名查询用户信息，包含密码等敏感信息。用于登录使用
     *
     * @param username 用户名
     * @return SysUser
     */
    SysUser getByUsername(String username);

    /**
     * 条件查询用户信息
     *
     * @param query 查询条件
     * @return SysUser
     */
    PageResponse<SysUser> list(SysUserQuery query);

    /**
     * 添加用户
     *
     * @param sysUser 用户信息
     */
    void add(SysUser sysUser);

    /**
     * 修改用户
     *
     * @param sysUser 用户信息
     */
    void edit(SysUser sysUser);

    /**
     * 修改密码
     *
     * @param sysUser 密码信息
     */
    void editPassword(SysUser sysUser);

    /**
     * 修改锁定状态
     *
     * @param sysUser 锁定信息
     */
    void editLock(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param uid uid
     */
    void delete(long uid);

    /**
     * 修改用户关联的角色
     *
     * @param sysUser 角色信息
     */
    void editRole(SysUser sysUser);

}
