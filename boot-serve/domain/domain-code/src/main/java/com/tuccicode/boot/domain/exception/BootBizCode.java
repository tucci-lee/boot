package com.tuccicode.boot.domain.exception;

import com.tuccicode.raccoon.exception.BizCode;

/**
 * @author tucci.lee
 */
public enum BootBizCode implements BizCode {

    /**
     * 101xx 参数错误
     */
    PARAMETER_ERROR(10101, "参数错误"),
    PARAMETER_TYPE_ERROR(10102, "参数类型错误"),
    JSON_PARSE_ERROR(10104, "json解析错误"),

    /**
     * 102xx 请求服务错误
     */
    REQUEST_ERROR(10200, "请求服务错误"),
    METHOD_NOT_ALLOWED(10201, "请求方法不支持"),
    UNSUPPORTED_MEDIA_TYPE(10202, "不支持的媒体类型"),
    NOT_FOUND(10203, "未找到"),
    FREQUENT_REQUESTS(10290, "请求频繁"),

    /**
     * 103xx 文件上传错误
     */
    FILE_SIZE_LIMIT(10301, "文件大小超过限制"),
    FILE_TYPE_ERROR(10302, "文件类型错误"),


    /**
     * 111xx 用户认证错误
     */
    UNAUTHENTICATED(11101, "未认证"),
    ACCOUNT_OR_PASSWORD_ERROR(11102, "账号或密码错误"),
    ACCOUNT_LOCKED(11103, "账号锁定"),
    PASSWORD_ERROR(11104, "密码错误"),

    /**
     * 112xx 用户权限错误
     */
    UNAUTHORIZED(10302, "未授权"),

    /**
     * 113xx 验证码错误
     */
    CAPTCHA_TYPE_ERROR(11300, "验证码类型错误"),
    IMAGE_CAPTCHA_ERROR(11301, "验证码错误"),
    SMS_CAPTCHA_ERROR(11302, "验证码错误"),
    EMAIL_CAPTCHA_ERROR(11303, "验证码错误"),

    /**
     * 20000 系统错误
     */
    SERVER_ERROR(20000, "系统错误"),

    /**
     * 5xxxx 业务错误
     */
    PARENT_NOT_EXIST(50001, "父级不存在"),
    LEVEL_ERROR(50002, "层级错误"),
    RES_RELATED(50003, "资源有角色关联，无法操作"),
    RES_NAME_EXIST(50004, "资源名称已经存在"),
    ROLE_NAME_EXIST(50005, "角色名称已经存在"),
    ROLE_RELATED(50006, "角色有用户关联，无法操作"),
    DEPT_NAME_EXIST(50007, "部门名称已经存在"),
    DEPT_RELATED(50008, "部门有用户关联，无法操作"),
    DEPT_HAS_SUB(50009, "部门有下级，无法操作"),
    ACCOUNT_EXIST(50010, "账号已经存在"),
    TASK_NAME_EXIST(51001, "定时任务名称已经存在"),
    TASK_ADD_ERROR(51002, "定时任务添加错误"),
    TASK_DELETE_ERROR(51003, "定时任务删除错误"),
    TASK_RESUME_ERROR(51004, "定时任务恢复错误"),
    TASK_PAUSE_ERROR(51005, "定时任务暂停错误"),
    TASK_START_ERROR(51006, "定时任务执行失败"),;

    BootBizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    final int code;
    final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
