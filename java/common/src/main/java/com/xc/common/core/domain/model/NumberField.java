package com.xc.common.core.domain.model;

import com.xc.common.enums.FieldType;
import lombok.Data;

/**
 * 数字字段规范
 * digital field specification
 */
@Data
public class NumberField extends Field{

    {
        // default number type
        this.setType(FieldType.NUMBER.getType());
    }

    NumberField(String type){
        if(!FieldType.INTEGER.getType().equals(type) || !FieldType.NUMBER.getType().equals(type)){
            this.setType(FieldType.NUMBER.getType());
        }else{
            this.setType(type);
        }
    }

    /**
     * 数字的倍数
     * multiples of numbers
     */
    private String multipleOf;

    private String minimum;

    /**
     * 大于的值
     * the value greater than
     */
    private String exclusiveMinimum;

    private String maximum;

    /**
     * 小于的值
     * the value less than
     */
    private String exclusiveMaximum;
}
