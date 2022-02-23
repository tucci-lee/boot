package com.tuccicode.boot.task.infrastructure.convertor;

import com.tuccicode.boot.task.domain.entity.Task;
import com.tuccicode.boot.task.infrastructure.dataobject.TaskDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class TaskConvertor {

    public static Task toEntity(TaskDO dataobject){
        if (dataobject == null) {
            return null;
        }
        Task entity = new Task();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static TaskDO toAddDO(Task entity) {
        return new TaskDO()
                .setName(entity.getName())
                .setClassName(entity.getClassName())
                .setCron(entity.getCron())
                .setRemarks(entity.getRemarks());
    }

    public static TaskDO toEditDO(Task entity) {
        return new TaskDO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setClassName(entity.getClassName())
                .setCron(entity.getCron())
                .setRemarks(entity.getRemarks());
    }

    public static TaskDO toEditStatusDO(Task entity) {
        return new TaskDO()
                .setId(entity.getId())
                .setStatus(entity.getStatus());
    }
}
