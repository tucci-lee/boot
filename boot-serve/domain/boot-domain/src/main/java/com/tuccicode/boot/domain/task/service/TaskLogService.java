package com.tuccicode.boot.domain.task.service;

import com.tuccicode.boot.domain.task.entity.TaskLog;

/**
 * @author tucci.lee
 */
public interface TaskLogService {

    Long create(TaskLog log);

    void update(TaskLog log);
}
