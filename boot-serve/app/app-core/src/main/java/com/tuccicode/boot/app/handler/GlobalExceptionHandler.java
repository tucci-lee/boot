package com.tuccicode.boot.app.handler;

import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.exception.BizException;
import org.apache.shiro.authz.UnauthorizedException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author tucci.lee
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常捕获
     *
     * @param e ServiceException
     * @return Response
     */
    @ExceptionHandler(BizException.class)
    public Response bizExceptionHandler(BizException e) {
        return Response.fail(e.getCode(), e.getMessage());
    }

    /**
     * shiro未授权
     *
     * @param e UnauthorizedException
     * @return Response
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Response unauthorizedExceptionHandler(UnauthorizedException e) {
        return Response.fail(BootBizCode.UNAUTHORIZED);
    }

    /**
     * 请求query参数校验异常
     *
     * @param e ConstraintViolationException
     * @return Response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response constraintViolationException(ConstraintViolationException e) {
        ConstraintViolation<?> error = e.getConstraintViolations().iterator().next();
        PathImpl path = (PathImpl) error.getPropertyPath();
        String field = path.getLeafNode().getName();
        String errMsg = error.getMessage();
        String msg = field + " " + errMsg;
        return Response.fail(BootBizCode.PARAMETER_ERROR.getCode(), msg);
    }

    /**
     * POST请求参数校验异常
     *
     * @param e MethodArgumentNotValidException
     * @return Response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        String errMsg = fieldError.getDefaultMessage();
        String msg = field + " " + errMsg;
        return Response.fail(BootBizCode.PARAMETER_ERROR.getCode(), msg);
    }

    /**
     * GET请求封装参数校验异常，参数封装错误也会返回这个异常
     *
     * @param e BindException
     * @return Response
     */
    @ExceptionHandler(BindException.class)
    public Response bindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        String field = fieldError.getField();
        String errMsg = fieldError.getDefaultMessage();
        String msg = field + " " + errMsg;
        return Response.fail(BootBizCode.PARAMETER_ERROR.getCode(), msg);
    }

    /**
     * 全局异常捕获
     *
     * @param e Throwable
     * @return Response
     */
    @ExceptionHandler(Throwable.class)
    public Response throwable(Throwable e) {
        BootBizCode bizCode;
        if (e instanceof HttpRequestMethodNotSupportedException) {
            // 请求方法不支持
            bizCode = BootBizCode.METHOD_NOT_ALLOWED;
        } else if (e instanceof HttpMessageNotReadableException) {
            // json解析错误
            bizCode = BootBizCode.JSON_PARSE_ERROR;
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            // 媒体类型不支持
            bizCode = BootBizCode.UNSUPPORTED_MEDIA_TYPE;
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            // 参数类型转换错误
            bizCode = BootBizCode.PARAMETER_TYPE_ERROR;
        } else if (e instanceof MultipartException) {
            bizCode = BootBizCode.FILE_SIZE_LIMIT;
        } else {
            bizCode = BootBizCode.SERVER_ERROR;
            log.error("未知异常", e);
        }
        return Response.fail(bizCode);
    }
}
