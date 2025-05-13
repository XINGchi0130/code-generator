package com.xc.common.core.domain.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fieldName;

    private String propertyName;

    private String fieldType;

    private String propertyType;

    private String description;

    private Object defaultValue;

    private Boolean isShow = Boolean.TRUE;

    private Boolean isRequired = Boolean.FALSE;

    /**
     * 数据类型
     */
    private String type;

}
