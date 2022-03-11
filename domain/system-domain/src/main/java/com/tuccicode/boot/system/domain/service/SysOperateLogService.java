package com.tuccicode.boot.system.domain.service;

import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.boot.system.domain.entity.log.SysOperateLog;
import com.tuccicode.boot.system.domain.entity.log.SysOperateLogQuery;

/**
 * @author tucci.lee
 */
public interface SysOperateLogService {


    void add(SysOperateLog log);

    PageResponse<SysOperateLog> list(SysOperateLogQuery query);
}
