package com.xc.common.core.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    private String packagePath;

    private String className;

    private String tableName;

    private String title;

    private List<? extends Field> properties;

    private List<String> required;

    private String version;
}
