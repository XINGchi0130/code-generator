package com.xc.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

    // TODO
    public static Object invokeMethod(Class<?> clazz, String methodName, Class<?>[] parametersType, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = clazz.getDeclaredMethod(methodName);
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(clazz);
        return null;
    }

    public static Object invokeMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(clazz, methodName, null, null);
    }
}
