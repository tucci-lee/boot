package com.tuccicode.boot.system.infrastructure.exception;


import com.tuccicode.boot.exception.ErrorMessage;

/**
 * @author tucci.lee
 */
public enum SysBizCode implements ErrorMessage {
    UNAUTHENTICATED(10101, "未认证"),
    UNAUTHORIZED(10102, "未授权"),

    USERNAME_OR_PASSWORD_ERROR(10301, "用户名或密码错误"),
    ACCOUNT_LOCK(10302, "账号锁定"),
    USER_EXIST(10303, "用户已经存在"),
    PASSWORD_ERROR(10304, "密码错误"),

    CAPTCHA_TYPE_ERROR(10400, "验证码类型错误"),
    IMAGE_CAPTCHA_ERROR(10401, "验证码错误"),

    PARENT_NOT_EXIST(11001, "父级不存在"),
    LEVEL_ERROR(11002, "层级错误"),
    RES_RELATED(11101, "资源有角色关联，无法操作"),
    RES_NAME_EXIST(11102, "资源名称已经存在"),
    ROLE_NAME_EXIST(11201, "角色名称已经存在"),
    ROLE_RELATED(11202, "角色有用户关联，无法操作"),
    DEPT_NAME_EXIST(11301, "部门名称已经存在"),
    DEPT_RELATED(11302, "部门有用户关联，无法操作"),
    DEPT_HAS_SUB(11303, "部门有下级，无法操作"),
    ;

    int code;
    String message;

    SysBizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
