package com.xc.common.enums;

public enum FieldType {

    STRING("string", "字符串类型"),
    INTEGER("integer", "整数类型"),
    NUMBER("number", "任何数字类型"),
    OBJECT("object", "对象类型"),
    ENUM("enum", "枚举类型");

    private final String type;
    private final String info;

    FieldType(String type, String info)
    {
        this.type = type;
        this.info = info;
    }

    public String getType()
    {
        return type;
    }

    public String getInfo()
    {
        return info;
    }
}
