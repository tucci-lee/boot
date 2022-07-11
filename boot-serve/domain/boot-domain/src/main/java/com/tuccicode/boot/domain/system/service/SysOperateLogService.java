package com.tuccicode.boot.domain.system.service;

import com.tuccicode.boot.domain.system.entity.log.SysOperateLog;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLogQuery;
import com.tuccicode.raccoon.dto.PageResponse;

/**
 * @author tucci.lee
 */
public interface SysOperateLogService {


    void create(SysOperateLog log);

}
