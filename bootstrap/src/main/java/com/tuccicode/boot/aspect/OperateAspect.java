package com.tuccicode.boot.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuccicode.boot.shiro.PrincipalUtils;
import com.tuccicode.boot.sys.domain.entity.log.LogOperate;
import com.tuccicode.boot.sys.domain.entity.user.SysUser;
import com.tuccicode.boot.sys.domain.service.LogOperateService;
import com.tuccicode.boot.util.WebUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 操作日志记录
 */
@Aspect
@Component
public class OperateAspect {

    private final LogOperateService logOperateService;

    public OperateAspect(LogOperateService logOperateService) {
        this.logOperateService = logOperateService;
    }

    @Pointcut("@annotation(operate)")
    public void pointcut(Operate operate) {
    }

    @Around(value = "pointcut(operate)", argNames = "pjp,operate")
    public Object around(ProceedingJoinPoint pjp, Operate operate) throws Throwable {
        Signature sig = pjp.getSignature();
        String method = pjp.getTarget().getClass().getName() + "." + sig.getName();
        String value = operate.value();
        String url = WebUtils.getRequest().getRequestURL().toString();
        SysUser user = PrincipalUtils.getUser();
        String ip = WebUtils.getIp();

        //获取参数, 只获取第一个参数
        String params = null;
        if(operate.recordParams()) {
            Object[] args = pjp.getArgs();
            params = JSON.toJSONString(args[0]);
        }

        LogOperate operationLog = new LogOperate()
                .setUsername(user.getUsername())
                .setIp(ip)
                .setUrl(url)
                .setMethod(method)
                .setParams(params)
                .setDescription(value);
        try {
            Object result = pjp.proceed();
            add(operationLog, JSONObject.toJSONString(result), null, true);
            return result;
        } catch (Throwable e) {
            add(operationLog, null, e.getMessage(), false);
            throw e;
        }
    }

    /**
     * 添加操作日志
     */
    private void add(LogOperate operationLog, String result, String errMsg, boolean status) {
        operationLog.setResult(result)
                .setStatus(status)
                .setErrorMessage(errMsg);
        logOperateService.add(operationLog);
    }
}
