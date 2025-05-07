package com.xc.common.utils;


import com.xc.common.constant.FreeMakerConstants;
import com.xc.common.core.generator.ThreadLocalManager;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.Map;

public class FreeMakerUtils {

    public static Configuration crateConfiguration(String templatesUrl) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        configuration.setDirectoryForTemplateLoading(new File(templatesUrl));
        configuration.setDefaultEncoding("utf-8");

        return configuration;
    }

    public static String getCurrentExecutorType(){
        Map<String, Object> parameterMap = ThreadLocalManager.getResources();
        return (String) parameterMap.get(FreeMakerConstants.EXECUTOR_TYPE);
    }

    public static void setRequestParameters(Parameter[] parameters, Object[] args) {
        Map<String, Object> parameterMap = ThreadLocalManager.getResources();
        for (int i = 0; i < parameters.length; i++) {
            parameterMap.put(parameters[i].getName(), args[i]);
        }
        validateParameters(parameterMap);
        ThreadLocalManager.setResources(parameterMap);
    }

    public static Boolean validateParameters(Map<String, Object> parameterMap){
        return parameterMap.containsKey(FreeMakerConstants.JSON_SCHEMA) && parameterMap.containsKey(FreeMakerConstants.EXECUTOR_TYPE) && parameterMap.containsKey(FreeMakerConstants.PARENT_PATH);
    }
}
