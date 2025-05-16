package com.xc.common.aspect;

import com.xc.common.constant.RedisConstants;
import com.xc.common.core.domain.XCParameters;
import com.xc.common.core.generator.AbstractFreeMakerExecutor;
import com.xc.common.core.generator.FreeMakerExecutorHolder;
import com.xc.common.core.generator.ThreadLocalManager;
import com.xc.common.redis.RedisService;
import com.xc.common.utils.ReflectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;

@Aspect
@Component
public class GeneratorAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FreeMakerExecutorHolder freeMakerExecutorHolder;

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.xc.common.core.annotation.Generator)")
    public void generator() {
    }

    @Before("generator()")
    public void before(JoinPoint joinPoint){
        XCParameters xcParameters = (XCParameters) joinPoint.getArgs()[0];
        ThreadLocalManager.setResources(xcParameters);
    }

    @Around("generator()")
    public void generate(ProceedingJoinPoint proceedingJoinPoint){
        try {
            beforeGenerate();
            doGenerate();
            afterGenerate();
            proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void doGenerate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AbstractFreeMakerExecutor freeMakerExecutor = freeMakerExecutorHolder.getCurrentFreeMakerExecutor();
        if(ObjectUtils.isEmpty(freeMakerExecutor)){
            ReflectUtils.invokeMethod(freeMakerExecutor.getClass(), "execute");
        }else{
            throw new RuntimeException();
        }
    }

    private void beforeGenerate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AbstractFreeMakerExecutor freeMakerExecutor = freeMakerExecutorHolder.getCurrentFreeMakerExecutor();

        if(ObjectUtils.isEmpty(freeMakerExecutor)){
            throw new RuntimeException("GeneratorAspect.beforeGenerate: freeMakerExecutor is null");
        }

        String redisKey = RedisConstants.FREEMAKER_EXECUTE_KEY.concat(ThreadLocalManager.getCurrentExecutorType());
        if(redisService.exists(redisKey)){
            throw new RuntimeException("freeMakerExecutor is currently executing");
        }
        redisService.set(redisKey, ThreadLocalManager.getCurrentExecutorType(), 5 * 60);

        ReflectUtils.invokeMethod(freeMakerExecutor.getClass(), "beforeGenerate");
    }

    private void afterGenerate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AbstractFreeMakerExecutor freeMakerExecutor = freeMakerExecutorHolder.getCurrentFreeMakerExecutor();

        ReflectUtils.invokeMethod(freeMakerExecutor.getClass(), "afterGenerate");

        String redisKey = RedisConstants.FREEMAKER_EXECUTE_KEY.concat(ThreadLocalManager.getCurrentExecutorType());
        redisService.delete(redisKey);
    }
}
