package com.tuccicode.boot.domain.task.convertor;

import com.tuccicode.boot.domain.task.dataobject.TaskDO;
import com.tuccicode.boot.domain.task.entity.Task;
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

    public static TaskDO toCreateDO(Task entity) {
        return new TaskDO()
                .setName(entity.getName())
                .setClassName(entity.getClassName())
                .setCron(entity.getCron())
                .setRemarks(entity.getRemarks())
                .setCreateTime(System.currentTimeMillis());
    }

    public static TaskDO toUpdateDO(Task entity) {
        return new TaskDO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setClassName(entity.getClassName())
                .setCron(entity.getCron())
                .setRemarks(entity.getRemarks())
                .setUpdatedTime(System.currentTimeMillis());
    }

    public static TaskDO toUpdateStatusDO(Task entity) {
        return new TaskDO()
                .setId(entity.getId())
                .setStatus(entity.getStatus())
                .setUpdatedTime(System.currentTimeMillis());
    }
}
