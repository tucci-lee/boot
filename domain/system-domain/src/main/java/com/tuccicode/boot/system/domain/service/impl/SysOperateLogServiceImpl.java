package com.tuccicode.boot.system.domain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.boot.system.domain.convertor.LogOperateConvertor;
import com.tuccicode.boot.system.domain.dataobject.SysOperateLogDO;
import com.tuccicode.boot.system.domain.entity.log.SysOperateLog;
import com.tuccicode.boot.system.domain.entity.log.SysOperateLogQuery;
import com.tuccicode.boot.system.domain.mapper.SysOperateLogMapper;
import com.tuccicode.boot.system.domain.service.SysOperateLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysOperateLogServiceImpl implements SysOperateLogService {
    
    private final SysOperateLogMapper sysOperateLogMapper;

    public SysOperateLogServiceImpl(SysOperateLogMapper sysOperateLogMapper) {
        this.sysOperateLogMapper = sysOperateLogMapper;
    }

    @Override
    public void add(SysOperateLog log) {
        SysOperateLogDO logOperateDO = LogOperateConvertor.toAddDO(log);
        sysOperateLogMapper.insert(logOperateDO);
    }

    @Override
    public PageResponse<SysOperateLog> list(SysOperateLogQuery query) {
        Page<SysOperateLogDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysOperateLogMapper.selectPage(page, query);
        List<SysOperateLog> logOperateList = page.getRecords()
                .stream()
                .map(LogOperateConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(logOperateList, (int) page.getTotal());
    }
}
