package com.tuccicode.boot.domain.task.convertor;

import com.tuccicode.boot.domain.task.dataobject.TaskLogDO;
import com.tuccicode.boot.domain.task.entity.TaskLog;
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

    public static TaskLogDO toCreateDO(TaskLog entity) {
        return new TaskLogDO()
                .setTaskId(entity.getTaskId())
                .setStartTime(entity.getStartTime());
    }

    public static TaskLogDO toUpdateDO(TaskLog entity) {
        return new TaskLogDO()
                .setId(entity.getId())
                .setStatus(entity.getStatus())
                .setRunTime(entity.getRunTime())
                .setMessage(entity.getMessage());
    }
}
