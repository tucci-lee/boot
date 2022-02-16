package com.tuccicode.boot.sys.infrastructure.service.impl;

import com.tuccicode.boot.sys.domain.service.SysLoginVersionService;
import com.tuccicode.boot.sys.infrastructure.constant.CacheConst;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysLoginVersionDO;
import com.tuccicode.boot.sys.infrastructure.mapper.SysLoginVersionMapper;
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
    public void save(long uid) {
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
    public int getVersionByUid(long uid) {
        SysLoginVersionDO sysLoginVersionDO = sysLoginVersionMapper.selectById(uid);
        return sysLoginVersionDO.getVersion();
    }
}
