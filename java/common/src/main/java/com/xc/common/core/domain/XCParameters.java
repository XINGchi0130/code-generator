package com.xc.common.core.domain;

import com.xc.common.core.domain.model.Table;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashMap;

@Data
public class XCParameters {

    private HashMap<String, Table> templateTypeTable;

    @NotBlank(message = "executorType is blank")
    private String executorType;

    @NotBlank(message = "parentPath is blank")
    @Pattern(regexp = "^([a-zA-Z]:\\\\|/)([^/\\\\:*?\"<>|]+[/\\\\])+[^/\\\\:*?\"<>|]*$", message = "parentPath is illegal")
    private String parentPath;

    // TODO jsonSchema转table com.xc.common.utils.JsonUtils.jsonSchema2Table
    private String jsonSchema;

    // TODO DDL转table
    private String DDL;

    // TODO 数据库表结构转table
}
