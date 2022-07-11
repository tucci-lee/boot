package com.tuccicode.boot.domain.system.service.impl;

import com.tuccicode.boot.domain.system.convertor.LogLoginConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysLoginLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLog;
import com.tuccicode.boot.domain.system.mapper.SysLoginLogMapper;
import com.tuccicode.boot.domain.system.service.SysLoginLogService;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    private final SysLoginLogMapper sysLoginLogMapper;

    public SysLoginLogServiceImpl(SysLoginLogMapper sysLoginLogMapper) {
        this.sysLoginLogMapper = sysLoginLogMapper;
    }

    @Override
    public void create(SysLoginLog log) {
        SysLoginLogDO logLoginDO = LogLoginConvertor.toCreateDO(log);
        sysLoginLogMapper.insert(logLoginDO);
    }

}
