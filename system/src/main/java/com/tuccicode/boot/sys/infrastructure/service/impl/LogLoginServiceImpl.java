package com.tuccicode.boot.sys.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.log.LogLogin;
import com.tuccicode.boot.sys.domain.entity.log.LogLoginQuery;
import com.tuccicode.boot.sys.domain.service.LogLoginService;
import com.tuccicode.boot.sys.infrastructure.convertor.LogLoginConvertor;
import com.tuccicode.boot.sys.infrastructure.dataobject.LogLoginDO;
import com.tuccicode.boot.sys.infrastructure.mapper.LogLoginMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class LogLoginServiceImpl implements LogLoginService {
    private final LogLoginMapper logLoginMapper;

    public LogLoginServiceImpl(LogLoginMapper logLoginMapper) {
        this.logLoginMapper = logLoginMapper;
    }

    @Override
    public void add(LogLogin log) {
        LogLoginDO logLoginDO = LogLoginConvertor.toAddDO(log);
        logLoginMapper.insert(logLoginDO);
    }

    @Override
    public PageResponse<LogLogin> list(LogLoginQuery query) {
        Page<LogLoginDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        logLoginMapper.selectList(page, query);
        List<LogLogin> logLoginList = page.getRecords()
                .stream()
                .map(LogLoginConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(logLoginList, (int) page.getTotal());
    }
}
