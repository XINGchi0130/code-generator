package com.xc.common.core.generator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ThreadLocalManager {

    private static InheritableThreadLocal<Map<String, Object>> resources = new InheritableThreadLocal<>();

    public static Map<String, Object> getResources(){
        return resources.get();
    }

    public static void setResources(Map<String, Object> map){
        resources.set(map);
    }

    public static void clear(){
        resources.remove();
    }
}