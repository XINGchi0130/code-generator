package com.xc.common.core.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    private String packagePath;

    private String className;

    private String tableName;

    private String title;

    private String author;

    private Date date;

    private List<? extends Field> properties;

    private List<String> required;

    private HashMap<String, Object> other;

    private String version;
}
