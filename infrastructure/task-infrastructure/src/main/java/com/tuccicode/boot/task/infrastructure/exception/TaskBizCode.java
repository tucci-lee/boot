package com.tuccicode.boot.task.infrastructure.exception;

import com.tuccicode.boot.exception.ErrorMessage;

/**
 * @author tucci.lee
 */
public enum TaskBizCode implements ErrorMessage {
    TASK_NAME_EXIST(12001, "定时任务名称已经存在"),
    TASK_ADD_ERROR(12002, "定时任务添加错误"),
    TASK_DELETE_ERROR(12003, "定时任务删除错误"),
    TASK_RESUME_ERROR(12004, "定时任务恢复错误"),
    TASK_PAUSE_ERROR(12005, "定时任务暂停错误"),
    TASK_START_ERROR(12006, "定时任务执行失败"),
    ;

    int code;
    String message;

    TaskBizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
