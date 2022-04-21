package com.tuccicode.boot.domain.task.service;

import com.tuccicode.boot.domain.task.entity.TaskLog;
import com.tuccicode.boot.domain.task.entity.TaskLogListQuery;
import com.tuccicode.raccoon.dto.PageResponse;

/**
 * @author tucci.lee
 */
public interface TaskLogService {

    PageResponse<TaskLog> list(TaskLogListQuery query);

    Long add(TaskLog log);

    void edit(TaskLog log);
}
