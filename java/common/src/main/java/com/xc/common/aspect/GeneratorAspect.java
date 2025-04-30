package com.xc.common.aspect;

import com.xc.common.constant.FreeMakerConstants;
import com.xc.common.core.annotation.FreeMakerExecutor;
import com.xc.common.core.generator.AbstractFreeMakerExecutor;
import com.xc.common.core.generator.FreeMakerExecutorHolder;
import com.xc.common.core.generator.ThreadLocalManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Aspect
@Component
public class GeneratorAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FreeMakerExecutorHolder freeMakerExecutorHolder;

    @Pointcut("@annotation(com.xc.common.core.annotation.Generator)")
    public void generator() {
    }

    @Around("generator()")
    public void generate(ProceedingJoinPoint proceedingJoinPoint){
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        beforeGenerate(method);
        try {
            doGenerate();
            proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void doGenerate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> parameterMap = ThreadLocalManager.getResources();
        AbstractFreeMakerExecutor freeMakerExecutor = freeMakerExecutorHolder.getFreeMakerExecutorHolder().get(parameterMap.get(FreeMakerConstants.EXECUTOR_TYPE));
        if(ObjectUtils.isEmpty(freeMakerExecutor)){
            Method execute = freeMakerExecutor.getClass().getDeclaredMethod("execute");
            execute.setAccessible(true);
            execute.invoke(FreeMakerExecutor.class);
        }else{
            throw new RuntimeException();
        }
    }

    private void beforeGenerate(Method method){
        Parameter[] parameters = method.getParameters();
        Map<String, Object> parameterMap = ThreadLocalManager.getResources();
        for(Parameter parameter : parameters){
            parameterMap.put(parameter.getName(), parameter);
        }
        if(!validateParameters(parameterMap)){
            throw new RuntimeException();
        }
        ThreadLocalManager.setResources(parameterMap);
    }

    private Boolean validateParameters(Map<String, Object> parameterMap){
        return parameterMap.containsKey(FreeMakerConstants.JSON) && parameterMap.containsKey(FreeMakerConstants.EXECUTOR_TYPE);
    }
}
