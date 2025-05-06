package com.xc.common.core.generator;

import com.xc.common.constant.FreeMakerConstants;
import com.xc.common.core.annotation.FreeMaker;
import com.xc.common.core.annotation.FreeMakerExecutor;
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
        Map<String, Object> resources = ThreadLocalManager.getResources();
        String parentPath = (String) resources.get(FreeMakerConstants.PARENT_PATH);
        String executorType = this.getClass().getAnnotation(FreeMakerExecutor.class).executorType();
        Class<? extends AbstractFreeMaker>[] freeMakers = this.getClass().getAnnotation(FreeMakerExecutor.class).freeMakers();
        beforeExecute();
        if(!StringUtils.hasText(parentPath) && !StringUtils.hasText(executorType)){
            List<CompletableFuture<Void>> completableFutureList = new ArrayList<>();
            for(Class<? extends AbstractFreeMaker> freeMaker : freeMakers){
                if(freeMaker.isAnnotationPresent(FreeMaker.class)){
                    CompletableFuture<Void> task = CompletableFuture.runAsync(()->{
                        String outFileRelativePath =  parentPath.concat(freeMaker.getAnnotation(FreeMaker.class).outFileRelativePath());
                        String templateFilePath = freeMaker.getAnnotation(FreeMaker.class).templateFilePath();
                        String templateFileName = freeMaker.getAnnotation(FreeMaker.class).templateFileName();
                        Method executeMethod = null;
                        try {
                            executeMethod = freeMaker.getDeclaredMethod("execute", String.class, String.class, String.class);
                            executeMethod.setAccessible(true);
                            executeMethod.invoke(freeMaker, outFileRelativePath, templateFilePath, templateFileName);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            logger.warn(executorType.concat(":").concat(e.toString()));
                        }
                    }, threadPoolTaskExecutor);
                    completableFutureList.add(task);
                }else{
                    logger.warn(executorType.concat(":There is no FreeMaker annotation."));
                }
            }
            CompletableFuture<Void> completableFuture = CompletableFuture.allOf((CompletableFuture<?>) completableFutureList);
            completableFuture.join();
            ThreadLocalManager.clear();
        }
        afterExecute();
    }

    abstract public void beforeExecute();

    abstract public void afterExecute();

    abstract public void beforeGenerate();

    abstract public void afterGenerate();
}
