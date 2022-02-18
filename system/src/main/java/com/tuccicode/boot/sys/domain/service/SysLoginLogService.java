package com.tuccicode.boot.sys.domain.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.log.SysLoginLog;
import com.tuccicode.boot.sys.domain.entity.log.SysLoginLogQuery;

/**
 * @author tucci.lee
 */
public interface SysLoginLogService {

    void add(SysLoginLog log);

    PageResponse<SysLoginLog> list(SysLoginLogQuery query);
}
