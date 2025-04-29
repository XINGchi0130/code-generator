package com.xc.common.core.generator;

import com.xc.common.core.annotation.FreeMaker;
import com.xc.common.core.annotation.FreeMakerExecutor;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author: XINGchi0130
 * @create: 2025-04-26 17:16
 **/
public abstract class AbstractFreeMakerExecutor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private void execute() {
        Map<String, Object> resources = ThreadLocalManager.getResources().get();
        String parentPath = (String) resources.get("parentPath");
        String executorType = this.getClass().getAnnotation(FreeMakerExecutor.class).executorType();
        Class<? extends AbstractFreeMaker>[] freeMakers = this.getClass().getAnnotation(FreeMakerExecutor.class).freeMakers();
        if(!StringUtils.hasText(parentPath) && !StringUtils.hasText(executorType)){
            List<CompletableFuture<Void>> completableFutureList = new ArrayList<>();
            for(Class<? extends AbstractFreeMaker> freeMaker : freeMakers){
                CompletableFuture<Void> task = CompletableFuture.runAsync(()->{
                    if(freeMaker.isAnnotationPresent(FreeMaker.class)){
                        String outFileRelativePath =  parentPath.concat(freeMaker.getAnnotation(FreeMaker.class).outFileRelativePath());
                        String templateFilePath = freeMaker.getAnnotation(FreeMaker.class).templateFilePath();
                        String templateFileName = freeMaker.getAnnotation(FreeMaker.class).templateFileName();
                        Method executeMethod = null;
                        try {
                            executeMethod = freeMaker.getDeclaredMethod("execute", String.class, String.class, String.class);
                            executeMethod.setAccessible(true);
                            executeMethod.invoke(freeMaker, outFileRelativePath, templateFilePath, templateFileName);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            logger.warn(executorType.concat(":").concat(e.toString()));
                        }
                    }else{
                        logger.warn(executorType.concat(":There is no FreeMaker annotation."));
                    }
                }, threadPoolTaskExecutor);
                completableFutureList.add(task);
            }
            CompletableFuture<Void> completableFuture = CompletableFuture.allOf((CompletableFuture<?>) completableFutureList);
            completableFuture.join();
        }
    }
}
