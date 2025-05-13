package com.xc.common.core.domain.model;

import com.xc.common.enums.FieldType;
import lombok.Data;

@Data
public class StringField extends Field{

    {
        this.setType(FieldType.STRING.getType());
    }


    private String minLength;

    private String maxLength;

    private String format;

    /**
     * 正则表达式
     */
    private String pattern;
}
