package com.tuccicode.boot.system.infrastructure.service.impl;

import com.tuccicode.boot.system.domain.service.SysLoginVersionService;
import com.tuccicode.boot.system.infrastructure.constant.CacheConst;
import com.tuccicode.boot.system.infrastructure.dataobject.SysLoginVersionDO;
import com.tuccicode.boot.system.infrastructure.mapper.SysLoginVersionMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class SysLoginVersionServiceImpl implements SysLoginVersionService {

    private final SysLoginVersionMapper sysLoginVersionMapper;

    public SysLoginVersionServiceImpl(SysLoginVersionMapper sysLoginVersionMapper) {
        this.sysLoginVersionMapper = sysLoginVersionMapper;
    }

    @CacheEvict(value = CacheConst.LOGIN_VERSION, key = "#p0")
    @Override
    public void save(Long uid) {
        SysLoginVersionDO sysLoginVersionDO = sysLoginVersionMapper.selectById(uid);
        if (sysLoginVersionDO == null) {
            sysLoginVersionDO = new SysLoginVersionDO()
                    .setUid(uid);
            sysLoginVersionMapper.insert(sysLoginVersionDO);
        } else {
            synchronized (this) {
                sysLoginVersionMapper.updateVersion(uid);
            }
        }
    }

    @Cacheable(value = CacheConst.LOGIN_VERSION, key = "#p0")
    @Override
    public int getVersionByUid(Long uid) {
        SysLoginVersionDO sysLoginVersionDO = sysLoginVersionMapper.selectById(uid);
        return sysLoginVersionDO.getVersion();
    }
}
