package com.tuccicode.boot.task.infrastructure.convertor;

import com.tuccicode.boot.task.domain.entity.Task;
import com.tuccicode.boot.task.domain.entity.TaskLog;
import com.tuccicode.boot.task.infrastructure.dataobject.TaskDO;
import com.tuccicode.boot.task.infrastructure.dataobject.TaskLogDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class TaskLogConvertor {

    public static TaskLog toEntity(TaskLogDO dataobject){
        if (dataobject == null) {
            return null;
        }
        TaskLog entity = new TaskLog();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static TaskLogDO toAddDO(TaskLog entity) {
        return new TaskLogDO()
                .setTaskId(entity.getTaskId())
                .setStartTime(entity.getStartTime());
    }

    public static TaskLogDO toEditDO(TaskLog entity) {
        return new TaskLogDO()
                .setId(entity.getId())
                .setStatus(entity.getStatus())
                .setRunTime(entity.getRunTime())
                .setMessage(entity.getMessage());
    }
}
