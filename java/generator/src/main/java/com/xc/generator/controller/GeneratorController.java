package com.xc.generator.controller;

import com.xc.common.core.domain.XCResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneratorController {

    @GetMapping("/generatorByDDL")
    public XCResult generatorByDDL(){
        return XCResult.ok();
    }
}
