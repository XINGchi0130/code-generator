package com.xc.common.core.generator;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

/**
 * @author: XINGchi0130
 * @create: 2025-04-26 17:16
 **/
public abstract class FreeMakerExecutor {

    private String parentUrl;

    private String executorType;

    private List<Class<? extends FreeMaker>> freeMakerList;

    private void execute(){
        if(!StringUtils.hasText(this.parentUrl) && !StringUtils.hasText(this.executorType) && !CollectionUtils.isEmpty(freeMakerList)){
            File file = new File(this.parentUrl);
            for(Class<? extends FreeMaker> freeMaker : this.freeMakerList){

            }
        }
    }
}
