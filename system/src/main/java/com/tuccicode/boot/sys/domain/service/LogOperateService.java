package com.tuccicode.boot.sys.domain.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.log.LogOperate;
import com.tuccicode.boot.sys.domain.entity.log.LogOperateQuery;

/**
 * @author tucci.lee
 */
public interface LogOperateService {


    void add(LogOperate log);

    PageResponse<LogOperate> list(LogOperateQuery query);
}
