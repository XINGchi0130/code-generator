package com.xc.common.core.generator;

import com.xc.common.core.domain.XCParameters;
import com.xc.common.core.domain.model.Table;
import org.springframework.stereotype.Component;

@Component
public class ThreadLocalManager {

    private static InheritableThreadLocal<XCParameters> resources = new InheritableThreadLocal<>();

    static {
        resources.set(new XCParameters());
    }

    public static XCParameters getResources(){
        return resources.get();
    }

    public static String getCurrentJsonSchema(){
        return resources.get().getJsonSchema();
    }

    public static String getCurrentDDL(){
        return resources.get().getDDL();
    }

    public static String getCurrentExecutorType(){
        return resources.get().getExecutorType();
    }

    public static String getCurrentParentPath(){
        return resources.get().getParentPath();
    }

    public static Table getCurrentTable(){
        return resources.get().getTable();
    }

    public static void setResources(XCParameters xcParameters){
        resources.set(xcParameters);
    }

    public static void clear(){
        resources.remove();
    }
}