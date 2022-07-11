package com.tuccicode.boot.domain.system.service;

import com.tuccicode.boot.domain.system.entity.user.SysUser;

/**
 * @author tucci.lee
 */
public interface SysUserService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return SysUser
     */
    SysUser getByUsername(String username);

    /**
     * 根据uid查询用户信息
     *
     * @param uid uid
     * @return SysUser
     */
    SysUser getByUid(Long uid);

    /**
     * 添加用户
     *
     * @param sysUser 用户信息
     */
    void create(SysUser sysUser);

    /**
     * 修改用户
     *
     * @param sysUser 用户信息
     */
    void update(SysUser sysUser);

    /**
     * 修改密码
     *
     * @param sysUser 密码信息
     */
    void updatePassword(SysUser sysUser);

    /**
     * 校验密码是否正确
     *
     * @param plaintext  明文
     * @param ciphertext 密文
     * @return 是否正确
     */
    boolean verifyPassword(String plaintext, String ciphertext);

    /**
     * 修改锁定状态
     *
     * @param sysUser 锁定信息
     */
    void updateLock(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param uid uid
     */
    void delete(Long uid);

    /**
     * 修改用户关联的角色
     *
     * @param sysUser 角色信息
     */
    void updateRole(SysUser sysUser);

}
