package com.tuccicode.boot.system.domain.service;


import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.boot.system.domain.entity.log.SysLoginLog;
import com.tuccicode.boot.system.domain.entity.log.SysLoginLogQuery;

/**
 * @author tucci.lee
 */
public interface SysLoginLogService {

    void add(SysLoginLog log);

    PageResponse<SysLoginLog> list(SysLoginLogQuery query);
}
