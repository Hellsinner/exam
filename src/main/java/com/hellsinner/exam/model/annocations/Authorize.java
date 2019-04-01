package com.hellsinner.exam.model.annocations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    /**
     * -1 需要认证
     * 0 学生身份认证
     * 1 老师身份认证
     * @return
     */
    int value() default -1;
}
