package com.xc.common.core.domain;

import lombok.Data;

import java.util.List;

@Data
public class GeneratorVo {

    private List<String> allExecutorList;

    private List<String> executorList;

    private List<String> allFreeMakerList;

    private List<String> freeMakerList;
}
