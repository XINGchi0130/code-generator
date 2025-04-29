package com.xc.common.core.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FreeMaker {
    String outFileRelativePath() default "";
    String templateFilePath() default "";
    String templateFileName() default "";
}
