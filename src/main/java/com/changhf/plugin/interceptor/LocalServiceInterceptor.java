package com.changhf.plugin.interceptor;

import com.alibaba.fastjson.JSON;
import com.changhf.common.exception.DemoBusinessException;
import com.changhf.common.log.ServiceInvokeLog;
import com.changhf.common.log.SystemErrorLog;
import com.changhf.common.session.BaseRequest;
import com.changhf.common.session.BaseResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Aspect
@Lazy(false)
public class LocalServiceInterceptor {

    @Around("execution( public * com.changhf.controller.user.*.*(..))")
    public Object aroundInvoke(final ProceedingJoinPoint pjp) {
        Class<?> targetClass = pjp.getTarget().getClass();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = targetClass.getSimpleName() + "." + signature.getMethod().getName();
        Class returnType = signature.getReturnType();
        Class<?>[] parameterTypes = signature.getParameterTypes();
        String params = JSON.toJSONString(pjp.getArgs());
        Assert.isTrue(BaseResponse.class.isAssignableFrom(returnType),
                "response must be BaseResponse instance, currentResponse:" + returnType);
        long startTime = System.currentTimeMillis();
        Object result = null;
        String msg = null;
        try {
            result = pjp.proceed();
            Assert.notNull(result, "remote result must not be null");
            ServiceInvokeLog.info("invoke [%s] success, params:[%s] ", methodName, params);
        } catch (DemoBusinessException e) {
            msg = e.getMessage();
            ServiceInvokeLog.error("invoke [%s] failed, params:[%s], msg:[%s], exception:", methodName, params,
                    e.getMessage(), e);
        } catch (Throwable t) {
            msg = t.getMessage();
            SystemErrorLog.error("invoke [%s] failed, params:[%s], msg:[%s], exception:", methodName, params,
                    t.getMessage(), t);
        }
        long endTime = System.currentTimeMillis();
        BaseResponse baseResponse = null;
        if (result == null) {
            baseResponse = new BaseResponse(BaseResponse.SYSTEM_ERROR_CODE, msg);
        }
        if (baseResponse == null) {
            baseResponse = (BaseResponse) result;
        }
        baseResponse.setElapseTime(endTime - startTime);
        if (parameterTypes.length == 1 && BaseRequest.class.isAssignableFrom(parameterTypes[0])) {
            BaseRequest request = (BaseRequest) pjp.getArgs()[0];
            baseResponse.setRequestId(request.getRequestId());
        }
        return baseResponse;
    }
}
