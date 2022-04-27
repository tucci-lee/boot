package com.tuccicode.boot.domain.system.service;

import com.tuccicode.boot.domain.system.entity.log.SysLoginLog;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLogQuery;
import com.tuccicode.raccoon.dto.PageResponse;

/**
 * @author tucci.lee
 */
public interface SysLoginLogService {

    void create(SysLoginLog log);

}
