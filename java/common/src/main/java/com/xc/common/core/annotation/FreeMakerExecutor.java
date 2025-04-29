package com.xc.common.core.annotation;

import com.xc.common.core.generator.AbstractFreeMaker;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FreeMakerExecutor {
    String executorType() default "";
    Class<? extends AbstractFreeMaker>[] freeMakers() default {};
}
