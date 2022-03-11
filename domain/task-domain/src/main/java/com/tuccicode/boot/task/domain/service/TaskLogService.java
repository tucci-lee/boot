package com.tuccicode.boot.task.domain.service;

import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.boot.task.domain.entity.TaskLog;
import com.tuccicode.boot.task.domain.entity.TaskLogListQuery;

/**
 * @author tucci.lee
 */
public interface TaskLogService {

    PageResponse<TaskLog> list(TaskLogListQuery query);

    Long add(TaskLog log);

    void edit(TaskLog log);
}
