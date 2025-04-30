package com.xc.common.core.generator;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ThreadLocalManager {

    private static ThreadLocal<Map<String, Object>> resources = new ThreadLocal<>();

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