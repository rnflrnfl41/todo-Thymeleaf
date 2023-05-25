package me.potato.finaltodo.utils;

import lombok.extern.slf4j.Slf4j;
import me.potato.finaltodo.service.exceptions.LoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogExecutionTimeAspect {


    @Around("@annotation(me.potato.finaltodo.utils.Tracking)")
    public Object logExecutionByAnnotation(ProceedingJoinPoint pjp) throws Throwable {
        return tracking(pjp);
    }

    @Around("@annotation(me.potato.finaltodo.utils.LoginCheck)")
    public Object loginCheckByAnnotation(ProceedingJoinPoint pjp) throws Throwable {
        return loginCheck(pjp);
    }



    private Object tracking(ProceedingJoinPoint pjp) throws Throwable{
        var start = System.currentTimeMillis();
        Object result = null;
        try {
           result = pjp.proceed();

        }catch (Exception e) {
            log.error("method in exception: {}",e.getMessage());
            throw e;
        }finally {
            var args = pjp.getArgs();
            var path = pjp.getSignature();
            var name = pjp.getSignature().getName();
            var elapse = (System.currentTimeMillis()-start)+"ms";
            log.info("path: {}, name: {}, args={}, elapse: {}",path,name,args,elapse);
        }
        return result;
    }

    private Object loginCheck(ProceedingJoinPoint pjp) throws Throwable{
        var start = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed();

        }catch (Exception e) {
            log.error("login exception: {}",e.getMessage());
            throw new LoginException("loginException",e.getMessage());
        }finally {
            var args = pjp.getArgs();
            var path = pjp.getSignature();
            var name = pjp.getSignature().getName();
            var elapse = (System.currentTimeMillis()-start)+"ms";
            log.info("loginCheck path: {}, name: {}, args={}, elapse: {}",path,name,args,elapse);
        }
        return result;
    }

}
