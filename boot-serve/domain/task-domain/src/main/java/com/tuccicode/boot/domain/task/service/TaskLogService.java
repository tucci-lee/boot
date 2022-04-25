package com.tuccicode.boot.domain.task.service;

import com.tuccicode.boot.domain.task.entity.TaskLog;
import com.tuccicode.boot.domain.task.entity.TaskLogQuery;
import com.tuccicode.raccoon.dto.PageResponse;

/**
 * @author tucci.lee
 */
public interface TaskLogService {

    PageResponse<TaskLog> list(TaskLogQuery query);

    Long create(TaskLog log);

    void update(TaskLog log);
}
