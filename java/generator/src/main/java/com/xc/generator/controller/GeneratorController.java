package com.xc.generator.controller;

import com.xc.common.core.annotation.Generator;
import com.xc.common.core.domain.XCResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class GeneratorController {

    @GetMapping("/generator")
    @Generator
    public XCResult generator(){
        return XCResult.ok();
    }

    @GetMapping("/getFile")
    @ApiOperation(value = "test")
    public XCResult<String> getFile() throws IOException {
        String code = FileUtils.readFileToString(new File("/home/zxc/IdeaProjects/code-generator/java/generator/src/main/java/com/xc/generator/controller/GeneratorController.java"), StandardCharsets.UTF_8);
        return XCResult.ok(code);
    }
}
