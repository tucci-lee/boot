package com.tuccicode.boot.sys.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.log.LogOperate;
import com.tuccicode.boot.sys.domain.entity.log.LogOperateQuery;
import com.tuccicode.boot.sys.domain.service.LogOperateService;
import com.tuccicode.boot.sys.infrastructure.convertor.LogOperateConvertor;
import com.tuccicode.boot.sys.infrastructure.dataobject.LogOperateDO;
import com.tuccicode.boot.sys.infrastructure.mapper.LogOperateMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class LogOperateServiceImpl implements LogOperateService {
    
    private final LogOperateMapper logOperateMapper;

    public LogOperateServiceImpl(LogOperateMapper logOperateMapper) {
        this.logOperateMapper = logOperateMapper;
    }

    @Override
    public void add(LogOperate log) {
        LogOperateDO logOperateDO = LogOperateConvertor.toAddDO(log);
        logOperateMapper.insert(logOperateDO);
    }

    @Override
    public PageResponse<LogOperate> list(LogOperateQuery query) {
        Page<LogOperateDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        logOperateMapper.selectList(page, query);
        List<LogOperate> logOperateList = page.getRecords()
                .stream()
                .map(LogOperateConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(logOperateList, (int) page.getTotal());
    }
}
