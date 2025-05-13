package com.xc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    public static Object invokeMethod(Class<?> clazz, String methodName, Class<?>[] parametersType, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(ObjectUtils.isEmpty(clazz) || StringUtils.hasText(methodName)){
            logger.warn("clazz or methodName is empty");
            return null;
        }
        if(parametersType == null && parameters == null){
            logger.warn("parametersType and parameters are null");
            return null;
        }
        Method declaredMethod = clazz.getDeclaredMethod(methodName, parametersType);
        if(ObjectUtils.isEmpty(declaredMethod)){
            logger.warn("There is no method here.");
            return null;
        }
        if(!declaredMethod.isAccessible()){
            declaredMethod.setAccessible(true);
        }
        try{
            Object res = declaredMethod.invoke(clazz, parameters);
            logger.info(clazz.getName().concat(".").concat(methodName).concat("()").concat("was executed successfully."));
            return res;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(clazz, methodName, new Class<?>[0], new Object[0]);
    }
}
