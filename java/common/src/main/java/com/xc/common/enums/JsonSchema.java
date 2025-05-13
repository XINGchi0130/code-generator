package com.xc.common.enums;

public enum JsonSchema {

    TITLE("title", "标题"),
    PROPERTIES("properties", "字段"),
    REQUIRED("required", "必须属性");

    private final String field;
    private final String info;

    JsonSchema(String field, String info)
    {
        this.field = field;
        this.info = info;
    }

    public String getField()
    {
        return field;
    }

    public String getInfo()
    {
        return info;
    }

}
