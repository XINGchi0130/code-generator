package com.xc.common.core.domain.model;

import com.xc.common.enums.FieldType;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ObjectField extends Field{

    {
        this.setType(FieldType.OBJECT.getType());
    }

    private HashMap<String, ? extends Field> properties;

    private List<String> required;
}
