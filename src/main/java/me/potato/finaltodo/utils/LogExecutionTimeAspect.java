package me.potato.finaltodo.utils;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import me.potato.finaltodo.controller.TodoOrderingController;
import me.potato.finaltodo.service.exceptions.NoUserInfoInSessionException;
import me.potato.finaltodo.store.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.service.spi.InjectService;
import org.hibernate.tool.schema.spi.ExceptionHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Slf4j
@Component
public class LogExecutionTimeAspect {


    @Around("@annotation(me.potato.finaltodo.utils.Tracking)")
    public Object logExecutionByAnnotation(ProceedingJoinPoint pjp) throws Throwable {
        return tracking(pjp);
    }

    private Object tracking(ProceedingJoinPoint pjp) throws Throwable{
        var start = System.currentTimeMillis();
        Object result = null;
        try {
           result = pjp.proceed();

        }catch (Exception e) {
            log.error("method in exception: {}",e.getMessage());
            throw new RuntimeException(e);
        }finally {
            var args = pjp.getArgs();
            var path = pjp.getSignature();
            var name = pjp.getSignature().getName();
            var elapse = (System.currentTimeMillis()-start)+"ms";
            log.info("path: {}, name: {}, args={}, elapse: {}",path,name,args,elapse);
        }
        return result;
    }

}
