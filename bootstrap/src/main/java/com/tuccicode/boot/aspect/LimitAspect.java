package com.tuccicode.boot.aspect;

import com.tuccicode.boot.exception.BizCode;
import com.tuccicode.boot.exception.ServiceException;
import com.tuccicode.boot.util.WebUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 使用redisson的RRateLimiter实现
 *
 * @author tucci.lee
 */
@Aspect
@Component
public class LimitAspect {

    private static final String LIMIT_KEY = "limit:%s:%s";
    private final ExpressionParser parser = new SpelExpressionParser();
    private final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    private final RedissonClient redissonClient;

    public LimitAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Pointcut("@annotation(limit)")
    public void pointcut(Limit limit) {
    }

    @Before(value = "pointcut(limit)", argNames = "pjp,limit")
    public void before(JoinPoint pjp, Limit limit) {
        //获取请求的方法和key
        Method method = getMethod(pjp);
        String methodName = method.getName();

        String key;
        if (limit.key().isEmpty()) {
            key = String.format(LIMIT_KEY, WebUtils.getIp(), methodName);
        } else {
            EvaluationContext context = bindParam(method, pjp.getArgs());
            Expression expression = parser.parseExpression(limit.key());
            key = String.format(LIMIT_KEY, methodName, expression.getValue(context));
        }

        long rate = limit.rate();
        int rateInterval = limit.rateInterval();

        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, rate, rateInterval, RateIntervalUnit.SECONDS);
        boolean isPass = rateLimiter.tryAcquire(1);
        // 设置过期时间，redisson限流缓存是不过期参数。rateLimiter.tryAcquire()执行之后执行才能将所有缓存设置过期时间
        rateLimiter.expire(rateInterval, TimeUnit.SECONDS);
        if (!isPass) { // 禁止访问
            throw new ServiceException(BizCode.FREQUENT_REQUESTS);
        }
    }

    /**
     * 获取当前执行的方法
     *
     * @param pjp
     * @return
     */
    private Method getMethod(JoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getMethod();
    }

    /**
     * 将方法的参数名和参数值绑定
     *
     * @param method 方法，根据方法获取参数名
     * @param args   方法的参数值
     * @return
     */
    private EvaluationContext bindParam(Method method, Object[] args) {
        EvaluationContext context = new StandardEvaluationContext();
        if (args == null || args.length == 0) {
            return context;
        }
        //获取方法的参数名
        String[] params = discoverer.getParameterNames(method);

        //将参数名与参数值对应起来
        for (int len = 0; len < Objects.requireNonNull(params).length; len++) {
            context.setVariable(params[len], args[len]);
        }
        return context;
    }
}
