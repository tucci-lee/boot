package com.tuccicode.boot.domain.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.system.convertor.LogOperateConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysOperateLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLog;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLogQuery;
import com.tuccicode.boot.domain.system.mapper.SysOperateLogMapper;
import com.tuccicode.boot.domain.system.service.SysOperateLogService;
import com.tuccicode.raccoon.dto.PageResponse;
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
    public void create(SysOperateLog log) {
        SysOperateLogDO logOperateDO = LogOperateConvertor.toAddDO(log);
        sysOperateLogMapper.insert(logOperateDO);
    }

    @Override
    public PageResponse<SysOperateLog> page(SysOperateLogQuery query) {
        Page<SysOperateLogDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysOperateLogMapper.selectPage(page, query);
        List<SysOperateLog> logOperateList = page.getRecords()
                .stream()
                .map(LogOperateConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(logOperateList, (int) page.getTotal());
    }
}
