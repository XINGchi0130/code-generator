package com.xc.common.core.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class XCParameters {

    @NotBlank(message = "jsonSchema is blank")
    private String jsonSchema;

    @NotBlank(message = "executorType is blank")
    private String executorType;

    @NotBlank(message = "parentPath is blank")
    @Pattern(regexp = "^([a-zA-Z]:\\\\|/)([^/\\\\:*?\"<>|]+[/\\\\])+[^/\\\\:*?\"<>|]*$", message = "parentPath is illegal")
    private String parentPath;
}
