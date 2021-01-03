package com.lxh.jpa.utils.nrptredis;

import com.lxh.jpa.controller.norepeat.ApiResult;
import com.lxh.jpa.utils.norepeatsubmit.annotation.NoRepeatSubmit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class RptSubmitAspect {

    @Autowired
    private RedisLock redisLock;

    @Pointcut("@annotation(noRptSubmit)")
    public void pointCut(NoRptSubmit noRptSubmit) {
    }

    /**
     * @title: RepeatSubmitAspect
     * @Description:在业务方法执行前，获取当前用户的
     * token（或者JSessionId）+ 当前请求地址，作为一个唯一 KEY，
     * 去获取 Redis 分布式锁（如果此时并发获取，只有一个线程会成功获取锁。）
     * @Author: ZouTao
     * @Date: 2020/4/14
     */

    @Around("pointCut(noRptSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRptSubmit noRptSubmit) throws Throwable {
        int lockSeconds = noRptSubmit.lockTime();

        HttpServletRequest request = RequestUtils.getRequest();
        Assert.notNull(request, "request can not null");

        // 此处可以用token或者JSessionId
        String token = request.getHeader("Authorization");
        String path = request.getServletPath();
        String key = getKey(token, path);
        String clientId = getClientId();

        boolean isSuccess = redisLock.tryLock(key, clientId, lockSeconds);
        log.info("tryLock key = [{}], clientId = [{}]", key, clientId);
        // 主要逻辑
        if (isSuccess) {
            log.info("tryLock success, key = [{}], clientId = [{}]", key, clientId);
            // 获取锁成功
            Object result;
            try {
                // 执行进程
                result = pjp.proceed();
            } finally {
                // 解锁
                redisLock.releaseLock(key, clientId);
                log.info("releaseLock success, key = [{}], clientId = [{}]", key, clientId);
            }
            return result;
        } else {
            // 获取锁失败，认为是重复提交的请求。
            log.info("tryLock fail, key = [{}]", key);
            return new ApiResult(200, "重复请求，请稍后再试", null);
        }
    }

    // token（或者JSessionId）+ 当前请求地址，作为一个唯一KEY
    private String getKey(String token, String path) {
        return token + path;
    }

    // 生成uuid
    private String getClientId() {
        return UUID.randomUUID().toString();
    }
}
