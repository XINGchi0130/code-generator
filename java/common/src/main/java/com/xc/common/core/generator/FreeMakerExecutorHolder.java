package com.xc.common.core.generator;

import com.xc.common.core.annotation.FreeMakerExecutor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class FreeMakerExecutorHolder {

    @Getter
    private HashMap<String, ? extends AbstractFreeMakerExecutor> freeMakerExecutorHolder = new HashMap<>();

    @Autowired
    public FreeMakerExecutorHolder(List<? extends AbstractFreeMakerExecutor> freeMakerExecutorList){
        initExecutorHolder(freeMakerExecutorList);
    }

    private <T extends AbstractFreeMakerExecutor> void initExecutorHolder(List<T> freeMakerExecutorList) {
        HashMap<String, T> tempMap = new HashMap<>();
        for (T freeMakerExecutor : freeMakerExecutorList) {
            if (freeMakerExecutor.getClass().isAnnotationPresent(FreeMakerExecutor.class)) {
                tempMap.put(
                        freeMakerExecutor.getClass().getAnnotation(FreeMakerExecutor.class).executorType(),
                        freeMakerExecutor
                );
            }
        }
        this.freeMakerExecutorHolder = tempMap;
    }
}
