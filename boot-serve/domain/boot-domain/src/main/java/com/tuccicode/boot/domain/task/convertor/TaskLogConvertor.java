package com.tuccicode.boot.domain.task.convertor;

import com.tuccicode.boot.domain.task.dataobject.TaskLogDO;
import com.tuccicode.boot.domain.task.entity.TaskLog;

/**
 * @author tucci.lee
 */
public class TaskLogConvertor {

    public static TaskLogDO toCreateDO(TaskLog entity) {
        return new TaskLogDO()
                .setTaskId(entity.getTaskId())
                .setStartTime(entity.getStartTime())
                .setCreateTime(System.currentTimeMillis());
    }

    public static TaskLogDO toUpdateDO(TaskLog entity) {
        return new TaskLogDO()
                .setId(entity.getId())
                .setStatus(entity.getStatus())
                .setRunTime(entity.getRunTime())
                .setMessage(entity.getMessage());
    }
}
