package com.tuccicode.boot.sys.domain.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.log.LogLogin;
import com.tuccicode.boot.sys.domain.entity.log.LogLoginQuery;

/**
 * @author tucci.lee
 */
public interface LogLoginService {

    void add(LogLogin log);

    PageResponse<LogLogin> list(LogLoginQuery query);
}
