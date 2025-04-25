package com.xc.common.core.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * JSON Schema规范
 * JSON Schema specification
 */
@Data
public abstract class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据类型
     * Data type
     */
    private String type;

}
