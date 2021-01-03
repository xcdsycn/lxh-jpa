package com.lxh.jpa.utils.norepeatsubmit.aop;

import com.google.common.cache.Cache;
import com.lxh.jpa.utils.norepeatsubmit.annotation.NoRepeatSubmit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 防止重复提交，利用注解，加上guava的cache 防止
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAop {

    @Autowired
    private Cache<String, Integer> cache;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {

    }

    @Around("pointCut(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = attributes.getRequest();
            String key = sessionId + "-" + request.getServletPath();
            if(cache.getIfPresent(key) == null) {
                Object o = pjp.proceed();
                cache.put(key,0);
                return o;
            } else{
                log.error("重复请求，请稍候再试");
                return null;
            }
        }catch (Throwable e) {
            e.printStackTrace();
            log.error("验证重复提交出现未知异常！",e);
            return "{\"code\":-889,\"message\":\"验证重复提交时出现未知异常!\"";
        }
    }

}
