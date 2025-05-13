package com.xc.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xc.common.core.domain.model.Table;
import com.xc.common.core.domain.model.Field;
import com.xc.common.enums.JsonSchema;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static volatile ObjectMapper objectMapper;

    private static final Object LOCK = new Object();

    public static ObjectMapper getObjectMapper(){
        if(ObjectUtils.isEmpty(objectMapper)){
            synchronized(LOCK){
                if(ObjectUtils.isEmpty(objectMapper)){
                    return new ObjectMapper();
                }
            }
        }
        return objectMapper;
    }

    // TODO
    public static HashMap<String, Object> jsonSchema2Table(String jsonSchema) throws JsonProcessingException {
        Table table = new Table();
        ObjectMapper mapper = getObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonSchema);

        String title = mapper.writeValueAsString(jsonNode.get(JsonSchema.TITLE.getField()));

        List<? extends Field> properties = new ArrayList<>();
        Map<String, Field> propertiesMap = mapper.readValue(jsonNode.get(JsonSchema.PROPERTIES.getField()).asText(), new TypeReference<Map<String, Field>>() {});
        for(Map.Entry<String, Field> property : propertiesMap.entrySet()){
            String key = property.getKey();
            Field field = property.getValue();
        }

        List<String> required = mapper.readValue(jsonNode.get(JsonSchema.REQUIRED.getField()).asText(), new TypeReference<List<String>>() {});

        table.setTitle(title);
        table.setRequired(required);
        table.setProperties(properties);
        return mapper.convertValue(table, new TypeReference<HashMap<String, Object>>() {});
    }
}
