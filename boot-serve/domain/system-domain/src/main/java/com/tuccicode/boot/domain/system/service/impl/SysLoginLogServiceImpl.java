package com.tuccicode.boot.domain.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.system.convertor.LogLoginConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysLoginLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLog;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLogQuery;
import com.tuccicode.boot.domain.system.mapper.SysLoginLogMapper;
import com.tuccicode.boot.domain.system.service.SysLoginLogService;
import com.tuccicode.raccoon.dto.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void add(SysLoginLog log) {
        SysLoginLogDO logLoginDO = LogLoginConvertor.toAddDO(log);
        sysLoginLogMapper.insert(logLoginDO);
    }

    @Override
    public PageResponse<SysLoginLog> list(SysLoginLogQuery query) {
        Page<SysLoginLogDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysLoginLogMapper.selectPage(page, query);
        List<SysLoginLog> logLoginList = page.getRecords()
                .stream()
                .map(LogLoginConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(logLoginList, (int) page.getTotal());
    }
}
