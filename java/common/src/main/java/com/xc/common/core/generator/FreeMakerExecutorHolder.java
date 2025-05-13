package com.xc.common.core.generator;

import com.xc.common.constant.FreeMakerConstants;
import com.xc.common.core.annotation.FreeMakerExecutor;
import com.xc.common.core.domain.XCParameters;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FreeMakerExecutorHolder {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public AbstractFreeMakerExecutor getCurrentFreeMakerExecutor(){
        XCParameters resources = ThreadLocalManager.getResources();
        String executorType = resources.getExecutorType();
        AbstractFreeMakerExecutor freeMakerExecutor = freeMakerExecutorHolder.get(executorType);
        if(ObjectUtils.isEmpty(freeMakerExecutor)){
            logger.warn("FreeMakerExecutorHolder.getCurrentFreeMakerExecutor: freeMakerExecutor is null");
        }
        return freeMakerExecutor;
    }

    public AbstractFreeMakerExecutor getFreeMakerExecutorByExecutorType(String executorType){
        AbstractFreeMakerExecutor freeMakerExecutor = freeMakerExecutorHolder.get(executorType);
        if(ObjectUtils.isEmpty(freeMakerExecutor)){
            logger.warn("FreeMakerExecutorHolder.getFreeMakerExecutorByExecutorType: freeMakerExecutor is null");
        }
        return freeMakerExecutor;
    }
}
