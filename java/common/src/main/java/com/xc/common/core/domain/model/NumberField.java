package com.xc.common.core.domain.model;

import com.xc.common.enums.FieldType;
import lombok.Data;

@Data
public class NumberField extends Field{

    {
        // 默认数字类型
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
     */
    private String multipleOf;

    private String minimum;

    /**
     * 大于的值
     */
    private String exclusiveMinimum;

    private String maximum;

    /**
     * 小于的值
     */
    private String exclusiveMaximum;
}
