package com.tuccicode.boot.system.domain.service;

/**
 * @author tucci.lee
 */
public interface SysLoginVersionService {
    /**
     * 保存用户登录的版本号。
     * 如果没有则添加，如果有则更新版本号
     *
     * @param uid uid
     */
    void save(Long uid);

    /**
     * 获取用户登录的版本号
     *
     * @param uid uid
     * @return 版本号
     */
    int getVersionByUid(Long uid);
}
