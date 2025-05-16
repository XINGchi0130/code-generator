package com.xc.common.core.domain;

import com.xc.common.core.domain.model.Table;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class XCParameters {

    private Table table;

    @NotBlank(message = "executorType is blank")
    private String executorType;

    @NotBlank(message = "parentPath is blank")
    private String parentPath;

    // TODO jsonSchema转table com.xc.common.utils.JsonUtils.jsonSchema2Table
    private String jsonSchema;

    // TODO DDL转table
    private String DDL;

    // TODO 数据库表结构转table
}
