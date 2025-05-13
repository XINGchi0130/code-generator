package com.xc.common.core.generator;

import com.xc.common.core.annotation.FreeMaker;
import com.xc.common.core.annotation.FreeMakerExecutor;
import com.xc.common.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author: XINGchi0130
 * @create: 2025-04-26 17:16
 **/
public abstract class AbstractFreeMakerExecutor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private void execute() {
        String parentPath = ThreadLocalManager.getCurrentParentPath();
        String executorType = ThreadLocalManager.getCurrentExecutorType();
        Class<? extends AbstractFreeMaker>[] freeMakers = this.getClass().getAnnotation(FreeMakerExecutor.class).freeMakers();
        List<CompletableFuture<Void>> completableFutureList = new ArrayList<>();
        beforeExecute();
        for(Class<? extends AbstractFreeMaker> freeMaker : freeMakers){
            if(freeMaker.isAnnotationPresent(FreeMaker.class)){
                CompletableFuture<Void> task = CompletableFuture.runAsync(()->{
                    String outFileRelativePath =  parentPath.concat(freeMaker.getAnnotation(FreeMaker.class).outFileRelativePath());
                    String templateFilePath = freeMaker.getAnnotation(FreeMaker.class).templateFilePath();
                    String templateFileName = freeMaker.getAnnotation(FreeMaker.class).templateFileName();
                    boolean myExeCute = freeMaker.getAnnotation(FreeMaker.class).myExeCute();
                    try {
                        if(!myExeCute){
                            ReflectUtils.invokeMethod(freeMaker, "execute", new Class[]{String.class, String.class, String.class}, new Object[]{outFileRelativePath, templateFilePath, templateFileName});
                        }else{
                            ReflectUtils.invokeMethod(freeMaker, "myExeCute", new Class[]{String.class, String.class, String.class}, new Object[]{outFileRelativePath, templateFilePath, templateFileName});
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        logger.warn(executorType.concat(":").concat(e.toString()));
                    }
                }, threadPoolTaskExecutor);
                completableFutureList.add(task);
            }else{
                logger.warn(executorType.concat(":There is no FreeMaker annotation."));
            }
        }
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
        completableFuture.join();
        ThreadLocalManager.clear();
        afterExecute();
    }

    abstract public void beforeExecute();

    abstract public void afterExecute();

    abstract public void beforeGenerate();

    abstract public void afterGenerate();
}
