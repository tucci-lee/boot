package com.tuccicode.boot.sys.domain.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.log.SysOperateLog;
import com.tuccicode.boot.sys.domain.entity.log.SysOperateLogQuery;

/**
 * @author tucci.lee
 */
public interface SysOperateLogService {


    void add(SysOperateLog log);

    PageResponse<SysOperateLog> list(SysOperateLogQuery query);
}
