package com.xc.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xc.common.enums.FieldType;

import java.util.HashMap;
import java.util.Map;

public class DDLUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    static public HashMap<String,Object> DDL2HashMap(String ddl, String title, String description){
        Map<String,Object> JsonSchema = new HashMap<>();
        JsonSchema.put("title",title);
        JsonSchema.put("description",description);
        JsonSchema.put("type", FieldType.OBJECT.getType());
        return null;
    }

    static public String hashMap2JsonSchema(HashMap<String,Object> hashMap) throws JsonProcessingException {
        return objectMapper.writeValueAsString(hashMap);
    }
}
